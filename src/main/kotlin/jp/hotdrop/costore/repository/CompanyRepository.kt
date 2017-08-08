package jp.hotdrop.costore.repository

import jp.hotdrop.costore.model.Company
import org.springframework.stereotype.Repository

@Repository
class CompanyRepository {

    private val db = DatabaseClient(DatabaseNo.Company)

    // IDはKeyに使用するため、hashMapの対象にしない。
    private fun Company.toHashMap(): HashMap<String, String?> =
            hashMapOf("name" to this.name, "overView" to this.overView,
            "workPlace" to this.workPlace, "employeeNum" to this.employeeNum,
            "salaryLow" to this.salaryLow, "salaryHigh" to this.salaryHigh)

    // Redisの書き方がここにあるのは嫌だが、HashMapのキー名と一致させなければならないため
    // CompanyRepositoryで定義することにした。なお、最初の#はidを取得する指定。それ以降はHashMapと一致させる。
    private val takeParams = arrayOf("#",
            "*->name", "*->overView",
            "*->workPlace", "*->employeeNum",
            "*->salaryLow", "*->salaryHigh")

    fun save(companies: List<Company>) {
        if(companies.isEmpty()) {
            return
        }
        companies.forEach { company ->
            db.saveToHash(company.id, company.toHashMap())
        }
    }

    fun loadUnAcquired(): List<Company>? {

        val values = db.loadToHash(takeParams) ?: return null
        val companies = mutableListOf<Company>()

        val aggregationDataCount = values.size / Company.FIELD_NUM
        (0..aggregationDataCount - 1).forEach {
            val index = it * Company.FIELD_NUM
            val company = Company(
                    id = values[0 + index],
                    name = values[1 + index],
                    overView = values[2 + index],
                    workPlace = values[3 + index],
                    employeeNum = values[4 + index],
                    salaryLow = values[5 + index],
                    salaryHigh = values[6 + index])
            companies.add(company)
        }
        return companies
    }

    fun updateAcquired(keys: List<String>) {
        keys.forEach { db.deleteToHash(it) }
    }
}