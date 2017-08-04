package jp.hotdrop.costore.service

import jp.hotdrop.costore.model.KeyData
import jp.hotdrop.costore.repository.KeyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class KeyService {

    @Autowired
    private lateinit var repository: KeyRepository

    fun find(key: String): String {
        return repository.find(key)
    }

    fun save(kv: KeyData) {
        repository.save(kv)
    }
}