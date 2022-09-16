package com.dimitriou.workdays.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Days")
public class Days {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Day")
    private final String day;

    @ColumnInfo(name = "Month")
    private final String month;

    @ColumnInfo(name = "Year")
    private final String year;

    @ColumnInfo(name = "Type")
    private final String type;

    @ColumnInfo(name = "Pay")
    private final String paid;

    @ColumnInfo(name = "Notes")
    private String notes;

    @ColumnInfo(name = "Time")
    private String time;

    public Days(String day, String month, String year, String type, String paid, String notes, String time) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.type = type;
        this.paid = paid;
        this.notes = notes;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public String getPaid() {
        return paid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
