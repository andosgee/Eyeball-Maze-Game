package com.example.bcde223ass3;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
// Import the model
import com.example.bcde223ass3.model.*;

public class EyeballMazeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Game game = new Game();

    }
}
