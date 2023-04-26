package com.example.bancodedadoscontatovivian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bancodedadoscontatovivian.DAO.DAO;
import com.example.bancodedadoscontatovivian.objetos.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextNome;
    EditText editTextTelefone;
    Button botaoSalvar;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        botaoSalvar = findViewById(R.id.botaoSalvar);
        listView = findViewById(R.id.listView);

        buscaNoBanco();

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!(editTextNome.getText().toString().equals("") || editTextTelefone.getText().toString().equals(""))){

                    DAO dao = new DAO(getApplicationContext());
                    Pessoa pessoa = new Pessoa();
                    pessoa.setNome(editTextNome.getText().toString());
                    pessoa.setTelefone(editTextTelefone.getText().toString());
                    dao.inserePessoa(pessoa);
                    dao.close();

                    limpaFormulario();
                    buscaNoBanco();

                } else {
                    Toast.makeText(getApplicationContext(), "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void buscaNoBanco() {
        DAO dao2 = new DAO(getApplicationContext());
        List<Pessoa> pessoas = dao2.buscaPessoa();
        List<String> nomes = new ArrayList<String>();

        for(Pessoa nomeBuscado : pessoas) {
            nomes.add(nomeBuscado.getNome());
            //nomes.add(nomeBuscado.getTelefone());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, nomes);
        listView.setAdapter(adapter);
    }

    private void limpaFormulario() {
        editTextNome.setText("");
        editTextNome.requestFocus();
        editTextTelefone.setText("");
    }
}