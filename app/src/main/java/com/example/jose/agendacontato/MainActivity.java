package com.example.jose.agendacontato;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import android.database.sqlite.*;
import android.database.*;

import com.example.jose.agendacontato.database.DataBase;
import com.example.jose.agendacontato.database.ScriptSQL;
import com.example.jose.agendacontato.dominio.Entidades.Contato;
import com.example.jose.agendacontato.dominio.RepositorioContato;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private FloatingActionButton btnAdicionar;
    private EditText edtPesquisa;
    private ListView lstContatos;
    private ArrayAdapter<Contato> adpContatos;


    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioContato repositorioContato;
    private Contato contato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//Cod do Botão de Email
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //-------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------



        btnAdicionar = (FloatingActionButton) findViewById(R.id.btnAdicionar);
        edtPesquisa = (EditText)findViewById(R.id.edtPesquisa);
        lstContatos = (ListView)findViewById(R.id.lstContatos);

        btnAdicionar.setOnClickListener(this);

        //Vincular o evento
        lstContatos.setOnItemClickListener(this);

        try{
            dataBase = new DataBase(this);
            conn = dataBase.getReadableDatabase();

            repositorioContato = new RepositorioContato(conn);

            adpContatos = repositorioContato.buscaContatos(this);

            lstContatos.setAdapter(adpContatos);


        } catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao criar o banco "+ ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }


    //
    @Override
    public void onClick(View v){
        Intent it1 = new Intent(MainActivity.this, Tela_CadContatos.class);
        startActivityForResult(it1, 0);
    }

    // RESPONSAVEL POR ATT O COMPONENTE LISTVIEW QUANDO FOR RELAIZADO UM CADASTRO
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        adpContatos = repositorioContato.buscaContatos(this);

        lstContatos.setAdapter(adpContatos);
    }
    //

    //Será dsparado ao clicar e algum item
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //Posição do meu objeto selecionado
        Contato contato = adpContatos.getItem(position);

        //Pasagem de parametro entre uma activity e outra
        //Ultilizar o metado putExtra ea interface Serializable
        //Serializable - Utilizada para trabalhar dados de entrada e saida de arquivos
        //Será usado para enviar dados entre uma interface e outra
        Intent it1 = new Intent(this, Tela_CadContatos.class);

        it1.putExtra("CONTATO", contato);

        startActivityForResult(it1, 0);

    }


















    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mni_acao1) {
            // Handle the camera action
        } else if (id == R.id.mni_acao2) {

        } else if (id == R.id.mni_acao3) {

        }  else if (id == R.id.mni_acao4sobre) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
