package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.ast.Or;
import org.example.entity.enums.Status;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Product")
@Table(name = "product", schema = "shopaonam")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID", nullable = false)
    private Integer productID;

    @Column(name = "Avatar", nullable = false)
    private String avatar;

    @Column(name = "Title",length = 100, nullable = false)
    private String title;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_typeid")
    private ProductType productType;

    @Column(name = "Price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "BrandID")
    private Brand brandID;

    @ManyToOne
    @JoinColumn(name = "OriginID")
    private Origin originID;

    @Column(name = "Material",length = 100)
    private String material;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;
    @OneToMany(mappedBy = "productID")

    @JsonBackReference
    private List<ProductSize> productSizes;

}