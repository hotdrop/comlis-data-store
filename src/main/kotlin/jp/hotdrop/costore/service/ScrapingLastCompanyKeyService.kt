package jp.hotdrop.costore.service

import jp.hotdrop.costore.repository.KeyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ScrapingLastCompanyKeyService {

    @Autowired
    private lateinit var repository: KeyRepository

    fun find(): String {
        return repository.getLastCompanyKey()
    }

    fun save(lastCompanyKey: String) {
        repository.saveLastCompanyKey(lastCompanyKey)
    }
}