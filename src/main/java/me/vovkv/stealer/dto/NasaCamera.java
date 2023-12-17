package me.vovkv.stealer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NasaCamera {

    @JsonProperty(value = "id")
    private Long nasaId;

    @JsonProperty(value = "name")
    private String name;

}
