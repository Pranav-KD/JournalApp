package androidsamples.java.journalapp;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class JournalTypeConverters {
    @TypeConverter
    public UUID toUUID(@NonNull String uuid) {
        return UUID.fromString(uuid);
    }

    @TypeConverter
    public String fromUUID(@NonNull UUID uuid) {
        return uuid.toString();
    }

    @TypeConverter
    public Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
