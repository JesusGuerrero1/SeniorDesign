package com.example.smartmirror.ui.app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AppViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AppViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is app settings page");
    }

    public LiveData<String> getText() {
        return mText;
    }
}