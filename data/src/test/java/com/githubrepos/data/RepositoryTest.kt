package com.githubrepos.data

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.githubrepos.data.repository.Repository
import com.githubrepos.data.source.LocalDataSource
import com.githubrepos.data.source.RemoteDataSource
import com.githubrepos.domain.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    private lateinit var repository: Repository

    private val localRepos =
        flowOf(listOf(sampleRepo, sampleRepo.copy(2, name = "Basenji")))

    @Before
    fun setUp() {
        whenever(localDataSource.repos).thenReturn(localRepos)
        repository = Repository(remoteDataSource, localDataSource)
    }

    @Test
    fun `Repos are taken from local data source if available`(): Unit = runTest {
        val result = repository.repos

        assertEquals(localRepos, result)
    }

    @Test
    fun `Repos are saved to local data source when it's empty`(): Unit = runTest {
        val remoteRepos = listOf(sampleRepo.copy(2, name = "Basenji"))
        whenever(localDataSource.isReposListEmpty()).thenReturn(true)
        whenever(remoteDataSource.getAllRepos()).thenReturn(remoteRepos.right())

        repository.loadAllRepos().collect {
            it.fold(
                { error -> assertEquals(Error.Unknown, error) },
                { verify(localDataSource).saveRepos(remoteRepos) }
            )
        }


    }
}

private fun <T> doRun(block: () -> Flow<Either<Error, T>>): Flow<Either<Error, T>> = block()
    .catch {
        emit(Error.Unknown.left())
    }.flowOn(Dispatchers.IO)