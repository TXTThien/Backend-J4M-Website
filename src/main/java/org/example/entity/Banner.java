package org.example.entity;

import lombok.*;
import jakarta.persistence.*;
import org.example.entity.enums.BannerType;
import org.example.entity.enums.Status;

import java.io.Serializable;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Banner")
@Table(name = "banner", schema = "shopaonam")
public class Banner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BannerID", nullable = false)
    private Integer bannerID;

    @Column(name = "BannerImage", nullable = false)
    private String bannerImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "BannerType", nullable = false)
    protected BannerType bannerType;

    @ManyToOne
    @JoinColumn (name = "LinkToProductID")
    private Product productID;

    @ManyToOne
    @JoinColumn (name = "LinkToProductTypeID")
    private ProductType productTypeID;

    @ManyToOne
    @JoinColumn (name = "LinkToCategoryID")
    private Category categoryID;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;;
}