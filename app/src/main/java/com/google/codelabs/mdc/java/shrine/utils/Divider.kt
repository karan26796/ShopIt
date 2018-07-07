package com.google.codelabs.mdc.java.shrine.utils

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Divider(private val mDivider: Drawable?) : RecyclerView.ItemDecoration() {

    val paint: Paint = Paint()
    private val mOffsetPx: Int = 0

    override fun onDraw(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        paint.setARGB(240, 240, 240, 240)
        val leftWithMargin = 90.toDp()
        val right = parent.width
        var left = parent.left
        val mBounds = Rect()

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val adapterPosition = parent.getChildAdapterPosition(child)
            left = if (adapterPosition == childCount) 0 else leftWithMargin
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child))
            val top = bottom - mDivider?.intrinsicHeight!!
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(canvas)
        }
        canvas.restore()
    }

    /**
     * Determines the size and location of the offset to be added to the start
     * of the RecyclerView.
     *
     * @param outRect The [Rect] of offsets to be added around the child view
     * @param view    The child view to be decorated with an offset
     * @param parent  The RecyclerView onto which dividers are being added
     * @param state   The current RecyclerView.State of the RecyclerView
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) < 1) {
            val orientation = (parent.layoutManager as LinearLayoutManager).orientation
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.left = mOffsetPx
            } else if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.top = mOffsetPx
            }
        }
    }
}

fun Int.toDp(): Int = (this * Resources.getSystem().displayMetrics.density)
        .toInt()

