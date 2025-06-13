package com.example.lab2.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Phone.class}, version = 1, exportSchema = false)
public abstract class PhoneRoomDatabase extends RoomDatabase {

    public abstract PhoneDao phoneDao();

    private static volatile PhoneRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PhoneRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhoneRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PhoneRoomDatabase.class, "phone_database")
                            .addCallback(roomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d("DB_INIT", "Dodano dane początkowe do bazy");

            databaseWriteExecutor.execute(() -> {

                PhoneDao dao = INSTANCE.phoneDao();
                dao.deleteAll();

                dao.insert(new Phone("Google", "Pixel 9", "14", "https://google.com/pixel9"));
                dao.insert(new Phone("Google", "Pixel 9 Pro", "14", "https://google.com/pixel9pro"));
                dao.insert(new Phone("Google", "Pixel 9 Pro XL", "14", "https://google.com/pixel9proxl"));
                dao.insert(new Phone("Google", "Pixel 9a", "14", "https://google.com/pixel9a"));
            });

        }
    };
}
