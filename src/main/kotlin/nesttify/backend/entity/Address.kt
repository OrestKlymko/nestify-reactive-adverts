package nesttify.backend.entity

import java.math.BigDecimal

class Address(
    val id: Long?=null,
    val advertId: String?=null,
    val buildMapTilerId: BigDecimal?=null,
    val district: String?=null,
    val addressName: String?=null,
    val city: String?=null,
    val latitude: Double?=null,
    val longitude: Double?=null
) {
}