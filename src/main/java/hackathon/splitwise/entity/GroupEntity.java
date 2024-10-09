package hackathon.splitwise.entity;

import hackathon.splitwise.convertor.JsonToMapConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Convert;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;
import java.util.Map;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "group")
public class GroupEntity extends AbstractJpaEntity {

    @Serial
    private static final long serialVersionUID = 1234567890123456789L;

    @Column(name = "name", columnDefinition = "VARCHAR(256)", nullable = false)
    private String name;

    @Column(name = "type", columnDefinition = "VARCHAR(256)", nullable = false)
    private String type;

    @Convert(converter = JsonToMapConverter.class)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private Map<String, String> metadata;
}
