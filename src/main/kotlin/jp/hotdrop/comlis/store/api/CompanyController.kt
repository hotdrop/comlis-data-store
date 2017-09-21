package jp.hotdrop.comlis.store.api

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import jp.hotdrop.comlis.store.model.Company
import jp.hotdrop.comlis.store.service.CompanyService
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

    @ApiOperation(value = "Get companies data", notes = "Get companies data. if fromDateEpoch param are specified, data after this date is acquired. ")
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun companies(@ApiParam(value = "fromDateEpoch")
                  @RequestParam fromDateEpoch: Long = 0): ResponseEntity<List<Company>> {
        val companies = service.load(fromDateEpoch) ?: return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity.ok(companies)
    }
}
