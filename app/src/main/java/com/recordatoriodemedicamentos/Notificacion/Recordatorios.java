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

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Recordatorios extends AppCompatActivity {
    private TextView notificationsTime;
    private int alarmID = 1;
    private SharedPreferences settings;
    //public static Context this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorios);


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

                        String primerHora = MedicamentoProvider.medicamentosList.get(0).getPrimeraToma();
                        String[] parts = primerHora.split(" ");
                        String uno = parts[0]; // 123
                        String[] parties = uno.split(":");
                        selectedHour = Integer.parseInt(parties[0]); // 123
                        selectedMinute = Integer.parseInt(parties[1]); // 654321

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
                            today.set(Calendar.HOUR_OF_DAY, selectedHour);
                            today.set(Calendar.MINUTE, selectedMinute);
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

                        //Toast.makeText(Recordatorios.this, getString(R.string.changed_to, finalHour + ":" + finalMinute), Toast.LENGTH_LONG).show();

                        //Utils.setAlarm(alarmID, today.getTimeInMillis(), Recordatorios.this);
                        //crea la alarma
                        //Utils.setAlarm(alarmID, today.getTimeInMillis(), Recordatorios.this);

                        for (int i = 0; i < MedicamentoProvider.medicamentosList.size(); i++) {
                            Toast.makeText(Recordatorios.this, MedicamentoProvider.medicamentosList.get(i).getNombre(), Toast.LENGTH_SHORT).show();

                            Utils.setAlarm(alarmID, fecha.get(i), Recordatorios.this);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();

            }
        });

        //accion que realizar el boton detener la notificacion
        findViewById(R.id.stop_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Recordatorios.this, "Se desactivo la alarma", Toast.LENGTH_LONG).show();
                NotificationService.mp.stop();
                try {
                    //AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    //long updateInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                    CharSequence hoy1 = DateFormat.format("yyyy-MM-dd", new Date().getTime());
                    CharSequence ultimoDia1 = (MedicamentoProvider.medicamentosList.get(0).getFechaFinal());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date hoy = dateFormat.parse(hoy1.toString());
                    Date ultimoDia = dateFormat.parse(ultimoDia1.toString());

                    if (hoy.before(ultimoDia) || hoy.equals(ultimoDia)) {
                        sigAlarma(Recordatorios.this);

                        //int horaRep = Integer.parseInt(MedicamentoProvider.medicamentosList.get(0).getRecordar());
                        //Utils.setAlarm(alarmID, System.currentTimeMillis() + (horaRep * 60 * 60 * 1000), Recordatorios.this);

                        //Toast.makeText(Recordatorios.this, "Ultimo dia; " + ultimoDia, Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void sigAlarma(Context context) {
        int alarmID = 1;
        int horaRep = Integer.parseInt(MedicamentoProvider.medicamentosList.get(0).getRecordar());
        Long nvaHora = System.currentTimeMillis() + (horaRep * 60 * 60 * 1000);
        Utils.setAlarm(alarmID, nvaHora, context);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(nvaHora);


        Toast.makeText(context, "Proxima alarma; " + resultdate, Toast.LENGTH_LONG).show();
        //se cambia (horaRep * 60 * 60 * 1000)
    }
}