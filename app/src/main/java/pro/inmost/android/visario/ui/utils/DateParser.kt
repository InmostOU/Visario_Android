package pro.inmost.android.visario.ui.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateParser {
    private val DATE_FORMAT_REGEXPS: Map<String, String> = hashMapOf(
        "^\\d{8}$" to "yyyyMMdd",
        "^\\d{12}$" to "yyyyMMddHHmm",
        "^\\d{8}\\s\\d{4}$" to "yyyyMMdd HHmm",
        "^\\d{8}\\s\\d{4}$" to "yyyyMMdd HHmm",
        "^\\d{14}$" to "yyyyMMddHHmmss",
        "^\\d{8}\\s\\d{6}$" to "yyyyMMdd HHmmss",
        "^\\d{8}\\s\\d{6}$" to "yyyyMMdd HHmmss",
        "^\\d{1,2}-\\d{1,2}-\\d{4}$" to "dd-MM-yyyy",
        "^\\d{4}-\\d{1,2}-\\d{1,2}$" to "yyyy-MM-dd",
        "^\\d{1,2}/\\d{1,2}/\\d{4}$" to "MM/dd/yyyy",
        "^\\d{4}/\\d{1,2}/\\d{1,2}$" to "yyyy/MM/dd",
        "^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$" to "dd MMM yyyy",
        "^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$" to "dd MMMM yyyy",
        "^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$" to "dd-MM-yyyy HH:mm",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$" to "yyyy-MM-dd HH:mm",
        "^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$" to "MM/dd/yyyy HH:mm",
        "^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$" to "yyyy/MM/dd HH:mm",
        "^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$" to "dd MMM yyyy HH:mm",
        "^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$" to "dd MMMM yyyy HH:mm",
        "^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$" to "dd-MM-yyyy HH:mm:ss",
        "^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$" to "yyyy-MM-dd HH:mm:ss",
        "^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$" to "MM/dd/yyyy HH:mm:ss",
        "^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$" to "yyyy/MM/dd HH:mm:ss",
        "^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$" to "dd MMM yyyy HH:mm:ss",
        "^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$" to "dd MMMM yyyy HH:mm:ss",
        "^\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}:\\d{2}\\.\\d{2}[-+]\\d{2}:\\d{2}$" to "yyyy-MM-dd'T'HH:mm:ss.SSS",
        "^\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}:\\d{2}\\.\\d{1,3}Z$" to "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    )

    /**
     * To Determine the pattern by the string date value
     *
     * @param dateString
     * @return The matching SimpleDateFormat pattern, or null if format is unknown.
     */
    private fun determineDateFormat(dateString: String): String? {
        for (regexp in DATE_FORMAT_REGEXPS.keys) {
            if (dateString.matches(regexp.toRegex()) || dateString.lowercase(Locale.getDefault()).matches(regexp.toRegex())) {
                return DATE_FORMAT_REGEXPS[regexp]
            }
        }
        return null
    }

    fun parseToMillis(value: String?): Long {
        value ?: return -1

        val format = determineDateFormat(value)
        if (format != null) {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            return try {
                val date: Date? = sdf.parse(value)
                date?.time ?: 0L
            } catch (e: ParseException) {
                e.printStackTrace()
                -1
            }
        }
        return -1
    }
}