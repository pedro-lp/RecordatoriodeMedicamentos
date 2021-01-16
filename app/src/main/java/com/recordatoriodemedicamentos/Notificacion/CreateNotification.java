package com.recordatoriodemedicamentos.Notificacion;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.recordatoriodemedicamentos.Modelo.MedicamentoProvider;
import com.recordatoriodemedicamentos.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateNotification {
    private int alarmID = 1;
    private SharedPreferences settings;

    public CreateNotification(Context context) {
        Toast.makeText(context, ("se realizo el iniciarNot"), Toast.LENGTH_SHORT).show();
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        String finalHour, finalMinute;
        int selectedHour = 13;
        int selectedMinute = 00;

        finalHour = "" + selectedHour;
        finalMinute = "" + selectedMinute;

        if (selectedHour < 10) finalHour = "0" + selectedHour;
        if (selectedMinute < 10) finalMinute = "0" + selectedMinute;

        Calendar today = Calendar.getInstance();

        //today.set(Calendar.HOUR_OF_DAY, selectedHour);
        //today.set(Calendar.MINUTE, selectedMinute);

        ArrayList<Long> fecha = new ArrayList<>();
        for (int i = 0; i < MedicamentoProvider.medicamentosList.size(); i++) {
            today.set(Calendar.HOUR_OF_DAY, 17);
            today.set(Calendar.MINUTE, 31 + (i * 2));
            today.set(Calendar.SECOND, 0);
            fecha.add(today.getTimeInMillis());
        }

        //SharedPreferences.Editor edit = settings.edit();
        //edit.putString("hour", finalHour);
        //edit.putString("minute", finalMinute);

        //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
        //guarda la notificacion en caso de reiniciarse

        Toast.makeText(context, (finalHour + ":" + finalMinute), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "El tamaño de los medicamentos es"+MedicamentoProvider.medicamentosList.size(), Toast.LENGTH_SHORT).show();

        //Utils.setAlarm(alarmID, today.getTimeInMillis(), Recordatorios.this);
        //crea la alarma
        //Utils.setAlarm(alarmID, today.getTimeInMillis(), Recordatorios.this);

        for (int i = 0; i < MedicamentoProvider.medicamentosList.size(); i++) {
            Toast.makeText(context, MedicamentoProvider.medicamentosList.get(i).getNombre(), Toast.LENGTH_SHORT).show();
            Toast.makeText(context, MedicamentoProvider.medicamentosList.get(i).getPrimeraToma(), Toast.LENGTH_SHORT).show();
            Toast.makeText(context, MedicamentoProvider.medicamentosList.get(i).getUltimaToma(), Toast.LENGTH_SHORT).show();
            Utils.setAlarm(alarmID, fecha.get(i), context);
        }
    }
}
