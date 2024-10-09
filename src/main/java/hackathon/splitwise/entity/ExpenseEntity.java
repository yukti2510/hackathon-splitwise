package hackathon.splitwise.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serial;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expense")
public class ExpenseEntity extends AbstractJpaEntity {

    @Serial
    private static final long serialVersionUID = 9876543210123489L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(256)", nullable = false)
    private String name;

    @Column(name = "type", columnDefinition = "VARCHAR(256)", nullable = false)
    private String type;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "amount_paid", columnDefinition = "DOUBLE", nullable = false)
    private Double amountPaid;

    public ExpenseEntity(ExpenseEntity expenseEntity) {
        this.id = expenseEntity.getId();
        this.name = expenseEntity.getName();
        this.type = expenseEntity.getType();
        this.groupId = expenseEntity.getGroupId();
        this.amountPaid = expenseEntity.getAmountPaid();
    }

}
