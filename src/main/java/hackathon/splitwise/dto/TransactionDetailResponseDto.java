package hackathon.splitwise.dto;

import lombok.*;

import java.util.Date;

/**
 * @author gauravlikhar
 * @project hackathon-splitwise
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class TransactionDetailResponseDto {
    private Long id;
    private String name;
    private String type;
    private String splitType;
    private Long groupId;
    private Date createdAt;
    private String payerName;
    private String payerPhone;
    private Double totalTransactionAmount;
    private Double amountPaid;
}
