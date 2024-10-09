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
@Table(name = "expense_breakup")
public class ExpenseBreakupEntity {

    @Id
    @Column(name = "expense_id", nullable = false)
    private Long expenseId;

    @Column(name = "payer_id", columnDefinition = "VARCHAR(256)", nullable = false)
    private String payerId;

    @Column(name = "ower_id", columnDefinition = "VARCHAR(256)", nullable = false)
    private String owerId;

    @Column(name = "amount", columnDefinition = "DOUBLE", nullable = false)
    private Double amount;
}
