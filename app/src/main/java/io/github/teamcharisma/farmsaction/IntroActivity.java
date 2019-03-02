package io.github.teamcharisma.farmsaction;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class IntroActivity extends AppCompatActivity {

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public float px(float dp){
        return dp * ((float) getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public float dp(float px){
        return px / ((float) getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        new Handler().postDelayed(this::startAnimation, 1000);
    }

    private void startAnimation(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        ImageView farmLogo = findViewById(R.id.farm_logo),
                charismaLogo = findViewById(R.id.charisma_logo);
        farmLogo.animate()
                .scaleX(0.5f)
                .scaleY(0.5f)
                .translationY(-1.25f*farmLogo.getY()+px(5))
                .setDuration(600);
        charismaLogo.animate()
                .translationY(screenHeight-charismaLogo.getY())
                .setDuration(600);
    }
}
