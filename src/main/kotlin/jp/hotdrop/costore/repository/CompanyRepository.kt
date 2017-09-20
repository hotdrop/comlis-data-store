package jp.hotdrop.costore.repository

import jp.hotdrop.costore.model.Company
import jp.hotdrop.costore.repository.config.RedisProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import redis.clients.jedis.SortingParams

@Repository
class CompanyRepository @Autowired constructor(
        val redisProperties: RedisProperties
) {

    private val INDEX_KEY_FOR_SORT = "indices"

    private val jedis by lazy {
        JedisClient(DatabaseNo.Company, redisProperties).create()
    }

    /**
     * Extension function.
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

    private fun String.isNumber() = this.all { it.isDigit() }

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

    fun save(company: Company) {
        // The reason for sadd is because another key sets is required
        //  when sorting Redis Hash type.
        jedis.sadd(INDEX_KEY_FOR_SORT, company.id)
        jedis.hmset(company.id, company.toHashMap())
    }

    fun load(fromDateEpoch: Long): List<Company>? {

        val sortingParams = SortingParams().asc().get(*takeParams).alpha()
        val results = jedis.sort(INDEX_KEY_FOR_SORT, sortingParams) ?: return null

        val companies = mutableListOf<Company>()
        val aggregationDataCount = results.size / Company.FIELD_NUM

        for(dataCnt in 0 until aggregationDataCount) {
            val index = dataCnt * Company.FIELD_NUM
            val companyDateEpoch = (results[7 + index])
            if(isLoadTarget(fromDateEpoch, companyDateEpoch)) {
                companies.add(Company(
                        id = results[0 + index],
                        name = results[1 + index],
                        overview = results[2 + index],
                        workPlace = results[3 + index],
                        employeesNum = results[4 + index],
                        salaryLow = results[5 + index],
                        salaryHigh = results[6 + index],
                        dateEpoch = results[7 + index])
                )
            }

        }
        return companies
    }

    private fun isLoadTarget(fromDateEpoch: Long, targetDateEpoch: String?): Boolean {
        return when {
            fromDateEpoch == 0L -> true
            targetDateEpoch == null -> false
            targetDateEpoch.isNumber() -> (targetDateEpoch.toLong() > fromDateEpoch)
            else -> false
        }
    }
}
