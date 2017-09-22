package jp.hotdrop.comlis.store.repository

import jp.hotdrop.comlis.store.model.Company
import jp.hotdrop.comlis.store.repository.config.RedisProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import redis.clients.jedis.Jedis
import redis.clients.jedis.SortingParams

@Repository
class RedisClient @Autowired constructor(
        val redisProperties: RedisProperties
) {

    private enum class Database(val selectNo: Int) { Key(0), Company(1) }
    private val jedisToDBKey by lazy { create(Database.Key) }
    private val jedisToDBCompany by lazy { create(Database.Company) }

    private fun create(db: Database): Jedis {
        val host = redisProperties.host ?: throw NullPointerException("not be read spring.redis.host in application.yml.")
        val port = redisProperties.port ?: throw NullPointerException("not be read spring.redis.port in application.yml.")
        return Jedis(host, port).also { it.select(db.selectNo) }
    }

    fun find(key: String): String? = jedisToDBKey.get(key)

    fun exists(key: String): Boolean? = jedisToDBKey.exists(key)

    fun save(key: String, value: String) {
        jedisToDBKey.set(key, value)
    }

    fun delete(key: String): Long? = jedisToDBKey.del(key)

    /**
     * inner sort key for only redis.
     */
    private val INDEX_KEY_FOR_SORT = "indices"

    /**
     * Extension functions.
     * The ID is set as Key, so it is excluded from hashMap.
     */
    private fun Company.toHashMap(): HashMap<String, String?> =
            hashMapOf("name" to this.name,
                    "overview" to this.overview,
                    "workPlace" to this.workPlace,
                    "employeesNum" to this.employeesNum,
                    "salaryLow" to this.salaryLow,
                    "salaryHigh" to this.salaryHigh,
                    "dateEpoch" to this.dateEpoch)

    fun saveCompany(company: Company) {
        // The reason for sadd is because another key sets is required
        //  when sorting Redis Hash type.
        jedisToDBCompany.sadd(INDEX_KEY_FOR_SORT, company.id)
        jedisToDBCompany.hmset(company.id, company.toHashMap())
    }

    fun findCompanies(fromDateEpoch: Long): List<Company>? {

        val rawData = selectRawDataWithSort() ?: return null
        val aggregationDataCount = rawData.size / Company.FIELD_NUM

        val companies = mutableListOf<Company>()
        (0 until aggregationDataCount)
                .map { it * Company.FIELD_NUM }
                .filter { isLoadTarget(fromDateEpoch, rawData[7 + it]) }
                .mapTo(companies) {
                    Company(id = rawData[0 + it],
                            name = rawData[1 + it],
                            overview = rawData[2 + it],
                            workPlace = rawData[3 + it],
                            employeesNum = rawData[4 + it],
                            salaryLow = rawData[5 + it],
                            salaryHigh = rawData[6 + it],
                            dateEpoch = rawData[7 + it])
                }
        return companies
    }

    /**
     * It's not good that there is a way of writing Redis in CompanyRepository.kt.
     * However, I decided to write it here because it must match key in HashMap.
     * The first # specifies to acquire the ID.
     */
    private val takeParams =
            arrayOf("#",
                    "*->name",
                    "*->overview",
                    "*->workPlace",
                    "*->employeesNum",
                    "*->salaryLow",
                    "*->salaryHigh",
                    "*->dateEpoch")

    private fun selectRawDataWithSort(): List<String>? {
        val sortingParams = SortingParams().asc().get(*takeParams).alpha()
        return jedisToDBCompany.sort(INDEX_KEY_FOR_SORT, sortingParams)
    }

    private fun String.isNumber() = this.all { it.isDigit() }
    private fun isLoadTarget(fromDateEpoch: Long, targetDateEpoch: String?): Boolean {
        return when {
            fromDateEpoch == 0L -> true
            targetDateEpoch == null -> false
            targetDateEpoch.isNumber() -> (targetDateEpoch.toLong() > fromDateEpoch)
            else -> false
        }
    }
}
