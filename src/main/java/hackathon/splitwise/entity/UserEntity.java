package hackathon.splitwise.entity;

import hackathon.splitwise.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

/**
 * @author gauravlikhar
 * @project hackathon-splitwise
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity extends AbstractJpaEntity{

    private static final long serialVersionUID = 323569917172517286L;
    @Column(name = "name", columnDefinition = "VARCHAR(256)")

    private String name;

    @Column(name = "phone", columnDefinition = "VARCHAR(256)", nullable = false)
    private String phone;

    @Column(name = "jupiter_user_id", columnDefinition = "VARCHAR(100)")
    private String jupiterUserId;
    @Convert(converter = JsonConverter.class)
    private Map<String, String> metadata;

    public UserEntity(UserEntity userEntity) {
        this.name = userEntity.getName();
        this.phone = userEntity.getPhone();
        this.jupiterUserId = userEntity.getJupiterUserId();
        this.metadata = userEntity.getMetadata();
    }
}
