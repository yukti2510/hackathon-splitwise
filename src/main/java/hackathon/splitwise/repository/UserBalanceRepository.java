package hackathon.splitwise.repository;

import hackathon.splitwise.entity.UserBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalanceEntity, Long> {

    List<UserBalanceEntity> findByGroupIdAndPayerPhoneAndOwerPhone(Long groupId, String payerPhone, String ower);

    List<UserBalanceEntity> findByPayerPhoneAndGroupId(String phone, Long groupId);

    List<UserBalanceEntity> findByOwerPhoneAndGroupId(String phone, Long groupId);

    List<UserBalanceEntity> findByPayerPhoneAndOwerPhone(String phone, String user2Phone);

    List<UserBalanceEntity> findByPayerPhone(String phone);

    List<UserBalanceEntity> findByOwerPhone(String phone);
}
