package com.recordatoriodemedicamentos.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.recordatoriodemedicamentos.MedicamentoActivity;
import com.recordatoriodemedicamentos.Modelo.AuthProvider;
import com.recordatoriodemedicamentos.Modelo.IMedicamento;
import com.recordatoriodemedicamentos.Modelo.Medicamento;
import com.recordatoriodemedicamentos.Modelo.MedicamentoAdapter;
import com.recordatoriodemedicamentos.Modelo.MedicamentoProvider;
import com.recordatoriodemedicamentos.Notificacion.CreateNotification;
import com.recordatoriodemedicamentos.Notificacion.Recordatorios;
import com.recordatoriodemedicamentos.R;

import java.util.ArrayList;

public class MedicinasFragment extends Fragment implements IMedicamento {

    FloatingActionButton btnAgregar;
    View vista;

    AuthProvider authProvider;
    MedicamentoProvider mediProvider;
    ArrayList<Medicamento> medicamentoArrayList;
    RecyclerView idrecyclerView;
    private MedicamentoAdapter medicamentoAdapter;

    SharedPreferences preMed;
    SharedPreferences.Editor edtMed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_medicinas, container, false);

        btnAgregar = (FloatingActionButton) vista.findViewById(R.id.btnAgrMed);
        authProvider = new AuthProvider();
        mediProvider = new MedicamentoProvider();

        preMed = getContext().getSharedPreferences("typeBoton", getContext().MODE_PRIVATE);
        edtMed = preMed.edit();

        idrecyclerView = (RecyclerView) vista.findViewById(R.id.rcvlistaMedicamentos);
        medicamentoArrayList = new ArrayList<>();
        medicamentoAdapter = new MedicamentoAdapter(this, medicamentoArrayList);
        idrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        idrecyclerView.setAdapter(medicamentoAdapter);

        mediProvider.showMedicamento(authProvider.getId(), medicamentoAdapter, medicamentoArrayList);


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMed.putString("boton", "agregar");
                edtMed.apply();
                Intent intent = new Intent(getContext(), MedicamentoActivity.class);
                startActivity(intent);
            }
        });
        return vista;
    }


    @Override
    public void OpcionEditar(Medicamento medicamento) {
        edtMed.putString("boton", "modificar");
        edtMed.apply();
        Intent intent = new Intent(getContext(), MedicamentoActivity.class);
        intent.putExtra("medicamento", medicamento);
        startActivity(intent);
    }

    @Override
    public void OpcionEliminar(Medicamento medicamento) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
        alerta.setTitle("Mensaje de Advertencia");
        alerta.setMessage("¿Esta seguro que desea eliminar este medicamento?");
        alerta.setCancelable(false);
        alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediProvider.removeMedicamento(authProvider.getId(), medicamento, medicamentoAdapter);
                Toast.makeText(getContext(), "Se eliminó correctamente el medicamento", Toast.LENGTH_SHORT).show();
            }
        });
        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta.show();

    }
}
