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

    @ApiOperation(value = "Save companies data", notes = "Save companies data.")
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun companies(@ApiParam(value = "companies data")
                  @RequestBody companies: List<Company>) {
        service.save(companies)
    }

    @ApiOperation(value = "Get companies data", notes = "Get companies data.")
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun companies(): ResponseEntity<List<Company>> {
        val companies = service.load() ?: return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity.ok(companies)
    }

    @ApiOperation(value = "Delete companies data", notes = "Delete companies data.")
    @RequestMapping(method = arrayOf(RequestMethod.DELETE))
    fun doNotSendNextIds(@ApiParam(value = "ID of company data to delete")
                         @RequestBody ids: List<String>) {
        service.delete(ids)
    }
}
