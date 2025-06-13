package com.example.lab2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface PhoneDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Phone phone);

    @Query("DELETE FROM phone_table")
    void deleteAll();

    @Query("SELECT * FROM phone_table ORDER BY model ASC")
    LiveData<List<Phone>> getAllPhones();

    @Update
    void update(Phone phone);

    @Delete
    void delete(Phone phone);

}
