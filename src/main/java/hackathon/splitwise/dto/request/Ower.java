package hackathon.splitwise.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ower {
    private String phone; // Phone number of the ower
    private Double amountOwed; // Amount owed by the ower
}
