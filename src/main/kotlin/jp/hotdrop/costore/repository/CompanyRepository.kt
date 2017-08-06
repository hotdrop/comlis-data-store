package jp.hotdrop.costore.repository

import jp.hotdrop.costore.model.Company
import org.springframework.stereotype.Repository
import redis.clients.jedis.Jedis

@Repository
class CompanyRepository {

    private val DATABASE_NO = 1
    private val LIST_KEY = "company"

    fun save(company: Company) {
        // TODO Redisに保存するKey情報を外部から指定できてしまうとKeyの一意性や体系が壊れる可能性がるためこのアプリ内で発行＆管理する。
        val jedis = getJedis()
        // 保存形式をSetにするかListにするか・・
        // スクレイピング１回で送られてくるデータ単位でListの塊にするか、でもそれだとメンテナンス性が下がるような
        //jedis.rpush(LIST_KEY, company.jsonContents)
    }

    fun find(key: String): List<Company> {
        // TODO
        return arrayListOf()
    }

    // TODO 外部に切り出す
    private fun getJedis(): Jedis = Jedis("127.0.0.1", 3000).also { it.select(DATABASE_NO) }
}