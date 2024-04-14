package nesttify.backend.controller

import nesttify.backend.entity.Advert
import nesttify.backend.model.*
import nesttify.backend.service.AdvertService
import org.springframework.data.domain.PageImpl
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api/v1/adverts/")
class AdvertController(private val advertService: AdvertService) : IAdvertController {
    override fun getFinalPageAdvertById(id: String): Mono<AdvertFinalPageResponse> {
        return advertService.getFinalPageAdvertById(id)
    }

    override fun filterSearchForm(filterParams: Map<String, List<String>>): Mono<PageImpl<AdvertFilterResponse>> {
        return advertService.filter(filterParams)
    }

    override fun getAdvertOnMap(id: String): Mono<AdvertMapInfoResponse> {
        return advertService.getAdvertOnMap(id)
    }

    override fun getAdvertPoints(
        swLng: Double,
        swLat: Double,
        neLgt: Double,
        neLat: Double
    ): Flux<PointMapResponse> {
        return advertService.getPointsOnPortView(swLng, swLat, neLat, neLgt)
    }

    override fun autoSearch(search: String): Flux<DistrictSuggestions> {
        return advertService.findBySearch(search);
    }
    @GetMapping
     fun getAllListSecretMethod(): Flux<Advert> {
        return advertService.getAllListSecretMethod();
    }
}