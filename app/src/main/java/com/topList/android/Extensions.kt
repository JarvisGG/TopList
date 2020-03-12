import android.content.res.Resources

val Number.dp2px get() = (toFloat() * Resources.getSystem().displayMetrics.density).toFloat()
val Number.px2dp get() = (toFloat() / Resources.getSystem().displayMetrics.density).toFloat()