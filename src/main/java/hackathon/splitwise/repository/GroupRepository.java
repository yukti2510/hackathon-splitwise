package hackathon.splitwise.repository;

import hackathon.splitwise.entity.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<UserGroupEntity, Long> {
//    List<GroupEntity> findAllByPhone(String phone);
}
