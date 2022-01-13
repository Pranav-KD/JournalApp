package androidsamples.java.journalapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment {

    private static final String TAG = "EntryDetailsFragment";
    private static Button mStartTime;
    private static Button mEndTime;
    private static Button mDate;
    private EntryDetailsViewModel mEntryDetailsViewModel;
    private EditText mEditTitle;
    private JournalEntry mEntry;
    private static String dialogTime;
    private static boolean isStartTime;
    private static boolean isEndTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mEntryDetailsViewModel = new ViewModelProvider(getActivity()).get(EntryDetailsViewModel.class);
        String uid = EntryDetailsFragmentArgs.fromBundle(getArguments()).getEntryId();
        Log.d(TAG, "Loading entry with UID as :" + uid);
        mEntryDetailsViewModel.loadEntry(UUID.fromString(uid));

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_details, container, false);
        mStartTime = view.findViewById(R.id.btn_start_time);
        mEndTime = view.findViewById(R.id.btn_end_time);
        mDate = view.findViewById(R.id.btn_entry_date);
        mEditTitle = view.findViewById(R.id.edit_title);
        mStartTime.setOnClickListener(this::setStartTime);
        mEndTime.setOnClickListener(this::setEndTime);
        mDate.setOnClickListener(this::setDate);
        view.findViewById(R.id.btn_save).setOnClickListener(this::saveEntry);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEntryDetailsViewModel.getEntryLiveData().observe(getActivity(),
                entry -> {
                    this.mEntry = entry;
                    if (entry != null) updateUI();
                });

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share_delete_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.menu_share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Look what I have been up to: " +mEditTitle.getText() + " on " + mDate.getText().toString() + ", " + mStartTime.getText().toString() + " to " + mEndTime.getText().toString());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, "Share Activity");
            startActivity(shareIntent);
            return true;
        }
        if(item.getItemId() == R.id.menu_delete){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.delete_message)
                    .setTitle(R.string.delete_title);
            // Add the buttons
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    mEntryDetailsViewModel.deleteEntry(mEntry);
                    getActivity().onBackPressed();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });


            // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setStartTime(View v){
        isStartTime = true;
        Navigation.findNavController(v).navigate(R.id.timePickerAction);

    }

    public void setEndTime(View v){
        isEndTime = true;
        Navigation.findNavController(v).navigate(R.id.timePickerAction);
    }

    public void setDate(View v){
        Navigation.findNavController(v).navigate(R.id.datePickerAction);
    }

    private void updateUI() {
        String start_time_text = new SimpleDateFormat("HH:mm").format(mEntry.startTime());
        String end_time_text = new SimpleDateFormat("HH:mm").format(mEntry.endTime());
        String date_text = new SimpleDateFormat("dd/MM/yyyy").format(mEntry.date());
        mEditTitle.setText(mEntry.title());
        mStartTime.setText(start_time_text);
        mEndTime.setText(end_time_text);
        mDate.setText(date_text);
    }

    private void saveEntry(View v){
        Log.d(TAG, "Save button clicked");
        mEntry.setTitle(mEditTitle.getText().toString());
        String startTime = "01-01-2021 "+ mStartTime.getText().toString();
        Date startTimeDate;
        Date endTimeDate;
        Date dateSet;
        try {
            startTimeDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(startTime);
        }
        catch(Exception e){
            Log.e(TAG, "Start Time not set");
            startTimeDate = new Date();
        }
        String endTime = "01-01-2021 " + mEndTime.getText().toString();
        Log.d(TAG, "The end time is : " + endTime);
        try {
            endTimeDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(endTime);
            Log.d(TAG, "The date in endTime is :" + endTimeDate.toString());
        }
        catch(Exception e){
            Log.e(TAG, "End Time not set");
            endTimeDate = new Date();
        }
        Log.d(TAG, "The date is :" + mDate.getText().toString());
        try{
            dateSet = new SimpleDateFormat("dd/MM/yyyy").parse(mDate.getText().toString());
        }
        catch(Exception e) {
            Log.e(TAG, "Date not set");
            dateSet = new Date();
        }
        mEntry.setStartTime(startTimeDate);
        mEntry.setEndTime(endTimeDate);
        mEntry.setDate(dateSet);
        mEntryDetailsViewModel.saveEntry(mEntry);
        getActivity().onBackPressed();
    }

    public static void setDialogTime(String s){
        dialogTime = s;
        Log.d(TAG, "Inside setDialogTime, dialog time is :" + dialogTime );
        if(isStartTime) {
            mStartTime.setText(s);
            isStartTime = false;
        }
        if(isEndTime) {
            mEndTime.setText(s);
            isEndTime = false;
        }
    }
    public static void setDialogDate(String s){
        mDate.setText(s);
    }
}