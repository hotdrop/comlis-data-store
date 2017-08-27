package jp.hotdrop.costore.service

import jp.hotdrop.costore.model.Company
import jp.hotdrop.costore.repository.CompanyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CompanyService @Autowired constructor(
        val repository: CompanyRepository
) {

    fun save(companies: List<Company>) {
        // TODO 送信された値のバリデーションチェックが必要
        repository.save(companies)
    }

    fun load(): List<Company>? = repository.load()

    /**
     * 会社情報そのものは削除したくなかったため
     * 次回以降、取得対象にしないようなカラムを用意してそこを更新する。
     */
    fun delete(ids: List<String>) {
        repository.updateAcquired(ids)
    }
}