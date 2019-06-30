package com.ar.maloba.footballnews.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.ar.maloba.footballnews.model.Item

class NewsViewModel : ViewModel() {

    val allNewsMutableLiveData: MutableLiveData<List<Item>> = MutableLiveData<List<Item>>()

    init {

    }
}