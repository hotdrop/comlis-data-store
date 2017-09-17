package jp.hotdrop.costore.service

import jp.hotdrop.costore.model.Company
import jp.hotdrop.costore.repository.CompanyRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CompanyService @Autowired constructor(
        val repository: CompanyRepository
) {

    private val log = LoggerFactory.getLogger("jp.hotdrop.costore.trace")

    fun save(companies: List<Company>) {
        // TODO implements validation check for POST data.
        log.info("Save companies count = ${companies.size}")
        repository.save(companies)
    }

    fun load(): List<Company>? {
        val companies = repository.load()
        if(companies == null) {
            log.info("Load companies is nothing.")
        } else {
            log.info("Load companies count = ${companies.size}.")
        }
        return companies
    }

    /**
     * I did not want to delete the company data itself.
     * For that reason, I prepared a column that will not be subject to acquisition 
     *  from the next time onwards, and expressed delete by updating it.
     */
    fun delete(ids: List<String>) {
        log.info("Delete companies count = ${ids.size}")
        repository.updateAcquired(ids)
    }
}
