package hackathon.splitwise.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payer {
    private String phone; // Phone number of the payer
    private String amountPaid; // Amount paid by the payer
}
