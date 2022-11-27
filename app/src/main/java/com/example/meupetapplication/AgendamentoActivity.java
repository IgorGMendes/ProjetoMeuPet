package com.example.meupetapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AgendamentoActivity extends AppCompatActivity {

    EditText et_nomepet, et_qtd_hosp,et_qtd_spa,et_qtd_banhotosa,et_obs;
    EditText et_nomepet_agenda, et_hospedagem_agenda, et_banhotosa_agenda, et_spa_agenda, et_obs_agenda;

    Button btn_anterior, btn_proximo, btn_voltar;

    SQLiteDatabase db=null;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);



        et_nomepet=(EditText) findViewById(R.id.et_nomepet_agenda);
        et_qtd_hosp=(EditText) findViewById(R.id.et_hospedagem_agenda);
        et_qtd_banhotosa=(EditText) findViewById(R.id.et_banhotosa_agenda);
        et_qtd_spa=(EditText) findViewById(R.id.et_spa_agenda);
        et_obs=(EditText) findViewById(R.id.et_obs_agenda);

        btn_anterior=(Button) findViewById(R.id.btn_anterior);
        btn_proximo=(Button) findViewById(R.id.btn_proximo);
        btn_voltar=(Button) findViewById(R.id.btn_voltar_consulta);

        buscarDados();



    }



    public void fechar_tela_consulta(View v){
        this.finish();
    }

    public void abrirBanco(){
        try {
            db=openOrCreateDatabase("bancoMP",MODE_PRIVATE,null);
        }catch (Exception ex){
            msg("Erro ao abrir ou criar banco de dados");
        }
    }

    public void fecharDB(){

        db.close();
    }

    public void buscarDados(){
        abrirBanco();
        cursor=db.query("agendamento",
                new String[]{"nomepet","hospedagem","banhotosa","spa","obs",},
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            mostrarDados();
        }else{
            msg("Nenhum agendamento encontrado");
        }
    }

    public void mostrarDados(){
        Cursor c = db.rawQuery("SELECT nomepet,hospedagem,spa,banhotosa,obs FROM agendamento ", null);
        if (c.moveToFirst()){
            do {
                // Passing values
                String nomepet = c.getString(0);
                String hospedagem = c.getString(1);
                String spa = c.getString(2);
                String obs = c.getString(3);
                String banhotosa = c.getString(4);

                // Do something Here with values
            } while(c.moveToNext());
        }
        c.close();
        db.close();






    }

    public void msg(String txt){
        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setMessage(txt);
        adb.setNeutralButton("OK",null);
        adb.show();
    }

}