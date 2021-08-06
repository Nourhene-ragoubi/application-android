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

public class ContactAdmin extends AppCompatActivity {
    EditText sujet,contenu;
    String sujetE,contenuE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_admin);
        sujet = (EditText) findViewById(R.id.idSujet);
        contenu = (EditText) findViewById(R.id.idContenuMessage);

        Button envoie= (Button)findViewById(R.id.btnEnvoyer);
        envoie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sujetE = sujet.getText().toString();
                contenuE = contenu.getText().toString();
                if (sujetE.equals("") || contenuE.equals("")) {
                    Toast.makeText(ContactAdmin.this, "SVP remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }
                envoyerMessage();

            }
        });

    }

    private void envoyerMessage() {
        {
            class PostAsync extends AsyncTask<String, String, String> {
                JSONParser jsonParser = new JSONParser();
                private ProgressDialog pDialog;
                private  String url_php = SplashScreen.config+"Contact.php";


                @Override
                protected void onPreExecute() {
                    pDialog = new ProgressDialog(ContactAdmin.this);
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


                        params.add(new BasicNameValuePair("PP",LoginActivity.idUser ));
                        params.add(new BasicNameValuePair("SS", sujetE));
                        params.add(new BasicNameValuePair("CC", contenuE));

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
                        Toast.makeText(ContactAdmin.this, "Message envoyé avec succées", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(ContactAdmin.this, "Erreur d'envoie", Toast.LENGTH_SHORT).show();
                    }


                }

            }

            PostAsync la = new PostAsync();
            la.execute();


        }
    }
}
