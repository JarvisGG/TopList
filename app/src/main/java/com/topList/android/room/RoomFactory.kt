package com.topList.android.room

import android.content.Context
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import java.lang.reflect.ParameterizedType

/**
 * @author yyf
 * @since 04-09-2020
 */
abstract class RoomFactory<T : RoomDatabase?> {
    private var db: T? = null
    fun getDataBase(context: Context?): T {
        requireNotNull(context) { "Context should not be null." }
        if (db == null) {
            synchronized(RoomFactory::class.java) {
                if (db == null) {
                    val builder = Room.databaseBuilder(
                        context.applicationContext,
                        getTClass(), roomDbName()
                    )
                    if (deleteRoomIfMigrationNeeded()) {
                        builder.fallbackToDestructiveMigration()
                    } else {
                        val migrations: Array<Migration>? = addMigrations()
                        if (migrations != null && migrations.isNotEmpty()) {
                            builder.addMigrations(*migrations)
                        } else {
                            builder.fallbackToDestructiveMigration()
                        }
                    }
                    if (isAllowMainThreadQueries()) {
                        builder.allowMainThreadQueries()
                    }
                    val callback: RoomDatabase.Callback? = addCallBack()
                    if (callback != null) {
                        builder.addCallback(callback)
                    }
                    db = try {
                        builder.build()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        builder.fallbackToDestructiveMigration()
                        builder.build()
                    }
                }
            }
        } else if (!db!!.isOpen) {
            db = null
            return getDataBase(context.applicationContext)
        }
        return db!!
    }

    fun close() {
        synchronized(RoomFactory::class.java) {
            if (db != null && db!!.isOpen) {
                db!!.close()
            }
            db = null
        }
    }

    /**
     * 不推荐这种用法，谨慎！！！
     * 建议配合 Rx 使用
     *
     * @return
     */
    private fun isAllowMainThreadQueries(): Boolean {
        return false
    }

    @Nullable
    protected fun addCallBack(): RoomDatabase.Callback? {
        return null
    }

    @NonNull
    protected abstract fun roomDbName(): String


    protected abstract fun deleteRoomIfMigrationNeeded(): Boolean

    @Nullable
    protected abstract fun addMigrations(): Array<Migration>?

    private fun getTClass(): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType?)!!.actualTypeArguments[0] as Class<T>
    }
}