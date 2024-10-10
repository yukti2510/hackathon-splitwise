package hackathon.splitwise.service;

import hackathon.splitwise.dto.TransactionDetailResponseDto;
import hackathon.splitwise.dto.TransactionDto;
import hackathon.splitwise.dto.request.Ower;
import hackathon.splitwise.dto.request.TransactionRequestDto;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hackathon.splitwise.mappers.TransactionMapper.*;

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
        for (Ower ower: transactionRequestDto.getOweersList()) {
            transactionBreakupRepository.saveAndFlush(mapToTransactionBreakupEntity(transactionEntity.getId(), payerPhone, ower));

            UserBalanceEntity user1ToUser2UserBalance = userBalanceRepository.findByGroupIdAndPayerPhoneAndOwerPhone(transactionRequestDto.getGroupId(), payerPhone, ower.getPhone());
            UserBalanceEntity user2ToUser1UserBalance = userBalanceRepository.findByGroupIdAndPayerPhoneAndOwerPhone(transactionRequestDto.getGroupId(), ower.getPhone(), payerPhone);
            if (user1ToUser2UserBalance != null) {
                double amountPaid = Optional.ofNullable(user1ToUser2UserBalance.getAmountPaid()).orElse(0.0);
                user1ToUser2UserBalance.setAmountPaid(amountPaid + ower.getAmountOwed());
                userBalanceRepository.saveAndFlush(user1ToUser2UserBalance);
            } else if (user2ToUser1UserBalance != null) {
                double amountPaid = Optional.ofNullable(user2ToUser1UserBalance.getAmountPaid()).orElse(0.0);
                user2ToUser1UserBalance.setAmountPaid(amountPaid - ower.getAmountOwed());
                userBalanceRepository.saveAndFlush(user2ToUser1UserBalance);
            }
            else {
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

    public List<TransactionDetailResponseDto> searchTransactions(String name, Long groupId) {
        List<TransactionEntity> transactionEntityList = transactionRepository.findByGroupIdAndNameContaining(groupId, name);
        List<TransactionBreakupEntity> transactionBreakupEntityList = transactionBreakupRepository.findAllByTransactionIdIn(transactionEntityList.stream().map(TransactionEntity::getId).collect(Collectors.toList()));
        List<String> phoneNumbers = transactionBreakupEntityList.stream()
                .map(TransactionBreakupEntity::getPayerPhone)
                .collect(Collectors.toList());
        List<UserEntity> userEntities = userRepository.findByPhoneIn(phoneNumbers);
        return mapToTransactionDetailDto(transactionEntityList, transactionBreakupEntityList, userEntities, null);
    }
}
