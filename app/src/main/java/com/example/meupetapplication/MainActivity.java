package com.example.meupetapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.InterruptedIOException;

import android.database.sqlite.SQLiteDatabase; // Banco de dados
import android.database.Cursor; //Navegação entre os registros
import android.widget.*;

import android.content.ContentValues;

public class MainActivity extends AppCompatActivity {

    EditText et_nomepet,et_qtd_hosp,et_qtd_spa,et_qtd_banhotosa,et_obs;

    Button btn_finalizaagenda,btn_agendamentos,btn_fechar;

    SQLiteDatabase db=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nomepet=(EditText) findViewById((R.id.et_nomepet));
        et_qtd_hosp=(EditText) findViewById(R.id.et_qtd_hosp);
        et_qtd_spa=(EditText) findViewById(R.id.et_qtd_spa);
        et_qtd_banhotosa=(EditText) findViewById(R.id.et_qtd_banhotosa);
        et_obs=(EditText) findViewById(R.id.et_obs);
        btn_finalizaagenda=(Button) findViewById(R.id.btn_finalizaagenda);
        btn_agendamentos=(Button) findViewById(R.id.btn_agendamentos);
        btn_fechar=(Button) findViewById(R.id.btn_fechar);

        abrirBanco();
        abrirOuCriarTabela();
        fecharDB();


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

    public void abrirOuCriarTabela(){
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS agendamento (id INTEGER PRIMARY KEY,nomepet TEXT,hospedagem TEXT,spa TEXT,banhotosa TEXT,obs TEXT)");
        }catch (Exception ex){
            msg("Erro ao criar a tabela");
        }
    }




        public void inserirAgendamento(View v){

            String st_nomepet,st_hospedagem,st_banhotosa,st_spa,st_obs;
            st_nomepet=et_nomepet.getText().toString();
            st_hospedagem=et_qtd_hosp.getText().toString();
            st_banhotosa=et_qtd_banhotosa.getText().toString();
            st_spa=et_qtd_spa.getText().toString();
            st_obs=et_obs.getText().toString();
            if (st_nomepet=="" || st_hospedagem=="" || st_banhotosa=="" || st_spa=="" || st_obs==""){
                msg("Campos não podem ser vazios");
                return;
            }

            ContentValues cv = new ContentValues();

                cv.put("nomepet", st_nomepet);
                cv.put("hospedagem", st_hospedagem);
                cv.put("banhotosa", st_banhotosa);
                cv.put("spa", st_spa);
                cv.put("obs", st_obs);





            abrirBanco();
                long result = db.insert("agendamento",null, cv);
                    if (result == -1){
                        msg("deu erro");
                    }else{
                        msg("deu boa "+result);
                    }



            et_nomepet.setText(null);
            et_qtd_hosp.setText(null);
            et_qtd_spa.setText(null);
            et_qtd_banhotosa.setText(null);
            et_obs.setText(null);
        }



    public void abrir_tela_consulta(View v){
        Intent it_tela_consulta=new Intent(this, AgendamentoActivity.class);
        startActivity(it_tela_consulta);
    }



    public void fechar_tela_consulta(View v){
        this.finish();
    }

    public void msg(String txt){
        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setMessage(txt);
        adb.setNeutralButton("OK",null);
        adb.show();
    }



}