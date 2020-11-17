package com.example.farmaplus.client;

import androidx.annotation.Nullable;

public class User {
    private String idUser;
    private String correo;
    private String password;

    public User()
    {

    }

    public User(String idUser, String correo, String password) {
        this.idUser = idUser;
        this.correo = correo;
        this.password = password;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return idUser.equals(((User)obj).idUser);
    }
}
