package hackathon.splitwise.repository;

import hackathon.splitwise.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author gauravlikhar
 * @project hackathon-splitwise
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.phone = :phone")
    UserEntity findByPhone(String phone);
}
