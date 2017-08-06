package jp.hotdrop.costore.model

data class Company(
        val name: String = "",
        val overView: String? = null,
        val workPlace: String? = null,
        val employeeNum: Int? = null,
        val salaryLow: Int? = null,
        val salaryHigh: Int? = null,
        val url: String? = null,
        val note: String? = null
) {
    init {
        // IlleagalArgumentExceptionを起こすのは良くないか・・ステータス400にするべきか
        require(name != "") { "" }
    }
}
