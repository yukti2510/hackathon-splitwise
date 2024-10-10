package hackathon.splitwise.dto.response;

import hackathon.splitwise.dto.GroupDto;
import lombok.*;

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
}
