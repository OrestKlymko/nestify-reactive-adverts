package nesttify.backend.exception

data class NotFoundException(override val message: String?): Exception(message) {

}
