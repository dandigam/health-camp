
package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "camp_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 🔹 Camp Reference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "camp_id", nullable = false)
    private Camp camp;

    // 🔹 Event Status (Planned / Active / Closed)
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    // 🔹 Event Start Date
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    // 🔹 Event End Date
    @Column(name = "end_date")
    private LocalDate endDate;

    // 🔹 Audit Columns (Recommended)
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 🔹 Auto set timestamps
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}



