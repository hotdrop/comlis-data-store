package jp.hotdrop.costore.api

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import jp.hotdrop.costore.model.Company
import jp.hotdrop.costore.service.CompanyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("companies")
class CompanyController {

    @Autowired
    private lateinit var service: CompanyService

    @ApiOperation(value = "会社情報保存", notes = "会社情報を保存します。複数指定も可能です。")
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun companies(@ApiParam(value = "会社情報")
                  @RequestBody companies: List<Company>) {
        service.save(companies)
    }

    // データ取得（過去取得したデータは除外）
    @ApiOperation(value = "会社情報取得", notes = "保存されている会社情報を取得します。過去取得した会社情報は取得しません。")
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun companies(): ResponseEntity<List<Company>> {
        val companies = service.findUnAcquired() ?: return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity.ok(companies)
    }

    // データ取得（過去取得したデータ含む）
    // 過去取得したデータは除外した場合のデータ件数
    // 全データ件数
}