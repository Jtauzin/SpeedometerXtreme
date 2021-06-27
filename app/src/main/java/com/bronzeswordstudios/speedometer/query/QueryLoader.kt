package com.bronzeswordstudios.speedometer.query

import android.content.AsyncTaskLoader
import android.content.Context


class QueryLoader(context: Context, private val url: String) : AsyncTaskLoader<String?>(context) {

    // load our value on a background thread
    override fun loadInBackground(): String? {
        return Query.collectData(url)
    }

    override fun onStartLoading() {
        // if you don't force load here, you are going to have a bad time
        forceLoad()
    }
}