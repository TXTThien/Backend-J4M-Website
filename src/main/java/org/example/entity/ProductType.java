package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProductType")
@Table(name = "producttype", schema = "shopaonam")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_typeid", nullable = false)
    private Integer productTypeID;

    @Column(name = "type_name",length = 10,nullable = false)
    private String typeName;

    @ManyToOne
    @JoinColumn(name = "CategoryID", nullable = false)
    private Category categoryID;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;
}