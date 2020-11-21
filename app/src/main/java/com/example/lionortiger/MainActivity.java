package com.example.lionortiger;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView lionOrTiger;
    private GridLayout gridLayout;
    private Button resetButton;
    enum Player{
        TIEGR,LION,NONE};
    Player currentPlayer;
    int winningCombinations [][]={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6} };
    Player playerChoices [] = new Player[9];
    boolean isGameOver = false;
    int i=0;
    final float rotation =3600f;
    MediaPlayer mediaPlayer;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI COMPONENT
        lionOrTiger = findViewById(R.id.lionOrTiger);
        gridLayout = findViewById(R.id.gridLayout);
        resetButton = findViewById(R.id.resetButton);
        currentPlayer=Player.LION;

        for (int index = 0;index<gridLayout.getChildCount();index++)
        {
           playerChoices[index]=Player.NONE;


        }
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });




    }
    public  void imageViewIsTapped(View imageView)
    {

        ImageView tappedImageView = (ImageView) imageView;
        int tagTapped = Integer.parseInt(tappedImageView.getTag().toString());


        if(playerChoices[tagTapped]==Player.NONE && !isGameOver) {
            i++;
            tappedImageView.setTranslationX(-2000);


            if (currentPlayer == Player.LION) {
                tappedImageView.setImageResource(R.drawable.lion);
                playerChoices[tagTapped] = Player.LION;
                currentPlayer = Player.TIEGR;
                tappedImageView.animate().translationXBy(2000f).rotation(rotation).alpha(1).setDuration(2000);
            } else if (currentPlayer == Player.TIEGR) {
                tappedImageView.setImageResource(R.drawable.tiger);
                playerChoices[tagTapped] = Player.TIEGR;
                currentPlayer = Player.LION;
                tappedImageView.animate().translationXBy(2000f).rotation(3600f).alpha(1).setDuration(2000);

            }
            checkWinnerOrDraw();


        }

    }
    public void checkWinnerOrDraw() {
        for (int[] winnerCombination : winningCombinations) {

            if (playerChoices[winnerCombination[0]] == playerChoices[winnerCombination[1]]
                    && playerChoices[winnerCombination[1]] == playerChoices[winnerCombination[2]]
                    && playerChoices[winnerCombination[0]] != Player.NONE) {
                isGameOver = true;
                resetButton.setVisibility(View.VISIBLE);
                mediaPlayer= MediaPlayer.create(this,R.raw.win);
                mediaPlayer.start();

                if (currentPlayer == Player.LION) {
                    Toast.makeText(this, "WINNER IS TIGER", Toast.LENGTH_LONG).show();
                    lionOrTiger.setText("WINNER IS TIGER");

                } else if (currentPlayer == Player.TIEGR) {
                    Toast.makeText(this, "WINNER IS LION", Toast.LENGTH_LONG).show();
                    lionOrTiger.setText("WINNER IS LION");

                }


            }

        }
        if (i == 9&&!isGameOver)
        {
            MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.draw);
            mediaPlayer.start();
            resetButton.setVisibility(View.VISIBLE);
            Toast.makeText(this,"MATCH IS DRAW !",Toast.LENGTH_LONG).show();
            lionOrTiger.setText("MATCH DRAW !");

        }



    }
    public void resetGame()
    {
        isGameOver=false;
        lionOrTiger.setText("LION OR TIGER");
        i=0;
        for (int index = 0;index<playerChoices.length;index++)
        {
            ImageView imageViewToGetNull=(ImageView) gridLayout.getChildAt(index);
            imageViewToGetNull.setImageDrawable(null);
            imageViewToGetNull.setAlpha(0.2f);
            playerChoices[index]=Player.NONE;
            imageViewToGetNull.setTranslationX(0);
            imageViewToGetNull.setRotation(0);


        }
        currentPlayer=Player.LION;
       resetButton.setVisibility(View.GONE);

    }

}
