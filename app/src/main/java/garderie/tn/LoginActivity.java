package garderie.tn;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    EditText code, cin;
    String codU, cinU;
    static  String user, idUser, idEleve ,  idParent;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //declaration
        code = (EditText) findViewById(R.id.idCode);
        cin = (EditText) findViewById(R.id.idCin);
        Button Scan = (Button) findViewById(R.id.btnScan);
        Button connexion = (Button) findViewById(R.id.btnSeConnecter);
        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    lancerScan(); 
            }
        });
        //action du bouton
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recuperation
                codU = code.getText().toString();
                cinU = cin.getText().toString();
                if(codU.equals("") ||cinU.equals("")){
                    Toast.makeText(LoginActivity.this, "SVP remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }
                code.setText(""); cin.setText("");
                verifParent();
            }
        });

    }

    private void lancerScan() {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            Toast.makeText(LoginActivity.this, "Erreur Scan", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                code.setText(contents);
                //Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                //toast.show();
            }
        }
    }

    private void verifParent() {
        class PostAsync extends AsyncTask<String, String, JSONObject> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = SplashScreen.config+"authentification.php";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(LoginActivity.this);
                pDialog.setMessage("LoginActivity...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected JSONObject doInBackground(String... args) {
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("CODE", codU));
                    params.add(new BasicNameValuePair("CIN", cinU));
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
                    pDialog.dismiss();
                }
                System.out.println("json_data AAAA    : " + json);
                if (json != null) {
                    try {
                        // recuperation  user, idUser, idEleve
                        user =json.getString("nomParent")+" "+json.getString("prenomParent");
                        idUser=json.getString("cinParent");
                        idEleve = json.getString("code");
                        Intent i = new Intent(getApplicationContext(), EspaceParent.class);
                        startActivity(i);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                   //  verif si enseignant
                    verifEnseignant();
                }
            }

        }

        PostAsync la = new PostAsync();
        la.execute();


    }

    private void verifEnseignant() {
        class PostAsync extends AsyncTask<String, String, JSONObject> {
            JSONParser jsonParser = new JSONParser();
            private  String url_php = SplashScreen.config+"verifEnseignant.php";


            @Override
            protected void onPreExecute() {
            }

            @Override
            protected JSONObject doInBackground(String... args) {
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("CODE", codU));
                    params.add(new BasicNameValuePair("CIN", cinU));
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
                System.out.println("json_data AAAA    : " + json);
                if (json != null) {
                    try {
                        // recuperation  user, idUser, idEleve
                        user =json.getString("nomEnseignant")+" "+json.getString("prenomEnseignant");
                        idUser=json.getString("cinEnseignant");
                        idEleve = json.getString("code");
                        idParent = json.getString("parentCin");
                        finish();
                        Intent i = new Intent(getApplicationContext(), EspaceEnseignant.class);
                        startActivity(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "SVP, verifier votre Code et CIN", Toast.LENGTH_SHORT).show();
                }
            }

        }

        PostAsync la = new PostAsync();
        la.execute();


    }

}
