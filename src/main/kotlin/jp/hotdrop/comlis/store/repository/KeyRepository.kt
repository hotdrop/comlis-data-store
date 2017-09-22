package jp.hotdrop.comlis.store.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class KeyRepository @Autowired constructor(
        val dbClient: RedisClient
) {

    private val LAST_ITEM_KEY = "last_item_key"

    fun find(): String? =
            dbClient.find(LAST_ITEM_KEY)

    fun exists(): Boolean =
            dbClient.exists(LAST_ITEM_KEY) ?: false

    fun save(value: String) {
        dbClient.save(LAST_ITEM_KEY, value)
    }

    fun delete() {
        dbClient.delete(LAST_ITEM_KEY)
    }
}