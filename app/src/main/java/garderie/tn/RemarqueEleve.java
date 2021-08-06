package garderie.tn;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class RemarqueEleve extends AppCompatActivity {
    EditText sujet,contenu;
    String sujetR,contenuR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remarque_eleve);
        sujet = (EditText) findViewById(R.id.idSujet);
        contenu = (EditText) findViewById(R.id.idContenu);
       Button remarque= (Button)findViewById(R.id.btnValider);
        remarque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sujetR=sujet.getText().toString();
                contenuR=contenu.getText().toString();
                if(sujetR.equals("")||contenuR.equals("")) {
                    Toast.makeText(RemarqueEleve.this, "SVP remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                envoyerRemarque();

            }
        });

    }

    private void envoyerRemarque() {
        {
            class PostAsync extends AsyncTask<String, String, String> {
                JSONParser jsonParser = new JSONParser();
                private ProgressDialog pDialog;
                private  String url_php = SplashScreen.config+"remarque.php";


                @Override
                protected void onPreExecute() {
                    pDialog = new ProgressDialog(RemarqueEleve.this);
                    pDialog.setMessage("envoie en cours...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(true);
                    pDialog.show();
                }

                @Override
                protected String doInBackground(String... args) {
                    String resultt = null ;
                    try {
                        List<NameValuePair> params = new ArrayList<NameValuePair>();

                        params.add(new BasicNameValuePair("ENS",LoginActivity.idUser ));
                        params.add(new BasicNameValuePair("EE",LoginActivity.idEleve ));
                        params.add(new BasicNameValuePair("SS", sujetR));
                        params.add(new BasicNameValuePair("CC", contenuR));

                        resultt = jsonParser.makeHttpRequest( url_php, "POST", params);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return resultt;
                }

                protected void onPostExecute(String res) {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                    System.out.println("resultat   : " + res);
                    if (res.contains("OK")) {
                        Toast.makeText(RemarqueEleve.this, "Remarque effectuée avec succées", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(RemarqueEleve.this, "Erreur d'envoie", Toast.LENGTH_SHORT).show();
                    }


                }

            }

            PostAsync la = new PostAsync();
            la.execute();


        }
    }
}
