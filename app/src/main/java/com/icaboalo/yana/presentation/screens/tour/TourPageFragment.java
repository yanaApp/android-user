package com.icaboalo.yana.presentation.screens.tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.old.ui.activity.EvaluationActivity;
import com.icaboalo.yana.old.ui.widget.TypewriterView;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by icaboalo on 03/06/16.
 */
public class TourPageFragment extends BaseFragment {

    @Nullable
    @BindView(R.id.ivTutorial)
    ImageView mTutorialImage;
    @Nullable
    @BindView(R.id.tvTitle)
    TextView mTitle;
    @Nullable
    @BindView(R.id.tvDescription)
    TextView mDescription;
//    @Nullable
//    @BindView(R.id.typeView)
//    TypewriterView typeView;
    @Nullable
    @BindView(R.id.btContinue)
    Button mContinue;
    boolean isCreated = false;


    public TourPageFragment newInstance(int position){
        TourPageFragment fragment = new TourPageFragment();
        Bundle args = new Bundle();
        args.putInt("POSITION", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int position = getArguments().getInt("POSITION");
        switch (position){
            case 0:
                return inflater.inflate(R.layout.fragment_tutorial_start, container, false);

            case 1:
                return inflater.inflate(R.layout.fragment_tutorial_page, container, false);

            case 2:
                return inflater.inflate(R.layout.fragment_tutorial_page, container, false);

            case 3:
                return inflater.inflate(R.layout.fragment_tutorial_page, container, false);

            case 4:
                return inflater.inflate(R.layout.layout_title_description, container, false);

        }

        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void initialize() {
        final int position = getArguments().getInt("POSITION");
        switch (position){
            case 0:
                if (!isCreated){
//                    typeView.type("jugar.")
//                            .pause(500)
//                            .delete("jugar.")
//                            .pause(200)
//                            .type("divertirte.")
//                            .pause(500)
//                            .delete("divertirte.")
//                            .pause(200)
//                            .type("sonreir.")
//                            .pause(500)
//                            .delete("sonreir.")
//                            .pause(200)
//                            .type("vivir...");
                }
                break;
            case 1:
//                TODO CORRECT TEXTS
                Picasso.with(getActivity()).load(R.drawable.chat_128).into(mTutorialImage);
                mTitle.setText("RECIBE APOYO");
                mDescription.setText("Apoyate de una red de contactos que te respalde en esta etapa de tu vida.");
                break;
            case 2:
                Picasso.with(getActivity()).load(R.drawable.pie_chart_128).into(mTutorialImage);
                mTitle.setText("Monitorea tu progreso");
                mDescription.setText("El historial te permite revisar tus avances día con día.");
                break;
            case 3:
                Picasso.with(getActivity()).load(R.drawable.hand_128).into(mTutorialImage);
                mTitle.setText("Sigue tu plan de acción");
                mDescription.setText("Completa las actividades diarias que te ayudarán a sentirte mejor.");
                break;
            case 4:
                mContinue.setOnClickListener(v -> navigator.navigateTo(getContext(),
                        new Intent(getContext(), EvaluationActivity.class)));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isCreated = true;
    }
}
