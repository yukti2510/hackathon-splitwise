package hackathon.splitwise.service;

import hackathon.splitwise.dto.TransactionDto;
import hackathon.splitwise.dto.request.Ower;
import hackathon.splitwise.dto.request.TransactionRequestDto;
import hackathon.splitwise.dto.response.CreateGroupResponseDto;
import hackathon.splitwise.entity.TransactionEntity;
import hackathon.splitwise.entity.UserBalanceEntity;
import hackathon.splitwise.repository.TransactionBreakupRepository;
import hackathon.splitwise.repository.TransactionRepository;
import hackathon.splitwise.repository.UserBalanceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static hackathon.splitwise.mappers.TransactionMapper.mapToTransactionBreakupEntity;
import static hackathon.splitwise.mappers.TransactionMapper.mapToTransactionDto;
import static hackathon.splitwise.mappers.TransactionMapper.mapToTransactionEntity;

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

    public TransactionService(TransactionRepository transactionRepository, TransactionBreakupRepository transactionBreakupRepository, UserBalanceRepository userBalanceRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionBreakupRepository = transactionBreakupRepository;
        this.userBalanceRepository = userBalanceRepository;
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
}
