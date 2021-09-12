package de.colognecode.superheroes.presentation.overview

import de.colognecode.superheroes.repository.Repository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SuperHeroesOverviewViewModelTest {

    @RelaxedMockK
    private lateinit var mockRepository: Repository

    @InjectMockKs
    private lateinit var superHeroesOverviewViewModel: SuperHeroesOverviewViewModel

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(this.testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        this.testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Verify that super heroes are getting from repository`() {
        // assert
        verify { this@SuperHeroesOverviewViewModelTest.mockRepository.superHeroes }
    }

    @Test
    fun `Verify that heroes are fetching from repository`() {
        // act
        this@SuperHeroesOverviewViewModelTest
            .superHeroesOverviewViewModel.getSuperHeroesFromRepository()
        // assert
        coVerify { this@SuperHeroesOverviewViewModelTest.mockRepository.fetchSuperHeroesFromApi() }
    }
}
