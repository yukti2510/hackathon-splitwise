package hackathon.splitwise.repository;

import hackathon.splitwise.entity.ExpenseBreakupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseBreakupRepo extends JpaRepository<ExpenseBreakupEntity, Long> {

}
