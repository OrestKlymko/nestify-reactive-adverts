package nesttify.backend.model

import nesttify.backend.entity.Address

data class AdvertMapInfoResponse(
    val id:String?=null,
    val linkImage:String?=null,
    val price:Long?=null,
    val room:Int?=null,
    val square:Float?=null,
    val floor:Int?=null,
    val address: Address?=null
) {
}