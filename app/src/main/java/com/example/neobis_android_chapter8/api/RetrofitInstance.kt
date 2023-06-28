package com.example.neobis_android_chapter8.api

import com.example.neobis_android_chapter8.utils.Utils
import com.example.neobis_android_chapter8.utils.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(AuthorizationInterceptor()) // Add the AuthorizationInterceptor
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val authApi by lazy {
            retrofit.create(AuthInterface::class.java)
        }

        val productApi by lazy {
            retrofit.create(ProductInterface::class.java)
        }
    }

    private class AuthorizationInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request()
            val newRequest = if (requiresAuthorization(request)) {
                val token = Utils.access_token
                val authHeader = "Bearer $token"
                request.newBuilder()
                    .header("Authorization", authHeader)
                    .build()
            } else {
                request
            }
            return chain.proceed(newRequest)
        }

        private fun requiresAuthorization(request: okhttp3.Request): Boolean {
            return request.url.toString().endsWith("full_register/") || request.url.toString().endsWith("confirm/")
                    || request.url.toString().endsWith("profile/") || request.url.toString().endsWith("product/")
                    || request.url.toString().endsWith("product/my/")
                    || request.url.toString().endsWith("product/{id}/")
        }
    }
}
