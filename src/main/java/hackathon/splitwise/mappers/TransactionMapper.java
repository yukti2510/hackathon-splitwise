package hackathon.splitwise.mappers;

import hackathon.splitwise.dto.TransactionDetailResponseDto;
import hackathon.splitwise.dto.TransactionDto;
import hackathon.splitwise.dto.request.Ower;
import hackathon.splitwise.dto.request.TransactionRequestDto;
import hackathon.splitwise.entity.TransactionBreakupEntity;
import hackathon.splitwise.entity.TransactionEntity;
import hackathon.splitwise.entity.UserEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static List<TransactionDetailResponseDto> mapToTransactionDetailDto(
            List<TransactionEntity> transactionEntityList,
            List<TransactionBreakupEntity> transactionBreakupEntityList,
            List<UserEntity> userEntities, String phone) {
        Map<String, String> phoneToUserNameMap = userEntities.stream()
                .collect(Collectors.toMap(UserEntity::getPhone, UserEntity::getName));
        Map<Long, List<TransactionBreakupEntity>> transactionBreakupsMap = transactionBreakupEntityList.stream()
                .collect(Collectors.groupingBy(TransactionBreakupEntity::getTransactionId));
        return transactionEntityList.stream().sorted(Comparator.comparing(TransactionEntity::getCreatedAt).reversed()).map(transaction -> {
            List<TransactionBreakupEntity> breakups = transactionBreakupsMap.getOrDefault(transaction.getId(), List.of());
            String payerPhone = breakups.isEmpty() ? null : breakups.get(0).getPayerPhone();
            String payerName = phoneToUserNameMap.getOrDefault(payerPhone, "Unknown");
            return TransactionDetailResponseDto.builder()
                    .id(transaction.getId())
                    .name(transaction.getName())
                    .type(transaction.getType())
                    .createdAt(transaction.getCreatedAt())
                    .splitType(transaction.getSplitType())
                    .totalTransactionAmount(transaction.getAmount())
                    .groupId(transaction.getGroupId())
                    .payerName(payerName)
                    .payerPhone(payerPhone)
                    .amountPaid(breakups.stream()
                        .mapToDouble(it -> it.getPayerPhone().equals(phone) ? it.getAmount()
                        : it.getOwerPhone().equals(phone) ? -it.getAmount()
                        : 0)
                        .sum()
                    ).build();
        }).collect(Collectors.toList());
    }
}
