/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    private var viewModelJob=Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        uiScope.cancel()
    }

    private val uiScope= CoroutineScope(Dispatchers.Main+viewModelJob)

    private var tonight=MutableLiveData<SleepNight?>()
    private val nights=database.getAllNights()
    val nightString=Transformations.map(nights){nights
    -> formatNights(nights,application.resources)}

    val startButtonVisible=Transformations.map(tonight
    ){
        null==it
    }
    val stopButtonVisible=Transformations.map(tonight){
        null!=it
    }
    val clearButtonVisible=Transformations.map(nights){
        it?.isNotEmpty()
    }
    private val _navigateToSleepQuality=MutableLiveData<SleepNight>()
    val navigateSleepNight:LiveData<SleepNight>
        get() = _navigateToSleepQuality

    private val _showSnackBarEvent=MutableLiveData<Boolean?>()
    val showSnackbarEvent:LiveData<Boolean?>
        get() = _showSnackBarEvent

    fun doneSnackBarEvent(){
        _showSnackBarEvent.value=false
    }
    init {
        initializeTonight()
    }

    fun doneNavigating(){
        _navigateToSleepQuality.value=null
    }

    private  fun initializeTonight() {
        uiScope.launch {
            this.coroutineContext
            tonight.value = getTonightDatabase()
        }
    }

        private suspend fun getTonightDatabase(): SleepNight? {
        return withContext(Dispatchers.IO){
            var night=database.getTonight()
            if(night?.endTimeMilli!=night?.startTimeMilli){
                night=null
            }
            night
        }
    }

    fun onStartTracking(){
        uiScope.launch {
            val newNight=SleepNight()
              insert(newNight)
            tonight.value=getTonightDatabase()
            _showSnackBarEvent.value=true
        }
    }

    private suspend fun insert(newNight: SleepNight) {
            withContext(Dispatchers.IO){
                database.insert(newNight)
        }

    }
    
    fun onStopTracking(){
        uiScope.launch { 
            val oldNight=tonight.value?: return@launch
            oldNight.endTimeMilli=System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value=oldNight
        }
    }

    private suspend fun update(oldNight: SleepNight) {
        withContext(Dispatchers.IO){
            database.update(oldNight)
        }
    }
        fun onClear(){
            uiScope.launch {
                clear()
                tonight.value=null
            }
        }

    private suspend fun clear() {
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

}

