package com.app.orarmd;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.app.orarmd.Adapter.InfoAdapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.orarmd.Adapter.InfoAdapter;
import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private int currentApiVersion;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    Dialog myDialog;


    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        currentApiVersion = android.os.Build.VERSION.SDK_INT;


        final int flags =
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {

            getWindow().getDecorView().setSystemUiVisibility(flags);


            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
        myDialog = new Dialog(this);
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    //Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });


    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setInfoWindowAdapter(new InfoAdapter(MapsActivity.this));


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*Tr 8 tur*/
        LatLng traian_centru = new LatLng(46.97859760804641, 28.84606596082449);
        LatLng independ_centru = new LatLng(46.98175311217573, 28.850840628147125);
        LatLng dacia = new LatLng(46.985063046155446, 28.855952247977253);
        LatLng mcdecebal = new LatLng(46.987538443644446, 28.857862651348114);
        LatLng decebalxzelinski_centru = new LatLng(46.993662518469655, 28.85923426598311);
        LatLng decebalxtitulescu_centru = new LatLng(47.001066822185265, 28.860127106308937);
        LatLng decebalhidrotehnica = new LatLng(47.00766397435147, 28.8601291179657);
        LatLng garafer_centru = new LatLng(47.011060318886145, 28.858664967119694);
        LatLng hotcosmos = new LatLng(47.013662985215596, 28.853825591504577);
        LatLng stefanxismail_buiucani = new LatLng(47.01639993437067, 28.844954185187817);
        LatLng stefanxarmeneasca_buiucani = new LatLng(47.02022292467685, 28.83909020572901);
        LatLng mcpman = new LatLng(47.02310672971784, 28.834867402911186);
        LatLng stefanbiesu = new LatLng(47.02697014891067, 28.829255886375904);
        LatLng stefanutm = new LatLng(47.031353410640584, 28.82262445986271);
        LatLng stefanusmf_buiucani = new LatLng(47.034483634470554, 28.81795272231102);
        LatLng stefanxcaleaies = new LatLng(47.03810288399263, 28.81043717265129);
        LatLng zorile = new LatLng(47.038835661308, 28.804058209061626);
        LatLng barsculeni_buiucani = new LatLng(47.04283434143836, 28.794257752597332);
        LatLng laizvor_buiucani = new LatLng(47.04468792195574, 28.788279443979263);
        LatLng ghidighici_buiucani = new LatLng(47.04613957803248, 28.783253990113735);

        /*Tr 8 retur */
        LatLng butoias = new LatLng(47.04774037486963, 28.778744861483574);
        LatLng ghidighici_centru = new LatLng(47.045350470548755, 28.785113096237183);
        LatLng laizvor_centru = new LatLng(47.044216365117045, 28.78889568150043);
        LatLng barsculeni_centru = new LatLng(47.04224144710399, 28.794641979038712);
        LatLng alunelul = new LatLng(47.03814172806925, 28.805155567824837);
        LatLng dmcantemir = new LatLng(47.03795916066333, 28.809921853244308);
        LatLng stefanusmf_centru = new LatLng(47.03342973525378, 28.819257281720635);
        LatLng tomaciorba = new LatLng(47.03185181818262, 28.82156364619732);
        LatLng serlazo = new LatLng(47.029061727250955, 28.8256761431694);
        LatLng pman = new LatLng(47.02630622934, 28.82987011224031);
        LatLng teatrueminescu = new LatLng(47.02222105678275, 28.835914470255375);
        LatLng stefanxarmeneasca_botanica = new LatLng(47.01952577794241, 28.839893527328968);
        LatLng stefanxismail_botanica = new LatLng(47.016821905364, 28.843929581344128);
        LatLng bdnegruzzi = new LatLng(47.01356880222524, 28.85040041059256);
        LatLng garafer_botanica = new LatLng(47.011009795957165, 28.85839708149433);
        LatLng bddecebal = new LatLng(47.00886721400324, 28.860057033598423);
        LatLng decebalxtitulescu_botanica = new LatLng(46.999811501353506, 28.859749920666218);
        LatLng piatadecebal = new LatLng(46.997220965554604, 28.85949645191431);
        LatLng decebalxzelinski_botanica = new LatLng(46.99207702261247, 28.85875180363655);
        LatLng brancusi = new LatLng(46.98918262241333, 28.85809801518917);
        LatLng traian_botanica = new LatLng(46.98550424846652, 28.856303282082084);
        LatLng independ_botanica = new LatLng(46.98135511032011, 28.8499266654253);
        LatLng grenobleterminus8 = new LatLng(46.97875910484173, 28.845511078834537);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*33 TUR*/
        LatLng ismailstart = new LatLng(47.018159, 28.849770);
        LatLng dmitriecantemirsangera = new LatLng(47.0189671, 28.8515568);
        LatLng cosmossangera = new LatLng(47.0146002, 28.8548398);
        LatLng traiansangera = new LatLng(46.9854199, 28.8579726);
        LatLng cuzavodasangera = new LatLng(46.982397, 28.8623071);
        LatLng independsangera = new LatLng(46.9792056, 28.8667703);
        LatLng valeacruciisangera = new LatLng(46.9767607, 28.8704288);
        LatLng instplantesangera = new LatLng(46.9642417, 28.8904595);
        LatLng bacioisangera = new LatLng(46.9495372, 28.9139771);
        LatLng galatasangera = new LatLng(46.9417659, 28.9262509);
        LatLng expoaeroportsangera = new LatLng(46.9380154, 28.9325166);
        LatLng bazamilitarasangera = new LatLng(46.9305721, 28.9598644);
        LatLng muncestisangera = new LatLng(46.9287038, 28.962847);
        LatLng viilorsangera = new LatLng(46.9239265, 28.9675248);
        LatLng centrusangera = new LatLng(46.9192074, 28.9726853);
        LatLng chisinauluisangera = new LatLng(46.9174633, 28.9812577);
        LatLng tineretuluisangera = new LatLng(46.9139236, 28.9909887);
        LatLng tineretului2sangera = new LatLng(46.9139089, 28.9901626);
        LatLng tineretuluideal = new LatLng(46.902592, 28.9869547);
        LatLng bazametalsangera = new LatLng(46.9141068, 29.0046895);
        /*33 RETUR*/
        LatLng dobrogeastart = new LatLng(46.8834783, 28.9838219);
        LatLng tineretuluiorasdeal = new LatLng(46.9030831, 28.9869547);
        LatLng dobrogea = new LatLng(46.8836103, 28.9911497);
        LatLng dobrogeascentru = new LatLng(46.8868734, 28.9913857);
        LatLng dobrogeacentru2 = new LatLng(46.8902462, 28.9947546);
        LatLng mucava = new LatLng(46.9037722, 29.019388);
        LatLng bazametaloras = new LatLng(46.9137697, 29.0039492);
        LatLng tineretuluioras = new LatLng(46.9144732, 28.9904094);
        LatLng mioritaoras = new LatLng(46.9176318, 28.9809787);
        LatLng viilororas = new LatLng(46.9238752, 28.9682114);
        LatLng muncestioras = new LatLng(46.9285499, 28.9636302);
        LatLng zonaexpooras = new LatLng(46.9385574, 28.9330959);
        LatLng galataoras = new LatLng(46.9421541, 28.9263904);
        LatLng bacioioras = new LatLng(46.9503208, 28.9134085);
        LatLng instplantoras = new LatLng(46.9649665, 28.8901484);
        LatLng valeacruciioras = new LatLng(46.9774195, 28.8704181);
        LatLng burebistaoras = new LatLng(46.9791837, 28.8675535);
        LatLng cuzavodaoras = new LatLng(46.9834876, 28.8614595);
        LatLng ismailoras = new LatLng(47.0167801, 28.8476837);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*Tr 17 TUR*/
        LatLng schinoasa_start = new LatLng(46.9849514, 28.7782466);
        LatLng garasudvest = new LatLng(46.9916334, 28.7895441);
        LatLng ialoveni = new LatLng(46.9936385, 28.7950695);
        LatLng sihastrului = new LatLng(46.9957021, 28.801024);
        LatLng miorita = new LatLng(46.9970339, 28.8088775);
        LatLng campusacad = new LatLng(46.993741, 28.8150465);
        LatLng grenoble = new LatLng(46.993741, 28.8150465);
        LatLng polonco = new LatLng(46.9897013, 28.8258612);
        LatLng spitalcopii = new LatLng(46.9856467, 28.8320518);
        LatLng costiujeni = new LatLng(46.9812259, 28.8397658);
        LatLng decebal = new LatLng(46.9871837, 28.8564384);
        LatLng teilor = new LatLng(46.9897379, 28.8529193);
        LatLng zelinski = new LatLng(46.9926506, 28.8487458);
        LatLng spitalmunicipalnr1 = new LatLng(47.0048484, 28.8421476);
        LatLng bucuresti = new LatLng(47.0120692, 28.8424051);
        LatLng ismail = new LatLng(47.0152293, 28.845098);
        /*Tr 17 RETUR*/
        LatLng petreungureanu = new LatLng(46.9815845, 28.8395298);
        LatLng costiujeniretur = new LatLng(46.9830338, 28.8364506);
        LatLng spitalcopiiretur = new LatLng(46.9864665, 28.8314939);
        LatLng oncologicaretur = new LatLng(46.9900672, 28.8253355);
        LatLng mioritaretur = new LatLng(46.9926579, 28.8183832);
        LatLng campusretur = new LatLng(46.9952118, 28.8121498);
        LatLng pietrarilorretur = new LatLng(46.9981974, 28.8065922);
        LatLng sihastruluiretur = new LatLng(46.9963973, 28.8015068);
        LatLng ialoveniretur = new LatLng(46.9944947, 28.7965715);
        LatLng gararetur = new LatLng(46.9925847, 28.7912285);
        LatLng valearadiului = new LatLng(46.9875936, 28.7807465);
        LatLng schinoasaterminus = new LatLng(46.9851783, 28.7778711);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*Tr 1 TUR*/
        LatLng aschim = new LatLng(47.0266614, 28.7736547);
        LatLng mecanexp = new LatLng(47.0257838, 28.7778175);
        LatLng veterinar = new LatLng(47.0247818, 28.7824202);
        LatLng vasilelupu = new LatLng(47.0234727, 28.7898445);
        LatLng constitutiei = new LatLng(47.0243503, 28.7925804);
        LatLng ioncreanga = new LatLng(47.0282557, 28.7964964);
        LatLng stefaneaga = new LatLng(47.0325337, 28.8055193);
        LatLng universitateapedagogica = new LatLng(47.0371185, 28.8120532);
        LatLng zelinskitur = new LatLng(46.9925262, 28.8603115);

        /*Tr 1 RETUR*/
        LatLng sarmizegetusa = new LatLng(46.9934117, 28.8651395);
        LatLng minsk = new LatLng(46.9973047, 28.8610733);
        LatLng univpedagogicaretur = new LatLng(47.0371039, 28.8116992);
        LatLng stefaneagaretur = new LatLng(47.0325337, 28.8052404);
        LatLng doinateodor = new LatLng(47.0283434, 28.796196);
        LatLng constitutieiretur = new LatLng(47.0256375, 28.7927091);
        LatLng vasilelupuretur = new LatLng(47.0235604, 28.7907672);
        LatLng vetretur = new LatLng(47.0249793, 28.7821627);
        LatLng uzinamecanicaexp = new LatLng(47.0260836, 28.7769699);
        LatLng aschimterminus = new LatLng(47.0267565, 28.7739444);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        LatLng zelinskixdecebal1 = new LatLng(46.99269860296773, 28.85949980467558);
        LatLng zelinskixdecebal4 = new LatLng(46.99296571106882, 28.85805644094944);

        float zoomLevel = 18.0f; //This goes up to 21
        UiSettings uiSettings = googleMap.getUiSettings();

        uiSettings.setCompassEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        googleMap.setMyLocationEnabled(true);


        //ID_statie+troleibuze +autobuze +maxitaxi

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        //MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        //googleMap.addMarker(markerOptions);

        /*Statii*/
        /*Tr 8 tur*/
        googleMap.addMarker(new MarkerOptions().position(traian_centru).title("Stația bd. Traian")
                .snippet("08+8,17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(independ_centru).title("Stația str. Independenţei")
                .snippet("18+8,17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(dacia).title("Stația bd. Dacia")
                .snippet("28+8,17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(mcdecebal).title("Stația bd. Decebal")
                .snippet("38+8 +33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(decebalxzelinski_centru).title("Stația str. Nicolae Zelinski")
                .snippet("48+8 +33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(decebalxtitulescu_centru).title("Stația str. Nicolae Titulescu")
                .snippet("58+1,8 +33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(decebalhidrotehnica).title("Stația Centru Hidrotehnica")
                .snippet("68+1,8 +33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(garafer_centru).title("Stația Gara")
                .snippet("78+1,8 +33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(hotcosmos).title("Stația Hotelul COSMOS")
                .snippet("88+1,8 +33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(stefanxismail_buiucani).title("Stația str. Ismail")
                .snippet("98+1,8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(stefanxarmeneasca_buiucani).title("Stația str. Armenească")
                .snippet("108+1,8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(mcpman).title("Stația Piaţa Marii Adunări Naţionale")
                .snippet("118+1,8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(stefanbiesu).title("Stația Teatrul Naţional de Operă şi Balet \"Maria Bieşu\"")
                .snippet("128+1,8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(stefanutm).title("Stația Universitatea Tehnică")
                .snippet("138+1,8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(stefanusmf_buiucani).title("Stația Universitatea de Medicină")
                .snippet("148+1,8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(stefanxcaleaies).title("Stația Calea Ieşilor")
                .snippet("158+8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(zorile).title("Stația Asociaţia \"Zorile\"")
                .snippet("168+8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(barsculeni_buiucani).title("Stația str. Bariera Sculeni")
                .snippet("178+8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(laizvor_buiucani).title("Stația Parcul \"La izvor\"")
                .snippet("188+8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(ghidighici_buiucani).title("Stația str. Ghidighici")
                .snippet("198+8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        /*Tr 8 retur */

        googleMap.addMarker(new MarkerOptions().position(butoias).title("Stația Butoiaș")
                .snippet("80+8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(ghidighici_centru).title("Stația str. Ghidighici")
                .snippet("81+8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(laizvor_centru).title("Stația str. La-Izvor")
                .snippet("82+8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(barsculeni_centru).title("Stația str. Bariera Sculeni")
                .snippet("83+8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(alunelul).title("Stația Parcul \"Alunelul\"")
                .snippet("84+8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(dmcantemir).title("Stația Piaţa Dimitrie Cantemir")
                .snippet("85+8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(stefanusmf_centru).title("Stația Universitatea de Medicină")
                .snippet("86+1,8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(tomaciorba).title("Stația str. Toma Ciorbă")
                .snippet("87+1,8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(serlazo).title("Stația str. Serghei Lazo")
                .snippet("888+1,8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(pman).title("Stația Piaţa Marii Adunari Naţionale")
                .snippet("89+1,8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(teatrueminescu).title("Stația Teatrul Naţional \"Mihai Eminescu\"")
                .snippet("810+1,8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(stefanxarmeneasca_botanica).title("Stația str. Armenească")
                .snippet("811+1,8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(stefanxismail_botanica).title("Stația str. Ismail")
                .snippet("812+1,8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(bdnegruzzi).title("Stația bd. C. Negruzzi")
                .snippet("813+1,8 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(garafer_botanica).title("Stația Gara")
                .snippet("814+1,8,17 +33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(bddecebal).title("Stația bd. Decebal")
                .snippet("815+1,8,17 +33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(decebalxtitulescu_botanica).title("Stația str. N. Titulescu")
                .snippet("816+1,8,17 +33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(piatadecebal).title("Stația Piaţa Decebal")
                .snippet("817+1,8,17 +33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(decebalxzelinski_botanica).title("Stația str. N. Zelinski")
                .snippet("818+8,17 +33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(brancusi).title("Stația str. Constantin Brâncuşi")
                .snippet("819+8,17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(traian_botanica).title("Stația bd. Traian")
                .snippet("820+8,17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(independ_botanica).title("Stația str. Independenţei")
                .snippet("821+8,17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(grenobleterminus8).title("Stația str. Grenoble")
                .snippet("822+8,17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        /*Tr 8 */
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*Aut 33 TUR*/
        googleMap.addMarker(new MarkerOptions().position(ismailstart).title("Stația str. Ismail")
                .snippet("033++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(dmitriecantemirsangera).title("Stația bd. Dimitrie Cantemir")
                .snippet("133+17 +33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(cosmossangera).title("Stația Hotelul Cosmos")
                .snippet("233+17 +33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(traiansangera).title("Stația bd. Traian")
                .snippet("333++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(cuzavodasangera).title("Stația bd. Cuza Vodă")
                .snippet("433++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(independsangera).title("Stația str. Independenței")
                .snippet("533++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(valeacruciisangera).title("Stația str. Valea Crucii")
                .snippet("633++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(instplantesangera).title("Stația Institutul de protecție a plantelor")
                .snippet("733++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(bacioisangera).title("Stația Răscrucea spre Băcioi")
                .snippet("833++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(galatasangera).title("Stația Cartierul Galata")
                .snippet("933++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(expoaeroportsangera).title("Stația Zona liberă \"Expo-Business\" ")
                .snippet("1033++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(bazamilitarasangera).title("Stația Fosta baza militară ")
                .snippet("+++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(muncestisangera).title("Stația șos. Muncești, 801")
                .snippet("1133++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(viilorsangera).title("Stația or. Sângera, str. Viilor")
                .snippet("1233++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(centrusangera).title("Stația or. Sângera, Centru")
                .snippet("1333++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(chisinauluisangera).title("Stația or. Sângera, str. Chișinăului")
                .snippet("+++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(tineretuluisangera).title("Stația or. Sângera, str. Tineretului")
                .snippet("1633++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(tineretului2sangera).title("Stația or. Sângera, str. Tineretului")
                .snippet("1633++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(tineretuluideal).title("Stația or. Sângera, str. Tineretului")
                .snippet("1433++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(bazametalsangera).title("Stația Baza de aprovizionare cu metale")
                .snippet("1533++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        /*Aut 33 RETUR*/
        googleMap.addMarker(new MarkerOptions().position(dobrogeastart).title("Stația s. Dobrogea")
                .snippet("330++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(dobrogeascentru).title("Stația s. Dobrogea Centru")
                .snippet("331++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(dobrogea).title("Stația s. Dobrogea")
                .snippet("+++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(dobrogeacentru2).title("Stația s. Dobrogea")
                .snippet("+++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(mucava).title("Stația Fabrica de mucava")
                .snippet("332++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(bazametaloras).title("Stația Baza de aprovizionare cu metale")
                .snippet("3333++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(tineretuluiorasdeal).title("Stația or. Sângera, str. Tineretului")
                .snippet("334++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(tineretuluioras).title("Stația or. Sângera, str. Tineretului")
                .snippet("335++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(mioritaoras).title("Stația or. Sângera, str. Miorița")
                .snippet("+++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(viilororas).title("Stația or. Sângera, str. Viilor")
                .snippet("336++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(muncestioras).title("Stația șos. Muncești, 801")
                .snippet("337++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(zonaexpooras).title("Stația Zona liberă \"Expo-Business\" ")
                .snippet("338++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(galataoras).title("Stația Cartierul Galata")
                .snippet("339++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(bacioioras).title("Stația Răscrucea spre Băcioi")
                .snippet("3310++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(instplantoras).title("Stația Institutul de protecție a plantelor")
                .snippet("3311++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(valeacruciioras).title("Stația str. Valea Crucii")
                .snippet("3312++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(burebistaoras).title("Stația str. Burebista")
                .snippet("3313++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(cuzavodaoras).title("Stația bd. Cuza Vodă")
                .snippet("3314++33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(ismailoras).title("Stația str. Ismail")
                .snippet("3315+17 +33 +")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        /*Aut 33*/
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*Tr 17 TUR*/
        googleMap.addMarker(new MarkerOptions().position(schinoasa_start).title("Staţia Schinoasa")
                .snippet("017+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(garasudvest).title("Staţia Gara Auto Sud-Vest")
                .snippet("117+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(ialoveni).title("Staţia str. Ialoveni")
                .snippet("217+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(sihastrului).title("Staţia str. Sihastrului")
                .snippet("317+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(miorita).title("Staţia str. Mioriţa")
                .snippet("417+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(campusacad).title("Staţia Campusul Academic")
                .snippet("517+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(grenoble).title("Staţia str. Grenoble")
                .snippet("617+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(polonco).title("Staţia Policlinica Oncologică")
                .snippet("717+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(spitalcopii).title("Staţia Spitalul pentru Copii nr.3")
                .snippet("8817+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(costiujeni).title("Staţia str. Costiujeni")
                .snippet("917+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(decebal).title("Staţia bd. Decebal")
                .snippet("1017+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(teilor).title("Staţia str. Teilor")
                .snippet("1117+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(zelinski).title("Staţia str. N. Zelinski")
                .snippet("1217+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(spitalmunicipalnr1).title("Staţia Spitalul Municipal nr.1")
                .snippet("1317+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(bucuresti).title("Staţia str. Bucuresti")
                .snippet("1417+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(ismail).title("Staţia str. Ismail")
                .snippet("1517+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        /*Tr 17 RETUR*/
        googleMap.addMarker(new MarkerOptions().position(petreungureanu).title("Staţia str. Petre Ungureanu")
                .snippet("170+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(costiujeniretur).title("Staţia str. Costiujeni")
                .snippet("171+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(spitalcopiiretur).title("Staţia Spitalul pentru Copii nr.3")
                .snippet("172+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(oncologicaretur).title("Staţia Policlinica Oncologică")
                .snippet("173+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(mioritaretur).title("Staţia str. Mioriţa")
                .snippet("174+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(campusretur).title("Staţia Campusul Academic")
                .snippet("175+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(pietrarilorretur).title("Staţia str. Pietrarilor")
                .snippet("176+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(sihastruluiretur).title("Staţia str. Sihastrului")
                .snippet("177+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(ialoveniretur).title("Staţia str. Ialoveni")
                .snippet("1788+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(gararetur).title("Staţia Gara Auto Sud Vest")
                .snippet("179+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(valearadiului).title("Staţia str. Valea Radiului")
                .snippet("1710+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(schinoasaterminus).title("Staţia Schinoasa")
                .snippet("1711+17 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*Tr 1 TUR*/
        googleMap.addMarker(new MarkerOptions().position(aschim).title("Staţia Uzina \"Aschim\"")
                .snippet("01+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(mecanexp).title("Staţia Uzina Mecanică Experimentală")
                .snippet("11+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(veterinar).title("Staţia Centrul Republican de Diagnostică Veterinară")
                .snippet("21+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(vasilelupu).title("Staţia str. Vasile Lupu")
                .snippet("31+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(constitutiei).title("Staţia str. Constituției")
                .snippet("41+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(ioncreanga).title("Staţia str. Ion Creangă")
                .snippet("51+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(stefaneaga).title("Staţia str. Ștefan Neaga")
                .snippet("61+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(universitateapedagogica).title("Staţia Universitatea Pedagogică")
                .snippet("71+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(zelinskitur).title("Staţia str. N. Zelinski")
                .snippet("881+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
            /*Tr 1 RETUR*/

        googleMap.addMarker(new MarkerOptions().position(sarmizegetusa).title("Staţia str. Sarmizegetusa")
                .snippet("10+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(minsk).title("Staţia str. Minsk")
                .snippet("111+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(univpedagogicaretur).title("Universitatea Pedagogică")
                .snippet("122+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(stefaneagaretur).title("str. Ștefan Neaga")
                .snippet("13+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(doinateodor).title("str. Doina și Ion Aldea Teodorovici")
                .snippet("14+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(constitutieiretur).title("Staţia str. Constituţiei")
                .snippet("15+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(vasilelupuretur).title("Staţia str. Vasile Lupu")
                .snippet("16+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(vetretur).title("Staţia Centrul Republican de Diagnostică Veterinară")
                .snippet("17+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(uzinamecanicaexp).title("Staţia Uzina Mecanică Experimentală")
                .snippet("1888+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(aschimterminus).title("Staţia Uzina \"Aschim\"")
                .snippet("19+1 ++")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));




/////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*


        googleMap.addMarker(new MarkerOptions().position(zelinskixdecebal1).title("Stația Nicolae Zelinski")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
        googleMap.addMarker(new MarkerOptions().position(zelinskixdecebal4).title("Stația Nicolae Zelinski")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pin)));
*/

        /*Statii*/





        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                String rute = marker.getSnippet();
                String statia = marker.getTitle();

                Intent toSchedule = new Intent(MapsActivity.this, CloseScheduleActivity.class);
                toSchedule.putExtra("Statia", statia);
                toSchedule.putExtra("Rute", rute);

                startActivity(toSchedule);

                return false;
            }
        });


    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getMinimumHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


}
