package jp.hotdrop.costore.repository

import jp.hotdrop.costore.repository.config.RedisProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class KeyRepository {

    private val LAST_ITEM_KEY = "last_item_key"

    @Autowired
    lateinit var redisProperties: RedisProperties

    private val jedis by lazy {
        JedisClient(DatabaseNo.Company, redisProperties).create()
    }

    fun findLastItemKey(): String? =
            if(jedis.exists(LAST_ITEM_KEY))
                jedis.get(LAST_ITEM_KEY)
            else
                null

    fun saveLastItemKey(value: String) {
        jedis.set(LAST_ITEM_KEY, value)
    }
}