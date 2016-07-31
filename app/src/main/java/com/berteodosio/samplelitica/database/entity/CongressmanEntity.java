package com.berteodosio.samplelitica.database.entity;

import android.support.annotation.NonNull;

import com.berteodosio.samplelitica.congressman.model.Congressman;
import com.berteodosio.samplelitica.database.migration.LocalDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = LocalDatabase.class)
public class CongressmanEntity extends BaseModel {

    @PrimaryKey
    long registration;

    @Column
    String name;

    @Column
    String socialName;

    @Column
    String party;

    @Column
    String email;

    @Column
    String phone;

    @Column
    String photoUrl;

    @SuppressWarnings("unused")
    CongressmanEntity() {

    }

    private CongressmanEntity(final long registration, final String name, final String socialName, final String party,
                             final String email, final String phone, final String photoUrl) {
        this.registration = registration;
        this.name = name;
        this.socialName = socialName;
        this.party = party;
        this.email = email;
        this.phone = phone;
        this.photoUrl = photoUrl;
    }

    @NonNull
    public static CongressmanEntity fromModel(@NonNull final Congressman congressman) {
        return new CongressmanEntity(congressman.getRegistration(), congressman.getName(), congressman.getSocialName(), congressman.getParty(),
                congressman.getEmail(), congressman.getPhone(), congressman.getPhotoUrl());
    }

    @NonNull
    public Congressman toModel() {
        return new Congressman(registration, name, socialName, party, email, phone, photoUrl);
    }
}
