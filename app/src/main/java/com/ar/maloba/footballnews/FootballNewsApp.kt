package com.ar.maloba.footballnews

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProviders
import com.ar.maloba.footballnews.model.Channel
import com.ar.maloba.footballnews.model.Item
import com.ar.maloba.footballnews.model.RssResponse
import com.ar.maloba.footballnews.remote.RssAPI
import com.ar.maloba.footballnews.viewmodel.NewsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.core.Persister
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

class FootballNewsApp: Application() {

    companion object {

    }

    override fun onCreate() {
        super.onCreate()
    }


}