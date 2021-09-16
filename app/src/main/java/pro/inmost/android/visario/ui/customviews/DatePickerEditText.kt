package pro.inmost.android.visario.ui.customviews

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.DatePicker
import com.google.android.material.textfield.TextInputEditText
import pro.inmost.android.visario.ui.utils.DateParser
import java.util.*


/**
 * Edit text with [DatePickerDialog] that opens by clicking and set selected date to the view
 *
 */
class DatePickerEditText(
    context: Context,
    attrs: AttributeSet? = null
) : TextInputEditText(context, attrs),
    View.OnClickListener, OnDateSetListener {
    private var day = 0
    private var month = 0
    private var birthYear = 0

    init {
        setOnClickListener(this)
    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        birthYear = year
        month = monthOfYear
        day = dayOfMonth
        updateDisplay()
    }

    override fun onClick(v: View?) {
        val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.timeInMillis = DateParser.parseToMillis(text.toString())
        val dialog = DatePickerDialog(
            context, this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.datePicker.maxDate = System.currentTimeMillis()
        dialog.show()
    }

    // updates the date in the birth date EditText
    private fun updateDisplay() {
        setText(
            StringBuilder() // Month is 0 based so add 1
                .append(birthYear)
                .append("-")
                .append(if (month + 1 < 10) "0${month+1}" else month + 1)
                .append("-")
                .append(if (day < 10) "0${day}" else day)
        )
    }
}