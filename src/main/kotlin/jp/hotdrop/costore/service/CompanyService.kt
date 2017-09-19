package jp.hotdrop.costore.service

import jp.hotdrop.costore.exception.ComlisException
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
        log.info("Number of saved companies: ${companies.size}")
        companies.forEach { company ->
            if(company.id.isEmpty() || company.name.isEmpty()) {
                throw ComlisException(400, "Detected Error data! ID or name is empty. ID=${company.id}, Name=${company.name} ")
            }
            repository.save(company)
        }
    }

    fun load(): List<Company>? {
        val companies = repository.load()
        if(companies == null) {
            log.info("There is no company data loaded.")
        } else {
            log.info("Number of loaded companies: ${companies.size}.")
        }
        return companies
    }

    /**
     * I did not want to delete the company data itself.
     * For that reason, I prepared a column that will not be subject to acquisition 
     *  from the next time onwards, and expressed delete by updating it.
     */
    fun delete(ids: List<String>) {
        log.info("Number of deleted company ids: ${ids.size}")
        repository.updateAcquired(ids)
    }
}
