package garderie.tn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
   // static String config = "http://10.0.3.2/projects/android_db/";
   static String config = "http://192.168.43.38/projects/android_db/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView menuV = (ImageView) findViewById(R.id.imageView);
        menuV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(SplashScreen.this, Accueil.class);
                startActivity(k);
            }
        });

    }
}
