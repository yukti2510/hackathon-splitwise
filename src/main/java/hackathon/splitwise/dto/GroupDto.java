package hackathon.splitwise.dto;

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
public class GroupDto {
    private long id;
    private String name;
    private String type;
    private String logo;
    private Double amountPaid;
    private List<UserDto> membersList;
}
