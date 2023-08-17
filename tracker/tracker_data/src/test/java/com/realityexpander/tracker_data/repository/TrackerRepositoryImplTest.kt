package com.realityexpander.tracker_data.repository


import com.google.common.truth.Truth.assertThat
import com.realityexpander.tracker_data.remote.OpenFoodApi
import com.realityexpander.tracker_data.remote.malformedFoodResponse
import com.realityexpander.tracker_data.remote.validFoodResponse
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class TrackerRepositoryImplTest {

    private lateinit var repository: TrackerRepositoryImpl
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: OpenFoodApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1000, java.util.concurrent.TimeUnit.MILLISECONDS)
            .readTimeout(1000, java.util.concurrent.TimeUnit.MILLISECONDS)
            .connectTimeout(1000, java.util.concurrent.TimeUnit.MILLISECONDS)
            .build()
        api = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(OpenFoodApi::class.java)
        repository = TrackerRepositoryImpl(
            dao = mockk(relaxed = true),
            api = api
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Search food, valid response code, valid response body, returns SUCCESS`() =
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(validFoodResponse)
            )

            val result = repository.searchFood("DOES_NOT_MATTER", 1, 40)

            assertThat(result.isSuccess).isTrue()
        }

    @Test
    fun `Search food, INVALID response code, valid response body, returns FAILURE`() =
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(403)
                    .setBody(validFoodResponse)
            )

            val result = repository.searchFood("DOES_NOT_MATTER", 1, 40)

            assertThat(result.isFailure).isTrue()
        }

    @Test
    fun `Search food, NO response code, INVALID response body, returns FAILURE`() =
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setBody(malformedFoodResponse)
            )

            val result = repository.searchFood("DOES_NOT_MATTER", 1, 40)

            assertThat(result.isFailure).isTrue()
        }
}