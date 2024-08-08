package khani.behnam.displaytasks.presentation.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class ColorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var fallbacColor: Int = Color.BLACK

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    init {
        paint.color = ContextCompat.getColor(context, android.R.color.black)
    }

    fun setColor(colorCode: String) {
        if (isValidColor(colorCode)) {
            paint.color = Color.parseColor(colorCode)
        } else {
            paint.color = fallbacColor
        }

        invalidate()
    }

    private fun isValidColor(colorString: String): Boolean {
        return try {
            if (colorString.isEmpty()){
                return false
            }

            Color.parseColor(colorString)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(75, 75)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val radius = width / 2f
        canvas.drawCircle(radius, radius, radius, paint)
    }
}