package com.example.bcde223ass3;

// Import the model
import com.example.bcde223ass3.model.*;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.graphics.Bitmap;



public class MainActivity extends AppCompatActivity {

    Game theGame;

    ImageView[][] levelImages = new ImageView[4][6];

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        levelImages[0][0] = findViewById(R.id.imageGrid0);
        levelImages[1][0] = findViewById(R.id.imageGrid1);
        levelImages[2][0] = findViewById(R.id.imageGrid2);
        levelImages[3][0] = findViewById(R.id.imageGrid3);
        levelImages[0][1] = findViewById(R.id.imageGrid4);
        levelImages[1][1] = findViewById(R.id.imageGrid5);
        levelImages[2][1] = findViewById(R.id.imageGrid6);
        levelImages[3][1] = findViewById(R.id.imageGrid7);
        levelImages[0][2] = findViewById(R.id.imageGrid8);
        levelImages[1][2] = findViewById(R.id.imageGrid9);
        levelImages[2][2] = findViewById(R.id.imageGrid10);
        levelImages[3][2] = findViewById(R.id.imageGrid11);
        levelImages[0][3] = findViewById(R.id.imageGrid12);
        levelImages[1][3] = findViewById(R.id.imageGrid13);
        levelImages[2][3] = findViewById(R.id.imageGrid14);
        levelImages[3][3] = findViewById(R.id.imageGrid15);
        levelImages[0][4] = findViewById(R.id.imageGrid16);
        levelImages[1][4] = findViewById(R.id.imageGrid17);
        levelImages[2][4] = findViewById(R.id.imageGrid18);
        levelImages[3][4] = findViewById(R.id.imageGrid19);
        levelImages[0][5] = findViewById(R.id.imageGrid20);
        levelImages[1][5] = findViewById(R.id.imageGrid21);
        levelImages[2][5] = findViewById(R.id.imageGrid22);
        levelImages[3][5] = findViewById(R.id.imageGrid23);

    }

    public void startGame(View view){
        this.addGame();
        for (int width = 0; width < theGame.getLevelWidth(); width ++){
            for (int height = 0; height < theGame.getLevelHeight(); height ++){
                updateImageLayout(width, height);
            }
        }

    }

    public void addGame(){
        String levelName;
        theGame = new Game();
        levelName = this.level1();
        TextView currentLevelText = findViewById(R.id.textViewCurrentLevel);
        currentLevelText.setText(levelName);
    }

    private String level1(){
        String levelName = "Level 1";
//         Add level builder stuff here
        theGame.addLevel(6, 4);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.STAR), 0, 1);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.DIAMOND), 1, 1);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.FLOWER), 2, 1);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.DIAMOND), 3, 1);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.FLOWER), 0, 2);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.FLOWER), 1, 2);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.STAR), 2, 2);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.FLOWER), 3, 2);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.FLOWER), 0, 3);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.STAR), 1, 3);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.STAR), 2, 3);
        theGame.addSquare(new PlayableSquare(Color.YELLOW, Shape.DIAMOND), 3, 3);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.CROSS), 0, 4);
        theGame.addSquare(new PlayableSquare(Color.YELLOW, Shape.FLOWER), 1, 4);
        theGame.addSquare(new PlayableSquare(Color.YELLOW, Shape.DIAMOND), 2, 4);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.CROSS), 3, 4);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.DIAMOND), 1, 0);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.FLOWER), 2, 5);
        theGame.addEyeball(1, 0, Direction.UP);
        theGame.addGoal(2, 5);
        return levelName;
    }

    private Bitmap resizeImage(Bitmap image){
        int desiredWidth = (int) getResources().getDisplayMetrics().density * 75;
        int desiredHeight = (int) getResources().getDisplayMetrics().density * 75;
        return Bitmap.createScaledBitmap(image, desiredWidth, desiredHeight, true);
    }

    // Image Handler
    public void updateImageLayout(int width, int height){
        Color squareColour = theGame.getColorAt(width,height);
        Shape squareShape = theGame.getShapeAt(width, height);
        ImageView square = levelImages[height][width];
        Bitmap image;
        Bitmap resizedImage;
        // Checks Square colour, Checks Square shape, applies sprite to board
        if (squareColour == Color.BLUE){
            switch (squareShape){
                case STAR:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.blue_star);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
                case FLOWER:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.blue_flower);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
                case CROSS:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.blue_cross);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
                case DIAMOND:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.blue_diamond);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
            }
        }
        if (squareColour == Color.RED){
            switch (squareShape){
                case STAR:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.red_star);
                  resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
                case FLOWER:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.red_flower);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
                case CROSS:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.red_cross);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
                case DIAMOND:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.red_diamond);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
            }
        }
        if (squareColour == Color.YELLOW){
            switch (squareShape){
                case STAR:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_star);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
                case FLOWER:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_flower);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
                case CROSS:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_cross);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
                case DIAMOND:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_diamond);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
            }
        }
        if (squareColour == Color.GREEN){
            switch (squareShape){
                case STAR:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.green_star);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
                case FLOWER:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.green_flower);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
                case CROSS:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.green_cross);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
                case DIAMOND:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.green_diamond);
                    resizedImage = resizeImage(image);
                    square.setImageBitmap(resizedImage);
                    break;
            }
        }
    }
}
