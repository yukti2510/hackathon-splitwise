package hackathon.splitwise.dto.response;

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
public class CreateGroupResponseDto {
    private long id;
    private String name;
    private String type;
    private String logo;
    private Double amountPaid;
    private UserDto creator;
    private List<UserDto> membersList;
}
