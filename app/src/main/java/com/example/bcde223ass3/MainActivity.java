package com.example.bcde223ass3;

// Import the model
import static android.widget.Toast.LENGTH_SHORT;

import com.example.bcde223ass3.model.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.graphics.Bitmap;
import android.widget.Toast;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Game theGame;

    int moveCount = 0;

    ImageView[][] levelImages = new ImageView[6][4];
    HashMap<ImageView, Square> squareMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        levelImages[0][0] = findViewById(R.id.imageGrid0);
        levelImages[0][1] = findViewById(R.id.imageGrid1);
        levelImages[0][2] = findViewById(R.id.imageGrid2);
        levelImages[0][3] = findViewById(R.id.imageGrid3);
        levelImages[1][0] = findViewById(R.id.imageGrid4);
        levelImages[1][1] = findViewById(R.id.imageGrid5);
        levelImages[1][2] = findViewById(R.id.imageGrid6);
        levelImages[1][3] = findViewById(R.id.imageGrid7);
        levelImages[2][0] = findViewById(R.id.imageGrid8);
        levelImages[2][1] = findViewById(R.id.imageGrid9);
        levelImages[2][2] = findViewById(R.id.imageGrid10);
        levelImages[2][3] = findViewById(R.id.imageGrid11);
        levelImages[3][0] = findViewById(R.id.imageGrid12);
        levelImages[3][1] = findViewById(R.id.imageGrid13);
        levelImages[3][2] = findViewById(R.id.imageGrid14);
        levelImages[3][3] = findViewById(R.id.imageGrid15);
        levelImages[4][0] = findViewById(R.id.imageGrid16);
        levelImages[4][1] = findViewById(R.id.imageGrid17);
        levelImages[4][2] = findViewById(R.id.imageGrid18);
        levelImages[4][3] = findViewById(R.id.imageGrid19);
        levelImages[5][0] = findViewById(R.id.imageGrid20);
        levelImages[5][1] = findViewById(R.id.imageGrid21);
        levelImages[5][2] = findViewById(R.id.imageGrid22);
        levelImages[5][3] = findViewById(R.id.imageGrid23);

        // Listeners for click on image
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                // Create a BlankSquare by default
                Square square = new BlankSquare();
                squareMap.put(levelImages[i][j], square);

                // Set click listener for the ImageView
                levelImages[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        squareClick(v);
                    }
                });
            }
        }


    }

    public void startGame(View view){
        this.addGame();
        for (int column = 0; column < theGame.getLevelWidth(); column ++){
            for (int row = 0; row < theGame.getLevelHeight(); row ++){
                updateImageLayout(row, column);
            }
        }
        this.addEyeballDirection();

    }

    public void addGame(){
        String levelName;
        theGame = new Game();
        levelName = this.level1();
        TextView currentLevelText = findViewById(R.id.textViewCurrentLevel);
        currentLevelText.setText(levelName);
        TextView goalsRemainingText = findViewById(R.id.textViewGoalsLeft);
        goalsRemainingText.setText(String.valueOf(theGame.getGoalCount()));
        TextView moveCounter = findViewById(R.id.textViewMoveCounter);
        moveCounter.setText(String.valueOf(moveCount));
    }

    private String level1(){
        String levelName = "Level 1";
        theGame.addLevel(6, 4);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.STAR), 1, 0);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.DIAMOND), 1, 1);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.FLOWER), 1, 2);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.DIAMOND), 1, 3);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.FLOWER), 2, 0);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.FLOWER), 2, 1);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.STAR), 2, 2);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.FLOWER), 2, 3);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.FLOWER), 3, 0);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.STAR), 3, 1);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.STAR), 3, 2);
        theGame.addSquare(new PlayableSquare(Color.YELLOW, Shape.DIAMOND), 3, 3);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.CROSS), 4, 0);
        theGame.addSquare(new PlayableSquare(Color.YELLOW, Shape.FLOWER), 4, 1);
        theGame.addSquare(new PlayableSquare(Color.YELLOW, Shape.DIAMOND), 4, 2);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.CROSS), 4, 3);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.DIAMOND), 0, 1);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.FLOWER), 5, 2);
        theGame.addEyeball(0, 1, Direction.UP);
        this.setTileColor(0,1, "#00FF00");

        theGame.addGoal(5, 2);
        this.setTileColor(5,2, "#e8b923");

        return levelName;
    }

    private Bitmap resizeImage(Bitmap image){
        int desiredWidth = (int) getResources().getDisplayMetrics().density * 75;
        int desiredHeight = (int) getResources().getDisplayMetrics().density * 75;
        return Bitmap.createScaledBitmap(image, desiredWidth, desiredHeight, true);
    }

    // Image Handler
    public void updateImageLayout(int row, int column){
        Color squareColour = theGame.getColorAt(row,column);
        Shape squareShape = theGame.getShapeAt(row, column);
        ImageView square = levelImages[row][column];
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

    private void addEyeballDirection(){
        // Get eyeball properties
        int eyeballRow = theGame.getEyeballRow();
        int eyeballCol = theGame.getEyeballColumn();
        Direction eyeballDirection = theGame.getEyeballDirection();

        // Square for Eyeball
        ImageView square = levelImages[eyeballRow][eyeballCol];

        // Image Controllers
        Bitmap eyeballImage;
        Bitmap resizedImage;
        Bitmap shapeImage = ((BitmapDrawable) square.getDrawable()).getBitmap();

        // Switch Case for Loading Image
        switch (eyeballDirection) {
            case UP:
                eyeballImage = BitmapFactory.decodeResource(getResources(), R.drawable.eye_up);
                resizedImage = resizeImage(eyeballImage);
                break;
            case DOWN:
                eyeballImage = BitmapFactory.decodeResource(getResources(), R.drawable.eye_down);
                resizedImage = resizeImage(eyeballImage);
                break;
            case LEFT:
                eyeballImage = BitmapFactory.decodeResource(getResources(), R.drawable.eye_left);
                resizedImage = resizeImage(eyeballImage);
                break;
            case RIGHT:
                eyeballImage = BitmapFactory.decodeResource(getResources(), R.drawable.eye_right);
                resizedImage = resizeImage(eyeballImage);
                break;
            default:
                eyeballImage = BitmapFactory.decodeResource(getResources(), R.drawable.unknown);
                resizedImage = resizeImage(eyeballImage);
                break;
        }
        Bitmap mergedImage = combineTwoImagesAsOne(shapeImage, resizedImage);
        square.setImageBitmap(mergedImage);

    }

    public void handleClick(int x, int y) {
        boolean moveMessage = theGame.canMoveTo(y, x);
        String illegalMoveMessage = "";
        if (moveMessage) {
            // Move the eyeball
            theGame.moveTo(y, x);
            // Add to counter
            moveCount ++;
            TextView moveCounter = findViewById(R.id.textViewMoveCounter);
            moveCounter.setText(String.valueOf(moveCount));


            System.out.println(theGame.hasGoalAt(y,x));
            // Check if the new square is a goal
            if (theGame.getGoalCount() == 0) {
                // Update goals remaining
                int goalsRemaining = 0;
                System.out.println(goalsRemaining);
                System.out.println(theGame.getGoalCount());
                System.out.println(theGame.getCompletedGoalCount());

                TextView goalsRemainingText = findViewById(R.id.textViewGoalsLeft);
                goalsRemainingText.setText(String.valueOf(goalsRemaining));

                // Set tile color to blank or some indication of goal completion
                setTileColor(y, x, "#FFFFFF");
            }


            // Update the entire board
            for (int column = 0; column < theGame.getLevelWidth(); column++) {
                for (int row = 0; row < theGame.getLevelHeight(); row++) {
                    updateImageLayout(row, column);
                }
            }

            // Add eyeball direction after updating the board
            addEyeballDirection();

            // Check for game win condition
            if (theGame.getGoalCount() == 0) {
                showAlertDialog("You Won!", "You beat the maze in "+moveCount+" moves, well done!", "Ok");
            }
        } else {
            Message checkMessage = theGame.checkDirectionMessage(y, x);
            if (checkMessage != Message.OK) {
                if (checkMessage == Message.MOVING_DIAGONALLY) {
                    illegalMoveMessage = "Move Not Allowed. Cannot move diagonally.";
                } else if (checkMessage == Message.BACKWARDS_MOVE) {
                    illegalMoveMessage = "Move Not Allowed. Cannot move backwards.";
                }
            } else {
                Color currColor = theGame.getColorAt(theGame.getEyeballRow(), theGame.getEyeballColumn());
                Shape currShape = theGame.getShapeAt(theGame.getEyeballRow(), theGame.getEyeballColumn());
                illegalMoveMessage = "Move Not Allowed. Needs to be a " + currColor + " shape or a " + currShape + " shape.";
            }
            Toast.makeText(this, illegalMoveMessage, Toast.LENGTH_LONG).show();
        }
    }



    public void squareClick(View view) {
        int[] coords = getCoordinates(view);
        if (coords[0] != -1 && coords[1] != -1) {
            handleClick(coords[0], coords[1]);
        }
    }

    private int[] getCoordinates(View view) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (levelImages[i][j] == view) {
                    return new int[]{j, i}; // Return x, y coordinates
                }
            }
        }
        return new int[]{-1, -1}; // Invalid coordinates
    }

    private Bitmap combineTwoImagesAsOne(Bitmap imageOne, Bitmap imageTwo){
        Bitmap resultImage = Bitmap.createBitmap(imageTwo.getWidth(),
                imageTwo.getHeight(),
                imageTwo.getConfig());
        Canvas canvas = new Canvas(resultImage);
        canvas.drawBitmap(imageOne, 0f,0f, null);
        canvas.drawBitmap(imageTwo, 0, 0, null);
        return resultImage;
    }

    private void setTileColor (int x, int y, String chosenColor){
        ImageView imageView = levelImages[x][y];
        int color = android.graphics.Color.parseColor(chosenColor);
        imageView.setBackgroundColor(color);
    }

    private void showAlertDialog(String title ,String message, String buttonText){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(buttonText, (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
