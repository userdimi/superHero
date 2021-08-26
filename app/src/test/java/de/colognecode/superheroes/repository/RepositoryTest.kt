package de.colognecode.superheroes.repository

import de.colognecode.superheroes.di.apiModule
import de.colognecode.superheroes.di.databaseModule
import de.colognecode.superheroes.di.repositoryModule
import de.colognecode.superheroes.repository.database.SuperHeroDao
import de.colognecode.superheroes.repository.model.ResultsItem
import de.colognecode.superheroes.repository.model.SuperHeroesResponse
import de.colognecode.superheroes.repository.network.ApiKeyQuery
import de.colognecode.superheroes.repository.network.SuperHeroesApi
import io.mockk.*
import org.junit.Rule
import org.junit.Test
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import retrofit2.Response

internal class RepositoryTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.DEBUG)
        modules(apiModule)
        modules(databaseModule)
        modules(repositoryModule)
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz.java.kotlin)
    }

    @Test
    fun `Verify that super heroes are saved into the database on success fetch`() {
        // arrange
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
        val mockSuperHeroDao = mockk<SuperHeroDao>()
        val mockSuperHeroApi = declareMock<SuperHeroesApi> {
            coEvery {
                fetchHeroes(
                    mockApiKeyQuery.timestamp,
                    mockApiKeyQuery.publicApiKey,
                    mockApiKeyQuery.apiKeyHash
                )
            } returns mockResponse
        }
        // act
        Repository(mockSuperHeroApi, mockApiKeyQuery, mockSuperHeroDao)
        // assert
        coVerify {
            mockSuperHeroApi.fetchHeroes(
                mockApiKeyQuery.timestamp,
                mockApiKeyQuery.publicApiKey,
                mockApiKeyQuery.apiKeyHash
            )
        }
        coVerify { mockSuperHeroDao.addSuperHero(any()) }
    }
}
