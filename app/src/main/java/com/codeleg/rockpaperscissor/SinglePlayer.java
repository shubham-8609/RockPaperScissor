package com.codeleg.rockpaperscissor;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SinglePlayer extends AppCompatActivity {
    TextView wonScoreValue;
    TextView drawScoreValue;
    TextView loseScoreValue;
    LinearLayout choosingBox;
    LinearLayout resultBox;
    CardView rockButton;
    CardView paperButton;
    CardView scissorButton;
    ImageView userValueImage;
    ImageView computerValueImage;
    Animation rotateAnim;
    LinearLayout mainElement;
    TextView resultText;
    Animation scaleAnim;
    AppCompatButton playAgainButton;
    Animation slideanim;
    Animation fadeInAnim;
    int wonCount = 0;
    int drawnCount = 0;
    int loseCount = 0;
    Game game = new Game();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_single_player);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        rockButton.setOnClickListener(v -> handlePlayerChoice(Choice.ROCK));
        paperButton.setOnClickListener(v -> handlePlayerChoice(Choice.PAPER));
        scissorButton.setOnClickListener(v -> handlePlayerChoice(Choice.SCISSOR));
    }

    public void init() {
        this.wonScoreValue = findViewById(R.id.won_score_value);
        this.drawScoreValue = findViewById(R.id.draw_score_value);
        this.loseScoreValue = findViewById(R.id.lose_score_value);
        this.rockButton = findViewById(R.id.rock_button);
        this.paperButton = findViewById(R.id.paper_button);
        this.choosingBox = findViewById(R.id.choosing_box);
        this.mainElement = findViewById(R.id.main);
        this.resultBox = findViewById(R.id.result_box);
        this.scissorButton = findViewById(R.id.scissor_button);
        this.userValueImage = findViewById(R.id.user_value);
        this.computerValueImage = findViewById(R.id.computer_value);
        this.playAgainButton = findViewById(R.id.play_again_button);
        this.resultText = findViewById(R.id.result_text);
        this.slideanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_anim);
        this.rotateAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anim);
        this.scaleAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_anim);
        this.fadeInAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
    }

    public void handlePlayerChoice(Choice userChosenOption) {
        int computerChoiceIndex = game.generateRandom();
        Choice computerChosenOption = Choice.values()[computerChoiceIndex];

        int wonState = checkWinner(userChosenOption, computerChosenOption);

        changeLayout();
        setupChoiceImages(userChosenOption, computerChosenOption);
        startChoiceAnimations();
    }

    // Returns: 0 = Draw, 1 = Win, 2 = Lose
    public int checkWinner(Choice userChosenOption, Choice computerChosenOption) {
        int state;
        if (userChosenOption == computerChosenOption) {
            state = 0;
            resultText.setText("Game Drawn..");
            drawnCount++;
            setValues(state);
            drawScoreValue.setTextColor(ContextCompat.getColor(this, R.color.orange));
        } else if (
                (userChosenOption == Choice.ROCK && computerChosenOption == Choice.PAPER) ||
                        (userChosenOption == Choice.PAPER && computerChosenOption == Choice.SCISSOR) ||
                        (userChosenOption == Choice.SCISSOR && computerChosenOption == Choice.ROCK)
        ) {
            state = 2;
            loseCount++;
            setValues(state);
            resultText.setText("You lose..😟🥺");
            loseScoreValue.setTextColor(ContextCompat.getColor(this, R.color.red));
        } else {
            state = 1;
            wonCount++;
            setValues(state);
            resultText.setText("You Won..🥳🎉");
            wonScoreValue.setTextColor(ContextCompat.getColor(this, R.color.pure_green));
        }
        return state;
    }

    public void changeLayout() {
        choosingBox.setVisibility(View.GONE);
        resultBox.setVisibility(View.VISIBLE);
    }

    // Helper to set images for both players
    private void setupChoiceImages(Choice userChoice, Choice computerChoice) {
        userValueImage.setImageResource(getChoiceDrawable(userChoice));
        computerValueImage.setImageResource(getChoiceDrawable(computerChoice));
    }

    // Helper to start animations for both images and result text
    private void startChoiceAnimations() {
        AnimationSet animSet = new AnimationSet(true);
        animSet.addAnimation(rotateAnim);
        animSet.addAnimation(scaleAnim);

        computerValueImage.startAnimation(animSet);
        userValueImage.startAnimation(animSet);
        resultText.startAnimation(slideanim);
    }

    private int getChoiceDrawable(Choice choice) {
        switch (choice) {
            case ROCK:
                return R.drawable.rock_big;
            case PAPER:
                return R.drawable.paper_big;
            case SCISSOR:
                return R.drawable.scissor_big;
            default:
                return 0;
        }
    }

    public void playAgain(View view) {
        resultBox.setVisibility(View.GONE);
        choosingBox.setVisibility(View.VISIBLE);

        choosingBox.startAnimation(fadeInAnim);
        choosingBox.startAnimation(scaleAnim);
    }

    void setValues(int state) {
        switch (state) {
            case 0:
                drawScoreValue.setText(String.valueOf(drawnCount));
                drawScoreValue.startAnimation(slideanim);
                drawScoreValue.setTextColor(ContextCompat.getColor(this, R.color.orange));
                break;
            case 1:
                wonScoreValue.setText(String.valueOf(wonCount));
                wonScoreValue.startAnimation(slideanim);
                wonScoreValue.setTextColor(ContextCompat.getColor(this, R.color.pure_green));
                break;
            case 2:
                loseScoreValue.setText(String.valueOf(loseCount));
                loseScoreValue.startAnimation(slideanim);
                loseScoreValue.setTextColor(ContextCompat.getColor(this, R.color.red));
                break;
        }
    }

    public enum Choice {
        ROCK,
        PAPER,
        SCISSOR
    }
}