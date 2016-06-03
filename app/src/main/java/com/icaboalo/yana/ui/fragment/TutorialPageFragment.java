package com.icaboalo.yana.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icaboalo.yana.R;

/**
 * Created by icaboalo on 03/06/16.
 */
public class TutorialPageFragment extends Fragment {

    ImageView mTutorialImage;
    TextView mTitle, mDescription;

    public TutorialPageFragment newInstance(int position){
        TutorialPageFragment fragment = new TutorialPageFragment();
        Bundle args = new Bundle();
        args.putInt("POSITION", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutorial_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTutorialImage = (ImageView) view.findViewById(R.id.tutorial_image);
        mTitle = (TextView) view.findViewById(R.id.title);
        mDescription = (TextView) view.findViewById(R.id.description);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
