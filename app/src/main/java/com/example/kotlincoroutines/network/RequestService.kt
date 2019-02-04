package com.example.kotlincoroutines.network

import android.content.Context
import com.example.kotlincoroutines.model.CodeStandardResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Created by Ganesh Tikone on 4/2/19.
 * Company: Silicus Technologies Pvt. Ltd.
 * Email: ganesh.tikone@silicus.com
 * Class: RequestService
 * Description: RequestService , Retrofit class
 */
class RequestService(context: Context) {


    /**
     * RequestInterface object
     */
    private var requestInterface: RequestInterface? = null

    /**
     * OkHTTPClient , Logger for Retrofit
     */
    private var okHttpClient: OkHttpClient? = null


    /**
     * TIME OUT
     */
    private val REQUEST_TIME_OUT = 60L

    /**
     * Context
     */
    private val mContext = context

    companion object {

        /**
         * TAG
         */
        private val TAG = RequestService::class.java.simpleName

        /**
         * BASE URL
         */
        private var BASEURL = "http://fhs-web-api-01.azurewebsites.net/api/MobileApi/"

    }

    init {
        // Initialise Request Interface Object
        initRequestInterface()
    }

    /**
     * initRequestInterface
     */
    private fun initRequestInterface() {
        initOkHTTPClientLogger()

        requestInterface = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient!!)
            .build()
            .create(RequestInterface::class.java)
    }


    /**
     * initOkHTTPClientLogger
     */
    private fun initOkHTTPClientLogger() {

        val builder = OkHttpClient.Builder()
            .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        builder.addInterceptor(interceptor)

        builder.addInterceptor { chain: Interceptor.Chain? ->

            //TODO: Missing code of check if connected to network

//            if (!Utils.isInternetAvailable(mContext)) {
//                throw NoConnectivityException()
//            }

            val builderr = chain!!.request().newBuilder()
            chain.proceed(builderr.build())
        }

        builder.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")

            // Adding Authorization token (API Key)
            // Requests will be denied without API key

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        okHttpClient = builder.build()
    }

    /**
     * Top Level function to avoid writing try catch block to each api function
     */
    suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> = try {
        call.invoke()
    } catch (e: Exception) {
        Result.Error(IOException(errorMessage, e))
    }

    /**
     * Get Code Standards from Server with
     * help of Suspended function call
     */
    suspend fun getCodeStandards() = safeApiCall(
        call = { codeStandards() },
        errorMessage = "Error occurred"
    )


    /**
     * Suspend function to make api call with help of
     * Coroutines
     */
    private suspend fun codeStandards(): Result<CodeStandardResponse> {

        /*
           Because of this change, we can no longer use our chained map operation from the RxJava API — and
           in fact at this point, we don’t even have our data available as we only have the Deferred instance
           and not the value that represents it.
           What we need to do here is use the await() function to wait for the result of our request and
           then continue with our function body once a value has been received
         */

        val response = requestInterface?.getCodeStandards()?.await()

        return if (null != response) {
            if (response.isSuccessful
                && null != response.body()
            ) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(IOException(response.message()))
            }
        } else {
            Result.Error(IOException("Unable to fetch data"))
        }
    }
}