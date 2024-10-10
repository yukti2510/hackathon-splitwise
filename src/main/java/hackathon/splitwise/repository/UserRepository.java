package hackathon.splitwise.repository;

import hackathon.splitwise.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gauravlikhar
 * @project hackathon-splitwise
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.phone = :phone")
    UserEntity findByPhone(String phone);

    List<UserEntity> findAllByPhoneIn(List<String> membersPhoneList);

    List<UserEntity> findByPhoneIn(List<String> phoneNumbers);
}
