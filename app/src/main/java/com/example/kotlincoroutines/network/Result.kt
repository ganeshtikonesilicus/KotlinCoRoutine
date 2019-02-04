package com.example.kotlincoroutines.network


/**
 * Created by Ganesh Tikone on 4/2/19.
 * Company: Silicus Technologies Pvt. Ltd.
 * Email: ganesh.tikone@silicus.com
 * Class: Result
 * Description: Result to handle response from Server
 */
sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}