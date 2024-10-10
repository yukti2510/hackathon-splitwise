package hackathon.splitwise.dto;

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
public class UserDto {
    private long id;
    private String name;
    private String phone;
}
