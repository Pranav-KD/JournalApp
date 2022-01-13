package androidsamples.java.journalapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "journal_table")
public class JournalEntry {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private UUID mUid;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "date")
    private Date mDate;

    @ColumnInfo(name = "startTime")
    private Date mStartTime;

    @ColumnInfo(name = "endTime")
    private Date mEndTime;

    public JournalEntry(@NonNull String title, Date date, Date startTime, Date endTime) {
        mUid = UUID.randomUUID();
        mTitle = title;
        mStartTime = startTime;
        mEndTime = endTime;
        mDate = date;
    }

    @NonNull
    public UUID getUid() {
        return mUid;
    }

    public void setUid(@NonNull UUID id) {
        mUid = id;
    }

    @NonNull
    public String title() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date startTime() {
        return mStartTime;
    }

    public Date endTime() {
        return mEndTime;
    }

    public Date date() {return mDate;}

    public void setStartTime(Date startTime){
        mStartTime = startTime;
    }

    public void setEndTime(Date endTime){
        mEndTime = endTime;
    }

    public void setDate(Date date) {mDate = date;}

}
