package nesttify.backend.service

import com.mongodb.client.model.Filters.eq
import com.mongodb.reactivestreams.client.MongoClient
import kotlinx.coroutines.reactive.collect
import nesttify.backend.entity.Advert
import nesttify.backend.exception.NotFoundException
import nesttify.backend.mapper.AdvertMapper
import nesttify.backend.model.*
import nesttify.backend.repository.AdvertRepository
import org.bson.Document
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.convert.MongoConverter
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import java.util.*
import java.util.stream.Collectors


@Service
class AdvertService(
    private val advertRepository: AdvertRepository,
    private val mongoTemplate: ReactiveMongoTemplate,
    private val advertMapper: AdvertMapper,
    private val clientMongo: MongoClient,
    private val converter: MongoConverter
) {

    fun getFinalPageAdvertById(id: String): Mono<AdvertFinalPageResponse> {
        return advertRepository
            .findById(id)
            .map(advertMapper::mapToAdvertFinalPage)
            .switchIfEmpty(Mono.error(NotFoundException("Advert with id $id not found")))
    }

    fun getAdvertOnMap(id: String): Mono<AdvertMapInfoResponse> {
        return advertRepository
            .findById(id)
            .map(advertMapper::mapToAdvertMapInfo)
            .switchIfEmpty(Mono.error(NotFoundException("Advert with id $id not found")))
    }

    fun getAllListSecretMethod(): Flux<Advert> {
        return advertRepository
            .findAll();
    }

    fun getPointsOnPortView(swLng: Double, swLat: Double, neLat: Double, neLgt: Double): Flux<PointMapResponse> {
        val query = Query()
        query.addCriteria(Criteria.where("address.latitude").gte(swLat).lte(neLat))
            .addCriteria(Criteria.where("address.longitude").gte(swLng).lte(neLgt));

        return mongoTemplate.find(query, Advert::class.java)
            .map(advertMapper::mapToPointMap)

    }

    fun filter(urlParameters: Map<String, List<String>>): Mono<PageImpl<AdvertFilterResponse>> {
        val query = Query()
        var priceFrom: Long = 0
        var priceTo = Long.MAX_VALUE
        for (entry in urlParameters.iterator()) {
            when (entry.key) {
                "city" -> query.addCriteria(
                    Criteria.where("address.city").regex(
                        entry.value.toString(), "i"
                    )
                )

                "address" -> query.addCriteria(
                    Criteria.where("address.addressName").regex(
                        entry.value.toString(), "i"
                    )
                )

                "rooms" -> {
                    val list = Arrays.stream(entry.value.toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray())
                        .map { s: String -> s.toInt() }
                        .collect(Collectors.toList())

                    query.addCriteria(Criteria.where("propertyRealty.room").`in`(list))
                }

                "districts" -> {
                    val splitDistrict = entry.value.toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()
                    val districtCriterias = Arrays.stream(splitDistrict).toList().stream()
                        .map { district: String? ->
                            Criteria.where("address.district").regex(
                                district!!, "i"
                            )
                        }
                        .toList()
                    query.addCriteria(Criteria().orOperator(*districtCriterias.toTypedArray<Criteria>()))
                }

                "priceFrom" -> priceFrom = entry.value.toString().toLong()
                "priceTo" -> priceTo = entry.value.toString().toLong()
                "typeOwner" -> query.addCriteria(
                    Criteria.where("seller.typeOwner").`is`(
                        entry.value.toString()
                    )
                )

                "withKids" -> query.addCriteria(
                    Criteria.where("propertyRealty.withKids").`is`(
                        entry.value.toString()
                    )
                )

                "withPets" -> query.addCriteria(
                    Criteria.where("propertyRealty.withPets").`is`(
                        entry.value
                    )
                )

                "typeRealty" -> query.addCriteria(
                    Criteria.where("typeRealty").`is`(
                        entry.value
                    )
                )
            }
        }

        query.addCriteria(Criteria.where("propertyRealty.totalPrice").gte(priceFrom).lte(priceTo))


        val offset = urlParameters["offset"].toString().toInt()
        val limit = urlParameters["limit"].toString().toInt()
        val pageRequest = PageRequest.of(offset, limit)

        val countMono = mongoTemplate.count(query, Advert::class.java)

        val advertsMono = mongoTemplate.find(query, Advert::class.java)
            .map(advertMapper::mapToAdvertFilter)
            .collectList()

        return Mono.zip(countMono, advertsMono) { count, adverts ->
            PageImpl(adverts, pageRequest, count)
        }
    }

    fun findBySearch(search: String): Flux<DistrictSuggestions> {
        val query = Query()
        val splitDistrict = search.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val districtCriterias = Arrays.stream(splitDistrict).toList().stream()
            .map { district: String? ->
                Criteria.where("address.district").regex(
                    district!!, "i"
                )
            }
            .toList()
        query.addCriteria(Criteria().orOperator(*districtCriterias.toTypedArray<Criteria>()))
        return mongoTemplate.find(query, Advert::class.java).mapNotNull { DistrictSuggestions(it.address?.district,it.address?.city) }.distinct()


    }

}