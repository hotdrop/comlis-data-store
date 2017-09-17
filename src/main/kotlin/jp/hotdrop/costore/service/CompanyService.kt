package jp.hotdrop.costore.service

import jp.hotdrop.costore.model.Company
import jp.hotdrop.costore.repository.CompanyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CompanyService @Autowired constructor(
        val repository: CompanyRepository
) {

    fun save(companies: List<Company>) {
        // TODO implements validation check for POST data.
        repository.save(companies)
    }

    fun load(): List<Company>? = repository.load()

    /**
     * I did not want to delete the company data itself.
     * For that reason, I prepared a column that will not be subject to acquisition 
     *  from the next time onwards, and expressed delete by updating it.
     */
    fun delete(ids: List<String>) {
        repository.updateAcquired(ids)
    }
}
