package raum.muchbeer.total.repository

import kotlinx.coroutines.flow.*
import raum.muchbeer.total.model.DataState
import raum.muchbeer.total.model.PapState
import java.lang.Exception

inline fun <ResultType, RequestType> networkBoundResource(
        crossinline query: () -> Flow<ResultType>,
        crossinline fetch: suspend () -> RequestType,
        crossinline saveFetchResult: suspend (RequestType) -> Unit,
        crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(PapState.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { PapState.Success(it) }
        } catch (throwable: Throwable) {
            query().map { PapState.Error(throwable, it) }
        }
    } else {
        query().map { PapState.Success(it) }
    }

    emitAll(flow)
}