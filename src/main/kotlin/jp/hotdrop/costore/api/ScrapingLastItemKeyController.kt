package jp.hotdrop.costore.api

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import jp.hotdrop.costore.service.ScrapingLastItemKeyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("scraping/companies/lastItemKey")
class ScrapingLastItemKeyController {

    @Autowired
    private lateinit var service: ScrapingLastItemKeyService

    @ApiOperation(value = "スクレイピングで取得した最新データを一意に識別する情報を保存",
            notes = "どこまでスクレイピングでデータを取得したか目印にするための情報を保存します。")
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun lastItemKey(@ApiParam(value = "スクレイピングしたデータを一意に識別できる情報")
                    @RequestParam lastItemKey: String) {
        service.save(lastItemKey)
    }

    @ApiOperation(value = "前回保存した最新データの識別情報を取得", notes = "前回保存した最新データの識別情報を取得します。")
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun lastItemKey(): String {
        return service.find()
    }
}