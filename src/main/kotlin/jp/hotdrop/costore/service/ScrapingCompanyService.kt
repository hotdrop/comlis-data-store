package jp.hotdrop.costore.service

import jp.hotdrop.costore.model.Company
import jp.hotdrop.costore.repository.CompanyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ScrapingCompanyService {

    @Autowired
    private lateinit var repository: CompanyRepository

    fun save(companies: List<Company>) {
        if(companies.isEmpty()) {
            return
        }
        // TODO coroutine使った方がいい
        companies.forEach{ repository.save(it) }
    }
}