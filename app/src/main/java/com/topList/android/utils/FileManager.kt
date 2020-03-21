package com.topList.android.utils

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.modules.SerialModule
import java.io.File
import java.lang.reflect.Type

/**
 * @author yyf
 * @since 03-19-2020
 */
open class FileManager(
    private val rootDir: File,
    private val format: BinaryFormat = JsonBinaryFormat(Json(JsonConfiguration.Stable))
) {
    companion object {
        private val SERIALIZERS = HashMap<Type, KSerializer<*>>()
    }

    /**
     * 保存数据
     */
    inline fun <reified T : Any> save(key: String, value: T?): Boolean {
        val type = typeTokenOf<T>()
        return save(key, obtainSerializer(type), value)
    }

    fun <T> save(key: String, serializer: SerializationStrategy<T>, value: T?): Boolean {
        val file = File(rootDir, key)
        if (file.exists()) {
            if (file.isDirectory) {
                return false
            }
        } else {
            file.parentFile.mkdirs()
            file.createNewFile()
        }

        if (value == null) {
            return file.delete()
        }

        return try {
            file.writeText(format.dumps(serializer, value))
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 读取数据
     */
    inline fun <reified T : Any> load(key: String): T? {
        val type = typeTokenOf<T>()
        return load(key, obtainSerializer(type))
    }

    fun <T> load(key: String, deserializer: DeserializationStrategy<T>): T? {
        val file = File(rootDir, key)
        if (!file.exists()) {
            return null
        }

        return try {
            format.loads(deserializer, file.readText())
        } catch (e: Exception) {
            null
        }
    }


    /**
     * 获取某个 Type 的 Serializer
     */
    fun <T> obtainSerializer(type: Type): KSerializer<T> {
        var serializer = SERIALIZERS[type]
        if (serializer == null) {
            serializer = serializerByTypeToken(type)
            SERIALIZERS[type] = serializer
        }
        return serializer as KSerializer<T>
    }

    class JsonBinaryFormat(private val json: Json) : BinaryFormat {
        override val context: SerialModule = json.context

        override fun <T> dump(serializer: SerializationStrategy<T>, obj: T): ByteArray {
            return json.stringify(serializer, obj).toByteArray()
        }

        override fun <T> load(deserializer: DeserializationStrategy<T>, bytes: ByteArray): T {
            return json.parse(deserializer, String(bytes))
        }
    }
}