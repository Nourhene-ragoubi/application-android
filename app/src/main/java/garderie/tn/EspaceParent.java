package garderie.tn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.HashMap;

public class EspaceParent extends AppCompatActivity {
     static String montantAPayer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_parent);
        Button profil= (Button)findViewById(R.id.btnProfil);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EspaceParent.this, ProfilEleve.class);
                startActivity(i);
            }
        });


        Button quitter=(Button)findViewById(R.id.btnQuitter);
        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(EspaceParent.this);
                alert.setTitle("Quitter");
                alert.setIcon(R.drawable.sortie);
                alert.setMessage("Voulez vous vraiment quitter");
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent j = new Intent(EspaceParent.this, Accueil.class);
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
        Button remarque=(Button)findViewById(R.id.btnRemarque);
        remarque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(EspaceParent.this, MesRemarque.class);
                startActivity(j);
            }
        });

        Button contactE=(Button)findViewById(R.id.btnContactEnseignant);
        contactE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tab[]  = {"Contacter le Propriétaire","Contacter un enseignant"};
                AlertDialog.Builder alert = new AlertDialog.Builder(EspaceParent.this);
                alert.setItems(tab, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indice) {
                        switch (indice) {
                            case 0: startActivity(new Intent(EspaceParent.this, ContactAdmin.class)); break;
                            case 1: startActivity(new Intent(EspaceParent.this, ContactEnseignant.class));break;
                        }
                    }
                });
                alert.show() ;

            }
        });

        /*Button adhesion=(Button)findViewById(R.id.btnAdhesion);
        adhesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(EspaceParent.this, MesAdhesion.class);
                startActivity(j);
            }
        });*/
          Button club=(Button)findViewById(R.id.btnClub);
        club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tab[]  = {"Participer à un club","Mes Clubs"};
                AlertDialog.Builder alert = new AlertDialog.Builder(EspaceParent.this);
                alert.setItems(tab, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indice) {
                        switch (indice) {
                            case 0: startActivity(new Intent(EspaceParent.this, ListClub.class)); break;
                            case 1: startActivity(new Intent(EspaceParent.this, MesAdhesion.class));break;
                        }
                    }
                });
                alert.show() ;

            }
        });

        Button reglement=(Button)findViewById(R.id.btnReglement);
        reglement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tab[] = {"Paiement", "Mes réglements"};
                AlertDialog.Builder alert = new AlertDialog.Builder(EspaceParent.this);
                alert.setItems(tab, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indice) {
                        switch (indice) {
                            case 0:
                                startActivity(new Intent(EspaceParent.this, PiementInscrit.class));
                                break;
                            case 1:
                                startActivity(new Intent(EspaceParent.this, Reglement.class));
                                break;
                        }
                    }
                });
                alert.show();

            }
        });
        Button event=(Button)findViewById(R.id.btnmesEvent);
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tab[] = {"Participer à un evenement", "Mes evenements"};
                AlertDialog.Builder alert = new AlertDialog.Builder(EspaceParent.this);
                alert.setItems(tab, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indice) {
                        switch (indice) {
                            case 0:
                                startActivity(new Intent(EspaceParent.this, Evenement.class));
                                break;
                            case 1:
                                startActivity(new Intent(EspaceParent.this, MesEvenement.class));
                                break;
                        }
                    }
                });
                alert.show();

            }
        });
        Button msg=(Button)findViewById(R.id.btnMsg);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(EspaceParent.this, MesMessagesP.class);
                startActivity(j);
            }
        });






    }
}
