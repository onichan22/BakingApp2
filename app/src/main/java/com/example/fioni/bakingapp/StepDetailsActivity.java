package com.example.fioni.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.fioni.bakingapp.fragments.StepDetailsFragment;
import com.example.fioni.bakingapp.utilities.Global;
import com.example.fioni.bakingapp.utilities.Step;

/**
 * Created by fioni on 9/9/2017.
 */

public class StepDetailsActivity extends AppCompatActivity {
    public static String ON_STEP_KEY = "ON STEP";
    Step mStep;
    int mOnStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent recipeIntent = getIntent();
        mStep = recipeIntent.getParcelableExtra("thisStep");

        setContentView(R.layout.activity_steps_detail);

        final View nextButton = findViewById(R.id.next_step);
        final View prevButton = findViewById(R.id.prev_step);

        View.OnClickListener clickNext = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepDetailsFragment newStepDetailsFragment = new StepDetailsFragment();
                newStepDetailsFragment.setRecipeId(mStep.getR_id());
                if (v == nextButton) {
                    if (mOnStep < Global.stepSetSize - 1) {
                        mOnStep = mOnStep + 1;
                        newStepDetailsFragment.setStepId(mOnStep);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.steps_detail_container, newStepDetailsFragment)
                                .commit();
                    } else {
                        mOnStep = Global.stepSetSize;
                    }

                }
                if (v == prevButton) {
                    if (mOnStep > 0) {
                        mOnStep = mOnStep - 1;
                        newStepDetailsFragment.setStepId(mOnStep);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.steps_detail_container, newStepDetailsFragment)
                                .commit();
                    } else {
                        mOnStep = 0;
                    }
                }
            }
        };

        nextButton.setOnClickListener(clickNext);
        prevButton.setOnClickListener(clickNext);

        if (savedInstanceState == null) {

            mOnStep = Integer.parseInt(mStep.getId());
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();

            stepDetailsFragment.setRecipeId(mStep.getR_id());
            stepDetailsFragment.setStepId(mOnStep);

            fragmentManager.beginTransaction()
                    .add(R.id.steps_detail_container, stepDetailsFragment)
                    .commit();
        }
        if (savedInstanceState != null) {
            mOnStep = savedInstanceState.getInt(ON_STEP_KEY);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ON_STEP_KEY, mOnStep);
        super.onSaveInstanceState(outState);
    }
}
