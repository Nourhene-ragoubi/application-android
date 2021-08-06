package garderie.tn;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListEvenement extends AppCompatActivity {
    ListView listE ;
    List<HashMap<String, Object>> eventsList;
    static String description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_evenement);
        listE = (ListView)findViewById(R.id.listView);
        eventsList = new ArrayList<>();
        getAllEvents();
        // Action lors de clique sur un club
        listE.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, Object> map = (HashMap<String, Object>) listE.getItemAtPosition(position);
                description = map.get("descrit").toString();
                AlertDialog.Builder alert = new AlertDialog.Builder(ListEvenement.this);
                alert.setTitle("Evénement");
                alert.setIcon(R.drawable.eventt);
                alert.setMessage("Description de l'événement : " + description);
                alert.setPositiveButton("Fermer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });


    }


    private void getAllEvents() {
          class PostAsync extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private String URL_PHP = SplashScreen.config+"allEvents.php";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(ListEvenement.this);
                pDialog.setMessage("Chargement ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... arg0) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //params.add(new BasicNameValuePair("NOM_VARIABLE", VALEUR));
                String result = jsonParser.makeHttpRequest(URL_PHP, "POST", params);
                System.out.println("Response from url: " + result);
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        HashMap<String, Object> mapp = new HashMap<>();
                        mapp.put("TT", json_data.getString("titreEvenement"));
                        mapp.put("DD", json_data.getString("dateEvenement"));
                        mapp.put("descrit", json_data.getString("descriptionEvenement"));
                        mapp.put("PP", json_data.getString("montantParticipation")+" DT");
                        eventsList.add(mapp);
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
                SimpleAdapter adapter1 = new SimpleAdapter(ListEvenement.this, eventsList,
                        R.layout.item_event, new String[]{"TT", "DD", "PP"},
                        new int[]{R.id.ttire, R.id.tdate, R.id.tprix});
                listE.setAdapter(adapter1);


            }


        }

        PostAsync p = new PostAsync();
        p.execute();

    }
}
