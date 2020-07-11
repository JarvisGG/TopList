package com.topList.android.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.topList.android.R
import kotlinx.android.synthetic.main.dialog_common.view.*

/**
 * 通用 Dialog
 */
class CommonAlertDialog : DialogFragment() {
    companion object {
        const val ARG_VO = "ARG_VO"

        /**
         * 这里的 style 都是根据设计稿的顺序定的
         */
        fun withTitle(
            context: Context,
            title: String,
            description: String? = null,
            action1: Spanned? = null,
            action2: Spanned? = null,
            extra: Bundle? = null
        ): CommonAlertDialog {
            val args = Bundle()
            args.putParcelable(
                ARG_VO,
                VO(
                    null, title, description,
                    action1 = action1 ?: SpannableString(context.getString(R.string.cancel)),
                    action2 = action2 ?: createPositiveStr(
                        context
                    )
                )
            )
            extra?.let {
                args.putAll(extra)
            }

            val dialog = CommonAlertDialog()
            dialog.arguments = args
            return dialog
        }

        fun withIcon(
            context: Context,
            iconRes: Int,
            title: String,
            description: String,
            action1: Spanned? = null,
            action2: Spanned? = null,
            extra: Bundle? = null
        ): CommonAlertDialog {
            val args = Bundle()
            args.putParcelable(
                ARG_VO,
                VO(
                    iconRes, title, description,
                    action1 = action1 ?: SpannableString(context.getString(R.string.cancel)),
                    action2 = action2 ?: createPositiveStr(
                        context
                    )
                )
            )
            extra?.let {
                args.putAll(extra)
            }

            val dialog = CommonAlertDialog()
            dialog.arguments = args
            return dialog
        }

        fun noTitle(
            context: Context,
            description: String,
            action1: Spanned? = null,
            action2: Spanned? = null,
            extra: Bundle? = null
        ): CommonAlertDialog {
            val args = Bundle()
            args.putParcelable(
                ARG_VO,
                VO(
                    null, null, description,
                    action1 = action1 ?: SpannableString(context.getString(R.string.cancel)),
                    action2 = action2 ?: createPositiveStr(
                        context
                    )
                )
            )
            extra?.let {
                args.putAll(extra)
            }

            val dialog = CommonAlertDialog()
            dialog.arguments = args
            return dialog
        }

        fun oneOption(
            context: Context,
            title: String,
            description: String,
            action1: Spanned? = null,
            extra: Bundle? = null
        ): CommonAlertDialog {
            val args = Bundle()
            args.putParcelable(
                ARG_VO,
                VO(
                    null, title, description,
                    action1 = action1 ?: createPositiveStr(
                        context
                    )
                )
            )
            extra?.let {
                args.putAll(extra)
            }

            val dialog = CommonAlertDialog()
            dialog.arguments = args
            return dialog
        }

        fun threeOption(
            context: Context,
            title: String,
            description: String,
            action1: Spanned,
            action2: Spanned,
            action3: Spanned,
            extra: Bundle? = null
        ): CommonAlertDialog {
            val args = Bundle()
            args.putParcelable(
                ARG_VO,
                VO(
                    null,
                    title,
                    description,
                    null,
                    1,
                    action1,
                    action2,
                    action3
                )
            )
            extra?.let {
                args.putAll(extra)
            }

            val dialog = CommonAlertDialog()
            dialog.arguments = args
            return dialog
        }

        fun withEditText(
            context: Context,
            title: String,
            description: String,
            editHint: String,
            action1: Spanned? = null,
            action2: Spanned? = null,
            extra: Bundle? = null
        ): CommonAlertDialog {
            val args = Bundle()
            args.putParcelable(
                ARG_VO,
                VO(
                    null, title, description, editHint,
                    action1 = action1 ?: SpannableString(context.getString(R.string.cancel)),
                    action2 = action2 ?: createPositiveStr(
                        context
                    )
                )
            )
            extra?.let {
                args.putAll(extra)
            }

            val dialog = CommonAlertDialog()
            dialog.arguments = args
            return dialog
        }

        /**
         * 创建蓝色的 SpannableString
         */
        fun createPositiveStr(context: Context, text: String? = null): SpannableString {
            val spanStr = SpannableString(text ?: context.getString(R.string.confirm))
            val color = ContextCompat.getColor(context, R.color.GBK04A)
            spanStr.setSpan(ForegroundColorSpan(color), 0, spanStr.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            return spanStr
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val rootView = LayoutInflater.from(context).inflate(R.layout.dialog_common, null, false)
        val dialog = builder.setView(rootView).create()

        // Get arguments
        val vo = arguments?.getParcelable<VO>(ARG_VO) ?: return dialog

        // Header icon
        if (vo.iconRes != null) {
            rootView.ivIcon.setImageResource(vo.iconRes)
            rootView.ivIcon.visibility = View.VISIBLE
        }

        // Title text
        if (vo.title != null) {
            rootView.tvTitle.text = vo.title
            rootView.tvTitle.visibility = View.VISIBLE
        }

        // Description
        rootView.tvDescription.text = vo.description ?: ""
        if (TextUtils.isEmpty(vo.description)) {
            rootView.tvDescription.visibility = View.GONE
        } else {
            rootView.tvDescription.visibility = View.VISIBLE
        }


        // Action buttons
        if (vo.actionsOrientation == 0) {
            rootView.lActions.dividerDrawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.divider_vertical)
            rootView.lActions.orientation = LinearLayoutCompat.HORIZONTAL
        } else {
            rootView.lActions.dividerDrawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.divider_horizontal)
            rootView.lActions.orientation = LinearLayoutCompat.VERTICAL
        }
        if (vo.action1 != null) {
            rootView.tvAction1.text = vo.action1
            rootView.tvAction1.setOnClickListener {
                if (parentFragment is Listener) {
                    (parentFragment as Listener).onActionClick(this, 1)
                }
            }

            if (vo.action2 != null) {
                rootView.tvAction2.text = vo.action2
                rootView.tvAction2.visibility = View.VISIBLE
                rootView.tvAction2.setOnClickListener {
                    if (parentFragment is Listener) {
                        (parentFragment as Listener).onActionClick(this, 2)
                    }
                }

                if (vo.action3 != null) {
                    rootView.tvAction3.text = vo.action3
                    rootView.tvAction3.visibility = View.VISIBLE
                    rootView.tvAction3.setOnClickListener {
                        if (parentFragment is Listener) {
                            (parentFragment as Listener).onActionClick(this, 3)
                        }
                    }
                }
            }
        } else {
            rootView.tvAction1.text =
                createPositiveStr(requireContext())
        }

        return dialog
    }

    override fun onResume() {
        val params = dialog!!.window!!.attributes
        params.width = resources.getDimensionPixelSize(R.dimen.dialog_width_default)
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params

        super.onResume()
    }

    interface Listener {
        fun onActionClick(dialog: CommonAlertDialog, actionIndex: Int)
    }

    class VO(
        @DrawableRes val iconRes: Int? = null,
        val title: String? = null,
        val description: String? = null,
        val editHint: String? = null,
        // 0: horizontal  1: vertical
        val actionsOrientation: Int = 0,
        val action1: Spanned? = null,
        val action2: Spanned? = null,
        val action3: Spanned? = null
    ) : Parcelable {
        constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source) as Spanned?,
            TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source) as Spanned?,
            TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source) as Spanned?
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeValue(iconRes)
            writeString(title)
            writeString(description)
            writeString(editHint)
            writeInt(actionsOrientation)
            TextUtils.writeToParcel(action1, dest, flags)
            TextUtils.writeToParcel(action2, dest, flags)
            TextUtils.writeToParcel(action3, dest, flags)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<VO> = object : Parcelable.Creator<VO> {
                override fun createFromParcel(source: Parcel): VO =
                    VO(source)
                override fun newArray(size: Int): Array<VO?> = arrayOfNulls(size)
            }
        }
    }
}