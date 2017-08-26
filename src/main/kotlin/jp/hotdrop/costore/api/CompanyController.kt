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
class CompanyController @Autowired constructor(
        val service: CompanyService
) {

    @ApiOperation(value = "会社情報保存", notes = "会社情報を保存します。複数指定も可能です。")
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun companies(@ApiParam(value = "会社情報")
                  @RequestBody companies: List<Company>) {
        service.save(companies)
    }

    @ApiOperation(value = "会社情報取得", notes = "保存されている会社情報を取得します。過去取得した会社情報は取得しません。")
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun companies(): ResponseEntity<List<Company>> {
        val companies = service.load() ?: return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity.ok(companies)
    }
}