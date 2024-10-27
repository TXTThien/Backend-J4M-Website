package org.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "News")
@Table(name = "news", schema = "shopaonam")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NewsID", nullable = false)
    private Integer newsID;

    @Column(name = "news_image",nullable = false)
    private String newsImage;

    @Column(name = "news_title",length = 100, nullable = false)
    private String newsTitle;

    @Lob
    @Column(name = "Content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;

    @Column(name = "Date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;
}
