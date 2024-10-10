package hackathon.splitwise.dto.request;

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
public class CreateUserRequestDto {
    private UserDto creator;
    private String name;
    private String type;
    private String logo;
    private List<UserDto> membersList;
}
