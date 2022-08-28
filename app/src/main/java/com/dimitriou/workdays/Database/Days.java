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

    public Days(String day, String month, String year, String type, String paid) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.type = type;
        this.paid = paid;
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
}
