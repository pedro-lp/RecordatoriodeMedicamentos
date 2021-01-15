package com.recordatoriodemedicamentos.Notificacion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.recordatoriodemedicamentos.Notificacion.NotificationService;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    //recibe la alarma donde hace un nuevo intent con ayuda de la clase NotificationService
    public void onReceive(Context context, Intent intent) {
        Intent service1 = new Intent(context, NotificationService.class);
        service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        ContextCompat.startForegroundService(context, service1 );
        Log.d("WALKIRIA", " ALARM RECEIVED!!!");
        Intent mIntent = new Intent(context, Recordatorios.class);
    }
}
