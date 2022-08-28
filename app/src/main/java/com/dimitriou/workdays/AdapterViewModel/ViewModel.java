package com.dimitriou.workdays.AdapterViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dimitriou.workdays.Database.Days;
import com.dimitriou.workdays.Database.Repository;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private final LiveData<List<Days>> allDays;

    public ViewModel(@NonNull Application application) {
        super(application);
        Repository repository = new Repository(application);
        allDays = repository.getAllDays();
    }

    public LiveData<List<Days>> getAllDays() {
        return allDays;
    }
}