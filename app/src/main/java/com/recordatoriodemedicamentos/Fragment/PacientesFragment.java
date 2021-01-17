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
import com.recordatoriodemedicamentos.Modelo.IPaciente;
import com.recordatoriodemedicamentos.Modelo.Medicamento;
import com.recordatoriodemedicamentos.Modelo.MedicamentoAdapter;
import com.recordatoriodemedicamentos.Modelo.MedicamentoProvider;
import com.recordatoriodemedicamentos.Modelo.Paciente;
import com.recordatoriodemedicamentos.Modelo.PacienteAdapter;
import com.recordatoriodemedicamentos.Modelo.PacienteProvider;
import com.recordatoriodemedicamentos.PacienteActivity;
import com.recordatoriodemedicamentos.R;

import java.util.ArrayList;

public class PacientesFragment extends Fragment implements IPaciente {
    FloatingActionButton btnAgregar;
    View vista;

    AuthProvider authProvider;
    PacienteProvider pacienteProvider;
    ArrayList<Paciente> pacienteArrayList;
    RecyclerView idrecyclerView;
    private PacienteAdapter pacienteAdapter;

    SharedPreferences preMed;
    SharedPreferences.Editor edtMed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_pacientes, container, false);
        btnAgregar = (FloatingActionButton) vista.findViewById(R.id.btnAgrPac);
        authProvider = new AuthProvider();
        pacienteProvider = new PacienteProvider();

        preMed = getContext().getSharedPreferences("typeBoton", getContext().MODE_PRIVATE);
        edtMed = preMed.edit();

        idrecyclerView = (RecyclerView) vista.findViewById(R.id.rcvlistaPacientes);
        pacienteArrayList = new ArrayList<>();
        pacienteAdapter = new PacienteAdapter(this, pacienteArrayList);
        idrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        idrecyclerView.setAdapter(pacienteAdapter);

        pacienteProvider.showPaciente(authProvider.getId(), pacienteAdapter, pacienteArrayList);


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMed.putString("boton", "agregar");
                edtMed.apply();
                Intent intent = new Intent(getContext(), PacienteActivity.class);
                startActivity(intent);
            }
        });
        return vista;
    }

    public void OpcionEditar(Paciente paciente) {
        edtMed.putString("boton", "modificar");
        edtMed.apply();
        Intent intent = new Intent(getContext(), PacienteActivity.class);
        intent.putExtra("paciente", paciente);
        startActivity(intent);
    }

    public void OpcionEliminar(Paciente paciente) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
        alerta.setTitle("Mensaje de Advertencia");
        alerta.setMessage("¿Esta seguro que desea eliminar este paciente?");
        alerta.setCancelable(false);
        alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pacienteProvider.removePaciente(authProvider.getId(), paciente, pacienteAdapter);
                Toast.makeText(getContext(), "Se elimino correctamente el paciente", Toast.LENGTH_SHORT).show();
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
