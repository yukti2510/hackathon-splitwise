package hackathon.splitwise.dto.response;

import hackathon.splitwise.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class AmountInvolvedResponseDto {
    private UserDto user1Dto;
    private UserDto user2Dto;
    private Double amountInvolved;
}
