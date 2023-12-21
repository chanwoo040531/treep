package me.chnu.treep.presentation

// message 는 default parameter 를 설정 해놓으셨는데 nullable 하게 설정하신 이유가 있나요?
internal class ApiResponse<T>(
    val message: String = "",
    val body: T? = null,
) {
    companion object {
        fun error(message: String?): ApiResponse<Unit> = ApiResponse(message = message)

        fun <T> success(body: T?): ApiResponse<T> = ApiResponse(body = body)
    }
}
