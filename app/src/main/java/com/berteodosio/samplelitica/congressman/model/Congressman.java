package com.berteodosio.samplelitica.congressman.model;

import com.berteodosio.samplelitica.congressman.CongressmanMVP;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "deputado")
public class Congressman implements CongressmanMVP.Model {

    @Element(name = "ideCadastro", required = false)
    private long registerId;

    @Element(name = "codOrcamento", required = false)
    private long budgetId;

    @Element(name = "condicao", required = false)
    private String status;

    @Element(name = "matricula", required = false)
    private long registration;

    @Element(name = "idParlamentar", required = false)
    private long congressmanId;

    @Element(name = "nome")
    private String name;

    @Element(name = "nomeParlamentar", required = false)
    private String socialName;

    @Element(name = "urlFoto", required = false)
    private String photoUrl;

    @Element(name = "sexo", required = false)
    private String sex;

    @Element(name = "uf", required = false)
    private String state;

    @Element(name = "partido", required = false)
    private String party;

    @Element(name = "gabinete", required = false)
    private long cabinetId;

    @Element(name = "anexo", required = false)
    private long outbuildingId;

    @Element(name = "fone", required = false)
    private String phone;

    @Element(name = "email", required = false)
    private String email;

    @ElementList(inline = true, required = false)
    private List<Comission> comissions;

    public Congressman(final long registration, final String name, final String socialName, final String party,
                       final String email, final String phone, final String photoUrl) {
        this.registration = registration;
        this.name = name;
        this.socialName = socialName;
        this.party = party;
        this.email = email;
        this.phone = phone;
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "Name: " + name;
    }

    public String getSocialName() {
        return socialName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getParty() {
        return party;
    }

    public String getName() {
        return name;
    }

    public long getRegisterId() {
        return registerId;
    }

    public long getBudgetId() {
        return budgetId;
    }

    public String getStatus() {
        return status;
    }

    public long getRegistration() {
        return registration;
    }

    public long getCongressmanId() {
        return congressmanId;
    }

    public String getSex() {
        return sex;
    }

    public String getState() {
        return state;
    }

    public long getCabinetId() {
        return cabinetId;
    }

    public long getOutbuildingId() {
        return outbuildingId;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public List<Comission> getComissions() {
        return comissions;
    }

    public Congressman() {

    }
}
