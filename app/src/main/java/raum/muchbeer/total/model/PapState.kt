package raum.muchbeer.total.model

sealed class PapState<T>(
        val data : T? = null,
        val message : Throwable? = null
)  {

    class Success<T>(data: T) : PapState<T>(data)
    class Loading<T>(data : T?=null) : PapState<T>(data)
    class Error<T>(message: Throwable, data: T?=null) : PapState<T>(data, message)
}