package jp.hotdrop.costore.service

import jp.hotdrop.costore.repository.KeyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ScrapingLastItemKeyService {

    @Autowired
    private lateinit var repository: KeyRepository

    fun find() = repository.findLastItemKey()

    fun save(lastCompanyKey: String) {
        repository.saveLastItemKey(lastCompanyKey)
    }
}