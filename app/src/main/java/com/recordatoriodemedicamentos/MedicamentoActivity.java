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
    EditText nombre, unidad, fechaInicio, fechaFinal, recordar,primeraToma,ultimoToma,existencia;
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
        fechaInicio = (EditText) findViewById(R.id.edtFechaInicio);
        fechaFinal = (EditText) findViewById(R.id.edtFechaFinal);
        recordar = (EditText) findViewById(R.id.edtRecordar);
        primeraToma = (EditText) findViewById(R.id.edtPrimeraToma);
        ultimoToma = (EditText) findViewById(R.id.edtUltimaToma);
        existencia = (EditText) findViewById(R.id.edtExistencia);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        unidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment comboDialog = new ComboDialog();
                comboDialog.setCancelable(false);
                comboDialog.show(getSupportFragmentManager(), "Unidad");
            }
        });


        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MedicamentoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = year + "-" + twoDigits(month) + "-" + twoDigits(day);
                        fechaInicio.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        fechaFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MedicamentoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = year + "-" + twoDigits(month) + "-" + twoDigits(day);
                        fechaFinal.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        recordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker numberPicker = new NumberPicker(MedicamentoActivity.this);
                numberPicker.setMaxValue(12);
                numberPicker.setMinValue(1);
                numberPicker.setValue(6);
                NumberPicker.OnValueChangeListener changeListener = new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        recordar.setText("" + newVal);

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


        primeraToma.setOnClickListener(new View.OnClickListener() {
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

                                    primeraToma.setText(f12Hour.format(date));
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

        ultimoToma.setOnClickListener(new View.OnClickListener() {
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

                                    ultimoToma.setText(f12Hour.format(date));
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
        fechaInicio.setText(medicamento.getFechaInicio());
        fechaFinal.setText(medicamento.getFechaFinal());
        recordar.setText(medicamento.getRecordar());
        primeraToma.setText(medicamento.getPrimeraToma());
        ultimoToma.setText(medicamento.getUltimaToma());
        existencia.setText(medicamento.getPrimeraToma());
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
        final String FechaInicio = fechaInicio.getText().toString();
        final String FechaFinal = fechaFinal.getText().toString();
        final String Recordar = recordar.getText().toString();
        final String PrimeraToma = primeraToma.getText().toString();
        final String UltimaToma = ultimoToma.getText().toString();
        final String Existencia = existencia.getText().toString();

        if (!Nombre.isEmpty() && !Unidad.isEmpty() && !FechaInicio.isEmpty() && !FechaFinal.isEmpty() && !Recordar.isEmpty() && !PrimeraToma.isEmpty() && !UltimaToma.isEmpty() && !Existencia.isEmpty()) {
            String idMedAgr = UUID.randomUUID().toString();
            Medicamento mAgr = new Medicamento(idMedAgr, Nombre, Unidad, FechaInicio, FechaFinal, Recordar, PrimeraToma, UltimaToma, Existencia);
            mediProvider.createMedicamento(authProvider.getId(), mAgr);
            finish();
        } else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void modificarMedicamento() {
        final String Nombre = nombre.getText().toString();
        final String Unidad = unidad.getText().toString();
        final String FechaInicio = fechaInicio.getText().toString();
        final String FechaFinal = fechaFinal.getText().toString();
        final String Recordar = recordar.getText().toString();
        final String PrimeraToma = primeraToma.getText().toString();
        final String UltimaToma = ultimoToma.getText().toString();
        final String Existencia = existencia.getText().toString();

        if (!Nombre.isEmpty() && !Unidad.isEmpty() && !FechaInicio.isEmpty() && !FechaFinal.isEmpty() && !Recordar.isEmpty() && !PrimeraToma.isEmpty() && !UltimaToma.isEmpty() && !Existencia.isEmpty()) {
            Medicamento mMod = new Medicamento(idMedMod, Nombre, Unidad, FechaInicio, FechaFinal, Recordar, PrimeraToma, UltimaToma, Existencia);
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
        unidad.setText("");
    }
}