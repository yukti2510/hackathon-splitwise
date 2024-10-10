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
@Table(name = "user_balance")
public class UserBalanceEntity extends AbstractJpaEntity {

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "user_1", columnDefinition = "VARCHAR(100)", nullable = false)
    private String user1;

    @Column(name = "user_2", columnDefinition = "VARCHAR(100)", nullable = false)
    private String user2;

    @Column(name = "amount_paid", columnDefinition = "DOUBLE", nullable = false)
    private Double amountPaid;

    public UserBalanceEntity(UserBalanceEntity userBalanceEntity) {
        this.groupId = userBalanceEntity.getGroupId();
        this.user1 = userBalanceEntity.getUser1();
        this.user2 = userBalanceEntity.getUser2();
        this.amountPaid = userBalanceEntity.getAmountPaid();
    }

}

