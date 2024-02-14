package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.model.AdModel;

@Mapper(componentModel = "spring")
public interface AdMapper {

    AdMapper SAMPLE = Mappers.getMapper(AdMapper.class);


    @Mapping(source = "image.path", target = "image")
    @Mapping(source = "user.id", target = "author")
    AdDto toDtoAd(AdModel ad);

    AdModel toModelAd(AdsDto dto);
    AdsDto toDtoAds(AdModel ad);

    AdModel toModelAd(CreateOrUpdateAdDto dto);
    CreateOrUpdateAdDto toDtoCreateOrUpdateAd(AdModel ad);


    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "image.path", target = "image")
    @Mapping(source = "user.phone", target = "phone")
    ExtendedAdDto toDtoExtendedAd(AdModel ad);
}
