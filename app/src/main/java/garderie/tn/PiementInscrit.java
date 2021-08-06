package garderie.tn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PiementInscrit extends AppCompatActivity {
    TextView num,code,montant;
    RadioGroup rdg;
    Spinner sp;
    String numc,codec,montantP,rB,rBB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piement_inscrit);

        code = (TextView) findViewById(R.id.code);
        num = (TextView) findViewById(R.id.num);
        montant = (TextView) findViewById(R.id.montant);
        rdg = (RadioGroup) findViewById(R.id.rdg);
        montant.setText(EspaceParent.montantAPayer);
        sp = (Spinner)findViewById(R.id.spinner);
        String tab[] = {"Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","September","Octobre","Novombre","Décombre"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tab);
        sp.setAdapter(adapter);
        Button valider=(Button)findViewById(R.id.btnValider);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numc=num.getText().toString();

                codec = code.getText().toString();
                if (numc.equals("") || codec.equals("")) {
                    Toast.makeText(PiementInscrit.this, "SVP remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton selectRadio = (RadioButton) findViewById(rdg .getCheckedRadioButtonId());
                if(rdg.getCheckedRadioButtonId() == -1){
                    Toast.makeText(PiementInscrit.this, "SVP choisir le type de paiement", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(PiementInscrit.this, "Paiement effectué avec  succées", Toast.LENGTH_SHORT).show();
            }

        });
    }
}



