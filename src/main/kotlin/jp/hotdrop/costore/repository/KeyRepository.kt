package jp.hotdrop.costore.repository

import org.springframework.stereotype.Repository

@Repository
class KeyRepository {

    private val LAST_ITEM_KEY = "last_item_key"

    private val db = DatabaseClient(DatabaseNo.Key)

    fun findLastItemKey() = db.find(LAST_ITEM_KEY)

    fun saveLastItemKey(value: String) {
        db.save(LAST_ITEM_KEY, value)
    }
}