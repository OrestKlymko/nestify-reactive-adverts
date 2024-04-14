package nesttify.backend.controller

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import nesttify.backend.model.*
import org.springframework.data.domain.PageImpl
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface IAdvertController {
    @GetMapping("/advert/{id}")
    @Tag(name = "Інфо кінцевої сторінки")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = AdvertFinalPageResponse::class)
            )]
        )]
    )
    fun getFinalPageAdvertById(@PathVariable("id") id: String): Mono<AdvertFinalPageResponse>

    @GetMapping("/search")
    @Tag(name = "Пошук по фільтру")
    @Parameters(
        *[Parameter(name = "priceFrom", description = "Мінімальна сума", example = "0"), Parameter(
            name = "priceTo",
            description = "Гранична сума",
            example = "1000000"
        ), Parameter(
            name = "rooms",
            description = "Кількість кімнат, які потрібно відфільтрувати",
            example = "1,2,3"
        ), Parameter(
            name = "districts",
            description = "Назви районів, які потрібно відфільтрувати",
            example = "Bratislava Raca,Bratislava III"
        ), Parameter(
            name = "address",
            description = "Назви адреси, які потрібно відфільтрувати",
            example = "Znievska 1"
        ), Parameter(name = "city", description = "Місто", example = "Bratislava"), Parameter(
            name = "offset",
            description = "кількість записів, які потрібно пропустити від початку",
            example = "0, якщо потрібно першу сторінку"
        ), Parameter(name = "limit", description = "Кількість записів, які потрібно вивести", example = "5"), Parameter(
            name = "typeOwner",
            description = "Тип продавця",
            example = "OWNER або REALTY (мапимо відповідно Vlastník, Realitná kancelária)"
        ), Parameter(
            name = "withPets",
            description = "Дозволено з тваринами",
            example = "YES, NO, UNDEFINED (мапимо відповіно Áno, Nie, Nie je uvedené)"
        ), Parameter(
            name = "withKids",
            description = "Дозволено з дітьми",
            example = "YES, NO, UNDEFINED  (мапимо відповіно Áno, Nie, Nie je uvedené)"
        ), Parameter(
            name = "typeRealty",
            description = "Тип нерухомості",
            example = "FLAT (Byt), HOUSE (Dom), HALF_FLAT (Garsónka), DACHA (Záhradný dom), ROOM (Izba), COMMERCIAL_REALTY (Komerčná nehnuteľnosť)"
        )]
    )
    fun filterSearchForm(@RequestParam filterParams: Map<String, List<String>>): Mono<PageImpl<AdvertFilterResponse>>

    @GetMapping("/map/view/{id}")
    @Tag(name = "Дістати оголошення на карті")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = AdvertMapInfoResponse::class))]
        )]
    )
    fun getAdvertOnMap(@PathVariable("id") id: String): Mono<AdvertMapInfoResponse>


    @GetMapping("/map/view/")
    @Tag(name = "Фільтр точок на View Port")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            content = arrayOf(Content(array = ArraySchema(schema = Schema(implementation = PointMapResponse::class)))),
            description = "Get location information"
        )]
    )
    fun getAdvertPoints(
        @RequestParam("sw_lng") swLng: Double,
        @RequestParam("sw_lat") swLat: Double,
        @RequestParam("ne_lng") neLgt: Double,
        @RequestParam("ne_lat") neLat: Double
    ): Flux<PointMapResponse>

    @GetMapping("/{search}")
    fun autoSearch(@PathVariable search:String): Flux<DistrictSuggestions>
}