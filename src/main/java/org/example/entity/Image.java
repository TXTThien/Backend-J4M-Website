package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Image")
@Table(name = "image", schema = "shopaonam")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ImageID", nullable = false)
    private Integer imageID;

    @Column(name = "ImageURL", nullable = false)
    private String imageURL;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product; // Chắc chắn đây là Product, không phải productID

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;
}
