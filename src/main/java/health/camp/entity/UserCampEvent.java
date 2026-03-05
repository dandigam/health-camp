package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "user_camp_events",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "camp_event_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCampEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User Mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Camp Event Mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camp_event_id", nullable = false)
    private CampEvent campEvent;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(length = 20)
    private String status; // ACTIVE / INACTIVE
}
