package jp.hotdrop.costore.model

data class Company(
        val id: String = "",
        val name: String = "",
        val overview: String? = null,
        val workPlace: String? = null,
        val employeesNum: String? = null,
        val salaryLow: String? = null,
        val salaryHigh: String? = null
) {
    companion object {
        val FIELD_NUM = 7
    }
}