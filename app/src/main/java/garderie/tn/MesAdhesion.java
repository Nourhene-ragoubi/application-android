package garderie.tn;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class MesAdhesion extends AppCompatActivity {
    static  String montantAdhesion ;
    ListView listA ;
    List<HashMap<String, Object>> adhesionsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_adhesion);
        listA = (ListView)findViewById(R.id.listAdhesion);
        adhesionsList = new ArrayList<>();
        getAllAdhesions();
        /* action lors de cliqur sur  une Adhesion
        listA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, Object> map = (HashMap<String, Object>) listA.getItemAtPosition(position);
                EspaceParent.montantAPayer = map.get("MM").toString();
                startActivity(new Intent(MesAdhesion.this, Paiement.class)) ;
            }
        });*/
    }

    private void getAllAdhesions() {
        class PostAsync extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private String URL_PHP = SplashScreen.config+"allAdhesion.php";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(MesAdhesion.this);
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
                        mapp.put("nn", json_data.getString("nomClub"));
                        mapp.put("MM", json_data.getString("montantAdhesion")+" DT");
                        mapp.put("dd", json_data.getString("datePaimentAdhesion"));
                        adhesionsList.add(mapp);
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
                SimpleAdapter adapter1 = new SimpleAdapter(MesAdhesion.this, adhesionsList,
                        R.layout.item_adhesion, new String[]{"nn", "dd","MM"},
                        new int[]{R.id.tnomClub, R.id.tdateP,R.id.tmontant});
                listA.setAdapter(adapter1);


            }


        }

        PostAsync p = new PostAsync();
        p.execute();

    }
}
