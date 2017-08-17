package jp.hotdrop.costore.repository

import redis.clients.jedis.Jedis
import redis.clients.jedis.SortingParams

/**
 * Redisのデータベースselect番号を定義したenum
 */
enum class DatabaseNo { Key, Company }

/**
 * データベースとのアクセスを担当するクラス
 */
class DatabaseClient(selectNo: DatabaseNo) {

    // TODO yaml等に外出しする
    private val HOST = "127.0.0.1"
    private val PORT = 3000

    private val INDEX_KEY_FOR_SORT = "indices"

    private val jedis: Jedis

    init {
        val dbNo = when(selectNo) {
            DatabaseNo.Key -> 0
            DatabaseNo.Company -> 1
        }
        jedis = Jedis(HOST, PORT).also { it.select(dbNo) }
    }

    /**
     * 指定したkeyのvalueを取得する。
     * keyが存在しない場合はnullを取得する。
     */
    fun find(key: String) = if(jedis.exists(key)) jedis.get(key) else null

    /**
     * 指定したkeyでvalueを保存する。
     */
    fun save(key: String, value: String) {
        jedis.set(key, value)
    }

    /**
     * Hash型のValueをキーでソートし、リスト形式で取得する。
     * リストには、キーの昇順で、takeParamsの順にvalueが格納される。
     */
    fun loadToHash(takeParams: Array<String>): List<String>? {
        val sortingParams = SortingParams().asc().get(*takeParams)
        return jedis.sort(INDEX_KEY_FOR_SORT, sortingParams) ?: null
    }

    /**
     * Hash型のValueを追加する。
     * なお、RedisはHash型をsortする際、別途キーリストが必要となる。
     * そのため、Set型のキーリストをキーINDEX_KEY_FOR_SORTで作成する。
     */
    fun saveToHash(key: String, map: HashMap<String, String?>) {
        jedis.sadd(INDEX_KEY_FOR_SORT, key)
        jedis.hmset(key, map)
    }

    /**
     * 対応するkeyを削除する。
     * Hash型のValueは削除しないのでそのまま残る。
     */
    fun removeToKey(key: String) {
        jedis.srem(INDEX_KEY_FOR_SORT, key)
    }

}