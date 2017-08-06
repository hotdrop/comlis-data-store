package jp.hotdrop.costore.api

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import jp.hotdrop.costore.model.Company
import jp.hotdrop.costore.service.CompaniesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("companies")
class CompaniesController {

    @Autowired
    private lateinit var service: CompaniesService

    @ApiOperation(value = "会社情報保存", notes = "会社情報を保存します。複数指定も可能です。")
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun companies(@ApiParam(value = "会社情報")
                  @RequestBody companies: List<Company>) {
        service.save(companies)
    }

    @ApiOperation(value = "会社情報取得", notes = "保存されている会社情報を取得します。")
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun companies(@ApiParam(value = "取得したい会社情報のキー")
                  @RequestParam(value = "key") key: String) {
        // TODO getしたデータをResponseBodyに入れる。
        service.get(key)
    }
}