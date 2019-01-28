package stuff.nice.thenamequizapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_DATA = "DATA";


    Map<String, Uri> data = new HashMap<String, Uri>();
    String name;
    String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri slowGuy = Uri.parse("android.resource://stuff.nice.thenamequizapp/drawable/pardyhard");
        data.put("david", slowGuy);
        System.out.println("asdf       "+slowGuy);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(name != null && uri != null) {
            data.put(name, Uri.parse(uri));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case 11:
                if (requestCode == 11 && resultCode == RESULT_OK && intent != null) {
                    name = intent.getStringExtra(PhotoActivity.EXTRA_GUY);
                    uri = intent.getStringExtra(PhotoActivity.EXTRA_URI);
                }
            case 22:
                data = (HashMap)intent.getSerializableExtra(DeleteActivity.EXTRA_DELETE);
        }

    }

    public void goToQuiz(View view){
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra(EXTRA_DATA, (Serializable)data);
        startActivity(intent);
    }

    public void goToPhotos (View view){
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivityForResult(intent, 11);
    }

    public void deletePeople(View view) {
        Intent intent = new Intent(this, DeleteActivity.class);
        intent.putExtra(EXTRA_DATA, (Serializable)data);
        startActivityForResult(intent, 22);
    }

}
