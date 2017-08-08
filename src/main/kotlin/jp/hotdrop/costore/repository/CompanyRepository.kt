package jp.hotdrop.costore.repository

import jp.hotdrop.costore.model.Company
import org.springframework.stereotype.Repository
import redis.clients.jedis.Jedis

@Repository
class CompanyRepository {

    private val DATABASE_NO = 1

    private val INDEX_KEY = "indices"

    private fun Company.toHashMap(): HashMap<String, String?> =
            hashMapOf("name" to this.name,
            "overView" to this.overView,
            "workPlace" to this.workPlace,
            "employeeNum" to this.employeeNum,
            "salaryLow" to this.salaryLow,
            "salaryHigh" to this.salaryHigh)

    fun save(previousKeyIndex: Int, companies: List<Company>): Int {

        if(companies.isEmpty()) {
            return previousKeyIndex
        }

        val jedis = getJedis()
        var currentKeyIndex = previousKeyIndex

        companies.forEach {
            currentKeyIndex++
            val strCurrentKey = currentKeyIndex.toString()
            jedis.sadd(INDEX_KEY, strCurrentKey)
            jedis.hmset(strCurrentKey, it.toHashMap())
        }

        return currentKeyIndex
    }

    fun findUnAcquired(): List<Company>? {
        val jedis = getJedis()
        return mutableListOf()
    }

    // TODO 接続情報は後で外部に切り出す
    private fun getJedis() = Jedis("127.0.0.1", 3000).also { it.select(DATABASE_NO) }
}