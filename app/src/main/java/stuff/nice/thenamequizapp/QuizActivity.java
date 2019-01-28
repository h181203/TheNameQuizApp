package stuff.nice.thenamequizapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    Map<String, Uri> data = new HashMap<String, Uri>();
    ArrayList<Uri> randomList;
    int score;
    Uri currentGuy;
    int i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        data = (HashMap)intent.getSerializableExtra(MainActivity.EXTRA_DATA);
        score = 0;
        i = 0;
        if(!data.isEmpty()) {
            randomList = new ArrayList<Uri>(data.values());
            Collections.shuffle(randomList);
            currentGuy = randomList.get(i);
            System.out.println("asdf    " +currentGuy.getPath());
            ImageView photoView = findViewById(R.id.guyView);
            setNewImage(currentGuy);
        }
    }

    public void submitGuy(View view){
        EditText whoText = (EditText) findViewById(R.id.whoText);
        if(isRightGuy(whoText.getText().toString())){
            TextView scoreText = (TextView) findViewById(R.id.scoreView);
            score += 12;
            scoreText.setText(""+score);
        }
        if(i > data.size()) {
            i++;
            currentGuy = randomList.get(i);
            setNewImage(currentGuy);
        } else {
            setNewImage(null);
            TextView finalScoreText = (TextView) findViewById(R.id.finalScoreView);
            finalScoreText.setText("You got " + score + " points");
        }

    }

    private void setNewImage(Uri guy){
        ImageView photoView = findViewById(R.id.guyView);
        photoView.setImageURI(guy);
        photoView.getLayoutParams().height = 500;
        photoView.getLayoutParams().width = 500;
    }

    private boolean isRightGuy(String name){
        Uri thisGuy = data.get(name);
        if(currentGuy.equals(thisGuy)){
            return true;
        }
        return false;
    }


}
