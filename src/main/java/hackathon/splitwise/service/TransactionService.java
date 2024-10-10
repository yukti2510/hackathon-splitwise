package hackathon.splitwise.service;

import hackathon.splitwise.dto.TransactionDto;
import hackathon.splitwise.dto.request.Ower;
import hackathon.splitwise.dto.request.TransactionRequestDto;
import hackathon.splitwise.dto.response.CreateGroupResponseDto;
import hackathon.splitwise.entity.TransactionEntity;
import hackathon.splitwise.repository.TransactionBreakupRepository;
import hackathon.splitwise.repository.TransactionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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

    public TransactionService(TransactionRepository transactionRepository, TransactionBreakupRepository transactionBreakupRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionBreakupRepository = transactionBreakupRepository;
    }

    public TransactionDto createTransaction(TransactionRequestDto transactionRequestDto) {
        TransactionEntity transactionEntity = transactionRepository.saveAndFlush(mapToTransactionEntity(transactionRequestDto));
        log.info("Saved transaction with name: {} and id: {}", transactionRequestDto.getName(), transactionEntity.getId());
        String payerPhone = transactionRequestDto.getPayersList().get(0).getPhone();
        for (Ower ower: transactionRequestDto.getOweersList()) {
            transactionBreakupRepository.saveAndFlush(mapToTransactionBreakupEntity(transactionEntity.getId(), payerPhone, ower));
        }
        return mapToTransactionDto(transactionEntity);
    }
}
