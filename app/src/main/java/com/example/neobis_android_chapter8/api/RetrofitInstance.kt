package com.example.neobis_android_chapter8.api

import com.example.neobis_android_chapter8.utils.Utils
import com.example.neobis_android_chapter8.utils.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(AuthorizationInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
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
            val path = request.url.encodedPath
            return path.endsWith("full_register/") ||
                    path.endsWith("confirm/") ||
                    path.endsWith("profile/") ||
                    path.endsWith("product/") ||
                    path.endsWith("product/my/") ||
                    path.contains("product/") && request.method == "DELETE" ||
                    path.contains("product/") && request.method == "PUT" ||
                    path.contains("like/") || path.contains("fans/")
        }
    }
}
