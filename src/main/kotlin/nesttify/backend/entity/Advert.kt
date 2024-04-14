package nesttify.backend.entity

import nesttify.backend.entity.enums.AdditionalRequirementsFlat
import nesttify.backend.entity.enums.AdvantageList
import nesttify.backend.entity.enums.TypeRealty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime


@Document(collection = "adverts")
class Advert(
    @Id
    val id: String? = null,
    val typeRealty: TypeRealty? = null,
    val publishedAt: LocalDateTime? = null,
    val editedAt: LocalDateTime? = null,
    val description: String? = null,
    val finalUrl: String? = null,
    val images: List<String>,
    val propertyRealty: PropertyRealty? = null,
    val address: Address? = null,
    val seller: Seller? = null,
    val additionalRequirementsFlat: AdditionalRequirementsFlat? = null,
    val advantageList: AdvantageList? = null
)