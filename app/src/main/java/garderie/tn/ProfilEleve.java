package garderie.tn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfilEleve extends AppCompatActivity {
    TextView code,nom,prenom,dateNaissance,classe,etat,matiere,ecole;
    String codeE,nomE,prenomE,dateNaissanceE,classeE,etatE,matiereE,ecoLeE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_eleve);
        code = (TextView) findViewById(R.id.tvcode);
        nom = (TextView) findViewById(R.id.tvnom);
        prenom = (TextView) findViewById(R.id.tvprenom);
        dateNaissance = (TextView) findViewById(R.id.tvDate);
        classe = (TextView) findViewById(R.id.tvClasse);
        etat = (TextView) findViewById(R.id.tvetatSante);
        matiere = (TextView) findViewById(R.id.tvmatieres);
        ecole = (TextView) findViewById(R.id.tvecole);
        getInfoUser();
        Button modif=(Button)findViewById(R.id.btnModifier);
        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(ProfilEleve.this, ProfilParent.class);
                startActivity(j);
            }
        });}
    private void getInfoUser() {
        class PostAsync extends AsyncTask<String, String, JSONObject> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = SplashScreen.config+"Profil.php";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(ProfilEleve.this);
                pDialog.setMessage("Chargement...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected JSONObject doInBackground(String... args) {
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("CODE", LoginActivity.idEleve));
                    String resultt = jsonParser.makeHttpRequest( url_php, "POST", params);
                    JSONArray jArray = new JSONArray(resultt);
                    JSONObject json_data = jArray.getJSONObject(0);
                    if (json_data != null) {
                        Log.d("JSON result", jArray.toString());
                        System.out.println("json_data     : " + json_data.toString());
                        return json_data;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(JSONObject json) {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.cancel();
                }
                System.out.println("json_data AAAA    : " + json);
                if (json != null) {
                    try {
                        code.setText(json.getString("code"));
                        //nom.setText(json.getString("nom")+" "+json.getString("prenom"));
                        nom.setText(json.getString("nom"));
                        prenom.setText(json.getString("prenom"));
                        dateNaissance.setText(json.getString("dateNaissance"));
                        classe.setText(json.getString("niveau")+"ème année");
                        etat.setText(json.getString("etatSante"));
                        matiere.setText(json.getString("matieres"));
                        ecole.setText(json.getString("ecole"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

        }

        PostAsync la = new PostAsync();
        la.execute();


    }


}




