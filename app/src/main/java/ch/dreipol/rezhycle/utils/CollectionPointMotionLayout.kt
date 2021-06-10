/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.rezhycle.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import ch.dreipol.rezhycle.R
import kotlin.math.abs

class CollectionPointMotionLayout(context: Context, attributeSet: AttributeSet) : MotionLayout(context, attributeSet) {

    private var startX: Float? = null
    private var startY: Float? = null

    private fun touchEventInsideTargetView(v: View, ev: MotionEvent): Boolean {
        if (ev.x > v.left && ev.x < v.right) {
            if (ev.y > v.top && ev.y < v.bottom) {
                return true
            }
        }
        return false
    }

    // check if pane was dragged or clicked, because we can not both add for the same transition
    // https://stackoverflow.com/questions/52339024/can-we-use-onswipe-and-onclick-in-the-same-transition-for-android-motionlayout
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val clickableView: View = findViewById(R.id.visibility_toggle)
        val isTouchInClickableView = touchEventInsideTargetView(clickableView, ev)
        val contentView: View = findViewById(R.id.collection_point_view)
        val isTouchInContentView = touchEventInsideTargetView(contentView, ev)
        if (!isTouchInClickableView && !isTouchInContentView) {
            return false
        }
        if (isTouchInClickableView) {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = ev.x
                    startY = ev.y
                    clickableView.isPressed = true
                }
                MotionEvent.ACTION_UP -> {
                    val endX = ev.x
                    val endY = ev.y
                    if (isAClick(startX!!, endX, startY!!, endY)) {
                        doClickTransition()
                    }
                    clickableView.isPressed = false
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun doClickTransition() {
        transitionToStart()
    }

    private fun isAClick(startX: Float, endX: Float, startY: Float, endY: Float): Boolean {
        val differenceX = abs(startX - endX)
        val differenceY = abs(startY - endY)
        return differenceX < 10 && differenceY < 10
    }
}