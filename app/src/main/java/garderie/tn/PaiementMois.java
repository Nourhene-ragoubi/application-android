package garderie.tn;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaiementMois extends AppCompatActivity {


    TextView num,code,montant;
    RadioGroup rdg;
    Spinner sp;
    String numc,codec,montantP,rB,rBB,spp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement_mois);
        code = (TextView) findViewById(R.id.code);
        num = (TextView) findViewById(R.id.num);
        montant = (TextView) findViewById(R.id.montant);
        rdg = (RadioGroup) findViewById(R.id.rdg);

        sp = (Spinner)findViewById(R.id.spinner);
        String tab[] = {"Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","September","Octobre","Novombre","Décombre"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tab);
        sp.setAdapter(adapter);

        Button valider=(Button)findViewById(R.id.btnValider);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numc = num.getText().toString();
                codec = code.getText().toString();
                if (numc.equals("") || codec.equals("")) {
                    Toast.makeText(PaiementMois.this, "SVP remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectRadio = (RadioButton) findViewById(rdg .getCheckedRadioButtonId());
                if(rdg.getCheckedRadioButtonId() == -1){
                    Toast.makeText(PaiementMois.this, "SVP choisir le type de paiement", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(PaiementMois.this, "Paiement effectué avec  succées", Toast.LENGTH_SHORT).show();
            }

        });
    }
}



