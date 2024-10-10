package hackathon.splitwise.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_group_mapping")
public class UserGroupMappingEntity extends AbstractJpaEntity {

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "phone", columnDefinition = "VARCHAR(256)", nullable = false)
    private String phone;

    @Column(name = "is_admin")
    private Boolean isAdmin = false;

    @Column(name = "amount_paid", columnDefinition = "DOUBLE")
    private Double amountPaid = 0.0;

    public UserGroupMappingEntity(UserGroupMappingEntity userGroupMappingEntity) {
        this.groupId = userGroupMappingEntity.getGroupId();
        this.phone = userGroupMappingEntity.getPhone();
        this.isAdmin = userGroupMappingEntity.getIsAdmin();
        this.amountPaid = userGroupMappingEntity.getAmountPaid();
    }

}
