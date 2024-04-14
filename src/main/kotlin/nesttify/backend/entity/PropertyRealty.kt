package nesttify.backend.entity

import nesttify.backend.entity.enums.AdditionalRequirementsFlat
import nesttify.backend.entity.enums.AdvantageList
import nesttify.backend.entity.enums.AllowStatus

class PropertyRealty(
    val square: Float?=null,
    val floor: Int?=null,
    val room: Int?=null,
    val realtyPrice: Long?=null,
    val energyPrice: Long?=null,
    val totalPrice: Long?=null,
    val material: String?=null,
    val withPets: AllowStatus?=null,
    val withKids: AllowStatus?=null,
    val additionalRequirementsFlat: List<AdditionalRequirementsFlat>?=null,
    val advantageList: List<AdvantageList>?=null,
)