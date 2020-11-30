package com.example.farmaplus.pharmacy;

import androidx.annotation.Nullable;

import com.example.farmaplus.Repartidor;

public class Medicamento {
    private String idMedicamento;
    private String marca;
    private String dosis;
    private String principioActivo;
    private String formaFarmaceutica;
    private String precio;

    public Medicamento() {
    }

    public Medicamento(String idMedicamento, String marca, String dosis, String principioActivo, String formaFarmaceutica, String precio) {
        this.idMedicamento = idMedicamento;
        this.marca = marca;
        this.dosis = dosis;
        this.principioActivo = principioActivo;
        this.formaFarmaceutica = formaFarmaceutica;
        this.precio = precio;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public String getFormaFarmaceutica() {
        return formaFarmaceutica;
    }

    public void setFormaFarmaceutica(String formaFarmaceutica) {
        this.formaFarmaceutica = formaFarmaceutica;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return idMedicamento.equals(((Medicamento)obj).idMedicamento);
    }
}
