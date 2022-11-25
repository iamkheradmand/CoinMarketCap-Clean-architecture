package com.example.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.ApiService
import com.example.data.datasource.CoinLocalSource
import com.example.data.datasource.CoinRemoteSource
import com.example.data.mapper.CoinInfoEntityEntityMapper
import com.example.data.mapper.GetCoinBaseResponseToDomainModelMapper
import com.example.data.mapper.GetInfoResponseToDomainModelMapper
import com.example.data.mapper.SortFilterToQueryMapper
import com.example.data.model.remote.*
import com.example.data.utils.MockResponseFileReader
import com.example.data.utils.RepositoryHelper
import com.example.domain.entities.InfoDomainModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/25/2022.
 */

class CoinRepositoryImplTest {

    @Inject
    lateinit var coinLocalSource: CoinLocalSource

    @Inject
    lateinit var remoteSource: CoinRemoteSource

    @Inject
    lateinit var coinBaseResponseMapper: GetCoinBaseResponseToDomainModelMapper

    @Inject
    lateinit var infoResponseMapper: GetInfoResponseToDomainModelMapper

    @Inject
    lateinit var sortFilterToQueryMapper: SortFilterToQueryMapper

    @Inject
    lateinit var repositoryHelper: RepositoryHelper

    @Inject
    lateinit var  coinInfoEntityEntityMapper: CoinInfoEntityEntityMapper

    private lateinit var coinRepositoryImpl: CoinRepositoryImpl
    private lateinit var mockService: ApiService

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val server = MockWebServer()

    @Before
    fun setUp() {
        server.start(8000)

        val baseUrl = server.url("/").toString()
        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        mockService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build().create(ApiService::class.java)

        coinLocalSource = mockk()
        remoteSource = mockk()
        coinBaseResponseMapper = mockk()
        infoResponseMapper = mockk()
        sortFilterToQueryMapper = mockk()
        repositoryHelper = mockk()
        coinInfoEntityEntityMapper = mockk()

        coinRepositoryImpl =
            CoinRepositoryImpl(
                coinLocalSource,
                remoteSource,
                coinBaseResponseMapper,
                infoResponseMapper,
                sortFilterToQueryMapper,
                coinInfoEntityEntityMapper,
                repositoryHelper
            )
    }


    @ExperimentalCoroutinesApi
    @Test
    fun testGetInfoApi() = runTest {

        val mockedInfoResponse = MockResponseFileReader("JsonSample/infoResponseJson.json").content
        server.enqueue(MockResponse().setResponseCode(200).setBody(mockedInfoResponse))

        val info = GetInfoResponse(
            id = 1,
            name = "Bitcoin",
            symbol = "BTC",
            logo = "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
            description = "Bitcoin (BTC) is a cryptocurrency . Users are able to generate BTC through the process of mining. Bitcoin has a current supply of 19,216,456. The last known price of Bitcoin is 16,464.21514273 USD and is down -0.79 over the last 24 hours. It is currently trading on 9860 active market(s) with $24,011,613,914.84 traded over the last 24 hours. More information can be found at https://bitcoin.org/.",
            urls = UrlsResponseModel(
                website = arrayListOf("https://bitcoin.org/"),
                twitter = arrayListOf(),
                reddit = arrayListOf("https://reddit.com/r/bitcoin")
            )
        )

        val expect = InfoDomainModel(
            id = 1,
            logo = "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
            name = "Bitcoin",
            symbol = "BTC",
            description = "Bitcoin (BTC) is a cryptocurrency . Users are able to generate BTC through the process of mining. Bitcoin has a current supply of 19,216,456. The last known price of Bitcoin is 16,464.21514273 USD and is down -0.79 over the last 24 hours. It is currently trading on 9860 active market(s) with $24,011,613,914.84 traded over the last 24 hours. More information can be found at https://bitcoin.org/.",
            website = arrayListOf("https://bitcoin.org/")
        )

        coEvery { remoteSource.getInfo(1) } returns mockService.getInfo(1)

        coEvery { infoResponseMapper.toDomainModel(info) } returns expect

        val infoFlow = coinRepositoryImpl.getInfo(1)
        infoFlow.collect {
            assertEquals(expect, it.data)
        }

    }

}