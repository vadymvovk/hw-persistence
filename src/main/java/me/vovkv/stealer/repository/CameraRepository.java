package me.vovkv.stealer.repository;

import me.vovkv.stealer.entity.Camera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CameraRepository extends JpaRepository<Camera, Long> {

    Optional<Camera> findByNasaId(Long nasaId);

}
