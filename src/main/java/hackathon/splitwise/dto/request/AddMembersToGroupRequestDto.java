package hackathon.splitwise.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class AddMembersToGroupRequestDto {
    private Long groupId;
    private List<MemberDto> membersList;
}


