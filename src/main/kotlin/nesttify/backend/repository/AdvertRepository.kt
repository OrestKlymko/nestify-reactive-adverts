package nesttify.backend.repository

import nesttify.backend.entity.Advert
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface AdvertRepository:ReactiveMongoRepository<Advert,String> {
}