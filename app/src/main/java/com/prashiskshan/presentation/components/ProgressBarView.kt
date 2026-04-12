package com.prashiskshan.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * Structural placeholder for a progress indicator view.
 *
 * Later you can inflate a layout and bind to a progress percentage.
 */
class ProgressBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var progressPercent: Int = 0

    init {
        orientation = VERTICAL
    }

    fun setProgressPercent(percent: Int) {
        progressPercent = percent.coerceIn(0, 100)
        // Structural placeholder: update UI when the view is integrated.
    }

    fun getProgressPercent(): Int = progressPercent
}

