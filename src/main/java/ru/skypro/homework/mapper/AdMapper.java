package ru.skypro.homework.mapper;

import org.mapstruct.Mapping;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;

public interface AdMapper {


    @Mapping(source = "author", target = "user.id")
    Ad toModelAd(ru.skypro.homework.dto.ads.Ad dto);
    @Mapping(source = "user.id", target = "author")
    ru.skypro.homework.dto.ads.Ad toDtoAd(Ad ad, User user);

    Ad toModelAd(ru.skypro.homework.dto.ads.Ads dto);
    ru.skypro.homework.dto.ads.Ads toDtoAds(Ad ad);

    Ad toModelAd(ru.skypro.homework.dto.ads.CreateOrUpdateAd dto);
    ru.skypro.homework.dto.ads.CreateOrUpdateAd toDtoCreateOrUpdateAd(Ad ad);


    @Mapping(source = "authorFirstName", target = "user.firstName")
    @Mapping(source = "authorLastName", target = "user.lastName")
    Ad toModelAd(ru.skypro.homework.dto.ads.ExtendedAd dto);
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    ru.skypro.homework.dto.ads.ExtendedAd toDtoExtendedAd(Ad ad, User user);
}
