package data

data class CurrentData(
    val _id: String,
    val company_id: Int,
    val company_name: String,
    val company_verified: String,
    val experience: String,
    val job_type: String,
    val location: String,
    val title: String
)
data class JobsData(
    val code: Int,
    val msg: List<Msg>,
    val status: String
)

data class
Msg(
    val current_data: List<CurrentData>,
    val page_info: PageInfo
)

data class PageInfo(
    val page: Int,
    val total_pages: Int
)