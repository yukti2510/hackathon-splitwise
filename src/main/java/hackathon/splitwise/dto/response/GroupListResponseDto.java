package hackathon.splitwise.dto.response;

import hackathon.splitwise.dto.GroupDto;
import hackathon.splitwise.entity.GroupEntity;
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
    private List<GroupEntity> groupList;
}
