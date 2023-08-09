package com.extremeSolution.ecommerce.app.uiState

sealed class UiState<T>(
    val data: T? = null,
    val message: String? = null,
    val errorType: ErrorType = ErrorType.UNKNOWN
) {

    class Success<T>(data: T?) : UiState<T>(data)
    class Error<T>(errorType: ErrorType, message: String? = null, data: T? = null) : UiState<T>(data, message, errorType)
    class Loading<T> : UiState<T>()

}

enum class ErrorType{
    API_ERROR,
    EXCEPTION,
    UNKNOWN
}