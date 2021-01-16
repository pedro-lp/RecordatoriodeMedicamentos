package com.recordatoriodemedicamentos.Notificacion;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.recordatoriodemedicamentos.Modelo.MedicamentoProvider;
import com.recordatoriodemedicamentos.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateNotification {
    private int alarmID = 1;

    public CreateNotification(Context context) {
        Toast.makeText(context, "El tamaño es; " + MedicamentoProvider.medicamentosList.size(), Toast.LENGTH_SHORT).show();

        if (MedicamentoProvider.medicamentosList.size() > 0) {
            String primerHora = MedicamentoProvider.medicamentosList.get(0).getPrimeraToma();
            String[] parts = primerHora.split(" ");
            String uno = parts[0]; // 123
            String[] parties = uno.split(":");
            String selectedHour = parties[0]; // 123
            String selectedMinute = parties[1]; // 654321

            Calendar today = Calendar.getInstance();
            //today.set(Calendar.HOUR_OF_DAY, selectedHour);
            //today.set(Calendar.MINUTE, selectedMinute);
            Toast.makeText(context,"Minutos "+selectedMinute, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Hora "+selectedHour, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, MedicamentoProvider.medicamentosList.get(0).getUltimaToma(), Toast.LENGTH_SHORT).show();
            ArrayList<Long> fecha = new ArrayList<>();
            today.set(Calendar.HOUR_OF_DAY, Integer.parseInt(selectedHour));
            today.set(Calendar.MINUTE, Integer.parseInt(selectedMinute));
            today.set(Calendar.SECOND, 0);
            fecha.add(today.getTimeInMillis());
            Utils.setAlarm(alarmID, today.getTimeInMillis(), context);

        }

        /*try {
            ArrayList<Long> fecha = new ArrayList<>();
            for (int i = 0; i < MedicamentoProvider.medicamentosList.size(); i++) {
                Calendar today = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                CharSequence hoy1 = DateFormat.format("yyyy-MM-dd", new Date().getTime());
                Date hoy = dateFormat.parse(hoy1.toString());
                Date ultimoDia = dateFormat.parse(MedicamentoProvider.medicamentosList.get(i).getUltimaToma());
                String primerHora = MedicamentoProvider.medicamentosList.get(i).getPrimeraToma();
                String[] parts = primerHora.split(":");
                String part1 = parts[0]; // 123
                String part2 = parts[1]; // 654321
                //Toast.makeText(context, "Hora: " + MedicamentoProvider.medicamentosList.size(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Minutos: " + MedicamentoProvider.medicamentosList.size(), Toast.LENGTH_SHORT).show();
                today.set(Calendar.HOUR_OF_DAY, Integer.parseInt(part1));
                today.set(Calendar.MINUTE, Integer.parseInt(part2));
                today.set(Calendar.SECOND, 0);
                fecha.add(today.getTimeInMillis());
                //Toast.makeText(context, MedicamentoProvider.medicamentosList.get(i).getNombre(), Toast.LENGTH_SHORT).show();
                Utils.setAlarm(i, fecha.get(i), context);
            }
            Toast.makeText(context, "fecha: " + fecha.size(), Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }
}
