package hackathon.splitwise.entity;

import hackathon.splitwise.convertor.JsonToMapConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private boolean phone;

    @Column(name = "jupiter_user_id", columnDefinition = "VARCHAR(100)")
    private String jupiterUserId;

    @Convert(converter = JsonToMapConverter.class)
    @Column(name = "metadata", columnDefinition = "json", nullable = true)
    private Map<String, String> metadata;

    public UserEntity(UserEntity userEntity) {
        this.name = userEntity.getName();
        this.phone = userEntity.isPhone();
        this.jupiterUserId = userEntity.getJupiterUserId();
        this.metadata = userEntity.getMetadata();
    }
}
