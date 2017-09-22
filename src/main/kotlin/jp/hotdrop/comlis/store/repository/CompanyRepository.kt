package jp.hotdrop.comlis.store.repository

import jp.hotdrop.comlis.store.model.Company
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class CompanyRepository @Autowired constructor(
        val dbClient: RedisClient
) {

    fun save(company: Company) {
        dbClient.saveCompany(company)
    }

    fun load(fromDateEpoch: Long): List<Company>? =
            dbClient.findCompanies(fromDateEpoch)
}
