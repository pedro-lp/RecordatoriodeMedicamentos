package com.recordatoriodemedicamentos.Notificacion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.recordatoriodemedicamentos.Modelo.MedicamentoProvider;
import com.recordatoriodemedicamentos.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Recordatorios extends AppCompatActivity {
    private TextView notificationsTime;
    private int alarmID = 1;
    private SharedPreferences settings;
    //public static Context this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorios);
/*
        //se crean las configuraciones de la notificacion
        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String hour = settings.getString("hour", "");
        String minute = settings.getString("minute", "");
        notificationsTime = (TextView) findViewById(R.id.notifications_time);

        if (hour.length() > 0) {
            notificationsTime.setText(hour + ":" + minute);
        }
        //accion que se realiza al hacer clic en el picker
        findViewById(R.id.change_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Recordatorios.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String finalHour, finalMinute;
                        selectedHour = 13;
                        selectedMinute = 00;

                        finalHour = "" + selectedHour;
                        finalMinute = "" + selectedMinute;

                        if (selectedHour < 10) finalHour = "0" + selectedHour;
                        if (selectedMinute < 10) finalMinute = "0" + selectedMinute;
                        notificationsTime.setText(finalHour + ":" + finalMinute);

                        Calendar today = Calendar.getInstance();

                        //today.set(Calendar.HOUR_OF_DAY, selectedHour);
                        //today.set(Calendar.MINUTE, selectedMinute);

                        ArrayList<Long> fecha = new ArrayList<>();
                        for (int i = 0; i < MedicamentoProvider.medicamentosList.size(); i++) {
                            today.set(Calendar.HOUR_OF_DAY, 17);
                            today.set(Calendar.MINUTE, 31+(i*2));
                            today.set(Calendar.SECOND, 0);
                            fecha.add(today.getTimeInMillis());
                        }

                        //se asigna la hora
                        SharedPreferences.Editor edit = settings.edit();
                        edit.putString("hour", finalHour);
                        edit.putString("minute", finalMinute);
                        //SharedPreferences.Editor edit = settings.edit();
                        //edit.putString("hour", finalHour);
                        //edit.putString("minute", finalMinute);

                        //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
                        //guarda la notificacion en caso de reiniciarse
                        edit.putInt("alarmID", alarmID);
                        edit.putLong("alarmTime", today.getTimeInMillis());

                        edit.commit();

                        Toast.makeText(Recordatorios.this, getString(R.string.changed_to, finalHour + ":" + finalMinute), Toast.LENGTH_LONG).show();

                        //Utils.setAlarm(alarmID, today.getTimeInMillis(), Recordatorios.this);
                        //crea la alarma
                        //Utils.setAlarm(alarmID, today.getTimeInMillis(), Recordatorios.this);

                        for (int i = 0; i < MedicamentoProvider.medicamentosList.size(); i++) {
                            Toast.makeText(Recordatorios.this, MedicamentoProvider.medicamentosList.get(i).getNombre(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(Recordatorios.this, MedicamentoProvider.medicamentosList.get(i).getPrimeraToma(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(Recordatorios.this, MedicamentoProvider.medicamentosList.get(i).getUltimaToma(), Toast.LENGTH_SHORT).show();
                            Utils.setAlarm(alarmID, fecha.get(i), Recordatorios.this);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();

            }
        });*/

        //accion que realizar el boton detener la notificacion
        findViewById(R.id.stop_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationService.mp.stop();
                try {
                    //AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    //long updateInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                    CharSequence hoy1 = DateFormat.format("yyyy-MM-dd", new Date().getTime());
                    Toast.makeText(Recordatorios.this, "Hoy es;" + hoy1, Toast.LENGTH_LONG).show();
                    CharSequence ultimoDia1 = ("2021-01-15");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date hoy = dateFormat.parse(hoy1.toString());
                    Date ultimoDia = dateFormat.parse(ultimoDia1.toString());

                    Toast.makeText(Recordatorios.this, "Se desactivo la alarma", Toast.LENGTH_LONG).show();

                    if (ultimoDia.before(hoy)) {
                    } else {
                        Toast.makeText(Recordatorios.this, "El ultimo dia de tomar el medicamento es;" + ultimoDia, Toast.LENGTH_LONG).show();
                        Utils.setAlarm(alarmID, System.currentTimeMillis() + (5 * 60 * 1000), getBaseContext());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}