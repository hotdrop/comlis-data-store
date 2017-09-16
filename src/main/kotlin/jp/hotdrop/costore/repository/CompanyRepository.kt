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
     * 拡張関数
     * IDはKeyに使用するため、hashMapの対象にしない。
     */
    private fun Company.toHashMap(): HashMap<String, String?> =
            hashMapOf("name" to this.name,
                    "overview" to this.overview,
                    "workPlace" to this.workPlace,
                    "employeesNum" to this.employeesNum,
                    "salaryLow" to this.salaryLow,
                    "salaryHigh" to this.salaryHigh)

    /**
     * Redisの書き方がここにあるのは嫌だが、HashMapのキー名と一致させなければならないため
     * CompanyRepositoryで定義することにした。なお、最初の#はidを取得する指定。それ以降はHashMapと一致させる。
     */
    private val takeParams =
            arrayOf("#",
                    "*->name",
                    "*->overview",
                    "*->workPlace",
                    "*->employeesNum",
                    "*->salaryLow",
                    "*->salaryHigh")

    fun save(companies: List<Company>) {
        companies.forEach { company ->
            // Hash型をsortする際、別途キーセットが必要となるのでsaddしている。
            jedis.sadd(INDEX_KEY_FOR_SORT, company.id)
            jedis.hmset(company.id, company.toHashMap())
        }
    }

    fun load(): List<Company>? {

        val sortingParams = SortingParams().asc().get(*takeParams).alpha()
        val results = jedis.sort(INDEX_KEY_FOR_SORT, sortingParams) ?: return null

        val companies = mutableListOf<Company>()
        val aggregationDataCount = results.size / Company.FIELD_NUM
        (0 until aggregationDataCount)
                .map { it -> it * Company.FIELD_NUM }
                .mapTo(companies) {
                    Company(
                            id = results[0 + it],
                            name = results[1 + it],
                            overview = results[2 + it],
                            workPlace = results[3 + it],
                            employeesNum = results[4 + it],
                            salaryLow = results[5 + it],
                            salaryHigh = results[6 + it])
                }
        return companies
    }

    /**
     * 引数のkeysを取得済みに更新する。
     * 取得済みフラグカラムの追加を検討したが、Redisでそれを使うのは微妙。
     * そのため、インデックスとして持っているKeyValueのSetから削除することで実現する。
     */
    fun updateAcquired(keys: List<String>) {
        //Hash型のValueは削除しないのでそのまま残る。
        keys.forEach { jedis.srem(INDEX_KEY_FOR_SORT, it) }
    }
}