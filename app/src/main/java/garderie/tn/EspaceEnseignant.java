package garderie.tn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class EspaceEnseignant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_enseignant);
        Button comportement=(Button)findViewById(R.id.btnComportement);
        comportement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(EspaceEnseignant.this, RemarqueEleve.class);
                startActivity(j);
            }
        });

        Button quitter=(Button)findViewById(R.id.btnQuitter);
        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(EspaceEnseignant.this);
                alert.setTitle("Quitter");
                alert.setIcon(R.drawable.sortie);
                alert.setMessage("Voulez vous vraiment quitter");
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent j = new Intent(EspaceEnseignant.this, Accueil.class);
                        startActivity(j);
                    }
                });
                alert.setNegativeButton("Fermer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();

            }
        });
        Button contactP=(Button)findViewById(R.id.btnContactParent);
        contactP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(EspaceEnseignant.this, ContactParent.class);
                startActivity(j);
            }
        });
        Button msg=(Button)findViewById(R.id.btnMsg);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(EspaceEnseignant.this, MesMessagesEns.class);
                startActivity(j);
            }
        });
    }
}
