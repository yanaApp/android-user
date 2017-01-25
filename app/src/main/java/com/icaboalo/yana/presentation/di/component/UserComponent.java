package com.icaboalo.yana.presentation.di.component;

import com.icaboalo.yana.presentation.di.PerActivity;
import com.icaboalo.yana.presentation.di.module.ActivityModule;
import com.icaboalo.yana.presentation.di.module.UserModule;
import com.icaboalo.yana.presentation.screens.chat_bot.ChatBotActivity;
import com.icaboalo.yana.presentation.screens.evaluation.EvaluationActivity;
import com.icaboalo.yana.presentation.screens.login.LoginActivity;
import com.icaboalo.yana.presentation.screens.main.MainActivity;
import com.icaboalo.yana.presentation.screens.main.activities.ActivitiesFragment;
import com.icaboalo.yana.presentation.screens.main.contact.ContactFragment;
import com.icaboalo.yana.presentation.screens.main.hotline.HotlineFragment;
import com.icaboalo.yana.presentation.screens.main.loading.LoadingActivity;
import com.icaboalo.yana.presentation.screens.main.profile.ProfileFragment;
import com.icaboalo.yana.presentation.screens.main.profile.birth_date.BirthDateActivity;
import com.icaboalo.yana.presentation.screens.main.profile.change_password.ChangePasswordActivity;
import com.icaboalo.yana.presentation.screens.main.profile.update.UpdateProfileActivity;
import com.icaboalo.yana.presentation.screens.main.progress.ProgressFragment;
import com.icaboalo.yana.presentation.screens.settings.SettingsActivity;
import com.icaboalo.yana.presentation.screens.register.RegisterActivity;
import com.icaboalo.yana.presentation.screens.schedule.ScheduleActivity;
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

    void inject(ActivitiesFragment activitiesFragment);

    void inject(ProfileFragment profileFragment);

    void inject(UpdateProfileActivity updateProfileActivity);

    void inject(ContactFragment contactFragment);

    void inject(ScheduleActivity scheduleActivity);

    void inject(HotlineFragment hotlineFragment);

    void inject(ProgressFragment progressFragment);

    void inject(SettingsActivity settingsActivity);

    void inject(EvaluationActivity evaluationActivity);

    void inject(ChangePasswordActivity changePasswordActivity);

    void inject(BirthDateActivity birthDateActivity);

    void inject(ChatBotActivity chatBotActivity);
}
