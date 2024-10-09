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

    @Column(name = "banner_image", nullable = false)
    private String bannerImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "banner_type", nullable = false)
    protected BannerType bannerType;

    @ManyToOne
    @JoinColumn (name = "link_to_productid")
    private Product productID;

    @ManyToOne
    @JoinColumn (name = "link_to_product_typeid")
    private ProductType productTypeID;

    @ManyToOne
    @JoinColumn (name = "link_to_categoryid")
    private Category categoryID;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;;
}