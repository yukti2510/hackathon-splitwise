package hackathon.splitwise.repository;

import hackathon.splitwise.entity.GroupDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupDetailsEntity, Long> {

    @Query("select g from GroupDetailsEntity g where g.id in ?1 and g.name like %?2%")
    List<GroupDetailsEntity> findAllByIdAndNameContaining(List<Long> groupIds, String name);
}
