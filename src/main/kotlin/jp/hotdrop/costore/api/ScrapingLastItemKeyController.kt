package jp.hotdrop.costore.api

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import jp.hotdrop.costore.service.ScrapingLastItemKeyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("scraping/lastItemKey")
class ScrapingLastItemKeyController {

    @Autowired
    private lateinit var service: ScrapingLastItemKeyService

    @ApiOperation(value = "スクレイピングで取得したコンテンツデータを一意に識別する情報を保存",
            notes = "前回の続きからスクレイピングを開始するための目印情報を保存します。")
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun lastItemKey(@ApiParam(value = "スクレイピングしたコンテンツデータを一意に識別できる情報")
                    @RequestParam lastItemKey: String) {
        service.save(lastItemKey)
    }

    @ApiOperation(value = "スクレイピングで取得したコンテンツデータを一意に識別する情報を取得",
            notes = "前回の続きからスクレイピングを開始するための目印情報を取得します。")
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun lastItemKey(): ResponseEntity<String> {
        val itemKey = service.find() ?: return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity.ok(itemKey)
    }
}