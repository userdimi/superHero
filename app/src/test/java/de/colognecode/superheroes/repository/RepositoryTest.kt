package de.colognecode.superheroes.repository

import androidx.lifecycle.LiveData
import de.colognecode.superheroes.di.apiModule
import de.colognecode.superheroes.di.databaseModule
import de.colognecode.superheroes.repository.database.SuperHeroDao
import de.colognecode.superheroes.repository.database.entities.SuperHero
import de.colognecode.superheroes.repository.model.ResultsItem
import de.colognecode.superheroes.repository.model.SuperHeroesResponse
import de.colognecode.superheroes.repository.network.ApiKeyQuery
import de.colognecode.superheroes.repository.network.SuperHeroesApi
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock
import retrofit2.Response

internal class RepositoryTest : KoinTest {

    private val mockSuperHeroesApi: SuperHeroesApi by inject()
    private val mockSuperHeroDao: SuperHeroDao by inject()
    private val mockApiKeyQuery: ApiKeyQuery by inject()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            apiModule,
            databaseModule
        )
    }

    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { clazz ->
        mockkClass(clazz)
    }

    @BeforeEach
    fun initMocks() {
        val mockResultItem = declareMock<ResultsItem> {
            every { id } returns "12345"
            every { name } returns "Hulk"
            every { thumbnail?.path } returns "https://foo.bar.de"
        }
        val mockResultsItemList = listOf(mockResultItem)
        val mockResponse = declareMock<Response<SuperHeroesResponse>> {
            every { isSuccessful } returns true
            every { body()?.data?.results } returns mockResultsItemList
        }
        val mockApiKeyQuery = mockk<ApiKeyQuery>()
        val mockLiveData = mockk<LiveData<List<SuperHero>>>()
        val mockSuperHeroDao = declareMock<SuperHeroDao> {
            every { getAllSuperHeroes() } returns mockLiveData
        }
        val mockSuperHeroApi = declareMock<SuperHeroesApi> {
            coEvery {
                fetchHeroes(
                    mockApiKeyQuery.timestamp,
                    mockApiKeyQuery.publicApiKey,
                    mockApiKeyQuery.apiKeyHash
                )
            } returns mockResponse
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Verify that super heroes are saved into the database on success fetch`() {
        // act
        runBlockingTest {
            Repository(
                mockSuperHeroesApi,
                mockApiKeyQuery,
                mockSuperHeroDao
            ).fetchSuperHeroesFromApi()
        }
        // assert
        coVerify { mockSuperHeroDao.addSuperHero(any()) }
    }
}
