package hackathon.splitwise.repository;

import hackathon.splitwise.entity.UserGroupMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupMappingRepository extends JpaRepository<UserGroupMappingEntity, Long> {
    List<UserGroupMappingEntity> findAllByPhone(String phone);

    UserGroupMappingEntity findByGroupIdAndPhone(Long id, String phone);

    List<UserGroupMappingEntity> findAllByGroupId(Long groupId);

    Long countByGroupId(Long groupId);
}
