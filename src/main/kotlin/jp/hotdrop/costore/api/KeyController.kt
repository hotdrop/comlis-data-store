package jp.hotdrop.costore.api

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import jp.hotdrop.costore.model.KeyData
import jp.hotdrop.costore.service.KeyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/key")
class KeyController {

    @Autowired
    private lateinit var keyService: KeyService

    @ApiOperation(value = "Key情報保存", notes = "前回の処理やどこまで処理を行なったか目印にするためのKeyとValue情報を保存します。")
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun key(@ApiParam(value = "保存するKeyと値")
            @RequestBody keyVal: KeyData) {
        keyService.save(keyVal)
    }

    @ApiOperation(value = "Keyに対応した値を取得", notes = "保存したKey情報のうち、Keyに対応した値を取得します。")
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun key(@ApiParam(value = "取得したい値のKey")
            @RequestParam(value = "key") key: String): String {
        return keyService.find(key)
    }
}