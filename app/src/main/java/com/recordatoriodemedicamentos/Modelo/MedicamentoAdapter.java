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

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.medicamentoView> {

    private List<Medicamento> medicamentoList = new ArrayList<>();
    private Context context;
    private ArrayList<Medicamento> medicamentoArrayList;
    private IMedicamento iMedicamento;

    public MedicamentoAdapter(IMedicamento iMedicamento, ArrayList<Medicamento> medicamentoList) {
        this.iMedicamento = iMedicamento;
        this.medicamentoList = medicamentoList;
        this.context = context;
        this.medicamentoArrayList = medicamentoList;
    }

    @NonNull
    @Override
    public medicamentoView onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_medicamentos, viewGroup, false);
        return new medicamentoView(view);

    }

    @Override
    public void onBindViewHolder(medicamentoView medicamentoView, int i) {
        Medicamento medicamento = medicamentoList.get(i);
        medicamentoView.nombre.setText(medicamento.getNombre());
        medicamentoView.unidad.setText(medicamento.getUnidad());
        medicamentoView.duracion.setText(medicamento.getDuracion());
        medicamentoView.recordar.setText(medicamento.getRecordar());
        medicamentoView.priToma.setText(medicamento.getPriToma());
        medicamentoView.btnEditarMed.setOnClickListener(new eventoEditar(medicamento));
        medicamentoView.btnEliminarMed.setOnClickListener(new eventoEliminar(medicamento));
    }

    @Override
    public int getItemCount() {
        return medicamentoList.size();
    }

    public void agregarMedicamento(Medicamento medicamento) {
        medicamentoList.add(medicamento);
        this.notifyDataSetChanged();
    }

    public void eliminarMedicamento(Medicamento medicamento) {
        medicamentoList.remove(medicamento);
        this.notifyDataSetChanged();
    }


    class eventoEditar implements View.OnClickListener {
        private Medicamento medicamento;

        public eventoEditar(Medicamento medicamento) {
            this.medicamento = medicamento;
        }

        @Override
        public void onClick(View v) {
            iMedicamento.OpcionEditar(medicamento);
        }
    }

    class eventoEliminar implements View.OnClickListener {
        private Medicamento medicamento;

        public eventoEliminar(Medicamento medicamento) {
            this.medicamento = medicamento;
        }

        @Override
        public void onClick(View v) {
            iMedicamento.OpcionEliminar(medicamento);
        }
    }

    public class medicamentoView extends RecyclerView.ViewHolder {
        private TextView nombre, unidad, duracion, recordar, priToma;
        private Button btnEditarMed, btnEliminarMed;

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