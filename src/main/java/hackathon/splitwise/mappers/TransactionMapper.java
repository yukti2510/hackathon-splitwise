package hackathon.splitwise.mappers;

import hackathon.splitwise.dto.TransactionDto;
import hackathon.splitwise.dto.request.Ower;
import hackathon.splitwise.dto.request.TransactionRequestDto;
import hackathon.splitwise.entity.TransactionBreakupEntity;
import hackathon.splitwise.entity.TransactionEntity;

public class TransactionMapper {

    public static TransactionEntity mapToTransactionEntity(TransactionRequestDto transactionRequestDto) {
        TransactionEntity transactionEntity =  new TransactionEntity();
        transactionEntity.setName(transactionRequestDto.getName());
        transactionEntity.setJupiterTxnId(transactionRequestDto.getJupiterTxnId());
        transactionEntity.setGroupId(transactionRequestDto.getGroupId());
        transactionEntity.setSplitType(transactionRequestDto.getSplitType().name());
        transactionEntity.setType(transactionRequestDto.getType().name());
        transactionEntity.setAmount(transactionRequestDto.getAmount());
        return transactionEntity;
    }

    public static TransactionBreakupEntity mapToTransactionBreakupEntity(Long transactionId, String payerPhone, Ower ower) {
        TransactionBreakupEntity transactionBreakupEntity =  new TransactionBreakupEntity();
        transactionBreakupEntity.setTransactionId(transactionId);
        transactionBreakupEntity.setPayerPhone(payerPhone);
        transactionBreakupEntity.setOwerPhone(ower.getPhone());
        transactionBreakupEntity.setAmount(ower.getAmountOwed());
        return transactionBreakupEntity;
    }

    public static TransactionDto mapToTransactionDto(TransactionEntity transactionEntity) {
        return TransactionDto.builder()
                .id(transactionEntity.getId())
                .name(transactionEntity.getName())
                .type(transactionEntity.getType())
                .jupiterTxnId(transactionEntity.getJupiterTxnId())
                .splitType(transactionEntity.getSplitType())
                .amount(transactionEntity.getAmount())
                .groupId(transactionEntity.getGroupId())
                .createdAt(transactionEntity.getCreatedAt())
                .build();
    }
}
