package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int) : ViewModel() {
    // The final score
    private var _score = MutableLiveData<Int>()
    val score : LiveData<Int>
    get() = _score
    //In ScoreViewModel, add a LiveData object to hold a Boolean called _eventPlayAgain.
    private var _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain : LiveData<Boolean>
    get () = _eventPlayAgain


    init {
        _score.value = finalScore
        Log.i("ScoreViewModel", "Final score is $finalScore")
    }

    //In ScoreViewModel, define methods to set and reset the event, _eventPlayAgain.
    fun onPlayAgain(){
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete(){
        _eventPlayAgain.value = false
    }
}