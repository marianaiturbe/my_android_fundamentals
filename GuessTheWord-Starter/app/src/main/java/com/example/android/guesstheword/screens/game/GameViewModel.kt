package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    //moved from game fragment to preserve changes
    // The current word
    //Changed to mutable string
    //var word =  MutableLiveData<String>()
    // The current score
    //var score =  MutableLiveData<Int>()

    // Event which triggers the end of the game
    //Use LiveData to detect a game-finished event
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    //Add a backing property to score and word
    private val _score = MutableLiveData<Int>() //follow the naming convention used in backing with _
    val score: LiveData<Int>
        get() = _score

    private val _word = MutableLiveData<String>() //follow the naming convention used in backing with _
    val word: LiveData<String>
        get() = _word

    // for timer
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
    get() = _currentTime
    private val timer : CountDownTimer

    private lateinit var wordList: MutableList<String>

    val currentTimeString = Transformations.map(currentTime) {time ->
        DateUtils.formatElapsedTime(time)
    }

    // The Hint for the current word


    init {
        Log.i("GameViewModel", "GameViewModel created!")
        //moved from gamefragment
        resetList()
        nextWord()
        _word.value = ""
        _score.value = 0
        //timer
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND)  {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE
                onGameFinish()
            }


        }
        timer.start()


    }

    override fun onCleared(){
        super.onCleared()
        //Log.i("GameViewModel","GameViewModel destroyed")
        timer.cancel()
    }

    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */

//changed so that game finishes when there are no more words
        fun nextWord() {
        if(wordList.isEmpty()){
            //onGameFinish()
            resetList()
        } else {
            //Select and remove a word from the list
            //word = wordList.removeAt(0)
            _word.value = wordList.removeAt(0)
        }
    }

    //methos ofr game completed
    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

     fun onSkip() {
       // score--
         _score.value = (_score.value)?.minus(1)
         /* To resolve the error, add a null check to score.value in onSkip().
         Then call the minus() function on score, which performs the subtraction
         with null-safety.
          */
        nextWord()
    }

     fun onCorrect() {
       // score++
         _score.value = (_score.value)?.plus(1)
        nextWord()
    }

    /** Method for the game completed event **/
    //Use LiveData to detect a game-finished event
    fun onGameFinish() {
        _eventGameFinish.value = true
    }

//Create a companion object to hold the timer constants.
    companion object {

        // Time when the game is over
        private const val DONE = 0L

        // Countdown time interval
        private const val ONE_SECOND = 1000L

        // Total time for the game
        private const val COUNTDOWN_TIME = 60000L

    }

}