package hackathon.splitwise.repository;

import hackathon.splitwise.entity.TransactionBreakupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface TransactionBreakupRepository extends JpaRepository<TransactionBreakupEntity, Long> {

    List<TransactionBreakupEntity> findAllByTransactionIdIn(List<Long> transactionIds);
}
