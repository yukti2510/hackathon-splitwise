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
@Table(name = "transactions")
public class TransactionEntity extends AbstractJpaEntity {

    @Serial
    private static final long serialVersionUID = 9876543210123489L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(256)", nullable = false)
    private String name;

    @Column(name = "type", columnDefinition = "VARCHAR(256)", nullable = false)
    private String type;

    @Column(name = "jupiter_txn_id", columnDefinition = "VARCHAR(256)")
    private String jupiterTxnId;

    @Column(name = "split_type", columnDefinition = "VARCHAR(256)", nullable = false)
    private String splitType;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "amount", columnDefinition = "DOUBLE", nullable = false)
    private Double amount = 0.0;

    public TransactionEntity(TransactionEntity transactionEntity) {
        this.id = transactionEntity.getId();
        this.name = transactionEntity.getName();
        this.type = transactionEntity.getType();
        this.splitType = transactionEntity.getSplitType();
        this.groupId = transactionEntity.getGroupId();
        this.amount = transactionEntity.getAmount();
        this.jupiterTxnId = transactionEntity.getJupiterTxnId();
    }

}
