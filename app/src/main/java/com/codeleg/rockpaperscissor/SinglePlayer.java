    package com.codeleg.rockpaperscissor;

    import android.os.Bundle;

    import android.view.View;
    import android.view.animation.Animation;
    import android.view.animation.AnimationUtils;
import android.view.animation.AnimationSet;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.AppCompatButton;
    import androidx.cardview.widget.CardView;
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

            rockButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handlePlayerChoice("rock");
                }
            });

            paperButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handlePlayerChoice("paper");
                }
            });

            scissorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handlePlayerChoice("scissor");
                }
            });
        }

        public void init(){
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
            this.resultText  = findViewById(R.id.result_text);
            this.slideanim = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.slide_anim);
            this.rotateAnim = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.rotate_anim);
            this.scaleAnim = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.scale_anim);
            this.fadeInAnim = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.fade_in);



        }

        public void handlePlayerChoice(String userChoosenOption) {
            String computerChoosenOption = ""; // Initialize with a default value
            int computerChoice = game.generateRandom();

            switch (computerChoice){
                case 0:
                    computerChoosenOption = "rock";
                    break;
                case 1:
                    computerChoosenOption = "paper";
                    break;
                case 2 :
                    computerChoosenOption = "scissor";
            }

            int wonState = checkWinner(userChoosenOption , computerChoosenOption);
            switch (wonState){
                case 0:
                    break;
                case 1:
                    break;
                case 2:
            }
            changeLayout();
            showAnimations(wonState, userChoosenOption , computerChoosenOption);
        }

        public int checkWinner(String userChoosenOption , String computerChoosenOption){
            int state; // 0 --> draw , 1 --> userWon , 2 ---> computer Won..
            if (userChoosenOption.equals(computerChoosenOption)){
                state = 0;
                resultText.setText("Game Drawn..");
                drawnCount++;
                setValues();

                resultText.setTextColor(getResources().getColor(R.color.orange));

            }else if((userChoosenOption.equals("rock") && computerChoosenOption.equals("paper"))  || (userChoosenOption.equals("paper") && (computerChoosenOption.equals("scissor"))) || (userChoosenOption.equals("scissor") && computerChoosenOption.equals("rock"))){
                state = 2;
                loseCount++;
                setValues();
                resultText.setText("You lose..😟🥺");
                resultText.setTextColor(getResources().getColor(R.color.red));
               // Example: Set background to red for a loss
            }else{
                state = 1;
                wonCount++;
                setValues();
                resultText.setText("You Won..🥳🎉");
                resultText.setTextColor(getResources().getColor(R.color.pure_green));
             // Example: Set background to green for a win
            }
            return state;
        }
        public void changeLayout(){
            choosingBox.setVisibility(View.GONE);
            resultBox.setVisibility(View.VISIBLE);
        }
        public void showAnimations(int wonState , String userChoosenOption , String computerChoosenOption){
            int userImageResource = 0;
            int computerImageResource = 0;

            switch (userChoosenOption) {
                case "rock":
                    userImageResource = R.drawable.rock_big; // Assuming you have rock.png, rock.xml etc. in drawable

                    break;
                case "paper":
                    userImageResource = R.drawable.paper_big; // Assuming you have paper.png, paper.xml etc. in drawable

                    break;
                case "scissor":
                    userImageResource = R.drawable.scissor_big; // Assuming you have scissor.png, scissor.xml etc. in drawable

                    break;
            }

            switch (computerChoosenOption) {
                case "rock":
                    computerImageResource = R.drawable.rock_big;
                    break;
                case "paper":
                    computerImageResource = R.drawable.paper_big;
                    break;
                case "scissor":
                    computerImageResource = R.drawable.scissor_big;
                    break;
            }

            userValueImage.setImageResource(userImageResource);
            computerValueImage.setImageResource(computerImageResource);

            AnimationSet computerAnimationSet = new AnimationSet(true);
            computerAnimationSet.addAnimation(rotateAnim);
            computerAnimationSet.addAnimation(scaleAnim);

            AnimationSet userAnimationSet = new AnimationSet(true);
            userAnimationSet.addAnimation(rotateAnim);
            userAnimationSet.addAnimation(scaleAnim);

            computerValueImage.startAnimation(computerAnimationSet);
            userValueImage.startAnimation(userAnimationSet);
            resultText.startAnimation(slideanim);
        }

       public  void playAgain(View view){
            resultBox.setVisibility(View.GONE);
            choosingBox.setVisibility(View.VISIBLE);

            choosingBox.startAnimation(fadeInAnim);
            choosingBox.startAnimation(scaleAnim);

        }
        void setValues(){
            wonScoreValue.setText(String.valueOf(wonCount));
            drawScoreValue.setText(String.valueOf(drawnCount));
            loseScoreValue.setText(String.valueOf(loseCount));
        }

    }
