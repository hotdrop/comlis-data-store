package jp.hotdrop.costore.service

import jp.hotdrop.costore.model.Company
import jp.hotdrop.costore.repository.CompanyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CompanyService {

    @Autowired
    private lateinit var repository: CompanyRepository

    fun save(companies: List<Company>) {
        repository.save(companies)
    }

    fun load(): List<Company>? {
        val companies = repository.load() ?: return null
        // 本当は一旦送信し終わった後にこれをやりたい。
        repository.updateAcquired(companies.map { it.id })

        return companies
    }
}