package com.topList.android.utils

import android.content.Context
import android.util.SparseArray
import androidx.annotation.IntDef
import com.topList.android.R
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

/**
 */
object TimeFormatUtils {
    fun format(pMilliseconds: Long, pFormat: String?): String {
        val dateFormat =
            SimpleDateFormat(pFormat, Locale.getDefault())
        return dateFormat.format(Date(pMilliseconds))
    }

    const val SECOND_IN_MILLIS: Long = 1000
    const val MINUTE_IN_MILLIS = 60 * SECOND_IN_MILLIS
    const val HOUR_IN_MILLIS = 60 * MINUTE_IN_MILLIS
    const val DAY_IN_MILLIS = 24 * HOUR_IN_MILLIS
    const val WEEK_IN_MILLIS = 7 * DAY_IN_MILLIS
    const val MONTH_IN_MILLIS = 30 * DAY_IN_MILLIS
    const val YEAR_IN_MILLIS = 12 * MONTH_IN_MILLIS
    /**
     * 准确时间
     */
    const val ACCURATE = 0x00
    /**
     * 绝对时间
     */
    const val ABSOLUTE = 0x01
    /**
     * 相对时间
     */
    const val RELATIVE = 0x02

    fun getTime(context: Context, timeInSeconds: Long): String {
        return getRelativeTime(context, timeInSeconds)
    }

    /**
     * 相对时间。
     */
    fun getRelativeTime(
        context: Context,
        timeInSeconds: Long
    ): String {
        val now = System.currentTimeMillis()
        val target = timeInSeconds * SECOND_IN_MILLIS
        val duration = now - target // 这里只考虑过去
        return if (duration < MINUTE_IN_MILLIS) { // 刚刚 ── 1 分钟内的
            context.getString(R.string.time_just_now)
        } else if (duration < HOUR_IN_MILLIS) { // 1 分钟前 ... 59 分钟前
            context.getString(
                R.string.time_relative_minute,
                (duration / MINUTE_IN_MILLIS).toInt()
            )
        } else if (duration < DAY_IN_MILLIS) { // 1 小时前 ... 23 小时前
            context.getString(
                R.string.time_relative_hour,
                (duration / HOUR_IN_MILLIS).toInt()
            )
        } else if (duration < MONTH_IN_MILLIS) { // 1 天前、2 天前 ... 30 天前 ── 自然月计算 －1 天
            context.getString(
                R.string.time_relative_day,
                (duration / DAY_IN_MILLIS).toInt()
            )
        } else if (duration < YEAR_IN_MILLIS) { // 1 个月前、2 个月前 ... 12 个月前 ── 未满一个月不算一个月
            context.getString(
                R.string.time_relative_month,
                (duration / MONTH_IN_MILLIS).toInt()
            )
        } else { // N 年前
            context.getString(
                R.string.time_relative_year,
                (duration / YEAR_IN_MILLIS).toInt()
            )
        }
    }

    /**
     * 得到当前日期 返回格式为 MM-dd 星期X
     * @return
     */
    val todayTimeFormat: String
        get() {
            val calendar = Calendar.getInstance()
            return getTimeDayOfWeek(calendar.time)
        }

    var dayWeekMap: HashMap<Int, String> = HashMap()
    /**
     * @param date
     * @return 返回格式为 MM-dd 星期X
     */
    fun getTimeDayOfWeek(date: Date?): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MM-dd")
        calendar.time = date
        val day = calendar[Calendar.DAY_OF_WEEK]
        return dateFormat.format(date) + " 星期" + dayWeekMap[day]
    }

    /**
     * @param context       ctx
     * @param timeInSeconds time
     * @return 返回格式为 HH:mm MM-dd
     */
    fun getAccurateHourDayTime(
        context: Context?,
        timeInSeconds: Long
    ): String {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance()
        target.timeInMillis = timeInSeconds * 1000
        val dateFormat = SimpleDateFormat()
        dateFormat.applyPattern("HH:mm  MM-dd")
        return dateFormat.format(target.time)
    }

    /**
     * @param context       ctx
     * @param timeInSeconds time
     * @return 返回格式为 MM-dd
     */
    fun getAccurateMonthDayTime(
        context: Context?,
        timeInSeconds: Long
    ): String {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance()
        target.timeInMillis = timeInSeconds * 1000
        val dateFormat = SimpleDateFormat()
        dateFormat.applyPattern("MM-dd")
        return dateFormat.format(target.time)
    }

    fun isSameDay(date1: Date?, date2: Date?): Boolean {
        val cal1 = Calendar.getInstance()
        cal1.time = date1
        val cal2 = Calendar.getInstance()
        cal2.time = date2
        val isSameYear = cal1[Calendar.YEAR] == cal2[Calendar.YEAR]
        val isSameMonth = (isSameYear
                && cal1[Calendar.MONTH] == cal2[Calendar.MONTH])
        return (isSameMonth
                && cal1[Calendar.DAY_OF_MONTH] == cal2[Calendar.DAY_OF_MONTH])
    }

    /**
     * @param timeInMillisecond 毫秒值
     * @return 比如"1.5"，单位是小时
     */
    fun getHourTime(timeInMillisecond: Long): String {
        val decimalFormat = DecimalFormat("0.0")
        return decimalFormat.format(timeInMillisecond / 1000f / 60f / 60f.toDouble())
    }

    @IntDef(ACCURATE, ABSOLUTE, RELATIVE)
    @Retention(RetentionPolicy.SOURCE)
    annotation class Type

    init {
        dayWeekMap.put(2, "一")
        dayWeekMap.put(3, "二")
        dayWeekMap.put(4, "三")
        dayWeekMap.put(5, "四")
        dayWeekMap.put(6, "五")
        dayWeekMap.put(7, "六")
        dayWeekMap.put(1, "日")
    }
}