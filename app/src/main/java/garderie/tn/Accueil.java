package garderie.tn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Button apropos=(Button)findViewById(R.id.btnApropos);
        apropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Accueil.this, AProposGarderie.class);
                startActivity(i);
            }
        });
        Button club= (Button)findViewById(R.id.btnClub);
        club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Accueil.this, Club.class);
                startActivity(i);
            }
        });
        Button evenement=(Button)findViewById(R.id.btnEvenement);
        evenement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Accueil.this, ListEvenement.class);
                startActivity(j);
            }
        });
        Button cnx=(Button)findViewById(R.id.btnConnexion);
        cnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if(LoginActivity.idUser!=null) {
                    Intent k = new Intent(Accueil.this, LoginActivity.class);
                    startActivity(k);
              //  }else {
                   // Intent k = new Intent(Accueil.this, EspaceParent.class);
                   // startActivity(k);
               // }
            }
        });



    }
}
