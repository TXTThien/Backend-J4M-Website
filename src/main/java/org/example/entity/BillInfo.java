package org.example.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import jakarta.persistence.*;
import org.example.entity.enums.Status;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties("billID")
@Entity(name = "Billinfo")
@Table(name = "billinfo", schema = "shopaonam")
public class BillInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BillinfoID", nullable = false)
    private Integer billInfoID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BillID")
    private Bill billID;

    @Column(name = "Number" , nullable = false)
    private Integer number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductsizeID")
    private ProductSize productSizeID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DiscountID")
    private Discount discountID;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;;
}