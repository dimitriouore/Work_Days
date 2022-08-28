package com.dimitriou.workdays.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private final DaysDao DaysDao;
    private final LiveData<List<Days>> Days;

    public Repository(Application application){
        Database database = Database.getInstance(application);
        DaysDao = database.daysDao();
        Days = DaysDao.getDays();
    }

    public void DaysInsert(Days days){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> DaysDao.insertDays(days));
        executorService.shutdown();
    }

    public void DaysDelete(Days days){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> DaysDao.deleteDays(days));
        executorService.shutdown();
    }

    public LiveData<List<Days>> getAllDays() {
        return Days;
    }

    public String[] getYears(){
        return DaysDao.savedYears();
    }

    public String[] getMonths(int year){
        return DaysDao.selectMonths(year);
    }

    public int getDaysWorked(){
        return DaysDao.countAllDays();
    }

    public int getDaysPaid(){
        return DaysDao.countPayDays();
    }

    public int getRestPayDays(){
        return DaysDao.countRestPayDays();
    }

    public int getRestWorkDays(){
        return DaysDao.countRestWorkDays();
    }

    public int getHalfPayDays(){
        return DaysDao.countHalfPayDays();
    }

    public int getHalfWorkDays(){
        return DaysDao.countHalfWorkDays();
    }

    public int getDoubleWorkDays(){
        return DaysDao.countDoubleWorkDays();
    }

    public int getDoublePayDays(){
        return DaysDao.countDoublePayDays();
    }

    public void makePayYes(int id){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> DaysDao.makePayYes(id));
        executorService.shutdown();
    }

    public void makePayNo(int id){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> DaysDao.makePayNo(id));
        executorService.shutdown();
    }

    public void DaysDeleteAll(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(DaysDao::deleteAll);
        executorService.shutdown();
    }

    public void Update(Days days){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> DaysDao.updateDays(days));
        executorService.shutdown();
    }

    //MONTHS
    public int getAllMonthDays(int month, int year) {
        return DaysDao.countMonthWorkDays(month, year);
    }
    public int getAllMonthPayDays(int month, int year) {
        return DaysDao.countMonthPayDays(month, year);
    }
    public int getAllMonthHalfPayDays(int month, int year) {
        return DaysDao.countMonthHalfPayDays(month,year);
    }
    public int getAllMonthDoublePayDays(int month, int year) {
        return DaysDao.countMonthDoublePayDays(month, year);
    }
    public int getAllMonthRestPayDays(int month, int year) {
        return DaysDao.countMonthRestPayDays(month, year);
    }
}