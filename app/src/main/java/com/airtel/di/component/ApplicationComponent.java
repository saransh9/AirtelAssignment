package com.airtel.di.component;



import com.airtel.ApplicationClass;
import com.airtel.data.ApiCalls;
import com.airtel.di.modules.ApiModule;
import com.airtel.di.scope.ApplicationScope;

import dagger.Component;

/**
 * Created by saransh on 15/03/18.
 */

@ApplicationScope
@Component(modules = {ApiModule.class})
public interface ApplicationComponent {

    void inject(ApplicationClass applicationClass);

    ApiCalls getapiCalls();
}
