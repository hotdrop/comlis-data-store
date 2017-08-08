package jp.hotdrop.costore.model

data class Company(
        val name: String = "",
        val overView: String? = null,
        val workPlace: String? = null,
        val employeeNum: String? = null,
        val salaryLow: String? = null,
        val salaryHigh: String? = null
)