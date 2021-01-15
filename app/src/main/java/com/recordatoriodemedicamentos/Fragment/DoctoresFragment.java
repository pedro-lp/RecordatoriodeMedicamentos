package com.recordatoriodemedicamentos.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.recordatoriodemedicamentos.R;

public class DoctoresFragment extends Fragment implements OnMapReadyCallback {
    View vista;
    private GoogleMap mMap;
    private MapView mapView;
    private Marker mMarker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_doctores, container,false);

        mapView = (MapView) vista.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        return vista;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng centro = new LatLng(17.545981, -99.506910);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centro,13),5000,null);


        LatLng punto = new LatLng(17.5514605,-99.5082644);
        mMarker = mMap.addMarker(new MarkerOptions().position(punto).title("Doctor Calixto").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker)));

        LatLng punto1 = new LatLng(17.527076, -99.496730);
        mMarker = mMap.addMarker(new MarkerOptions().position(punto1).title("Doctor Wilfrido").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker)));

        LatLng punto2 = new LatLng(17.561464, -99.506948);
        mMarker = mMap.addMarker(new MarkerOptions().position(punto2).title("Doctor Ricardo").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker)));

        LatLng punto3 = new LatLng(17.552258, -99.509951);
        mMarker = mMap.addMarker(new MarkerOptions().position(punto3).title("Doctor Peralta").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker)));
    }
}
