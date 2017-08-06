package jp.hotdrop.costore.repository

import org.springframework.stereotype.Repository
import redis.clients.jedis.Jedis

@Repository
class KeyRepository {

    private val DATABASE_NO = 0
    private val LAST_ITEM_KEY = "last_item_key"
    private val SENT_KEY = "sent_key"

    fun getLastItemKey() = find(LAST_ITEM_KEY)

    fun saveLastItemKey(value: String) {
        save(LAST_ITEM_KEY, value)
    }

    fun getSentToMobileAppCompanyKey() = find(SENT_KEY)

    fun saveSentToMobileAppCompanyKey(value: String) {
        save(SENT_KEY, value)
    }

    private fun find(key: String): String? {
        val jedis = getJedis()
        return if(jedis.exists(key)) jedis.get(key) else null
    }

    private fun save(key: String, value: String) {
        val jedis = getJedis()
        jedis.set(key, value)
    }

    // TODO IPアドレスやポート番号は外部に切り出す
    private fun getJedis() = Jedis("127.0.0.1", 3000).also { it.select(DATABASE_NO) }
}