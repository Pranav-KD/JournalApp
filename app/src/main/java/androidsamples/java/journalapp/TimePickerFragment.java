package androidsamples.java.journalapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    private EntryDetailsViewModel model;
    public final String TAG = "TimePickerFragment";
    private String s;

    @NonNull
    public static TimePickerFragment newInstance(Date time) {
        // TODO implement the method
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO implement the method
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // TODO implement the method
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(requireContext(), this, hour, minute, false);

    }
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        if(minute > 10)
            s = hourOfDay + ":" + minute;
        else
            s = hourOfDay + ":0" + minute;
        Log.d(TAG, "The time is : " + s );
        EntryDetailsFragment.setDialogTime(s);
    }
}
