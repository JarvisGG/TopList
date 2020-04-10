package com.topList.android.ui.widget

import android.content.Context
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.InputType
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.topList.android.R
import com.topList.theme.dp2px
import kotlinx.android.synthetic.main.widget_title_edit_text.view.*

/**
 * @author yyf
 * @since 04-10-2020
 */
open class TitleEditText : ConstraintLayout {
    companion object {
        private val textPaint = TextPaint()
        fun balanceView(vararg titleEditText: TitleEditText) {
            titleEditText.forEach { it.visibility = View.INVISIBLE }
            //找到最长的title
            val maxWidth = titleEditText.map {
                textPaint.textSize = it.textSize.toFloat()
                textPaint.measureText(it.title) + 4.dp2px
            }.maxBy { it } ?: return

            // title view 宽度对齐
            titleEditText.forEach { et ->
                val params = et.titleView.layoutParams
                params.width = maxWidth.toInt()
                et.titleView.layoutParams = params
                et.visibility = View.VISIBLE
            }
        }
    }

    var textWatcher: TextWatcher? = null
    val editText: EditText by lazy { etContent }
    val titleView: TextView by lazy { tvTitle }
    var isShowClear:Boolean = true
        set(value) {
            field = value
            updateBtnClear()
        }
    var textSize: Int = context.resources.getDimensionPixelSize(R.dimen.edit_text_default)

    private var textColor: Int = Color.BLACK
    private var textWarningColor: Int = Color.RED
    var title: String = ""
        set(value) {
            tvTitle.text = value
            field = value
        }

    var text: String
        get() = editText.text.toString()
        set(value) {
            editText.setText(value)
        }

    var isWarning = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {
        initView(attr)
        initLogic()
    }

    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(context, attr, defStyle) {
        initView(attr)
        initLogic()
    }

    private fun initView(attributes: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.widget_title_edit_text, this, true)
        attributes?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.TitleEditText)
            title = typedArray.getString(R.styleable.TitleEditText_title) ?: ""
            textSize = typedArray.getDimensionPixelSize(R.styleable.TitleEditText_textSize, textSize)
            textColor = typedArray.getColor(R.styleable.TitleEditText_textColor, context.resources.getColor(R.color.GBK02A))
            textWarningColor = typedArray.getColor(R.styleable.TitleEditText_textWColor, context.resources.getColor(R.color.GRD03A))

            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
            etContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
            changeStatus()
            etContent.hint = typedArray.getString(R.styleable.TitleEditText_editHint)
            (etContent.layoutParams as LayoutParams).marginStart =
                typedArray.getDimension(
                    R.styleable.TitleEditText_contentPadding,
                    context.resources.getDimension(R.dimen.edit_text_padding_default)
                )
                    .toInt()
            typedArray.recycle()
        }
        etContent.isSaveEnabled = false
        updateBtnClear()
    }

    private fun updateBtnClear(t: CharSequence? = etContent.text, hasFocus: Boolean = hasFocus()) {
        btnClear.visibility = if (isShowClear) {
            if (!t.isNullOrEmpty() && hasFocus) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        } else {
            View.GONE
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val state = SavedState(super.onSaveInstanceState())
        state.text = text
        return state
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
        etContent.setText((state as SavedState).text)
    }

    open fun initLogic() {
        etContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateBtnClear(s)
                textWatcher?.afterTextChanged(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                textWatcher?.beforeTextChanged(s, start, count, after)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isWarning) {
                    warning(false)
                }
                textWatcher?.onTextChanged(s, start, before, count)
            }

        })
        etContent.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                bottomLine.background = context.resources.getDrawable(R.color.GBK02A)
            } else {
                bottomLine.background = context.resources.getDrawable(R.color.GBK07A)
            }
            updateBtnClear(hasFocus = hasFocus)
        }

        btnClear.setOnClickListener {
            etContent.setText("")
        }
    }

    /**
     * 警告状态，颜色变为警告色
     */
    fun warning(isWarning: Boolean) {
        this.isWarning = isWarning
        changeStatus()
    }

    fun inputType(type: Int) {
        editText.inputType = type
        when (type) {
            InputType.TYPE_TEXT_VARIATION_PASSWORD,
            InputType.TYPE_NUMBER_VARIATION_PASSWORD,
            InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD -> editText.transformationMethod =
                PasswordTransformationMethod.getInstance()
            else -> editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }

    }


    fun titleWidth(callback: (with: Int) -> Unit) {
        tvTitle.post {
            callback(tvTitle.width)
        }
    }

    private fun changeStatus() {
        if (isWarning) {
            etContent.setTextColor(textWarningColor)
            tvTitle.setTextColor(textWarningColor)
        } else {
            etContent.setTextColor(textColor)
            tvTitle.setTextColor(textColor)
        }
    }

    class SavedState : BaseSavedState {
        var text: String = ""

        constructor(source: Parcel?) : super(source)

        constructor(superState: Parcelable?) : super(superState)

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeString(text)
        }

    }
}