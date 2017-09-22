package jp.hotdrop.comlis.store.model

data class Company(
        val id: String = "",
        val name: String = "",
        val overview: String? = null,
        val workPlace: String? = null,
        val employeesNum: String? = null,
        val salaryLow: String? = null,
        val salaryHigh: String? = null,
        val url: String? = null,
        // If dateEpoch is null or not number, it can not be acquired with API.
        val dateEpoch: String? = null
) {
    companion object {
        val FIELD_NUM = 9
    }
}