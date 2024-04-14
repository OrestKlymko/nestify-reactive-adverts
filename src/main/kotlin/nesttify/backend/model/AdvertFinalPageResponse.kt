package nesttify.backend.model

import nesttify.backend.entity.Address
import nesttify.backend.entity.PropertyRealty
import nesttify.backend.entity.Seller
import nesttify.backend.entity.enums.AdditionalRequirementsFlat
import nesttify.backend.entity.enums.AdvantageList
import nesttify.backend.entity.enums.TypeRealty

data class AdvertFinalPageResponse(
    val id:String?=null,
    val typeRealty: TypeRealty?=null,
    val description:String?=null,
    val finalUrl:String?=null,
    val images:List<String>?=null,
    val propertyRealty: PropertyRealty?=null,
    val address:Address?=null,
    val seller:Seller?=null,
    val additionalRequirementsFlat: AdditionalRequirementsFlat?=null,
    val advantageList: AdvantageList?=null
) {
}