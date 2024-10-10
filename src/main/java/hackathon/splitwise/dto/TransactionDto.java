package hackathon.splitwise.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class TransactionDto {
    private Long id;
    private String name;
    private String type;
    private String jupiterTxnId;
    private String splitType;
    private Long groupId;
    private Double amount;
    private Date createdAt;
}
