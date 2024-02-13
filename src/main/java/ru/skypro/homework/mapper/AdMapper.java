package ru.skypro.homework.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;

public interface AdMapper {

    AdMapper SAMPLE = Mappers.getMapper(AdMapper.class);


    @Mapping(source = "author", target = "user.id")
    Ad toModelAd(AdDto dto);
    @Mapping(source = "user.id", target = "author")
    AdDto toDtoAd(Ad ad, User user);

    Ad toModelAd(AdsDto dto);
    AdsDto toDtoAds(Ad ad);

    Ad toModelAd(CreateOrUpdateAdDto dto);
    CreateOrUpdateAdDto toDtoCreateOrUpdateAd(Ad ad);


    @Mapping(source = "authorFirstName", target = "user.firstName")
    @Mapping(source = "authorLastName", target = "user.lastName")
    Ad toModelAd(ExtendedAdDto dto);
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    ExtendedAdDto toDtoExtendedAd(Ad ad, User user);
}
