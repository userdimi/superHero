package de.colognecode.superheroes.repository

import de.colognecode.superheroes.repository.database.SuperHeroDao
import de.colognecode.superheroes.repository.model.ResultsItem
import de.colognecode.superheroes.repository.model.SuperHeroesResponse
import de.colognecode.superheroes.repository.network.ApiKeyQuery
import de.colognecode.superheroes.repository.network.SuperHeroesApi
import de.colognecode.superheroes.repository.network.SuperHeroesFetchException
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
class RepositoryTest {

    @MockK
    private lateinit var mockSuperHeroesApi: SuperHeroesApi

    @MockK
    private lateinit var mockSuperHeroDao: SuperHeroDao

    @MockK
    private lateinit var mockApiKeyQuery: ApiKeyQuery

    @MockK
    private lateinit var mockResultsItem: ResultsItem

    @MockK
    private lateinit var mockResponse: Response<SuperHeroesResponse>

    @MockK
    private lateinit var mockSuperHeroesResponse: SuperHeroesResponse

    @InjectMockKs
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `Verify super heroes are loaded from the database`() {
        // assert
        verify { this@RepositoryTest.mockSuperHeroDao.getAllSuperHeroes() }
    }

    @Test
    fun `Verify super heroes are saved in the database on success`() {
        // arrange
        coEvery {
            this@RepositoryTest.mockSuperHeroesApi.fetchHeroes(any(), any(), any())
        } returns mockResponse
        every { this@RepositoryTest.mockResultsItem.id } returns "12345"
        every { this@RepositoryTest.mockResultsItem.name } returns "Hulk"
        every { this@RepositoryTest.mockResultsItem.thumbnail?.path } returns "http://foo.bar"
        every { this@RepositoryTest.mockResponse.isSuccessful } returns true
        every {
            this@RepositoryTest.mockResponse.body()?.data?.results
        } returns listOf(this.mockResultsItem)

        // act
        runBlockingTest {
            this@RepositoryTest.repository.fetchSuperHeroesFromApi()
        }

        // assert
        coVerify { this@RepositoryTest.mockSuperHeroDao.addSuperHero(any()) }
    }

    @Test
    fun `Verify SuperHeroesFetchException is thrown with correct message`() {
        // arrange
        coEvery {
            this@RepositoryTest.mockSuperHeroesApi.fetchHeroes(any(), any(), any())
        } throws Exception()
        assertFailsWith<SuperHeroesFetchException>(message = Repository.errorMessage) {
            runBlockingTest {
                this@RepositoryTest.repository.fetchSuperHeroesFromApi()
            }
        }
    }
}
