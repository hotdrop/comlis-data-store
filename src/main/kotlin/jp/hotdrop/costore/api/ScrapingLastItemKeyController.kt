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
class ScrapingLastItemKeyController @Autowired constructor(
        val service: ScrapingLastItemKeyService
) {

    @ApiOperation(value = "前回の続きからスクレイピングを開始するための目印情報を保存",
            notes = "前回の続きからスクレイピングを開始するための目印情報を保存します。")
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun lastItemKey(@ApiParam(value = "前回の続きからスクレイピングを開始するための目印情報")
                    @RequestBody lastItemKey: String) {
        service.save(lastItemKey)
    }

    @ApiOperation(value = "前回の続きからスクレイピングを開始するための目印情報を取得",
            notes = "前回の続きからスクレイピングを開始するための目印情報を取得します。")
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun lastItemKey(): ResponseEntity<String> {
        val itemKey = service.find() ?: return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity.ok(itemKey)
    }

    @ApiOperation(value = "目印情報を削除(検証用)", notes = "目印情報を削除します。(検証用)")
    @RequestMapping(method = arrayOf(RequestMethod.DELETE))
    fun lastItemKey(@ApiParam(value = "検証用のためこのパラメータのNumberがコード内のものと一致していないと削除できません。")
                    @RequestParam unlockNum: Int) {
        if(unlockNum == 2980) {
            service.delete()
        }
    }
}