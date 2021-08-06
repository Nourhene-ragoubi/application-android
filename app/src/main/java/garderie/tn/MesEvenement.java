package garderie.tn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MesEvenement extends AppCompatActivity {
    static  String montantEvent ;
    ListView listME ;
    List<HashMap<String, Object>> meseventsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_evenement);
        listME = (ListView)findViewById(R.id.listView);
        meseventsList= new ArrayList<>();
        getAllAdhesions();
        /*action lors de clique sur un evenement
        listME.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, Object> map = (HashMap<String, Object>) listME.getItemAtPosition(position);
                EspaceParent.montantAPayer= map.get("mm").toString();
                startActivity(new Intent(MesEvenement.this, Paiement.class));
            }
        });*/
    }

    private void getAllAdhesions() {
        class PostAsync extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private String URL_PHP = SplashScreen.config+"allParticipationEvent.php";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(MesEvenement.this);
                pDialog.setMessage("Chargement ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... arg0) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("code", LoginActivity.idEleve));
                String result = jsonParser.makeHttpRequest(URL_PHP, "POST", params);
                System.out.println("Response from url: " + result);
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        HashMap<String, Object> mapp = new HashMap<>();
                        mapp.put("tt", json_data.getString("titreEvenement"));
                        mapp.put("mm", json_data.getString("montantParticipation")+" DT");
                        //mapp.put("ee", json_data.getString("etatDemande"));
                        mapp.put("dd", json_data.getString("dateEvenement"));
                        meseventsList.add(mapp);
                    }
                } catch (final JSONException e) {
                    Log.e("error", "Json parsing error: " + e.getMessage());
                }

                return null;
            }

            protected void onPostExecute(String result) {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                SimpleAdapter adapter1 = new SimpleAdapter(MesEvenement.this, meseventsList,
                        R.layout.item_mesevent, new String[]{"tt","mm", "dd"},
                        new int[]{R.id.ttire, R.id.tprix, R.id.tdate});
                listME.setAdapter(adapter1);


            }


        }

        PostAsync p = new PostAsync();
        p.execute();

    }
}
