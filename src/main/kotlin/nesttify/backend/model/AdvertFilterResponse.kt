package nesttify.backend.model

import nesttify.backend.entity.Address
import nesttify.backend.entity.enums.AdvantageList
import java.time.LocalDateTime

data class AdvertFilterResponse(
    val id:String?=null,
    val price:Long?=null,
    val room:Int?=null,
    val square:Float?=null,
    val floor:Int?=null,
    val address: Address?=null,
    val description:String?=null,
    val advantageList: AdvantageList?=null,
    val publishedAt:LocalDateTime?=null,
    val advertImage:String?=null
)
