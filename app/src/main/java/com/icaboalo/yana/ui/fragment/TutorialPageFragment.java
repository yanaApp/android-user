package com.icaboalo.yana.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.icaboalo.yana.ui.activity.AutoEvaluationActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by icaboalo on 03/06/16.
 */
public class TutorialPageFragment extends Fragment {

    ImageView mTutorialImage;
    TextView mTitle, mDescription;
    Button mContinue;
    int wordIndex = 0;
    final Handler handler = new Handler();
    boolean isCreated = false;


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
        mTutorialImage = (ImageView) view.findViewById(R.id.tutorial_image);
        mTitle = (TextView) view.findViewById(R.id.title);
        mDescription = (TextView) view.findViewById(R.id.description);
        mContinue = (Button) view.findViewById(R.id.continue_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final int position = getArguments().getInt("POSITION");
        switch (position){
            case 0:
                final String words[] = {"jugar.", "divertirte.", "sonreir."};
                if (!isCreated){
                    final Runnable changeText = new Runnable() {
                        @Override
                        public void run() {
                            wordIndex = (wordIndex + 1 ) % words.length;
                            mDescription.setText(words[wordIndex]);
                            handler.postDelayed(this, 1000);
                        }
                    };
                    handler.postDelayed(changeText, 1000);
                }
                break;
            case 1:
                Picasso.with(getActivity()).load(R.drawable.chat_128).into(mTutorialImage);
                mTitle.setText("Recibe apoyo");
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
                mContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("INTENT", "action plan test");
                        Intent goToTest = new Intent(getActivity(), AutoEvaluationActivity.class);
                        startActivity(goToTest);
                        getActivity().finish();
                    }
                });
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isCreated = true;
    }

}
