package uz.otamurod.domain.util

sealed class DataState<out T>(
    val data: T? = null,
    val message: String? = null
) {
    // Represents a successful state with data
    class Success<out T>(data: T) : DataState<T>(data)

    // Represents an error state with an associated error message
    class Error<out T>(message: String) : DataState<T>(message = message)

    // Represents a loading state
    object Loading : DataState<Nothing>()
}