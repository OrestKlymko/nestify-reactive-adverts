package nesttify.backend.mapper

import nesttify.backend.entity.Advert
import nesttify.backend.model.AdvertFilterResponse
import nesttify.backend.model.AdvertFinalPageResponse
import nesttify.backend.model.AdvertMapInfoResponse
import nesttify.backend.model.PointMapResponse
import org.springframework.stereotype.Service


@Service
class AdvertMapper {
    fun mapToAdvertFinalPage(advert: Advert): AdvertFinalPageResponse {
        return AdvertFinalPageResponse(
            id = advert.id,
            typeRealty = advert.typeRealty,
            description = advert.description,
            finalUrl = advert.finalUrl,
            images = advert.images,
            propertyRealty = advert.propertyRealty,
            address = advert.address,
            seller = advert.seller,
            additionalRequirementsFlat = advert.additionalRequirementsFlat,
            advantageList = advert.advantageList
        )
    }

    fun mapToAdvertMapInfo(advert: Advert): AdvertMapInfoResponse {
        return AdvertMapInfoResponse(
            id = advert.id,
            linkImage = advert.images[0],
            price = advert.propertyRealty?.totalPrice,
            room = advert.propertyRealty?.room,
            square = advert.propertyRealty?.square,
            floor = advert.propertyRealty?.floor,
            address = advert.address
        )
    }

    fun mapToPointMap(advert: Advert): PointMapResponse {
        return PointMapResponse(
            id = advert.id,
            buildIdMapTiler = advert.address?.buildMapTilerId,
        )
    }

    fun mapToAdvertFilter(advert: Advert): AdvertFilterResponse {
        return AdvertFilterResponse(
            id = advert.id,
            price = advert.propertyRealty?.totalPrice,
            room = advert.propertyRealty?.room,
            square = advert.propertyRealty?.square,
            floor = advert.propertyRealty?.floor,
            address = advert.address,
            description = advert.description,
            advantageList = advert.advantageList,
            publishedAt = advert.publishedAt,
            advertImage = advert.images[0],
        )
    }
}