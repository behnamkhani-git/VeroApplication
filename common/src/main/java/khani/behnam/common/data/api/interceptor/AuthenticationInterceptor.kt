package khani.behnam.common.data.api.interceptor

import khani.behnam.common.data.api.ApiConstant
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor @Inject constructor() : Interceptor {

    private var accessToken: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // If there is no access token, get it
        if (accessToken == null) {
            accessToken = obtainAccessToken()
        }

        // Add the access token to the request
        val authenticatedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(authenticatedRequest)

        // If the token has expired, get a new token and retry the request
        if (response.code == 401) {
            accessToken = obtainAccessToken()
            val newAuthenticatedRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
            return chain.proceed(newAuthenticatedRequest)
        }

        return response
    }

    private fun obtainAccessToken(): String? {
        val client = OkHttpClient()
        val mediaType = "application/json".toMediaTypeOrNull()

        val requestBody = JSONObject().apply {
            put("username", ApiConstant.USER_NAME)
            put("password", ApiConstant.PASSWORD)
        }

        val body = RequestBody.create(mediaType, requestBody.toString())

        val request = Request.Builder()
            .url(ApiConstant.BASE_URL + ApiConstant.LOGIN_ENDPOINT)
            .post(body)
            .addHeader("Authorization", ApiConstant.AUTH_HEADER)
            .addHeader("Content-Type", ApiConstant.CONTENT_TYPE)
            .build()

        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }

            val responseBody = response.body?.string()
            val jsonObject = JSONObject(responseBody ?: "")
            val oauthObject = jsonObject.getJSONObject("oauth")
            return oauthObject.getString("access_token")

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}