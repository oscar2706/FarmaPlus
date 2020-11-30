package com.example.farmaplus.pharmacy;

import com.example.farmaplus.Repartidor;

import java.util.ArrayList;
import java.util.List;

public class MedicamentoService {
    public static List<Medicamento> medicamentos = new ArrayList<>();

    public static void addMedicamento(Medicamento medicamento){
        medicamentos.add(medicamento);
    }

    public static void eliminarMedicamento(Medicamento medicamento){
        medicamentos.remove(medicamento);
    }

    public static void actualizarStatus(Medicamento medicamento){
        medicamentos.set(medicamentos.indexOf(medicamento), medicamento);
    }

    public static void vaciarLista()
    {
        medicamentos.clear();
    }
}
