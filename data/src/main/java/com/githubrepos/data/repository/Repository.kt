package com.githubrepos.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.githubrepos.data.source.LocalDataSource
import com.githubrepos.data.source.RemoteDataSource
import com.githubrepos.domain.Empty
import com.githubrepos.domain.Error
import com.githubrepos.domain.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Repository class responsible for managing operations related to repos.
 *
 * This class serves as an intermediary between data sources (remote and local) and provides methods to access and manipulate repo data.
 *
 * @property remoteDataSource The remote data source for repo information.
 * @property localDataSource The local data source for repo information.
 */
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : BaseRepository() {

    /**
     * A [Flow] representing the list of repos retrieved from the local data source.
     *
     * This property allows you to observe changes to the list of repos in a reactive manner.
     *
     * @see LocalDataSource.repos
     */
    val repos: Flow<List<Repo>> get() = localDataSource.repos

    /**
     * Loads all repos from the remote data source and saves them to the local data source.
     *
     * If the local data source is empty, this method fetches all repos from the remote source and stores them locally.
     * If an error occurs during the process, it emits an [Error.Unknown] result.
     *
     * @return A [Flow] of [Either] indicating the operation result:
     *   - If the operation is successful, [Empty] is emitted as [Either.Right].
     *   - If an error occurs, [Error.Unknown] is emitted as [Either.Left].
     */
    fun loadAllRepos(): Flow<Either<Error, List<Repo>>> = doRun {
        flow {
            if (localDataSource.isReposListEmpty()) {
                val repos = remoteDataSource.getAllRepos()
                repos.fold(
                    ifLeft = { error -> emit(error.left()) },
                    ifRight = {
                        localDataSource.saveRepos(it)
                        emit(it.right())
                    }
                )
            } else {
                emit(emptyList<Repo>().right())
            }
        }
    }

    fun countStargazers(repoId: Int, stargazersUrl: String): Flow<Either<Error, Empty>> = doRun {
        flow {
            remoteDataSource.countStargazers(stargazersUrl).fold(
                ifLeft = { error -> emit(error.left()) },
                ifRight = { count ->
                    localDataSource.getRepo(repoId).fold(
                        ifLeft = { error -> emit(error.left()) },
                        ifRight = {
                            localDataSource.updateRepo(it.copy(stargazersCount = count))
                            emit(Empty().right())
                        }
                    )
                }
            )
        }
    }

    fun countForks(repoId: Int, forksUrl: String): Flow<Either<Error, Int>> = doRun {
        flow {
            remoteDataSource.countForks(forksUrl).fold(
                ifLeft = { emit(Error.Unknown.left()) },
                ifRight = { count ->
                    localDataSource.getRepo(repoId).fold(
                        ifLeft = { error -> emit(error.left()) },
                        ifRight = {
                            localDataSource.updateRepo(it.copy(forksCount = count))
                            emit(count.right())
                        }
                    )
                }
            )
        }
    }

    fun getLanguage(repoId: Int, languagesUrl: String): Flow<Either<Error, String>> = doRun {
        flow {
            remoteDataSource.getLanguage(languagesUrl).fold(
                ifLeft = { emit(Error.Unknown.left()) },
                ifRight = { language ->
                    localDataSource.getRepo(repoId).fold(
                        ifLeft = { error -> emit(error.left()) },
                        ifRight = {
                            localDataSource.updateRepo(it.copy(language = language))
                            emit(language.right())
                        }
                    )
                }
            )
        }
    }

    fun getRepo(repoId: Int): Flow<Either<Error, Repo>> = doRun {
        flow {
            emit(localDataSource.getRepo(repoId))
        }
    }

}