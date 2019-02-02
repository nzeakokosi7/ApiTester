package com.varscon.apitester.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;
import com.varscon.apitester.R;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_onboarding);
        //        Setting the cards pages with text and image icons

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("ApiTest", "This is a dummy app built to consume Reqres' dummy RESTful API", R.drawable.restapi);

        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Ease of Access", "Our app gives you access to a variety of content", R.drawable.contact);
//
//        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Collaborate", "Share your happiness, knowledge with like minds in your discipline" , R.drawable.collaborate);

        ahoyOnboarderCard1.setTitleColor(R.color.blue_primary_dark);

        ahoyOnboarderCard2.setTitleColor(R.color.background_color);
//
//        ahoyOnboarderCard3.setTitleColor(R.color.cyan_primary_dark);

        ahoyOnboarderCard1.setBackgroundColor(R.color.blue_primary_dark);

        ahoyOnboarderCard2.setBackgroundColor(R.color.background_color);
//
//        ahoyOnboarderCard3.setBackgroundColor(R.color.cyan_primary_dark);

//        Adding them to an Arraylist for swipeable view

        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
//        pages.add(ahoyOnboarderCard3);

        for (AhoyOnboarderCard page : pages){
            page.setTitleColor(R.color.grey_300);
            page.setDescriptionColor(R.color.grey_300);
            page.setDescriptionTextSize(dpToPixels(6,this));
            page.setTitleTextSize(dpToPixels(10, this));

        }

        setFinishButtonTitle("Get Started");
        showNavigationControls(true);
        setGradientBackground();

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
            setFinishButtonDrawableStyle(ContextCompat.getDrawable(this, R.drawable.rounded_button));
        }

        //       Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        //       setFont(face);

        setOnboardPages(pages);
    }


    @Override
    public void onFinishButtonPressed() {

//      This would add the boolean value to the first run file of the shared preferences ensuring that this activity is never shown again
//      unless this app is uninstalled and freshly reinstalled or app_data is lost/cleared

        SharedPreferences settings = getSharedPreferences("prefs", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("firstRun", false);
        editor.commit();


        startActivity(new Intent(this , AuthActivity.class));

    }
}
