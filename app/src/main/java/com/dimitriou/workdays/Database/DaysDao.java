package com.dimitriou.workdays.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaysDao {

    @Insert
    void insertDays(Days days);

    @Delete
    void deleteDays(Days days);

    @Update
    void updateDays(Days days);

    @Query("SELECT * FROM Days ORDER BY Id DESC")
    LiveData<List<Days>> getDays();

    @Query("DELETE FROM Days")
    void deleteAll();

    @Query("SELECT COUNT(id) FROM Days")
    int countAllDays();

    @Query("SELECT COUNT(id) FROM Days WHERE Pay = 'Yes'")
    int countPayDays();

    @Query("SELECT COUNT(id) FROM Days WHERE Pay = 'Yes' AND NOT Type = 'Half' AND NOT Type = 'Double' ")
    int countRestPayDays();

    @Query("SELECT COUNT(id) FROM Days WHERE NOT Type = 'Half' AND NOT Type = 'Double' ")
    int countRestWorkDays();

    @Query("SELECT COUNT(id) FROM Days WHERE Pay = 'Yes' AND Type = 'Half' ")
    int countHalfPayDays();

    @Query("SELECT COUNT(id) FROM Days WHERE Type = 'Half' ")
    int countHalfWorkDays();

    @Query("SELECT COUNT(id) FROM Days WHERE Pay = 'Yes' AND Type = 'Double' ")
    int countDoublePayDays();

    @Query("SELECT COUNT(id) FROM Days WHERE Type = 'Double' ")
    int countDoubleWorkDays();

    @Query("SELECT DISTINCT Year FROM Days ORDER BY Year DESC")
    String[] savedYears();

    @Query("SELECT DISTINCT Month FROM Days WHERE Year = :year")
    String[] selectMonths(int year);

    @Query("UPDATE Days SET Pay = 'Yes' WHERE Id = :id")
    void makePayYes(int id);

    @Query("UPDATE Days SET Pay = 'No' WHERE Id = :id")
    void makePayNo(int id);


    @Query("SELECT COUNT(id) FROM Days WHERE Month = :month AND Year = :year")
    int countMonthWorkDays(int month,int year);
    @Query("SELECT COUNT(id) FROM Days WHERE Pay = 'Yes' AND Month = :month AND Year = :year")
    int countMonthPayDays(int month, int year);
    @Query("SELECT COUNT(id) FROM Days WHERE Pay = 'Yes' AND Month = :month AND Year = :year AND Type = 'Half'")
    int countMonthHalfPayDays(int month, int year);
    @Query("SELECT COUNT(id) FROM Days WHERE Pay = 'Yes' AND Month = :month AND Year = :year AND Type = 'Double'")
    int countMonthDoublePayDays(int month, int year);
    @Query("SELECT COUNT(id) FROM Days WHERE Pay = 'Yes' AND Month = :month AND Year = :year AND NOT Type = 'Half' AND NOT Type = 'Double'")
    int countMonthRestPayDays(int month, int year);

}
