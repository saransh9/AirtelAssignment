package com.airtel.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.airtel.ApplicationClass;
import com.airtel.di.component.ActivityComponent;
import com.airtel.di.component.DaggerActivityComponent;
import com.airtel.di.modules.ActivityModule;


/**
 * Created by saransh on 15/03/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(ApplicationClass.get(this).getApplicationComponent())
                .build();

    }

    public ActivityComponent getActivityComponent()
    {
        return activityComponent;
    }
}
