package com.icaboalo.yana.presentation.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.presentation.di.HasComponent;
import com.icaboalo.yana.presentation.di.component.ApplicationComponent;
import com.icaboalo.yana.presentation.navigation.Navigator;

import javax.inject.Inject;

/**
 * @author icaboalo on 31/07/16.
 */
public abstract class BaseFragment extends Fragment{

    @Inject
    protected Navigator navigator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    public abstract void initialize();

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link com.icaboalo.yana.presentation.di.component.ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent(){
        return ((MyApplication) getContext().getApplicationContext()).getApplicationComponent();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    protected void showToastMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
