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
@Table(name = "user_balance")
public class UserBalanceEntity {

    @Id
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "user_1", columnDefinition = "VARCHAR(100)", nullable = false)
    private String user1;

    @Column(name = "user_2", columnDefinition = "VARCHAR(100)", nullable = false)
    private String user2;

    @Column(name = "amount_paid", columnDefinition = "DOUBLE", nullable = false)
    private Double amountPaid;
}

