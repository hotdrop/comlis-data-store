package jp.hotdrop.costore.exception

class ComlisException constructor(
        val status: Int,
        override val message: String,
        override val cause: Throwable? = null
): RuntimeException(message, cause)