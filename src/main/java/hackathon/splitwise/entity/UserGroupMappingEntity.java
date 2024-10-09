package hackathon.splitwise.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_group_mapping")
public class UserGroupMappingEntity {

    @Id
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "user_id", columnDefinition = "VARCHAR(256)", nullable = false)
    private String userId;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Column(name = "amount_paid", columnDefinition = "DOUBLE", nullable = false)
    private Double amountPaid;

    public UserGroupMappingEntity(UserGroupMappingEntity userGroupMappingEntity) {
        this.groupId = userGroupMappingEntity.getGroupId();
        this.userId = userGroupMappingEntity.getUserId();
        this.isAdmin = userGroupMappingEntity.getIsAdmin();
        this.amountPaid = userGroupMappingEntity.getAmountPaid();
    }

}
