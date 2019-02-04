package com.example.kotlincoroutines.network

import com.example.kotlincoroutines.model.CodeStandardResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET


/**
 * Created by Ganesh Tikone on 4/2/19.
 * Company: Silicus Technologies Pvt. Ltd.
 * Email: ganesh.tikone@silicus.com
 * Class: RequestInterface
 * Description: RequestInterface Retrofit Interface
 */
interface RequestInterface {

    @GET("GetCodeStandards")
    fun getCodeStandards(): Deferred<Response<CodeStandardResponse>>

    //Demo URL:  http://fhs-web-api-01.azurewebsites.net/api/MobileApi/GetCodeStandards
}