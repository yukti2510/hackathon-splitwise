package hackathon.splitwise.entity;

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







    @Column(name = "name", columnDefinition = "VARCHAR(250)")
    private String name;

    @Column(name = "phone", columnDefinition = "VARCHAR(250)", nullable = false)
    private boolean phone;

    @Column(name = "jupiter_user_id", columnDefinition = "VARCHAR(100)")
    private String jupiterUserId;

    @Convert(converter = JsonToMapConverter.class)
    @Column(name = "metadata", columnDefinition = "json", nullable = true)
    private Map<String, String> metadata;

    @Column(name = "residence_uuid", columnDefinition = "CHAR(40)", nullable = false)
    private String residenceUuid;

    @Column(name = "room_uuid", columnDefinition = "CHAR(40)", nullable = false)
    private String roomUuid;

    @Column(name = "room_number", columnDefinition = "CHAR(40)", nullable = false)
    private String roomNumber;

    @Column(name = "inventory_uuid", columnDefinition = "CHAR(40)", nullable = false)
    private String inventoryUuid;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "beds")
    private Double beds;

    @Column(name = "self_converted", columnDefinition = "bit(1)", nullable = false)
    private boolean selfConverted;

    @Column(name = "occupancy", columnDefinition = "INT(40)")
    private Integer occupancy;

    @Column(name = "occupancy_name", columnDefinition = "CHAR(40)", nullable = false)
    private String occupancyName;

    @Column(name = "old_occupancy", columnDefinition = "INT(40)")
    private Integer oldOccupancy;

    @Column(name = "floor", columnDefinition = "INT(40)")
    private Integer floor;

    @Column(name = "all_rooms_selected", columnDefinition = "bit(1)", nullable = false)
    private boolean allRoomsSelected;

    @Column(name = "bhk_type", columnDefinition = "CHAR(40)")
    private String bhkType;

    @Column(name = "is_inventory_selected", columnDefinition = "bit(1)", nullable = false)
    private boolean isInventorySelected;

    @Column(name = "bump_up_price", columnDefinition = "double")
    private double bumpUpPrice;

    @Convert(converter = JsonToMapConverter.class)
    @Column(name = "escalation_details", columnDefinition = "json")
    private Map<Double, List<Double>> escalationDetails;

    public BookingInventoryOccupancyEntity(BookingInventoryOccupancyEntity bookingInventoryOccupancyEntity) {
        this.bookingUuid = bookingInventoryOccupancyEntity.getBookingUuid();
        this.isInventorySelected =bookingInventoryOccupancyEntity.isInventorySelected();
        this.roomUuid =bookingInventoryOccupancyEntity.getRoomUuid();
        this.startDate = bookingInventoryOccupancyEntity.getStartDate();
        this.endDate =  bookingInventoryOccupancyEntity.getEndDate();
        this.inventoryUuid =bookingInventoryOccupancyEntity.getInventoryUuid();
        this.referenceType = bookingInventoryOccupancyEntity.getReferenceType();
        this.residenceUuid = bookingInventoryOccupancyEntity.getResidenceUuid();
        this.bhkType = bookingInventoryOccupancyEntity.getBhkType();
        this.escalationDetails =bookingInventoryOccupancyEntity.getEscalationDetails();
        this.allRoomsSelected = bookingInventoryOccupancyEntity.isAllRoomsSelected();
        this.roomNumber = bookingInventoryOccupancyEntity.getRoomNumber();
        this.occupancy = bookingInventoryOccupancyEntity.getOccupancy();
        this.beds= bookingInventoryOccupancyEntity.getBeds();
        this.bumpUpPrice =bookingInventoryOccupancyEntity.getBumpUpPrice();
        this.selfConverted =bookingInventoryOccupancyEntity.isSelfConverted();
        this.softBlocked = bookingInventoryOccupancyEntity.isSoftBlocked();
        this.floor = bookingInventoryOccupancyEntity.getFloor();
        this.oldOccupancy = bookingInventoryOccupancyEntity.getOldOccupancy();
        this.occupancyName = bookingInventoryOccupancyEntity.getOccupancyName();
    }
}
