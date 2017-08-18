package jp.hotdrop.costore.repository

import jp.hotdrop.costore.model.Company
import org.springframework.stereotype.Repository

@Repository
class CompanyRepository {

    private val db = DatabaseClient(DatabaseNo.Company)

    /**
     * 拡張関数
     * IDはKeyに使用するため、hashMapの対象にしない。
     */
    private fun Company.toHashMap(): HashMap<String, String?> =
            hashMapOf("name" to this.name,
                    "overView" to this.overView,
                    "workPlace" to this.workPlace,
                    "employeeNum" to this.employeeNum,
                    "salaryLow" to this.salaryLow,
                    "salaryHigh" to this.salaryHigh)

    /**
     * Redisの書き方がここにあるのは嫌だが、HashMapのキー名と一致させなければならないため
     * CompanyRepositoryで定義することにした。なお、最初の#はidを取得する指定。それ以降はHashMapと一致させる。
     */
    private val takeParams =
            arrayOf("#",
                    "*->name",
                    "*->overView",
                    "*->workPlace",
                    "*->employeeNum",
                    "*->salaryLow",
                    "*->salaryHigh")

    fun save(companies: List<Company>) {
        companies.forEach { company ->
            db.saveToHash(company.id, company.toHashMap())
        }
    }

    fun load(): List<Company>? {

        val results = db.loadToHash(takeParams) ?: return null

        val companies = mutableListOf<Company>()
        val aggregationDataCount = results.size / Company.FIELD_NUM
        (0 until aggregationDataCount)
                .map { it -> it * Company.FIELD_NUM }
                .mapTo(companies) {
                    Company(
                            id = results[0 + it],
                            name = results[1 + it],
                            overView = results[2 + it],
                            workPlace = results[3 + it],
                            employeeNum = results[4 + it],
                            salaryLow = results[5 + it],
                            salaryHigh = results[6 + it])
                }
        return companies
    }

    /**
     * 引数のkeysを取得済みに更新する。
     * データそのものは後々再取得したいので削除することはしたくない。
     * そのため、取得済みフラグなるカラムを追加することを検討したが、Redisで未取得フラグを使うのは微妙であった。
     * したがって、インデックスとして持っているKeyValueのSetから削除することでこのメソッドの機能を実現する。
     */
    fun updateAcquired(keys: List<String>) {
        keys.forEach { db.removeToKey(it) }
    }
}