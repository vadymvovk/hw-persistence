package me.vovkv.stealer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.vovkv.stealer.dto.NasaCamera;
import me.vovkv.stealer.dto.NasaPhoto;
import me.vovkv.stealer.entity.Camera;
import me.vovkv.stealer.entity.Picture;
import me.vovkv.stealer.repository.CameraRepository;
import me.vovkv.stealer.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class NasaPictureGrabberService implements PictureGrabberService {

    public static final String QUERY_PARAM_SOL = "sol";
    public static final String QUERY_PARAM_API_KEY = "api_key";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final CameraRepository cameraRepository;
    private final PictureRepository pictureRepository;

    @Value("${nasa.api.host}")
    private String host;

    @Value("${nasa.api.key}")
    private String apiKey;

    @Override
    @Transactional
    public void grabPictureBy(Integer sol) {
        URI resourceUri = buildRequesrUri(sol);
        JsonNode response = restTemplate.getForObject(resourceUri, JsonNode.class);
        List<JsonNode> photos = Optional.ofNullable(response)
                .map(node -> StreamSupport.stream(node.findValue("photos").spliterator(), true).toList())
                .orElse(Collections.emptyList());

        photos.forEach(
                photo -> {
                    NasaPhoto nasaPhoto = retrive(photo);
                    Camera camera = getOrCreateCamera(nasaPhoto.getCamera());
                    Picture picture = new Picture(nasaPhoto.getNasaId(), nasaPhoto.getImgSrc(), camera);
                    savePictureIfNotExist(picture);
                }
        );
    }

    private URI buildRequesrUri(int sol) {
        return UriComponentsBuilder.fromHttpUrl(this.host)
                .queryParam(QUERY_PARAM_SOL, sol)
                .queryParam(QUERY_PARAM_API_KEY, this.apiKey)
                .build().toUri();
    }

    @SneakyThrows
    private NasaPhoto retrive(JsonNode photo) {
       return objectMapper.treeToValue(photo, NasaPhoto.class);
    }

    private Camera getOrCreateCamera(NasaCamera nasaCamera) {
        return cameraRepository.findByNasaId(nasaCamera.getNasaId())
                .orElseGet(() -> cameraRepository.save(new Camera(nasaCamera.getNasaId(), nasaCamera.getName())));
    }

    private void savePictureIfNotExist(Picture picture) {
        pictureRepository.findByNasaId(picture.getNasaId())
                .orElseGet(() -> pictureRepository.save(picture));
    }

}
