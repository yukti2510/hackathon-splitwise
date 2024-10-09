package hackathon.splitwise.entity;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author gauravlikhar
 * @project hackathon-splitwise
 */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AbstractJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "uuid", columnDefinition = "char(40)", unique = true, nullable = false)
    protected String uuid;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;

    @Column(name = "created_by", columnDefinition = "char(40)")
    protected String createdBy;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedAt;

    @Column(name = "updated_by", columnDefinition = "char(40)")
    protected String updatedBy;

    @Builder.Default
    @Column(name = "status", columnDefinition = "bit(1) default 1")
    protected boolean status = true;

    @PrePersist
    private void validationBeforeSaving() {
        if (StringUtils.isBlank(uuid)) {
            uuid = UUID.randomUUID().toString();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractJpaEntity other = (AbstractJpaEntity) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (uuid == null) {
            if (other.uuid != null) {
                return false;
            }
        } else if (!uuid.equals(other.uuid)) {
            return false;
        }
        return true;
    }

}