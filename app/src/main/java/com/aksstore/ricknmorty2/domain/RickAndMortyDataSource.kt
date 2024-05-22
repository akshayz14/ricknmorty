package com.aksstore.ricknmorty2.domain

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aksstore.ricknmorty2.data.RNMResult
import javax.inject.Inject

class RickAndMortyDataSource @Inject constructor(private val service: RickAndMortyService) : PagingSource<Int, RNMResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RNMResult> {
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getRickNMortyCharacters(pageNumber)
            val pagedResponse = response.body()
            val data = pagedResponse?.results

            var nextPageNumber: Int? = null

            if (pagedResponse?.results != null) {
                val uri = Uri.parse(pagedResponse.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }
            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RNMResult>): Int = 1


}