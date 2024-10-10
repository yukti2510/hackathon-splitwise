package hackathon.splitwise.repository;

import hackathon.splitwise.entity.GroupDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupDetailsEntity, Long> {
}
