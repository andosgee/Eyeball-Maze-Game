package com.example.bcde223ass3;

import static android.widget.Toast.LENGTH_SHORT;

import com.example.bcde223ass3.model.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private int prevRow = -1;
    private int prevCol = -1;
    private HashMap<Integer, Bitmap> imageCache = new HashMap<>();

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
        Button resetButton = findViewById(R.id.buttonReset);
        Button startButton = findViewById(R.id.buttonStart);
        resetButton.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.INVISIBLE);
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

    private Bitmap resizeImage(int resourceId) {
        if (imageCache.containsKey(resourceId)) {
            return imageCache.get(resourceId);
        }

        Bitmap image = BitmapFactory.decodeResource(getResources(), resourceId);
        int desiredWidth = (int) getResources().getDisplayMetrics().density * 75;
        int desiredHeight = (int) getResources().getDisplayMetrics().density * 75;
        Bitmap resizedImage = Bitmap.createScaledBitmap(image, desiredWidth, desiredHeight, true);

        imageCache.put(resourceId, resizedImage);
        return resizedImage;
    }

    // Image Handler
    public void updateImageLayout(int row, int column) {
        Color squareColour = theGame.getColorAt(row, column);
        Shape squareShape = theGame.getShapeAt(row, column);
        ImageView square = levelImages[row][column];
        int resourceId = -1;

        // Checks Square colour, Checks Square shape, applies sprite to board
        if (squareColour == Color.BLUE) {
            switch (squareShape) {
                case STAR:
                    resourceId = R.drawable.blue_star;
                    break;
                case FLOWER:
                    resourceId = R.drawable.blue_flower;
                    break;
                case CROSS:
                    resourceId = R.drawable.blue_cross;
                    break;
                case DIAMOND:
                    resourceId = R.drawable.blue_diamond;
                    break;
            }
        } else if (squareColour == Color.RED) {
            switch (squareShape) {
                case STAR:
                    resourceId = R.drawable.red_star;
                    break;
                case FLOWER:
                    resourceId = R.drawable.red_flower;
                    break;
                case CROSS:
                    resourceId = R.drawable.red_cross;
                    break;
                case DIAMOND:
                    resourceId = R.drawable.red_diamond;
                    break;
            }
        } else if (squareColour == Color.YELLOW) {
            switch (squareShape) {
                case STAR:
                    resourceId = R.drawable.yellow_star;
                    break;
                case FLOWER:
                    resourceId = R.drawable.yellow_flower;
                    break;
                case CROSS:
                    resourceId = R.drawable.yellow_cross;
                    break;
                case DIAMOND:
                    resourceId = R.drawable.yellow_diamond;
                    break;
            }
        } else if (squareColour == Color.GREEN) {
            switch (squareShape) {
                case STAR:
                    resourceId = R.drawable.green_star;
                    break;
                case FLOWER:
                    resourceId = R.drawable.green_flower;
                    break;
                case CROSS:
                    resourceId = R.drawable.green_cross;
                    break;
                case DIAMOND:
                    resourceId = R.drawable.green_diamond;
                    break;
            }
        }

        if (resourceId != -1) {
            Bitmap resizedImage = resizeImage(resourceId);
            square.setImageBitmap(resizedImage);
        } else {
            square.setImageDrawable(null); // Clear the image if no valid resourceId
        }
    }

    private void addEyeballDirection() {
        // Clear previous eyeball image if it exists
        if (prevRow != -1 && prevCol != -1) {
            updateImageLayout(prevRow, prevCol);
        }

        // Get new eyeball properties
        int eyeballRow = theGame.getEyeballRow();
        int eyeballCol = theGame.getEyeballColumn();
        Direction eyeballDirection = theGame.getEyeballDirection();

        // Update previous position variables
        prevRow = eyeballRow;
        prevCol = eyeballCol;

        // Square for Eyeball
        ImageView square = levelImages[eyeballRow][eyeballCol];

        // Ensure the square has a drawable
        if (square.getDrawable() == null) {
            // Initialize with a blank square if necessary
            square.setImageResource(R.drawable.blank_square);
        }

        // Image Controllers
        Bitmap eyeballImage;
        Bitmap shapeImage = ((BitmapDrawable) square.getDrawable()).getBitmap();

        // Switch Case for Loading Image
        int resourceId = -1;
        switch (eyeballDirection) {
            case UP:
                resourceId = R.drawable.eye_up;
                break;
            case DOWN:
                resourceId = R.drawable.eye_down;
                break;
            case LEFT:
                resourceId = R.drawable.eye_left;
                break;
            case RIGHT:
                resourceId = R.drawable.eye_right;
                break;
            default:
                resourceId = R.drawable.unknown;
                break;
        }

        if (resourceId != -1) {
            eyeballImage = resizeImage(resourceId);
            Bitmap mergedImage = combineTwoImagesAsOne(shapeImage, eyeballImage);
            square.setImageBitmap(mergedImage);
        }
    }

    public void handleClick(int x, int y) {
        boolean moveMessage = theGame.canMoveTo(y, x);
        String illegalMoveMessage = "";

        if (moveMessage) {
            removeEyeballView();
            theGame.moveTo(y, x);
            moveCount++;

            TextView moveCounter = findViewById(R.id.textViewMoveCounter);
            moveCounter.setText(String.valueOf(moveCount));

            if (theGame.getGoalCount() == 0) {
                int goalsRemaining = 0;
                TextView goalsRemainingText = findViewById(R.id.textViewGoalsLeft);
                goalsRemainingText.setText(String.valueOf(goalsRemaining));
                setTileColor(y, x, "#FFFFFF");
            }

            updateImageLayout(theGame.getEyeballRow(), theGame.getEyeballColumn());
            addEyeballDirection();

            if (theGame.getGoalCount() == 0) {
                showAlertDialog("You Won!", "You beat the maze in " + moveCount + " moves, well done!", "Ok");
                Button resetButton = findViewById(R.id.buttonReset);
                Button startButton = findViewById(R.id.buttonStart);
                resetButton.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.VISIBLE);
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

    private void removeEyeballView(){
        int eyeRow = theGame.getEyeballRow();
        int eyeCol = theGame.getEyeballColumn();
        this.updateImageLayout(eyeRow,eyeCol);
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
