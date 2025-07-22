package com.codeleg.rockpaperscissor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {
    Intent iNext;
    TextView creditText;
    LottieAnimationView lottieAnimView;
    Animation textAnim;
    View rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        // Fade in whole splash screen
        rootLayout.setAlpha(0f);
        rootLayout.animate().alpha(1f).setDuration(400).start();

        // Animate credit text (pop-in)
        creditText.setScaleX(0.7f);
        creditText.setScaleY(0.7f);
        creditText.setAlpha(0f);
        creditText.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(650)
                .setStartDelay(180)
                .start();

        // Animate Lottie view for entrance (optional, feels premium)
        if (lottieAnimView != null) {
            lottieAnimView.setScaleX(0.88f);
            lottieAnimView.setScaleY(0.88f);
            lottieAnimView.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(400)
                    .setStartDelay(250)
                    .start();
        }

        // Splash duration
        int splashDuration = 1600; // 1.6 seconds

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Fade out before starting next activity
            rootLayout.animate()
                    .alpha(0f)
                    .setDuration(250)
                    .withEndAction(() -> {
                        startActivity(iNext);
                        finish();
                    })
                    .start();
        }, splashDuration);
    }

    public void init() {
        iNext = new Intent(SplashActivity.this, MainActivity.class);
        creditText = findViewById(R.id.credit_text);
        lottieAnimView = findViewById(R.id.lottie_animation_view); // update xml id accordingly
        textAnim = AnimationUtils.loadAnimation(this, R.anim.splash_text_anim);
        rootLayout = findViewById(R.id.main);
    }

//    @Override
//    public void onBackPressed() {
//        // Prevent back during splash
//    }
}