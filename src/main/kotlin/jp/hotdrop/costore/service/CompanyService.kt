package jp.hotdrop.costore.service

import jp.hotdrop.costore.model.Company
import jp.hotdrop.costore.repository.CompanyRepository
import jp.hotdrop.costore.repository.KeyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CompanyService {

    @Autowired
    private lateinit var companyRepository: CompanyRepository
    @Autowired
    private lateinit var keyRepository: KeyRepository

    fun findUnAcquired(): List<Company>? =
            companyRepository.findUnAcquired()

    fun save(companies: List<Company>) {
        val idxKey = keyRepository.findPreviousAddCompanyIndexKey()
        val currentIdxKey = companyRepository.save(idxKey, companies)
        keyRepository.saveCompanyIndexKey(currentIdxKey)
    }
}