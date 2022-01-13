package androidsamples.java.journalapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EntryListFragment extends Fragment {
    private static final String TAG = "EntryListFragment";
    private EntryListViewModel mEntryListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEntryListViewModel = new ViewModelProvider(this).get(EntryListViewModel.class);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_list, container, false);
        FloatingActionButton fab = view.findViewById(R.id.btn_add_entry);
        fab.setOnClickListener(this::addNewEntry);

        RecyclerView entriesList = view.findViewById(R.id.recyclerView);
        entriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        EntryListAdapter adapter = new EntryListAdapter(getActivity());
        entriesList.setAdapter(adapter);

        mEntryListViewModel.getAllEntries().observe(getActivity(), adapter::setEntries);

        return view;
    }

    public void addNewEntry(View view) {
        JournalEntry entry = new JournalEntry("", new Date(), new Date(), new Date());
        mEntryListViewModel.insert(entry);
        EntryListFragmentDirections.AddEntryAction directions = EntryListFragmentDirections.addEntryAction();
        directions.setEntryId(entry.getUid().toString());
        Navigation.findNavController(view).navigate(directions);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.info_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.menu_info){
            Navigation.findNavController(getView()).navigate(R.id.infoAction);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



    private class EntryViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTxtTitle;
        private final TextView mTxtDate;
        private final TextView mTxtStartTime;
        private final TextView mTxtEndTime;
        private JournalEntry mEntry;


        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);

            mTxtTitle = itemView.findViewById(R.id.txt_item_title);
            mTxtDate = itemView.findViewById(R.id.txt_item_date);
            mTxtEndTime = itemView.findViewById(R.id.txt_item_end_time);
            mTxtStartTime = itemView.findViewById(R.id.txt_item_start_time);

            itemView.setOnClickListener(this::launchJournalEntryFragment);
        }

        private void launchJournalEntryFragment(View v) {
            Log.d(TAG, "launchJournalEntryFragment with Entry: " + mEntry.title());
            EntryListFragmentDirections.AddEntryAction directions = EntryListFragmentDirections.addEntryAction();
            directions.setEntryId(mEntry.getUid().toString());
            Navigation.findNavController(v).navigate(directions);
        }

        void bind(JournalEntry entry) {
            mEntry = entry;
            this.mTxtTitle.setText(mEntry.title());
            this.mTxtDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(mEntry.date()));
            this.mTxtStartTime.setText(new SimpleDateFormat("HH:mm").format(mEntry.startTime()));
            this.mTxtEndTime.setText(new SimpleDateFormat("HH:mm").format(mEntry.endTime()));
        }
    }

    private class EntryListAdapter extends RecyclerView.Adapter<EntryViewHolder> {
        private final LayoutInflater mInflater;
        private List<JournalEntry> mEntries;

        public EntryListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.fragment_entry, parent, false);
            return new EntryViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
            if (mEntries != null) {
                JournalEntry current = mEntries.get(position);
                holder.bind(current);
            }
        }

        @Override
        public int getItemCount() {
            return (mEntries == null) ? 0 : mEntries.size();
        }

        public void setEntries(List<JournalEntry> entries) {
            mEntries = entries;
            notifyDataSetChanged();
        }
    }
}
