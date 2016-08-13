package com.icaboalo.yana.presentation.di.component;

import com.icaboalo.yana.presentation.di.PerActivity;
import com.icaboalo.yana.presentation.di.module.ActivityModule;
import com.icaboalo.yana.presentation.di.module.UserModule;
import com.icaboalo.yana.presentation.screens.action_plan.loading.LoadingActivity;
import com.icaboalo.yana.presentation.screens.action_plan.main.MainActivity;
import com.icaboalo.yana.presentation.screens.login.LoginActivity;
import com.icaboalo.yana.presentation.screens.register.RegisterActivity;
import com.icaboalo.yana.presentation.screens.tour.TourActivity;

import dagger.Component;

/**
 * @author icaboalo on 09/08/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent {

    void inject(LoginActivity loginActivity);

    void inject(LoadingActivity loadingActivity);

    void inject(TourActivity tourActivity);

    void inject(RegisterActivity registerActivity);

    void inject(MainActivity mainActivity);
}
