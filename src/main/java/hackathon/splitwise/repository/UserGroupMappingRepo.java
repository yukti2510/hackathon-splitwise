package hackathon.splitwise.repository;

import hackathon.splitwise.entity.UserGroupMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupMappingRepo extends JpaRepository<UserGroupMappingEntity, Long> {

}
