package hackathon.splitwise.dto.request;

import hackathon.splitwise.dto.UserDto;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class AddMembersToGroupRequestDto {
    private Long groupId;
    private List<UserDto> membersList;
}


