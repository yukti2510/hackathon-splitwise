package hackathon.splitwise.dto.response;

import hackathon.splitwise.dto.GroupDto;
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
public class GroupListResponseDto {
    private Double totalAmountPaid;
    private List<GroupDto> groupList;
}
