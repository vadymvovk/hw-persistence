package me.vovkv.stealer.repository;

import me.vovkv.stealer.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    Optional<Picture> findByNasaId(Long nasaId);

}
