package hackathon.splitwise.service;

import hackathon.splitwise.dto.TransactionDetailResponseDto;
import hackathon.splitwise.dto.TransactionDto;
import hackathon.splitwise.dto.UserDto;
import hackathon.splitwise.dto.request.Ower;
import hackathon.splitwise.dto.request.TransactionRequestDto;
import hackathon.splitwise.dto.response.AmountInvolvedResponseDto;
import hackathon.splitwise.entity.TransactionBreakupEntity;
import hackathon.splitwise.entity.TransactionEntity;
import hackathon.splitwise.entity.UserBalanceEntity;
import hackathon.splitwise.entity.UserEntity;
import hackathon.splitwise.repository.TransactionBreakupRepository;
import hackathon.splitwise.repository.TransactionRepository;
import hackathon.splitwise.repository.UserBalanceRepository;
import hackathon.splitwise.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static hackathon.splitwise.mappers.TransactionMapper.*;
import static hackathon.splitwise.mappers.UserMapper.mapUserEntityToUserDto;

/**
 * @author gauravlikhar
 * @project hackathon-splitwise
 */
@Service
@Log4j2
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionBreakupRepository transactionBreakupRepository;

    private final UserBalanceRepository userBalanceRepository;

    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, TransactionBreakupRepository transactionBreakupRepository, UserBalanceRepository userBalanceRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionBreakupRepository = transactionBreakupRepository;
        this.userBalanceRepository = userBalanceRepository;
        this.userRepository = userRepository;
    }

    public TransactionDto createTransaction(TransactionRequestDto transactionRequestDto) {
        TransactionEntity transactionEntity = transactionRepository.saveAndFlush(mapToTransactionEntity(transactionRequestDto));
        log.info("Saved transaction with name: {} and id: {}", transactionRequestDto.getName(), transactionEntity.getId());
        String payerPhone = transactionRequestDto.getPayersList().get(0).getPhone();
        for (Ower ower : transactionRequestDto.getOweersList()) {
            transactionBreakupRepository.saveAndFlush(mapToTransactionBreakupEntity(transactionEntity.getId(), payerPhone, ower));

            UserBalanceEntity user1ToUser2UserBalance = userBalanceRepository.findByGroupIdAndPayerPhoneAndOwerPhone(transactionRequestDto.getGroupId(), payerPhone, ower.getPhone()).get(0);
            UserBalanceEntity user2ToUser1UserBalance = userBalanceRepository.findByGroupIdAndPayerPhoneAndOwerPhone(transactionRequestDto.getGroupId(), ower.getPhone(), payerPhone).get(0);
            if (user1ToUser2UserBalance != null) {
                double amountPaid = Optional.ofNullable(user1ToUser2UserBalance.getAmountPaid()).orElse(0.0);
                user1ToUser2UserBalance.setAmountPaid(amountPaid + ower.getAmountOwed());
                userBalanceRepository.saveAndFlush(user1ToUser2UserBalance);
            } else if (user2ToUser1UserBalance != null) {
                double amountPaid = Optional.ofNullable(user2ToUser1UserBalance.getAmountPaid()).orElse(0.0);
                user2ToUser1UserBalance.setAmountPaid(amountPaid - ower.getAmountOwed());
                userBalanceRepository.saveAndFlush(user2ToUser1UserBalance);
            } else {
                UserBalanceEntity userBalanceEntity = new UserBalanceEntity();
                userBalanceEntity.setGroupId(transactionRequestDto.getGroupId());
                userBalanceEntity.setPayerPhone(payerPhone);
                userBalanceEntity.setOwerPhone(ower.getPhone());
                userBalanceEntity.setAmountPaid(ower.getAmountOwed());
                userBalanceRepository.saveAndFlush(userBalanceEntity);
            }
        }
        return mapToTransactionDto(transactionEntity);
    }

    public List<TransactionDetailResponseDto> getTransactionsList(Long groupId, String phone) {
        List<TransactionEntity> transactionEntityList = transactionRepository.findByGroupId(groupId);
        List<TransactionBreakupEntity> transactionBreakupEntityList = transactionBreakupRepository.findAllByTransactionIdIn(transactionEntityList.stream().map(TransactionEntity::getId).collect(Collectors.toList()));
        List<String> phoneNumbers = transactionBreakupEntityList.stream()
                .map(TransactionBreakupEntity::getPayerPhone)
                .collect(Collectors.toList());
        List<UserEntity> userEntities = userRepository.findByPhoneIn(phoneNumbers);
        return mapToTransactionDetailDto(transactionEntityList, transactionBreakupEntityList, userEntities, phone);
    }

    public List<AmountInvolvedResponseDto> fetchNetAmountInvolved(String phone, String user2Phone, Long groupId) {
        Map<String, Double> phoneToNetBalanceMap = new HashMap<>();
        List<AmountInvolvedResponseDto> amountInvolvedList = new ArrayList<>();
        if (groupId != null && user2Phone != null) {
            List<UserBalanceEntity> user1Balances = userBalanceRepository.findByGroupIdAndPayerPhoneAndOwerPhone(groupId, phone, user2Phone);
            List<UserBalanceEntity> user2Balances = userBalanceRepository.findByGroupIdAndPayerPhoneAndOwerPhone(groupId, user2Phone, phone);
            user1Balances = user1Balances.stream().filter(it -> !Objects.equals(it.getPayerPhone(), it.getOwerPhone())).toList();
            user2Balances = user2Balances.stream().filter(it -> !Objects.equals(it.getPayerPhone(), it.getOwerPhone())).toList();
            for (UserBalanceEntity balance : user1Balances) {
                Double amountPaid = balance.getAmountPaid();
                phoneToNetBalanceMap.merge(user2Phone, amountPaid, Double::sum);
            }
            for (UserBalanceEntity balance : user2Balances) {
                Double amountPaid = balance.getAmountPaid();
                phoneToNetBalanceMap.merge(user2Phone, -amountPaid, Double::sum);
            }
            Double netAmount = phoneToNetBalanceMap.getOrDefault(user2Phone, 0.0);
            UserDto user1Dto = mapUserEntityToUserDto(userRepository.findByPhone(phone));
            UserDto user2Dto = mapUserEntityToUserDto(userRepository.findByPhone(user2Phone));
            return Collections.singletonList(AmountInvolvedResponseDto.builder()
                    .user1Dto(user1Dto)
                    .user2Dto(user2Dto)
                    .amountInvolved(netAmount)
                    .build());
        } else if (groupId !=null){
            List<UserBalanceEntity> user1Balances = userBalanceRepository.findByPayerPhoneAndGroupId(phone, groupId);
            List<UserBalanceEntity> user2Balances = userBalanceRepository.findByOwerPhoneAndGroupId(phone, groupId);
            user1Balances = user1Balances.stream().filter(it -> !Objects.equals(it.getPayerPhone(), it.getOwerPhone())).toList();
            user2Balances = user2Balances.stream().filter(it -> !Objects.equals(it.getPayerPhone(), it.getOwerPhone())).toList();
            for (UserBalanceEntity balance : user1Balances) {
                String otherPhone = balance.getOwerPhone();
                Double amountPaid = balance.getAmountPaid();
                phoneToNetBalanceMap.merge(otherPhone, amountPaid, Double::sum);
            }
            for (UserBalanceEntity balance : user2Balances) {
                String otherPhone = balance.getPayerPhone();
                Double amountOwed = balance.getAmountPaid();
                phoneToNetBalanceMap.merge(otherPhone, -amountOwed, Double::sum);
            }
            for (Map.Entry<String, Double> entry : phoneToNetBalanceMap.entrySet()) {
                String otherPhone = entry.getKey();
                Double netAmount = entry.getValue();
                UserDto user1Dto = mapUserEntityToUserDto(userRepository.findByPhone(phone));
                UserDto user2Dto = mapUserEntityToUserDto(userRepository.findByPhone((otherPhone)));
                amountInvolvedList.add(AmountInvolvedResponseDto.builder()
                        .user1Dto(user1Dto)
                        .user2Dto(user2Dto)
                        .amountInvolved(netAmount)
                        .build());
            }
        } else if (user2Phone !=null) {
            List<UserBalanceEntity> user1Balances = userBalanceRepository.findByPayerPhoneAndOwerPhone(phone, user2Phone);
            List<UserBalanceEntity> user2Balances = userBalanceRepository.findByPayerPhoneAndOwerPhone(user2Phone, phone);
            user1Balances = user1Balances.stream().filter(it -> !Objects.equals(it.getPayerPhone(), it.getOwerPhone())).toList();
            user2Balances = user2Balances.stream().filter(it -> !Objects.equals(it.getPayerPhone(), it.getOwerPhone())).toList();
            for (UserBalanceEntity balance : user1Balances) {
                Double amount = balance.getAmountPaid();
                phoneToNetBalanceMap.merge(user2Phone, amount, Double::sum);
            }
            for (UserBalanceEntity balance : user2Balances) {
                Double amountOwed = balance.getAmountPaid();
                phoneToNetBalanceMap.merge(user2Phone, -amountOwed, Double::sum);
            }
            for (Map.Entry<String, Double> entry : phoneToNetBalanceMap.entrySet()) {
                String otherPhone = entry.getKey();
                Double netAmount = entry.getValue();
                UserDto user1Dto = mapUserEntityToUserDto(userRepository.findByPhone(phone));
                UserDto user2Dto = mapUserEntityToUserDto(userRepository.findByPhone((otherPhone)));
                amountInvolvedList.add(AmountInvolvedResponseDto.builder()
                        .user1Dto(user1Dto)
                        .user2Dto(user2Dto)
                        .amountInvolved(netAmount)
                        .build());
            }
        } else {
            List<UserBalanceEntity> user1Balances = userBalanceRepository.findByPayerPhone(phone);
            user1Balances = user1Balances.stream().filter(it -> !Objects.equals(it.getPayerPhone(), it.getOwerPhone())).toList();
            List<UserBalanceEntity> user2Balances = userBalanceRepository.findByOwerPhone(phone);
            user2Balances = user2Balances.stream().filter(it -> !Objects.equals(it.getPayerPhone(), it.getOwerPhone())).toList();
            for (UserBalanceEntity balance : user1Balances) {
                String otherPhone = balance.getOwerPhone();
                Double amount = balance.getAmountPaid();
                phoneToNetBalanceMap.merge(otherPhone, amount, Double::sum);
            }
            for (UserBalanceEntity balance : user2Balances) {
                String otherPhone = balance.getPayerPhone();
                Double amountOwed = balance.getAmountPaid();
                phoneToNetBalanceMap.merge(otherPhone, -amountOwed, Double::sum);
            }
            for (Map.Entry<String, Double> entry : phoneToNetBalanceMap.entrySet()) {
                String otherPhone = entry.getKey();
                Double netAmount = entry.getValue();
                UserDto user1Dto = mapUserEntityToUserDto(userRepository.findByPhone(phone));
                UserDto user2Dto = mapUserEntityToUserDto(userRepository.findByPhone((otherPhone)));
                amountInvolvedList.add(AmountInvolvedResponseDto.builder()
                        .user1Dto(user1Dto)
                        .user2Dto(user2Dto)
                        .amountInvolved(netAmount)
                        .build());
            }
        }
        return amountInvolvedList;
    }

}
