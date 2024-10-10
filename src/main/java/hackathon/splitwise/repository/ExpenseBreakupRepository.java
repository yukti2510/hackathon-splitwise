package hackathon.splitwise.repository;

import hackathon.splitwise.entity.ExpenseBreakupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseBreakupRepository extends JpaRepository<ExpenseBreakupEntity, Long> {

}
