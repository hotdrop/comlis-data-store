package jp.hotdrop.costore.repository

import org.springframework.stereotype.Repository
import redis.clients.jedis.Jedis

@Repository
class KeyRepository {

    private val DATABASE_NO = 0

    private val LAST_ITEM_KEY = "last_item_key"

    fun findLastItemKey(): String? {
        val jedis = getJedis()
        return if(jedis.exists(LAST_ITEM_KEY)) jedis.get(LAST_ITEM_KEY) else null
    }

    fun saveLastItemKey(value: String) {
        val jedis = getJedis()
        jedis.set(LAST_ITEM_KEY, value)
    }

    // TODO IPアドレスやポート番号は外部に切り出す
    private fun getJedis() = Jedis("127.0.0.1", 3000).also { it.select(DATABASE_NO) }
}