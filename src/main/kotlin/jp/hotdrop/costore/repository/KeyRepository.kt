package jp.hotdrop.costore.repository

import jp.hotdrop.costore.model.KeyData
import org.springframework.stereotype.Repository
import redis.clients.jedis.Jedis

@Repository
class KeyRepository {

    fun find(key: String): String {
        // TODO 外部に切り出す
        val jedis = Jedis("127.0.0.1", 3000).also { it.select(0) }
        return  if(jedis.exists(key)) jedis.get(key) else ""
    }

    fun save(kv: KeyData) {
        val jedis = Jedis("127.0.0.1", 3000).also { it.select(0) }
        jedis.set(kv.key, kv.value)
    }
}