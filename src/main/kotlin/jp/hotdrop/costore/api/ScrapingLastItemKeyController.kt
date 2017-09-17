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

    @ApiOperation(value = "Save mark info for starting scraping from the last continuation",
            notes = "Save mark info for starting scraping from the last continuation.")
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun lastItemKey(@ApiParam(value = "mark info for starting scraping from the last continuation.")
                    @RequestBody lastItemKey: String) {
        service.save(lastItemKey)
    }

    @ApiOperation(value = "Get mark info for starting scraping from the last continuation",
            notes = "Get mark info for starting scraping from the last continuation.")
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun lastItemKey(): ResponseEntity<String> {
        val itemKey = service.find() ?: return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity.ok(itemKey)
    }

    @ApiOperation(value = "Delete mark info for starting scraping from the last continuation",
            notes = "Delete mark info for starting scraping from the last continuation.")
    @RequestMapping(method = arrayOf(RequestMethod.DELETE))
    fun lastItemKey(@ApiParam(value = "It can not be deleted unless the Number of this parameter matches the one in the code for verification.")
                    @RequestParam unlockNum: Int) {
        if(unlockNum == 2980) {
            service.delete()
        }
    }
}
