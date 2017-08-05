package jp.hotdrop.costore.repository

import jp.hotdrop.costore.model.Company
import org.springframework.stereotype.Repository
import redis.clients.jedis.Jedis

@Repository
class CompanyRepository {

    private val DATABASE_NO = 1
    private val LIST_KEY = "companies"

    /**
     * List側でデータを保持する
     */
    fun save(company: Company) {
        // TODO キーはスクレイピングアプリ側ではなく、こちら側で管理する。
        //      キーは一意の連番にする。
        val jedis = getJedis()
        jedis.rpush(LIST_KEY, company.jsonContents)
    }

    /**
     * Listからポップする
     */
    fun find(key: String) {
    }

    // TODO 外部に切り出す
    private fun getJedis(): Jedis = Jedis("127.0.0.1", 3000).also { it.select(DATABASE_NO) }
}