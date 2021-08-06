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

public class Club extends AppCompatActivity {
    ListView listC ;
    List<HashMap<String, Object>> clubsList;
    String description ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);
        listC = (ListView)findViewById(R.id.listClub);
        clubsList = new ArrayList<>();
        getAllClubs();
        // Action lors de clique sur un club
        listC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, Object> map = (HashMap<String, Object>) listC.getItemAtPosition(position);
                description = map.get("descrit").toString() ;
                AlertDialog.Builder alert = new AlertDialog.Builder(Club.this);
                alert.setTitle("Club");
                alert.setIcon(R.drawable.c);
                alert.setMessage("Description : " + description);
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




    private void getAllClubs() {
        class PostAsync extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private String URL_PHP = SplashScreen.config+"allClubs.php";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Club.this);
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
                        mapp.put("nn", json_data.getString("nomClub"));
                        mapp.put("IDC", json_data.getString("codeClub"));
                        mapp.put("anim", "Animateur : "+json_data.getString("animateurClub"));
                        mapp.put("descrit", json_data.getString("descriptionClub"));
                        mapp.put("ma", json_data.getString("montantAdhesion")+" DT");
                        clubsList.add(mapp);
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
                SimpleAdapter adapter1 = new SimpleAdapter(Club.this, clubsList,
                        R.layout.item_club, new String[]{"nn", "anim", "ma"},
                        new int[]{R.id.tnomClub,  R.id.tdescrit, R.id.tma});
                listC.setAdapter(adapter1);


            }


        }

        PostAsync p = new PostAsync();
        p.execute();

    }
}
