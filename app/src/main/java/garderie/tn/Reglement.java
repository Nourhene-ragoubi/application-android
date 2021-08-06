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

public class Reglement extends AppCompatActivity {

    ListView listR ;
    List<HashMap<String, Object>> reglementList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglement);
        listR = (ListView)findViewById(R.id.listView3);
        reglementList = new ArrayList<>();
        getAllReglement();
    }

    private void getAllReglement() {
        class PostAsync extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private String URL_PHP = SplashScreen.config+"allReglement.php";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Reglement.this);
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
                        mapp.put("cc", json_data.getString("moisPaimentInscription"));

                        mapp.put("mm", json_data.getString("montantPaimentInscription")+" Dt");
                        reglementList.add(mapp);
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
                SimpleAdapter adapter1 = new SimpleAdapter(Reglement.this,reglementList,
                        R.layout.item_reglement, new String[]{"cc", "mm"},
                        new int[]{R.id.tmois, R.id.tmontant});
                listR.setAdapter(adapter1);


            }


        }

        PostAsync p = new PostAsync();
        p.execute();

    }
}
