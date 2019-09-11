package com.marioguerra.marvelapp.data.network

import android.net.ConnectivityManager
import com.marioguerra.marvelapp.data.exception.NoNetworkException
import io.reactivex.Completable
import io.reactivex.Single
/**
 * @author Mario Guerra on 11/09/2019
 */
class RequestUtils(private val connectivityManager: ConnectivityManager) {

    fun <T> prepareRequest(request: Single<T>): Single<T> {
        return Completable.fromCallable {
            if (!isNetworkConnected()) {
                throw NoNetworkException()
            }
        }.andThen(request)
    }

    private fun isNetworkConnected(): Boolean {
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }

}