package hackathon.splitwise.dto.response;

import hackathon.splitwise.entity.UserGroupEntity;
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
    private List<UserGroupEntity> groupList;
}
