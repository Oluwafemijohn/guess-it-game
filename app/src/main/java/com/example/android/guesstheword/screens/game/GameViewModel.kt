package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }



    // The current word
    private var _word = MutableLiveData<String>()
    val word:LiveData<String> get() = _word

    // The current score
   private var _score = MutableLiveData<Int>()
    val score :LiveData<Int> get() = _score

    //Game has finished
    private var _finished = MutableLiveData<Boolean>()
    val finished :LiveData<Boolean> get() = _finished

    // The list of words - the front of the list is the next word to guess
     private lateinit var wordList: MutableList<String>

    private var time:CountDownTimer
    private var _timer = MutableLiveData<String>()
    val timer:LiveData<String> get() = _timer

    init {
        _finished.value = false
        resetList()
        nextWord()
        _score.value = 0
        time = object: CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onTick(millisUntilFinished: Long) {
//                _timer.value = millisUntilFinished
               _timer.value = DateUtils.formatElapsedTime(millisUntilFinished)
            }

            override fun onFinish() {
                _finished.value = true
            }
        }
        time.start()
//        DateUtils.formatElapsedTime()


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
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        time.cancel()
    }

    fun gameHasEnded() {
        _finished.value = false
    }
}