package com.marioguerra.marvelapp.app.ui.utils

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.marioguerra.marvelapp.R

/**
 * @author Mario Guerra on 11/09/2019
 */


fun Activity.showCustomDialog(
        image: Int? = null,
        title: Int? = null,
        subtitle: Int? = null,
        yes: (() -> Unit)? = null,
        no: (() -> Unit)? = null,
        yesTitle: Int? = null,
        noTitle: Int? = null,
        subtitleString: String? = null,
        underText: Int? = null,
        underTextAction: (() -> Unit)? = null,
        yesDismissesDialog: Boolean? = false,
        noDismissesDialog: Boolean? = false,
        extraString: String? = null,
        cancel: (() -> Unit)? = null,
        showClose: Boolean? = false,
        dismissOutside: Boolean? = true,
        titleString: String? = null,
        isRating: Boolean = false,
        close: (() -> Unit)? = null
    ) {

        lateinit var ivClose: ImageView
        lateinit var mImg: ImageView
        lateinit var mTitle: TextView
        lateinit var mSubTitle: TextView
        lateinit var mBtnYes: Button
        lateinit var mBtnNo: Button
        lateinit var mTvUnderText: TextView
        lateinit var mCardViewDialog: CardView

        val view = this.layoutInflater.inflate(R.layout.custom_dialog, null)

        val alert = AlertDialog.Builder(this)
        alert.setView(view)
        val dialog = alert.create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        ivClose = view.findViewById(R.id.ivClose)
        mImg = view.findViewById(R.id.img)
        mTitle = view.findViewById(R.id.title)
        mSubTitle = view.findViewById(R.id.subTitle)
        mBtnYes = view.findViewById(R.id.btnYes)
        mBtnNo = view.findViewById(R.id.btnNo)
        mTvUnderText = view.findViewById(R.id.mTvUnderText)
        mCardViewDialog = view.findViewById(R.id.cvCustomDialog)

        mBtnYes.addButtonAnimation()
        mBtnNo.addButtonAnimation()


        dialog.setCanceledOnTouchOutside(dismissOutside ?: true)
        if (!dismissOutside!!) {
            dialog.setOnKeyListener(object : DialogInterface.OnKeyListener {
                override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event?.getAction() == KeyEvent.ACTION_UP && !event.isCanceled()) {
                        return true
                    }
                    return false
                }
            })
        }

        if (image != null) {
            mImg.setImageDrawable(ContextCompat.getDrawable(this.applicationContext, image))
        } else {
            mImg.visibility = View.GONE
        }

        if (title != null || titleString != null) {
            if (extraString != null) {
                mTitle.text =
                    if (title != null) this.applicationContext.getString(title, extraString) else titleString + extraString
            } else {
                mTitle.text = if (title != null) this.applicationContext.getText(title) else titleString
            }

            mTitle.visibility = View.VISIBLE
        } else {
            mTitle.visibility = View.GONE
        }

        if (subtitle != null || subtitleString != null) {
            if (extraString != null) {
                mSubTitle.text = if (subtitle != null) this.applicationContext.getString(
                    subtitle,
                    extraString
                ) else subtitleString + extraString
            } else {
                mSubTitle.text = if (subtitle != null) this.applicationContext.getText(subtitle) else subtitleString
            }

            mSubTitle.visibility = View.VISIBLE
        } else {
            mSubTitle.visibility = View.GONE
        }

        if (yes != null) {
            mBtnYes.setOnClickListener {
                yes()
                if (yesDismissesDialog!!) {
                    dialog.dismiss()
                }
            }
        } else {
            mBtnYes.visibility = View.GONE
        }

        if (no != null) {
            mBtnNo.setOnClickListener {
                no()
                if (noDismissesDialog!!) {
                    dialog.dismiss()
                }
            }
        } else {
            mBtnNo.visibility = View.GONE

//        mBtnNo.setOnClickListener {
//            dialog.dismiss()
//        }
        }

        if (yesTitle != null) {
            mBtnYes.text = this.applicationContext.getText(yesTitle)
            mBtnYes.visibility = View.VISIBLE
        }

        if (noTitle != null) {
            mBtnNo.text = this.applicationContext.getText(noTitle)
        }

        if (cancel != null) {
            dialog.setOnCancelListener {
                cancel()
            }
        }

        if (close != null) {
            dialog.setOnCancelListener {
                close()
            }
        }

        if (underText != null) {
            mTvUnderText.text = this.applicationContext.getText(underText)

            mTvUnderText.setOnClickListener {
                if (underTextAction != null) {
                    underTextAction()
                }
                dialog.dismiss()
            }


        } else {
            mTvUnderText.visibility = View.GONE
        }

        if (showClose!!) {
            ivClose.visibility = View.VISIBLE

            ivClose.setOnClickListener {
                dialog.dismiss()
            }


        }

        if (isRating) {
           // mCardViewDialog.setBackgroundResource(R.drawable.bg_rating_dialog)
           // mTitle.setTextColor(resources.getColor(R.color.white))
           // mBtnYes.setBackgroundResource(R.drawable.bg_button_white)
           // mBtnYes.setTextColor(resources.getColor(R.color.colorPrimary))

        }



        dialog.show()
    }


    fun View.addButtonAnimation() {

        this.setOnTouchListener { v, event ->
            when (event?.action) {

                MotionEvent.ACTION_DOWN -> {
                    v.animate().scaleX(0.9f).setInterpolator(AccelerateDecelerateInterpolator()).duration = 300
                    v.animate().scaleY(0.9f).setInterpolator(AccelerateDecelerateInterpolator()).duration = 300

                    Handler().postDelayed({
                        v.animate().scaleX(1f).setInterpolator(AccelerateDecelerateInterpolator()).duration = 300
                        v.animate().scaleY(1f).setInterpolator(AccelerateDecelerateInterpolator()).duration = 300
                    }, 300)
                }
            }
            v?.onTouchEvent(event) ?: true
        }
    }









