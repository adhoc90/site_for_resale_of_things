package ru.skypro.homework.dto.ads;

import lombok.Data;

import java.util.List;

@Data
public class AdsDto {

    private Integer count;
    private List<AdDto> results;

    public AdsDto() {
    }

    public AdsDto(Integer count, List<AdDto> results) {
        this.count = count;
        this.results = results;
    }
}