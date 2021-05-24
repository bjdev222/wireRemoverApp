package com.wolcnore.wireremover;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;

public class Appintro extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Step 1:","Choose Image From Storage",R.drawable.s, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)));

        addSlide(AppIntroFragment.newInstance("Step 2:","Then Click on Upload Button",R.drawable.ss, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)));

        addSlide(AppIntroFragment.newInstance("Step 3:","Then Click on next button to process your image",R.drawable.sss, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)));

        addSlide(AppIntroFragment.newInstance("Step 4:","Yeah Your Photo done. You can download and share",R.drawable.ssss, ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)));


    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}
