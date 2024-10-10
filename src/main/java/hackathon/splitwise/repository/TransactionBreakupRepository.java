package hackathon.splitwise.repository;

import hackathon.splitwise.entity.TransactionBreakupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionBreakupRepository extends JpaRepository<TransactionBreakupEntity, Long> {

}
