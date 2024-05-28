package com.example.bcde223ass3;

// Import the model
import com.example.bcde223ass3.model.*;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

public class EyeballMazeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void createNewGame(int width, int height){
        Game game = new Game();
        game.addLevel(width, height);
        int levelHeight = game.getLevelHeight();
        int levelWidth = game.getLevelWidth();
        String message = "Level Created. Height: " + levelHeight + "Width: " + levelWidth;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
