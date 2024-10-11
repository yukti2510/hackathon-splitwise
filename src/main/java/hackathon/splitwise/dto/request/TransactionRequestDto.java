package hackathon.splitwise.dto.request;

import hackathon.splitwise.enums.SplitType;
import hackathon.splitwise.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequestDto {
    private String name;
    private String jupiterTxnId;
    private SplitType splitType;
    private List<Payer> payersList;
    private List<Ower> owersList;
    private Long groupId;
    private TransactionType type;
    private Double amount;
}