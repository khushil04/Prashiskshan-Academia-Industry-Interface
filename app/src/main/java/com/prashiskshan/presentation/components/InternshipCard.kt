package com.prashiskshan.presentation.components

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.card.MaterialCardView

/**
 * Structural placeholder for a reusable internship card view.
 *
 * UI binding can be added later once the view is integrated.
 */
class InternshipCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : MaterialCardView(context, attrs) {

    init {
        // Keep default MaterialCardView behavior; styling can be applied via theme/attrs.
        useCompatPadding = true
    }
}

