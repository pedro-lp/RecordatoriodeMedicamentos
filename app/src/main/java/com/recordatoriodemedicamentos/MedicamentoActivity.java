package com.recordatoriodemedicamentos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.recordatoriodemedicamentos.Modelo.AuthProvider;
import com.recordatoriodemedicamentos.Modelo.Medicamento;
import com.recordatoriodemedicamentos.Modelo.MedicamentoProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class MedicamentoActivity extends AppCompatActivity implements ComboDialog.ComboListener {
    EditText nombre, unidad, duracion, recordar, priToma;
    AuthProvider authProvider;
    MedicamentoProvider mediProvider;
    Button btnAgregar;
    Button btnModificar;
    private Medicamento medicamento;
    String idMedMod;
    DatePickerDialog.OnDateSetListener setListener;
    int Hour, Minute;

    SharedPreferences preMed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);

        preMed = getApplicationContext().getSharedPreferences("typeBoton", MODE_PRIVATE);

        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnModificar = (Button) findViewById(R.id.btnModificar);

        authProvider = new AuthProvider();
        mediProvider = new MedicamentoProvider();

        nombre = (EditText) findViewById(R.id.edtNomMedicamento);
        unidad = (EditText) findViewById(R.id.edtUnidad);
        duracion = (EditText) findViewById(R.id.edtDuracion);
        recordar = (EditText) findViewById(R.id.edtRecordar);
        priToma = (EditText) findViewById(R.id.edtPriToma);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        unidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment comboDialog = new ComboDialog();
                comboDialog.setCancelable(false);
                comboDialog.show(getSupportFragmentManager(), "Single Choise Dialog");
            }
        });


        duracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MedicamentoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = year + "-" + twoDigits(month) + "-" + twoDigits(day);
                        duracion.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        recordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MedicamentoActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Hour = hourOfDay;
                                Minute = minute;

                                String time = Hour + ":" + Minute;
                                SimpleDateFormat f24Hour = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hour.parse(time);

                                    SimpleDateFormat f12Hour = new SimpleDateFormat("hh:mm aa");

                                    recordar.setText(f12Hour.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(Hour, Minute);
                timePickerDialog.show();
            }
        });

        priToma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker numberPicker = new NumberPicker(MedicamentoActivity.this);
                numberPicker.setMaxValue(12);
                numberPicker.setMinValue(1);
                numberPicker.setValue(6);
                NumberPicker.OnValueChangeListener changeListener = new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        priToma.setText("" + newVal);

                    }
                };
                numberPicker.setOnValueChangedListener(changeListener);
                AlertDialog.Builder builder = new AlertDialog.Builder(MedicamentoActivity.this).setView(numberPicker);
                builder.setTitle("Cada cuantas horas:");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });


        String typeUser = preMed.getString("boton", "");
        if (typeUser.equals("agregar")) {
            getSupportActionBar().setTitle("Añadir medicamento");
            btnAgregar.setVisibility(View.VISIBLE);
        } else if (typeUser.equals("modificar")) {
            getSupportActionBar().setTitle("Modificar medicamento");
            btnModificar.setVisibility(View.VISIBLE);
            medicamento = (Medicamento) getIntent().getSerializableExtra("medicamento");
            obtenerDatos();
        }
    }

    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

    private void obtenerDatos() {
        idMedMod = String.valueOf(medicamento.getId());
        nombre.setText(medicamento.getNombre());
        unidad.setText(medicamento.getUnidad());
        duracion.setText(medicamento.getDuracion());
        recordar.setText(medicamento.getRecordar());
        priToma.setText(medicamento.getPriToma());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAgregar:
                AgregarMedicamento();
                break;
            case R.id.btnModificar:
                modificarMedicamento();
                break;
        }
    }

    public void AgregarMedicamento() {
        final String Nombre = nombre.getText().toString();
        final String Unidad = unidad.getText().toString();
        final String Duracion = duracion.getText().toString();
        final String Recordar = recordar.getText().toString();
        final String PriToma = priToma.getText().toString();

        if (!Nombre.isEmpty() && !Unidad.isEmpty() && !Duracion.isEmpty() && !Recordar.isEmpty() && !PriToma.isEmpty()) {
            String idMedAgr = UUID.randomUUID().toString();
            Medicamento mAgr = new Medicamento(idMedAgr, Nombre, Unidad, Duracion, Recordar, PriToma);
            mediProvider.createMedicamento(authProvider.getId(), mAgr);
            finish();
        } else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void modificarMedicamento() {
        final String Nombre = nombre.getText().toString();
        final String Unidad = unidad.getText().toString();
        final String Duracion = duracion.getText().toString();
        final String Recordar = recordar.getText().toString();
        final String PriToma = priToma.getText().toString();

        if (!Nombre.isEmpty() && !Unidad.isEmpty() && !Duracion.isEmpty() && !Recordar.isEmpty() && !PriToma.isEmpty()) {
            Medicamento mMod = new Medicamento(idMedMod, Nombre, Unidad, Duracion, Recordar, PriToma);
            mediProvider.changeMedicamento(authProvider.getId(), mMod);
            finish();
        } else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        unidad.setText(list[position]);
    }

    @Override
    public void onNegativeButtonClicked() {
        unidad.setText("Dialog Cancel");

    }
}