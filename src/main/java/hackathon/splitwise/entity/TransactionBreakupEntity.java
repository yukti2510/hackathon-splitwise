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
@Table(name = "transaction_breakup")
public class TransactionBreakupEntity extends AbstractJpaEntity {

    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    @Column(name = "payer_phone", columnDefinition = "VARCHAR(256)", nullable = false)
    private String payerPhone;

    @Column(name = "ower_phone", columnDefinition = "VARCHAR(256)", nullable = false)
    private String owerPhone;

    @Column(name = "amount", columnDefinition = "DOUBLE", nullable = false)
    private Double amount = 0.0;

    public TransactionBreakupEntity(TransactionBreakupEntity transactionBreakupEntity) {
        this.transactionId = transactionBreakupEntity.getTransactionId();
        this.payerPhone = transactionBreakupEntity.getPayerPhone();
        this.owerPhone = transactionBreakupEntity.getOwerPhone();
        this.amount = transactionBreakupEntity.getAmount();
    }

}
