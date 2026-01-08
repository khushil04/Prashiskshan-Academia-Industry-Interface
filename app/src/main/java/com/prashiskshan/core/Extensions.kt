package com.prashiskshan.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

/**
 * Context Extensions
 */
fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * Activity Extensions
 */
fun Activity.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Activity.hideKeyboard() {
    val view = this.currentFocus ?: View(this)
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * Fragment Extensions
 */
fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    requireContext().toast(message, length)
}

fun Fragment.hideKeyboard() {
    view?.let {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

/**
 * View Extensions
 */
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun View.snackbar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, length).show()
}

fun View.snackbarWithAction(
    message: String,
    actionText: String,
    action: () -> Unit,
    length: Int = Snackbar.LENGTH_LONG
) {
    Snackbar.make(this, message, length)
        .setAction(actionText) { action() }
        .show()
}

/**
 * String Extensions
 */
fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
    return this.matches(emailRegex)
}

fun String.isValidPhone(): Boolean {
    val phoneRegex = "^[6-9]\\d{9}\$".toRegex()
    return this.matches(phoneRegex)
}

fun String.isValidPassword(): Boolean {
    return this.length >= Constants.MIN_PASSWORD_LENGTH
}

fun String.capitalizeWords(): String {
    return this.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}

/**
 * Date Extensions
 */
fun Date.toDisplayFormat(): String {
    val formatter = SimpleDateFormat(Constants.DATE_FORMAT_DISPLAY, Locale.getDefault())
    return formatter.format(this)
}

fun Date.toApiFormat(): String {
    val formatter = SimpleDateFormat(Constants.DATE_FORMAT_API, Locale.getDefault())
    return formatter.format(this)
}

fun Date.toDateTimeDisplay(): String {
    val formatter = SimpleDateFormat(Constants.DATETIME_FORMAT_DISPLAY, Locale.getDefault())
    return formatter.format(this)
}

fun String.toDate(format: String = Constants.DATE_FORMAT_API): Date? {
    return try {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        formatter.parse(this)
    } catch (e: Exception) {
        null
    }
}

fun Long.toDate(): Date {
    return Date(this)
}

/**
 * Intent Extensions
 */
fun Intent.clearStack(): Intent {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    return this
}

/**
 * Uri Extensions
 */
fun Uri.getFileName(context: Context): String? {
    var fileName: String? = null
    context.contentResolver.query(this, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        fileName = cursor.getString(nameIndex)
    }
    return fileName
}

fun Uri.getFileSize(context: Context): Long {
    var fileSize: Long = 0
    context.contentResolver.query(this, null, null, null, null)?.use { cursor ->
        val sizeIndex = cursor.getColumnIndex(android.provider.OpenableColumns.SIZE)
        cursor.moveToFirst()
        fileSize = cursor.getLong(sizeIndex)
    }
    return fileSize
}

/**
 * Number Extensions
 */
fun Long.toFileSizeString(): String {
    val kb = this / 1024.0
    val mb = kb / 1024.0
    val gb = mb / 1024.0
    
    return when {
        gb >= 1 -> String.format("%.2f GB", gb)
        mb >= 1 -> String.format("%.2f MB", mb)
        kb >= 1 -> String.format("%.2f KB", kb)
        else -> "$this Bytes"
    }
}

/**
 * Collection Extensions
 */
fun <T> List<T>?.orEmpty(): List<T> = this ?: emptyList()

fun <T> MutableList<T>.addIfNotExists(item: T) {
    if (!this.contains(item)) {
        this.add(item)
    }
}

/**
 * Boolean Extensions
 */
fun Boolean.toInt(): Int = if (this) 1 else 0

fun Int.toBoolean(): Boolean = this != 0
