package com.recordatoriodemedicamentos.Modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.recordatoriodemedicamentos.R;

import java.util.ArrayList;
import java.util.List;

public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.pacienteView> {

    private List<Paciente> pacienteList = new ArrayList<>();
    private Context context;
    private ArrayList<Paciente> pacienteArrayList;
    private IPaciente iPaciente;

    public PacienteAdapter(IPaciente iPaciente, ArrayList<Paciente> pacienteList) {
        this.iPaciente = iPaciente;
        this.pacienteList = pacienteList;
        this.context = context;
        this.pacienteArrayList = pacienteList;
    }

    @NonNull
    @Override
    public pacienteView onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_pacientes, viewGroup, false);
        return new pacienteView(view);

    }

    @Override
    public void onBindViewHolder(pacienteView pacienteView, int i) {
        Paciente paciente = pacienteList.get(i);
        pacienteView.nombre.setText(paciente.getNombre());
        pacienteView.edad.setText(paciente.getEdad());
        pacienteView.btnAdministrar.setOnClickListener(new eventoEditar(paciente));
        pacienteView.btnEliminar.setOnClickListener(new eventoEliminar(paciente));
    }

    @Override
    public int getItemCount() {
        return pacienteList.size();
    }

    public void agregarPaciente(Paciente paciente) {
        pacienteList.add(paciente);
        this.notifyDataSetChanged();
    }

    public void eliminarPaciente(Paciente paciente) {
        pacienteList.remove(paciente);
        this.notifyDataSetChanged();
    }


    class eventoEditar implements View.OnClickListener {
        private Paciente paciente;
        public eventoEditar(Paciente paciente) {
            this.paciente = paciente;
        }
        @Override
        public void onClick(View v) {
            iPaciente.OpcionEditar(paciente);
        }
    }

    class eventoEliminar implements View.OnClickListener {
        private Paciente paciente;
        public eventoEliminar(Paciente paciente) {
            this.paciente = paciente;
        }
        @Override
        public void onClick(View v) {
            iPaciente.OpcionEliminar(paciente);
        }
    }

    public class pacienteView extends RecyclerView.ViewHolder {
        private TextView nombre,edad;
        private Button btnAdministrar,btnEliminar;

        public pacienteView(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombre);
            edad = itemView.findViewById(R.id.textEdad);
            btnAdministrar = itemView.findViewById(R.id.btnAdministrar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}

