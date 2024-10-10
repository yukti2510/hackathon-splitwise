package hackathon.splitwise.dto.request;

import hackathon.splitwise.enums.SplitType;
import hackathon.splitwise.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDto {
    private String name;
    private String jupiterTxnId;
    private SplitType splitType;
    private List<Payer> payersList;
    private List<Ower> oweersList;
    private Long groupId;
    private TransactionType type;
    private Double amount;
}