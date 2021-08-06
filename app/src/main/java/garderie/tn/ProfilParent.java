package garderie.tn;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfilParent extends AppCompatActivity {

    EditText cin,nomprenomp,nomprenomm,email,adresse,telephonep,telephonem,fonctionp,fonctionm;
    String cinP,nomPrenomP,emailP,adresseP,telephoneP,telephoneM,nomPrenomM,fonctionP,fonctionM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_parent);
        cin = (EditText) findViewById(R.id.idCin);
        nomprenomp = (EditText) findViewById(R.id.idNomPrenomP);
       nomprenomm = (EditText) findViewById(R.id.idNomPrenomM);
        email = (EditText) findViewById(R.id.idEmail);
        adresse = (EditText) findViewById(R.id.idAdresse);
        telephonep = (EditText) findViewById(R.id.idTelephoneP);
        telephonem = (EditText) findViewById(R.id.idTelephoneM);
        fonctionm= (EditText) findViewById(R.id.idFonctionM);
        fonctionp= (EditText) findViewById(R.id.idFonctionP);
        Button modif=(Button)findViewById(R.id.btnEnregistrer);
        getInfoUser();
        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cinP=cin.getText().toString();
                nomPrenomP=nomprenomp.getText().toString();
                nomPrenomM=nomprenomm.getText().toString();
                emailP=email.getText().toString();
                adresseP=adresse.getText().toString();
                telephoneP=telephonep.getText().toString();
                telephoneM=telephonem.getText().toString();
                fonctionM=fonctionm.getText().toString();
                fonctionP=fonctionp.getText().toString();

                if(fonctionP.equals("")|| fonctionM.equals("")|| telephoneM.equals("")|| emailP.equals("")|| adresseP.equals("")|| telephoneP.equals(""))  {
                    Toast.makeText(ProfilParent.this, "SVP remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }
                //verif  mail
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailP).matches()) {
                    email.setError("E-mail invalide");
                    return ;
                }
                if(telephonem.length()<8){
                    telephonem.setError("N° Telephone invalide");
                    return;
                }
                editProfilUser();
                 }});
        }
    private void getInfoUser() {
        class PostAsync extends AsyncTask<String, String, JSONObject> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = SplashScreen.config+"ProfilP.php";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(ProfilParent.this);
                pDialog.setMessage("Chargement...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected JSONObject doInBackground(String... args) {
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("CIN", LoginActivity.idUser));
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
                        //nom.setText(json.getString("nom")+" "+json.getString("prenom"));
                        cin.setText(json.getString("cinParent"));
                        nomprenomp.setText(json.getString("nomParent")+""+json.getString("prenomParent"));
                        nomprenomm.setText(json.getString("nomMere")+""+json.getString("prenomMere"));

                        email.setText(json.getString("emailParent"));

                        telephonep.setText(json.getString("telephoneParent"));
                        telephonem.setText(json.getString("telephoneMere"));
                        adresse.setText(json.getString("adresseParent"));
                        fonctionm.setText(json.getString("fonctionMere"));
                        fonctionp.setText(json.getString("fonctionPere"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

        }

        PostAsync la = new PostAsync();
        la.execute();


    }


    private void editProfilUser() {
        class PostAsync extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = SplashScreen.config+"EditProfilP.php";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(ProfilParent.this);
                pDialog.setMessage("Modification en cours...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... args) {
                String resultt = null ;
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();


                    params.add(new BasicNameValuePair("MAIL", emailP));
                    params.add(new BasicNameValuePair("ADR", adresseP));
                    params.add(new BasicNameValuePair("TELP", telephoneP));
                    params.add(new BasicNameValuePair("TELM", telephoneM));
                    params.add(new BasicNameValuePair("FP", fonctionP));
                    params.add(new BasicNameValuePair("FM", fonctionM));
                    params.add(new BasicNameValuePair("CIN", LoginActivity.idUser));
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
                    Toast.makeText(ProfilParent.this, "Modification effectuée avec succées", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(ProfilParent.this, "Erreur de Modification", Toast.LENGTH_SHORT).show();
                }


            }

        }

        PostAsync la = new PostAsync();
        la.execute();


    }
    }
