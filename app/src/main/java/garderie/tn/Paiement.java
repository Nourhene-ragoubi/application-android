package garderie.tn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Paiement extends AppCompatActivity {
TextView num,code,montant;
    RadioGroup rdg;
    String numc,codec,montantP,rB,rBB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement);
        code = (TextView) findViewById(R.id.code);
        num = (TextView) findViewById(R.id.num);
        montant = (TextView) findViewById(R.id.montant);
        rdg = (RadioGroup) findViewById(R.id.rdg);
        montant.setText(EspaceParent.montantAPayer);
        Button valider=(Button)findViewById(R.id.btnValider);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numc = num.getText().toString();
                codec = code.getText().toString();
                if (numc.equals("") || codec.equals("")) {
                    Toast.makeText(Paiement.this, "SVP remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton selectRadio = (RadioButton) findViewById(rdg .getCheckedRadioButtonId());
                if(rdg.getCheckedRadioButtonId() == -1){
                    Toast.makeText(Paiement.this, "SVP choisir le type de paiement", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(Paiement.this, "Paiement effectué avec  succées", Toast.LENGTH_SHORT).show();
        }

        });
    }
    }

