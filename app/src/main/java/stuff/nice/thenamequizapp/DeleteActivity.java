package stuff.nice.thenamequizapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class DeleteActivity extends AppCompatActivity {

    public static final String EXTRA_DELETE = "DELETE";
    Map<String, Uri> data = new HashMap<String, Uri>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
    }

    @Override
    protected void onResume() {
        super.onResume();
        data = (HashMap)getIntent().getSerializableExtra(MainActivity.EXTRA_DATA);
    }

    public void deleteThisGuy(View view){
        Intent intent = new Intent(this, MainActivity.class);

        setResult(RESULT_OK, intent);
        finish();
    }



}
