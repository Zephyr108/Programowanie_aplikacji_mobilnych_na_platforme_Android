package com.example.lab2.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.lab2.data.Phone;
import com.example.lab2.data.PhoneDao;
import com.example.lab2.data.PhoneRoomDatabase;

import java.util.List;

public class PhoneRepository {
    private final PhoneDao mPhoneDao;
    private final LiveData<List<Phone>> mAllPhones;

    public PhoneRepository(Application application) {
        PhoneRoomDatabase db = PhoneRoomDatabase.getDatabase(application);
        mPhoneDao = db.phoneDao();
        mAllPhones = mPhoneDao.getAllPhones();
    }

    public LiveData<List<Phone>> getAllPhones() {
        return mAllPhones;
    }

    public void deleteAll() {
        PhoneRoomDatabase.databaseWriteExecutor.execute(mPhoneDao::deleteAll);
    }

    public void insert(Phone phone) {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPhoneDao.insert(phone);
        });
    }

    public void update(Phone phone) {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> mPhoneDao.update(phone));
    }

    public void delete(Phone phone) {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> mPhoneDao.delete(phone));
    }

}

