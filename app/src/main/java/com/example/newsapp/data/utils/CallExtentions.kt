package com.example.newsapp.data.utils

import retrofit2.Response


suspend fun <R> makeApiCall(
    call: suspend () -> Result<R>,
    errorMessage: Int = 4567
) = try {
    call()
}catch (e: Exception) {
    Result.Error(CallException<Nothing>(errorMessage = e.message, errorCode = errorMessage))
}

fun <R> analyzeResponse(
    response: Response<R>
): Result<R> {
    return when {
        response.isSuccessful -> {
            Result.Success(response.body())
        }
        else -> {
            Result.Error(CallException(response.code(),response.message()))
        }
    }
}