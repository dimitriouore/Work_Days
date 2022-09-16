package com.dimitriou.workdays.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {Days.class}, version = 2, exportSchema = false)

public abstract class Database extends RoomDatabase {

    public abstract DaysDao daysDao();
    private static Database instance;

    static Migration migration = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'Days' ADD COLUMN 'Notes' TEXT DEFAULT ''");
            database.execSQL("ALTER TABLE 'Days' ADD COLUMN 'Time' TEXT DEFAULT '' ");
        }
    };

    static Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    Database.class, "database")
                    .addMigrations(migration)
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static final Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}