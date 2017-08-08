package jp.hotdrop.costore.model

data class Company(
        val id: String = "",
        val name: String = "",
        val overView: String? = null,
        val workPlace: String? = null,
        val employeeNum: String? = null,
        val salaryLow: String? = null,
        val salaryHigh: String? = null
) {
    companion object {
        val FIELD_NUM = 7
    }
}