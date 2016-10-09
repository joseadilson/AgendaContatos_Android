package com.example.jose.agendacontato.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.*;
import android.widget.ArrayAdapter;
import android.widget.*;

import com.example.jose.agendacontato.R;
import com.example.jose.agendacontato.dominio.Entidades.Contato;

import java.util.Date;
import java.util.prefs.PreferenceChangeEvent;

/**
 * Created by Jose on 24/09/2016.
 */
public class RepositorioContato {

    private SQLiteDatabase conn;

    //referencia da conexao com o BD
    public RepositorioContato(SQLiteDatabase conn){
        //receba a referencia do construtor
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Contato contato){
        ContentValues values = new ContentValues();
        values.put("NOME",contato.getNome());
        values.put("TELEFONE", contato.getTelefone());
        values.put("TIPOTELEFONE", contato.getTipoTelefone());
        values.put("EMAIL", contato.getEmail());
        values.put("TIPOEMAIL", contato.getTipoEmail());
        values.put("ENDERECO", contato.getEndereco());
        values.put("TIPOENDERECO", contato.getTipoEndereco());
        values.put("DATASESPECIAIS", contato.getDatasEspeciais().getTime());
        values.put("TIPODATASESPECIAIS", contato.getTipoDatasEspeciais());
        values.put("GRUPOS", contato.getGrupos());

        return values;
    }

    public void excluir(long id){
        conn.delete("CONTATO","_id = ?", new String[]{ String.valueOf( id )} );
    }

    public void alterar(Contato contato){
        ContentValues values = preencheContentValues(contato);
        conn.update("CONTATO", values,"_id = ?", new String[]{ String.valueOf(contato.getId()) });
    }

    public void inserir(Contato contato){
        ContentValues values = preencheContentValues(contato);
        conn.insertOrThrow("CONTATO", null, values);
    }



    //Responsavel pela consulta dos meus dados
    public ArrayAdapter<Contato> buscaContatos(Context context){
        ArrayAdapter<Contato> adpContatos = new ArrayAdapter<Contato>(context, android.R.layout.simple_list_item_1);


        //Cursor responsavel por armazenar todos registro que são consultados
        Cursor cursor = conn.query("CONTATO", null, null, null, null, null, null);


        if (cursor.getCount() > 0){
            //Verifico se ha registro, e movo para o primeiro registro
            cursor.moveToFirst();

            //Para inserir meus dados, recupera cada valor que foi add na lista
            do {
                Contato contato = new Contato();
                contato.setId(cursor.getLong( cursor.getColumnIndex(Contato.ID) ));
                contato.setNome(cursor.getString( cursor.getColumnIndex(Contato.NOME)));
                contato.setTelefone(cursor.getString( cursor.getColumnIndex(Contato.TELEFONE)));
                contato.setTipoTelefone(cursor.getString( cursor.getColumnIndex(Contato.TIPOTELEFONE)));
                contato.setEmail(cursor.getString( cursor.getColumnIndex(Contato.EMAIL)));
                contato.setTipoEmail(cursor.getString( cursor.getColumnIndex(Contato.TIPOEMAIL)));
                contato.setEndereco(cursor.getString( cursor.getColumnIndex(Contato.ENDERECO)));
                contato.setTipoEndereco(cursor.getString( cursor.getColumnIndex(Contato.TIPOENDERECO)));
                contato.setDatasEspeciais(new Date(cursor.getLong( cursor.getColumnIndex(Contato.DATASESPECIAIS))));
                contato.setTipoDatasEspeciais(cursor.getString( cursor.getColumnIndex(Contato.TIPODATASESPECIAIS)));
                contato.setGrupos(cursor.getString( cursor.getColumnIndex(Contato.GRUPOS)));


                adpContatos.add(contato);
            } while (cursor.moveToNext());

        }
        return adpContatos;
    }



}
