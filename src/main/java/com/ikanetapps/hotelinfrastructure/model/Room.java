package com.ikanetapps.hotelinfrastructure.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ikanetapps.hotelinfrastructure.utils.Locations;
import com.ikanetapps.hotelinfrastructure.utils.RoomTypes;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "room", indexes = {
        @Index(name = "idx_room_id_unq", columnList = "id", unique = true)
})
@Getter
@Setter
@ToString(exclude = {"description", "images"})
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq_gen")
    @SequenceGenerator(name = "room_seq_gen", sequenceName = "room_seq", allocationSize = 1)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true, length = 5)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomTypes roomType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Locations location;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

    @NotNull
    @Min(value = 1)
    @Column(nullable = false)
    private int bedCount = 1;

    @NotNull
    @Min(value = 1)
    @Column(nullable = false)
    private int maxOccupancy = 1;

    @NotNull
    @Min(value = 0)
    @Column(nullable = false)
    private Double basePrice = 0.0;

    @Column(nullable = false)
    private Boolean availability = false;

    @Min(value = 0)
    @Column(nullable = false)
    private Double rating;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<RoomImages> images;  // List of images associated with the room

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room that = (Room) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
