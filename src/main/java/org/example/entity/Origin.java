package org.example.entity;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Origin")
@Table(name = "origin", schema = "shopaonam")
public class Origin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OriginID", nullable = false)
    private Integer originID;

    @Column(name = "Country",length = 100,nullable = false)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;
}
