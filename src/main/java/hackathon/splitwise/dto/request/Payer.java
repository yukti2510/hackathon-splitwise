package hackathon.splitwise.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payer {
    private String phone; // Phone number of the payer
    private Double amountPaid; // Amount paid by the payer
}
