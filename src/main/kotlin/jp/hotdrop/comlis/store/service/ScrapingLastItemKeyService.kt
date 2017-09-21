package jp.hotdrop.comlis.store.service

import jp.hotdrop.comlis.store.repository.KeyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ScrapingLastItemKeyService @Autowired constructor(
        val repository: KeyRepository
) {

    fun find(): String? =
            repository.findLastItemKey()

    fun save(lastCompanyKey: String) {
        repository.saveLastItemKey(lastCompanyKey)
    }

    fun delete() {
        repository.deleteLastItemKey()
    }
}