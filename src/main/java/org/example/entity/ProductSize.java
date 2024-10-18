package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "ProductSize")
@Table(name = "productsize", schema = "shopaonam")
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_sizeid", nullable = false)
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