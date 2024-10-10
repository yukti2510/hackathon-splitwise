package hackathon.splitwise.repository;

import hackathon.splitwise.entity.UserBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalanceEntity, Long> {

}
