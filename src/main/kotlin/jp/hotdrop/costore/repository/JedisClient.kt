package jp.hotdrop.costore.repository

import jp.hotdrop.costore.repository.config.RedisProperties
import redis.clients.jedis.Jedis

class JedisClient(
        private val selectNo: DatabaseNo,
        private val redisProperties: RedisProperties
) {
    fun create(): Jedis {
        val dbNo = when(selectNo) {
            DatabaseNo.Key -> 0
            DatabaseNo.Company -> 1
        }
        val host = redisProperties.host ?: throw NullPointerException("application.ymlのspring.redis.hostが読めていない可能性があります。")
        val port = redisProperties.port ?: throw NullPointerException("application.ymlのspring.redis.portが読めていない可能性があります。")
        return Jedis(host, port).also { it.select(dbNo) }
    }
}