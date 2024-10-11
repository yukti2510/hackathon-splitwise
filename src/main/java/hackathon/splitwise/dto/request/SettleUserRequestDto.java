package hackathon.splitwise.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gauravlikhar
 * @project hackathon-splitwise
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettleUserRequestDto {
    private String payerPhone;
    private String owerPhone;
    private Double amount;
}
