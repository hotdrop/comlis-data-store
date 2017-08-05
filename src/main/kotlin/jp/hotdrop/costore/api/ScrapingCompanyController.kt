package jp.hotdrop.costore.api

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import jp.hotdrop.costore.model.Company
import jp.hotdrop.costore.service.ScrapingCompanyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("scraping/companies")
class ScrapingCompanyController {

    @Autowired
    private lateinit var service: ScrapingCompanyService

    @ApiOperation(value = "会社情報を保存", notes = "受け取った会社情報を保存します。")
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun companies(@ApiParam(value = "会社情報")
                  @RequestBody companies: List<Company>) {
        service.save(companies)
    }
}