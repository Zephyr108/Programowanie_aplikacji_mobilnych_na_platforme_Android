package com.example.lab2.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "phone_table")
public class Phone {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "producer")
    private String producer;

    @NonNull
    @ColumnInfo(name = "model")
    private String model;

    @ColumnInfo(name = "version")
    private String androidVersion;

    @ColumnInfo(name = "www")
    private String www;

    public Phone(@NonNull String producer, @NonNull String model, String androidVersion, String www) {
        this.producer = producer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.www = www;
    }

    public int getId() {
        return id;
    }

    public String getProducer() {
        return producer;
    }

    public String getModel() {
        return model;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public String getWww() {
        return www;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public String getVersion() {
        return androidVersion;
    }
}
