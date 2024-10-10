package hackathon.splitwise.dto.response;

import hackathon.splitwise.dto.GroupDto;
import hackathon.splitwise.dto.TransactionDetailResponseDto;
import hackathon.splitwise.dto.TransactionDto;
import hackathon.splitwise.dto.UserDto;
import lombok.*;

import java.util.List;

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
public class GroupDetailsResponseDto {
    GroupDto group;
    List<UserDto> membersList;
    List<TransactionDetailResponseDto> transactionsList;
}
