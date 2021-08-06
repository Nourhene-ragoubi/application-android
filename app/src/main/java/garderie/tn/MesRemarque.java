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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MesRemarque extends AppCompatActivity {

    ListView listR ;
    List<HashMap<String, Object>> remarquesList;
static  String contenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_remarque);
        listR = (ListView)findViewById(R.id.listReq);
        remarquesList= new ArrayList<>();
        getAllRemarque();

    listR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final HashMap<String, Object> map = (HashMap<String, Object>) listR.getItemAtPosition(position);
            contenu = map.get("cc").toString();
            AlertDialog.Builder alert = new AlertDialog.Builder(MesRemarque.this);
            alert.setTitle("Remarque");
            alert.setIcon(R.drawable.rr);
            alert.setMessage("Contenu du remarque : " + contenu);
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


    private void getAllRemarque() {
        class PostAsync extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private String URL_PHP =SplashScreen.config+"mesRemarques.php";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(MesRemarque.this);
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
                        mapp.put("ss", json_data.getString("sujet"));
                        mapp.put("cc", json_data.getString("cotenuRemarque"));
                        mapp.put("np", json_data.getString("nomEnseignant")+" "+ json_data.getString("prenomEnseignant"));

                        remarquesList.add(mapp);
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
                SimpleAdapter adapter1 = new SimpleAdapter(MesRemarque.this, remarquesList,
                        R.layout.item_remarque, new String[]{"np","ss"},
                        new int[]{R.id.tnp, R.id.tsujet});
                listR.setAdapter(adapter1);


            }


        }

        PostAsync p = new PostAsync();
        p.execute();

    }
}
