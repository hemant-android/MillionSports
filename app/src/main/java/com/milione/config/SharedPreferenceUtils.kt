package com.milione.config

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Base64
import com.google.gson.Gson

/**
 * Created by Hemant Kumar Sharma on 30-08-2022
 * @Version 1
 */
open class SharedPreferenceUtils(context: Context) {
    val mSharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("sampleStructureApp", MODE_PRIVATE)
    }
    var mEditor: SharedPreferences.Editor = mSharedPreferences.edit()

    inline fun <reified T : Any> get(key: String, defaultValue: T): T {
        val encodedKey = encode(key)
        var value = mSharedPreferences.getString(encodedKey, "default")
        value = if (value == "default") {
            defaultValue.toString()
        } else {
            decode(value!!)
        }
        return when (T::class) {
            String::class -> value as T
            Int::class -> value.toInt() as T
            Long::class -> value.toLong() as T
            Boolean::class -> value.toBoolean() as T
            else -> throw IllegalArgumentException("This default value type is not accepted only pass String, Long, Int, Boolean.")
        }
    }

    inline fun <reified T : Any> set(key: String, value: T) {
        val encryptedKey = encode(key)
        val encodedValue = encode(value.toString())
        mEditor.putString(encryptedKey, encodedValue).also { mEditor.commit() }
    }

    inline fun <reified T> setprefObject(key: String, obj: T) {
        set(key, Gson().toJson(obj))
    }

    inline fun <reified T> getprefObject(key: String): T {
        return Gson().fromJson(get(key, ""), T::class.java)
    }

    inline fun <reified T> clear(vararg restore: Pair<String, T>) {
        clear()
        restore.forEach {
            when (T::class) {
                String::class -> set(it.first, it.second as String)
                Int::class -> set(it.first, it.second as Int)
                Long::class -> set(it.first, it.second as Long)
                Boolean::class -> set(it.first, it.second as Boolean)
                else -> throw IllegalArgumentException("This value type is not accepted only pass String, Long, Int, Boolean.")
            }
        }
    }

    inline fun clear() {
        mEditor.clear()
        mEditor.commit()
    }

    fun encode(plain: String): String {
        val b64encoded = Base64.encodeToString(plain.toByteArray(), Base64.DEFAULT)

        // Reverse the string
        val reverse = StringBuffer(b64encoded).reverse().toString()

        val tmp = StringBuilder()
        val offset = 4
        for (element in reverse) {
            tmp.append((element.toInt() + offset).toChar())
        }
        return tmp.toString()
    }

    fun decode(secret: String): String {
        val tmp = StringBuilder()
        val offset = 4
        for (element in secret) {
            tmp.append((element.toInt() - offset).toChar())
        }

        val reversed = StringBuffer(tmp.toString()).reverse().toString()
        return String(Base64.decode(reversed, Base64.DEFAULT))
    }

}