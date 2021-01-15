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
        pacienteView.btnEditarPac.setOnClickListener(new eventoEditar(paciente));
        pacienteView.btnEliminarPac.setOnClickListener(new eventoEliminar(paciente));
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
            IPaciente.OpcionEliminar(paciente);
        }
    }

    public class medicamentoView extends RecyclerView.ViewHolder {
        private TextView nombre,unidad,duracion,recordar,priToma;
        private Button btnEditarMed,btnEliminarMed;

        public medicamentoView(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombreMed);
            unidad = itemView.findViewById(R.id.txtUnidadMed);
            duracion = itemView.findViewById(R.id.txtDuracionMed);
            recordar = itemView.findViewById(R.id.txtRecordarMed);
            priToma = itemView.findViewById(R.id.txtPriToma);
            btnEditarMed = itemView.findViewById(R.id.btnEditarContacto);
            btnEliminarMed = itemView.findViewById(R.id.btnEliminarContacto);
        }
    }
}

