package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Size")
@Table(name = "size", schema = "shopaonam")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SizeID", nullable = false)
    private Integer sizeID;

    @Column(name = "Sizename",length = 10,nullable = false)
    private String sizeName;

    @Column(name = "Bonus", nullable = false, precision = 10, scale = 3)
    private BigDecimal bonus;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;
}
