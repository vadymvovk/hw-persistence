package me.vovkv.stealer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "pictures")
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nasa_id", unique = true, nullable = false)
    private Long nasaId;

    @Column(name = "img_src")
    private String imgSrc;

    @ManyToOne(optional = false)
    @JoinColumn(name = "camera_id")
    private Camera camera;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    public Picture(Long nasaId, String imgSrc, Camera camera) {
        this.nasaId = nasaId;
        this.imgSrc = imgSrc;
        this.camera = camera;
    }

}
