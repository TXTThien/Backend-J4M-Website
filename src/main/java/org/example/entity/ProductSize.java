package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProductSize")
@Table(name = "productSize", schema = "shopaonam")
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductSizeID", nullable = false)
    private Integer productSizeID;

    @ManyToOne
    @JoinColumn(name = "ProductID",nullable = false)
    private Product productID;

    @ManyToOne
    @JoinColumn(name = "SizeID", nullable = false)
    private Size sizeID;

    @Column(name = "Stock", nullable = false)
    private Integer stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;
}