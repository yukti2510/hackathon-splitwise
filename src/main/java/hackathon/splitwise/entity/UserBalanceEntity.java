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

    @Column(name = "payer_phone", columnDefinition = "VARCHAR(256)", nullable = false)
    private String payerPhone;

    @Column(name = "ower_phone", columnDefinition = "VARCHAR(256)", nullable = false)
    private String owerPhone;

    @Column(name = "amount_paid", columnDefinition = "DOUBLE", nullable = false)
    private Double amountPaid = 0.0;

    public UserBalanceEntity(UserBalanceEntity userBalanceEntity) {
        this.groupId = userBalanceEntity.getGroupId();
        this.payerPhone = userBalanceEntity.getPayerPhone();
        this.owerPhone = userBalanceEntity.getOwerPhone();
        this.amountPaid = userBalanceEntity.getAmountPaid();
    }

}

