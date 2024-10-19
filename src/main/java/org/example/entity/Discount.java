package org.example.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Discount")
@Table(name = "discount", schema = "shopaonam")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiscountID", nullable = false)
    private Integer discountID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID")
    @JsonIgnore
    private Category categoryID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductTypeID")
    @JsonBackReference // Sử dụng JsonBackReference để tránh lỗi tuần hoàn và để ánh xạ đúng từ JSON
    private ProductType productTypeID;

    @Column(name = "Discountpercent", precision = 5, scale = 2)
    private BigDecimal discountPercent;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;
}