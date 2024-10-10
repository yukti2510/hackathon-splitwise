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
@Table(name = "expense_breakup")
public class ExpenseBreakupEntity extends AbstractJpaEntity {

    @Column(name = "expense_id", nullable = false)
    private Long expenseId;

    @Column(name = "payer_id", columnDefinition = "VARCHAR(256)", nullable = false)
    private String payerId;

    @Column(name = "ower_id", columnDefinition = "VARCHAR(256)", nullable = false)
    private String owerId;

    @Column(name = "amount", columnDefinition = "DOUBLE", nullable = false)
    private Double amount = 0.0;

    public ExpenseBreakupEntity(ExpenseBreakupEntity expenseBreakupEntity) {
        this.expenseId = expenseBreakupEntity.getExpenseId();
        this.payerId = expenseBreakupEntity.getPayerId();
        this.owerId = expenseBreakupEntity.getOwerId();
        this.amount = expenseBreakupEntity.getAmount();
    }

}
