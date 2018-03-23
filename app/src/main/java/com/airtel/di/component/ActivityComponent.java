package com.airtel.di.component;

import android.content.Context;


import com.airtel.ui.fullscreenimage.FullScreenImageView;
import com.airtel.ui.main.MainActivity;
import com.airtel.di.modules.ActivityModule;
import com.airtel.di.scope.ActivityContext;
import com.airtel.di.scope.PerActivity;

import dagger.Component;

/**
 * Created by saransh on 15/03/18.
 */

@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {

    @ActivityContext
    Context getContext();

    void inject(MainActivity mainActivity);

    void inject(FullScreenImageView fullScreenImageView);

}
