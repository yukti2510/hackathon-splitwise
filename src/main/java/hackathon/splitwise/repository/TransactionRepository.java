package hackathon.splitwise.repository;

import hackathon.splitwise.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findByGroupId(Long groupId);

    @Query("SELECT t FROM TransactionEntity t WHERE t.groupId = ?1 AND t.name LIKE %?2%")
    List<TransactionEntity> findByGroupIdAndNameContaining(Long groupId, String name);
}
