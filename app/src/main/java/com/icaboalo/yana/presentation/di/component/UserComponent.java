package com.icaboalo.yana.presentation.di.component;

import com.icaboalo.yana.presentation.di.PerActivity;
import com.icaboalo.yana.presentation.di.module.ActivityModule;
import com.icaboalo.yana.presentation.di.module.UserModule;
import com.icaboalo.yana.presentation.screens.action_plan.loading.LoadingActivity;
import com.icaboalo.yana.presentation.screens.login.LoginActivity;

import dagger.Component;

/**
 * @author icaboalo on 09/08/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent {

    void inject(LoginActivity loginActivity);

    void inject(LoadingActivity loadingActivity);
}
