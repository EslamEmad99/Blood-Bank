package eslam.emad.bloodbank.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.emad.bloodbank.R;
import eslam.emad.bloodbank.adapters.OnBoardingPagerAdapter;
import eslam.emad.bloodbank.data.SharedPreferencesManger;
import eslam.emad.bloodbank.data.models.OnBoardingModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnBoardingActivity extends AppCompatActivity {

    @BindView(R.id.activity_on_boarding_screen_viewpager)
    ViewPager screenPager;
    @BindView(R.id.activity_on_boarding_btn_next)
    Button btnNext;
    @BindView(R.id.activity_on_boarding_tab_indicator)
    TabLayout tabIndicator;
    @BindView(R.id.activity_on_boarding_btn_get_started)
    Button btnGetStarted;
    @BindView(R.id.activity_on_boarding_tv_skip)
    TextView tvSkip;
    OnBoardingPagerAdapter introViewPagerAdapter;
    int position = 0;
    Animation btnAnim;
    List<OnBoardingModel> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // when this activity is about to be launch we need to check if its openened before or not
        if (SharedPreferencesManger.getINSTANCE(getApplicationContext()).restoreBooleanValue("is_intro_opened")) {
            Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(mainActivity);
            finish();
        }

        setContentView(R.layout.activity_on_boarding);
        ButterKnife.bind(this);

        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);

        mList = new ArrayList<>();
        mList.add(new OnBoardingModel("1 Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit", R.drawable.onboarding_baclground_one));
        mList.add(new OnBoardingModel("2 Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit", R.drawable.onboarding_background_two));
        mList.add(new OnBoardingModel("3 Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit", R.drawable.onboarding_background_three));

        introViewPagerAdapter = new OnBoardingPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenPager);

        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() - 1) {
                    loadLastScreen();
                } else {
                    btnNext.setVisibility(View.VISIBLE);
                    btnGetStarted.setVisibility(View.INVISIBLE);
                    tvSkip.setVisibility(View.VISIBLE);
                    tabIndicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        btnGetStarted.setAnimation(btnAnim);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @OnClick(R.id.activity_on_boarding_btn_next)
    public void onActivityOnBoardingBtnNextClicked() {
        position = screenPager.getCurrentItem();
        if (position < mList.size()) {
            position++;
            screenPager.setCurrentItem(position);
        }
        if (position == mList.size() - 1) { // when we rech to the last screen
            //show the GETSTARTED Button and hide the indicator and the next button
            loadLastScreen();
        }
    }

    @OnClick(R.id.activity_on_boarding_btn_get_started)
    public void onActivityOnBoardingBtnGetStartedClicked() {
        Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(mainActivity);
        // also we need to save a boolean value to storage so next time when the user run the app
        // we could know that he is already checked the intro screen activity
        SharedPreferencesManger.getINSTANCE(getApplicationContext()).saveBooleanValue("is_intro_opened",true);
        finish();
    }

    @OnClick(R.id.activity_on_boarding_tv_skip)
    public void onActivityOnBoardingTvSkipClicked() {
        screenPager.setCurrentItem(mList.size());
    }
}