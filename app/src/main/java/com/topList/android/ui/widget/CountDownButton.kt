package com.topList.android.ui.widget

import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.util.AttributeSet
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import com.topList.android.R

class CountDownButton : AppCompatButton {

    enum class State {
        BEFORE_COUNT,
        IN_COUNT,
        AFTER_COUNT
    }

    // Unit:second
    var countDownTime: Int = 60
        set(value) {
            field = value
            updateTimer()
        }
    // Unit:second
    var countDownInterval: Int = 1
        set(value) {
            field = value
            updateTimer()
        }
    var startText: String = ""
    var countDownTextPrefix: String = ""
    var countDownTextSuffix: String = ""
    var resetText: String = ""
    var enableColor: Int = Color.BLACK
    var disableColor: Int = Color.GRAY
    private var isStartState = true
        set(value) {
            field = value
            updateState()
        }

    var onStateChange: (state: State, btn: CountDownButton) -> Unit = { _, _ -> }

    private lateinit var timer: CountDownTimer


    constructor(context: Context) : super(context)

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        initParams(attr)
    }

    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(context, attr, defStyle) {
        initParams(attr)
    }

    private fun initParams(attr: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(attr, R.styleable.CountDownButton, 0, 0)
        countDownTime = a.getInteger(R.styleable.CountDownButton_countDownTime, 60)
        countDownInterval = a.getInteger(R.styleable.CountDownButton_countDownInterval, 1)
        startText = a.getString(R.styleable.CountDownButton_textStart) ?: startText
        countDownTextPrefix = a.getString(R.styleable.CountDownButton_textInCountPrefix) ?: startText
        countDownTextSuffix = a.getString(R.styleable.CountDownButton_textInCountSuffix) ?: startText
        resetText = a.getString(R.styleable.CountDownButton_textReset) ?: startText
        enableColor = a.getColor(R.styleable.CountDownButton_textColorEnable, enableColor)
        disableColor = a.getColor(R.styleable.CountDownButton_textColorDisable, disableColor)
        isAllCaps = false
        a.recycle()
        updateState()
        updateTimer()
    }

    private fun updateTimer() {
        timer = object : CountDownTimer(countDownTime * 1000L, countDownInterval * 1000L) {
            override fun onFinish() {
                isStartState = false
            }

            override fun onTick(millisUntilFinished: Long) {
                text = "$countDownTextPrefix ${millisUntilFinished / 1000L} $countDownTextSuffix"

            }

        }
    }

    private fun updateState() {
        text = if (isStartState) {
            onStateChange(State.BEFORE_COUNT, this)
            startText
        } else {
            onStateChange(State.AFTER_COUNT, this)
            resetText
        }
        setTextColor(enableColor)
        isEnabled = true
    }

    fun startCount() {
        timer.cancel()
        timer.start()
        setTextColor(disableColor)
        isEnabled = false
        postDelayed({
            onStateChange(State.IN_COUNT, this)
        },1000)
    }

    fun stopCount() {
        timer.cancel()
        isStartState = false
    }

    fun resetState() {
        isStartState = true
    }
}