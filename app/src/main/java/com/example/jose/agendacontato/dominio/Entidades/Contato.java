package com.example.jose.agendacontato.dominio.Entidades;

import java.util.Date;
import java.io.Serializable;

/**
 * Created by Jose on 01/10/2016.
 */
public class Contato implements Serializable {

    //Nome do campo para reculpera o valor
    public static String ID= "_id";
    public static String NOME= "NOME";
    public static String TELEFONE= "TELEFONE";
    public static String TIPOTELEFONE= "TIPOTELEFONE";
    public static String EMAIL = "EMAIL";
    public static String TIPOEMAIL= "TIPOEMAIL";
    public static String ENDERECO= "ENDERECO";
    public static String TIPOENDERECO = "TIPOENDERECO";
    public static String DATASESPECIAIS= "DATASESPECIAIS";
    public static String TIPODATASESPECIAIS= "TIPODATASESPECIAIS";
    public static String GRUPOS= "GRUPOS";
    public static String FOTO= "FOTO";

    private long id;
    private String nome;
    private String telefone;
    private String tipoTelefone;
    private String email;
    private String tipoEmail;
    private String endereco;
    private String tipoEndereco;
    private Date datasEspeciais;
    private String tipoDatasEspeciais;
    private String grupos;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    //

    private String foto;

    public Contato(){
        id = 0;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    public String getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(String tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getTipoEmail() {
        return tipoEmail;
    }

    public void setTipoEmail(String tipoEmail) {
        this.tipoEmail = tipoEmail;
    }


    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }


    public String getTipoEndereco() {
        return tipoEndereco;
    }

    public void setTipoEndereco(String tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }


    public Date getDatasEspeciais() {
        return datasEspeciais;
    }

    public void setDatasEspeciais(Date datasEspeciais) {
        this.datasEspeciais = datasEspeciais;
    }


    public String getTipoDatasEspeciais() {
        return tipoDatasEspeciais;
    }

    public void setTipoDatasEspeciais(String tipoDatasEspeciais) {
        this.tipoDatasEspeciais = tipoDatasEspeciais;
    }


    public String getGrupos() {
        return grupos;
    }

    public void setGrupos(String grupos) {
        this.grupos = grupos;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    //Retornar valor que deve ser convertido para String.
    @Override
    public String toString(){
        return nome + " " + telefone;
    }
}
