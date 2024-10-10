package hackathon.splitwise.repository;

import hackathon.splitwise.entity.GroupEntity;
import hackathon.splitwise.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
//    List<GroupEntity> findAllByPhone(String phone);
}
