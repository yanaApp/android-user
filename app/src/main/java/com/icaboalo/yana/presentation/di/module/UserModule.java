package com.icaboalo.yana.presentation.di.module;

import com.icaboalo.yana.domain.executors.PostExecutionThread;
import com.icaboalo.yana.domain.executors.ThreadExecutor;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.respository.Repository;
import com.icaboalo.yana.presentation.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author icaboalo on 09/08/16.
 */
@Module
public class UserModule {

    public UserModule() {
    }

    @Provides
    @PerActivity
    @Named("generalizedDetailUseCase")
    GenericUseCase provideGetGenericUseCase(Repository repository, PostExecutionThread postExecutionThread, ThreadExecutor threadExecutor){
        return new GenericUseCase(repository, postExecutionThread, threadExecutor);
    }
}
