package nesttify.backend.entity

import nesttify.backend.entity.enums.TypeOwner

class Seller(
    val nameOwner: String?=null,
    val typeOwner: TypeOwner?=null,
    val numberPhone: String?=null,
    val adverts: List<Advert>?=null
) {
}