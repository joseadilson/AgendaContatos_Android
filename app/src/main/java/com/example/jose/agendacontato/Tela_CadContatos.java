    package com.example.jose.agendacontato;

    import android.app.AlertDialog;
    import android.app.DatePickerDialog;
    import android.app.ProgressDialog;
    import android.content.DialogInterface;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.widget.*;
    import android.view.*;

    import com.example.jose.agendacontato.database.DataBase;
    import com.example.jose.agendacontato.dominio.Entidades.Contato;
    import com.example.jose.agendacontato.dominio.RepositorioContato;

    import java.text.DateFormat;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.StringTokenizer;

    public class Tela_CadContatos extends AppCompatActivity implements View.OnClickListener {

        //private Button btnSalvar;
        private MenuItem btnSalvar;
        private Button btnAlterar;
        private Button btnExcluir;

        ProgressDialog progressDialog;

        private EditText edtNome;
        private EditText edtEmail;
        private EditText edtTelefone;
        private EditText edtEndereco;
        private EditText edtDatasEspeciais;
        private EditText edtGrupos;

        private Spinner spnTipoEmail;
        private Spinner spnTipoTelefone;
        private Spinner spnTipoEndereco;
        private Spinner spnTipoDatasEspeciais;

        private ArrayAdapter<String> adpTipoEmaill;
        private ArrayAdapter<String> adpTipoTelefone;
        private ArrayAdapter<String> adpTipoEndereco;
        private ArrayAdapter<String> adpTipoDatasEspeciais;

        private DataBase dataBase;
        private SQLiteDatabase conn;
        private RepositorioContato repositorioContato;
        private Contato contato;


        //Add os campos
        private String[] tipoEmail      = new String[]{"Casa", "Trabalho","Faculdade", "Outros"};
        private String[] tipoTelefone   = new String[]{"Casa", "Trabalho","Faculdade","Celular", "Outros"};
        private String[] tipoEndereco   = new String[]{"Casa", "Trabalho","Faculdade", "Outros"};
        private String[] datasEspeciais = new String[]{"Aniversario", "Data comemorativa", "Outros"};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tela__cad_contatos);

            btnSalvar = (MenuItem) findViewById(R.id.btnSalvar);
            btnAlterar = (Button)findViewById(R.id.btnAlterar);
            btnExcluir = (Button)findViewById(R.id.btnExcluir);

            progressDialog = new ProgressDialog(Tela_CadContatos.this);

            //Busca os componentes do tipo text
            edtNome = (EditText)findViewById(R.id.edtNome);
            edtEmail = (EditText)findViewById(R.id.edtEmail);
            edtTelefone = (EditText)findViewById(R.id.edtTelefone);
            edtEndereco = (EditText)findViewById(R.id.edtEndereco);
            edtDatasEspeciais = (EditText)findViewById(R.id.edtDatasEspeciais);
            edtGrupos = (EditText)findViewById(R.id.edtGrupos);

            spnTipoEmail = (Spinner)findViewById(R.id.spnTipoEmail);
            spnTipoTelefone = (Spinner)findViewById(R.id.spnTipoTelefone);
            spnTipoEndereco = (Spinner)findViewById(R.id.spnTipoEndereco);
            spnTipoDatasEspeciais = (Spinner)findViewById(R.id.spnDatasEspeciais);

            //Configurando o ArrayAdapter
            adpTipoEmaill = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoEmail);
            adpTipoEmaill.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            adpTipoTelefone = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoTelefone);
            adpTipoTelefone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            adpTipoEndereco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoEndereco);
            adpTipoEndereco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            adpTipoDatasEspeciais = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datasEspeciais);
            adpTipoDatasEspeciais.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //Vincula os componentes do tipo Spinner ao seu ArrayAdapter
            spnTipoEmail.setAdapter(adpTipoEmaill);
            spnTipoTelefone.setAdapter(adpTipoTelefone);
            spnTipoEndereco.setAdapter(adpTipoEndereco);
            spnTipoDatasEspeciais.setAdapter(adpTipoDatasEspeciais);

            //Objeto anonimo - Data
            edtDatasEspeciais.setOnClickListener(new ExibeDataListener());
            edtDatasEspeciais.setOnFocusChangeListener(new ExibeDataListener());

            //Recuperar o objeto
            //Ira retornar a referencia do Intent da clase MainActivity
            Bundle bundle = getIntent().getExtras();
            if ((bundle != null) && (bundle.containsKey("CONTATO"))) {
                contato = (Contato)bundle.getSerializable("CONTATO");
                //Se pasado algum parametro para esse interface
                preencheDados();

            } else
            //Ira criar a instancia do meu objeto contato
            contato = new Contato();


            try{
                dataBase = new DataBase(this);
                conn = dataBase.getReadableDatabase();

                repositorioContato = new RepositorioContato(conn);

            } catch (SQLException ex){
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setMessage("Erro ao criar o banco "+ ex.getMessage());
                dlg.setNeutralButton("OK", null);
                dlg.show();
            }

            //Botoes
    //        btnSalvar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
    //
    //            @Override
    //            public boolean onMenuItemClick(MenuItem item) {
    //                Toast.makeText(Tela_CadContatos.this, "BLZ", Toast.LENGTH_SHORT).show();
    //                return false;
    ////                salvar();
    ////
    ////                return true;
    //            }
    //        });
           // btnSalvar.setOnClickListener(this);
            btnAlterar.setOnClickListener(this);
            btnExcluir.setOnClickListener(this);

            //Se botão visivel ou não
            if (contato.getId() != 0) {
                btnAlterar.setVisibility(View.VISIBLE);
                btnExcluir.setVisibility(View.VISIBLE);
               // btnSalvar.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onClick(View v) {
            //Codigo dos botoes
    //        if (v == btnSalvar) {
    //            //SALVAR
    //            salvar();
    //            finish();
    //        }

            if (v == btnAlterar){
                //ALTERAR
                salvar();
                finish();
            }

            if (v == btnExcluir){
                //EXCLUIR
                excluir();
                finish();
            }
        }

        //Metado Preencher dados quando item for selecionado
        private void preencheDados(){
            edtNome.setText(contato.getNome());
            edtTelefone.setText(contato.getTelefone());
            //setSelection para atribuir o item selecionado no meu spinner
            spnTipoTelefone.setSelection(Integer.parseInt(contato.getTipoTelefone()));
            edtEmail.setText(contato.getEmail());
            spnTipoEmail.setSelection(Integer.parseInt(contato.getTipoEmail()));
            edtEndereco.setText(contato.getEndereco());
            spnTipoEndereco.setSelection(Integer.parseInt(contato.getTipoEndereco()));

            DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
            String dt =  format.format(contato.getDatasEspeciais());
            edtDatasEspeciais.setText( dt );
            spnTipoDatasEspeciais.setSelection(Integer.parseInt(contato.getTipoDatasEspeciais()));

            edtGrupos.setText(contato.getGrupos());

        }

        private void excluir(){
            try {

                repositorioContato.excluir(contato.getId());

            } catch (Exception ex){
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setMessage("Erro ao excluir dados!"+ ex.getMessage());
                dlg.setNeutralButton("OK", null);
                dlg.show();
            }
        }

        private void salvar(){
            try {
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Salvando...");
                progressDialog.show();

                contato.setNome(edtNome.getText().toString());
                contato.setTelefone(edtTelefone.getText().toString());
                contato.setEmail(edtEmail.getText().toString());
                contato.setEndereco(edtEndereco.getText().toString());
                contato.setGrupos(edtGrupos.getText().toString());

                //Retorna o indice selecionado no spinner
                contato.setTipoTelefone(String.valueOf( spnTipoTelefone.getSelectedItemPosition()));
                contato.setTipoDatasEspeciais(String.valueOf( spnTipoDatasEspeciais.getSelectedItemPosition()));
                contato.setTipoEmail(String.valueOf( spnTipoEmail.getSelectedItemPosition()));
                contato.setTipoEndereco(String.valueOf( spnTipoEndereco.getSelectedItemPosition()));

                if (contato.getId() == 0) {
                    repositorioContato.inserir(contato);

                } else {
                    repositorioContato.alterar(contato);

                }


            } catch (Exception ex){
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setMessage("Erro ao inserir dados!"+ ex.getMessage());
                dlg.setNeutralButton("OK", null);
                dlg.show();
            }
        }


        //DATA
        //Responsavel por exibir janela de dialogo para selecionar a data
        private void exibeData(){
            Calendar calendar = Calendar.getInstance();
            int ano = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog dlg = new DatePickerDialog(this, new SelecionaDataListener(), ano, mes, dia);
            dlg.show();
        }


        //Ao Clicar exibe a data | So dispara a o receber o focu
        private class ExibeDataListener implements View.OnClickListener, View.OnFocusChangeListener{
            @Override
            public void onClick(View v) {
                exibeData();
            }

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //Quando focado, exibe a janela de data
                if (hasFocus)
                    exibeData();
            }
        }

        //Sera disparado quando a data for selecionada
        private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener{
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Auxiliar a criar a instancia do objeto da classe Calendar e retorna a data do sistema.
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear,dayOfMonth);
                Date data = calendar.getTime();

                //Passar o valor da minha data, para o meu objeto edtDatasEspeciais,e para o objeto contato
                //Clase para formatar a data e deixar visivel para o usuario
                DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
                //Passar o objeto data criada pelo objeto Calendar
                String dt =  format.format(data);

                edtDatasEspeciais.setText(dt);

                contato.setDatasEspeciais(data);
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_cadastro, menu);
            return true;
        }

        public void menuClique(MenuItem item) {
            salvar();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            finish();
                        }
                    }, 2000);

        }
        //

    }
