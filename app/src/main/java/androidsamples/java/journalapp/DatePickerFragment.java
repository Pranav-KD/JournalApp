package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
  @NonNull
  public static DatePickerFragment newInstance(Date date) {
    // TODO implement the method
    return null;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // TODO implement the method
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int date = c.get(Calendar.DATE);
    return new DatePickerDialog(requireContext(), this, year, month, date);
  }
  public void onDateSet(DatePicker v, int year, int month, int date){
    //do something
    String s = date + "/" + (month + 1) + "/" + year;
    EntryDetailsFragment.setDialogDate(s);
  }
}
