package com.example.lab2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.lab2.data.Phone;
import com.example.lab2.repository.PhoneRepository;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {

    private final PhoneRepository mRepository;
    private final LiveData<List<Phone>> mAllPhones;

    public PhoneViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PhoneRepository(application);
        mAllPhones = mRepository.getAllPhones();
    }

    public LiveData<List<Phone>> getAllPhones() {
        return mAllPhones;
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void insert(Phone phone) {
        mRepository.insert(phone);
    }

    public void update(Phone phone) {
        mRepository.update(phone);
    }

    public void delete(Phone phone) {
        mRepository.delete(phone);
    }

}
