package com.example.bikerer;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ListView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
public class HomeMapsActivity extends FragmentActivity implements OnMapReadyCallback, RoutingListener {


    private GoogleMap androidMap;
    protected LatLng startLocation = null;
    protected LatLng endLocation = null;
    Location myLocation = null;
    private List<Polyline> polylineList = null;
    protected FusedLocationProviderClient customer;
    protected LocationRequest mLocationRequest;
    private PlacesClient mClient;
    private ArrayAdapter<String> mAdapter;
    private List<String> mPlacesList;
    private List<AutocompletePrediction> mPredictions;
    String distance1;
    String destinationName;
    private Marker endMarker;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.homeMap);
        mapFragment.getMapAsync(this);
        // Khởi tạo Places SDK
        Places.initialize(getApplicationContext(), "AIzaSyB_sIZ6bziC7B8OVm8o6AuCrEcJXFbSKk0"); // Thay thế bằng API key của bạn

        // Tạo PlacesClient
        mClient = Places.createClient(this);
        Button obtainUserLocationButton = findViewById(R.id.obtainUserLocation);
        obtainUserLocationButton.setOnClickListener(v -> getCurrentPosition(v));
        Button goToSelectDeviceLocationButton = findViewById(R.id.goToSelectDeviceLocation);
        goToSelectDeviceLocationButton.setOnClickListener(v->{
            if(endLocation==null){
                Toast.makeText(HomeMapsActivity.this, "Choose your destination first" , Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(HomeMapsActivity.this, SelectDeviceActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("distance", distance1);
                bundle2.putString("destinationName",destinationName);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });
        EditText searchLocation = findViewById(R.id.searchLocation);
        ListView autocompleteList = findViewById(R.id.autocompleteList);
        mPlacesList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, R.layout.list_item_place, mPlacesList);
        autocompleteList.setAdapter(mAdapter);
        searchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int a, int a1, int a2) {}

            @Override
            public void onTextChanged(CharSequence sequence, int a, int a1, int a2) {
                // Xóa routes cũ khi có sự thay đổi trong nội dung của EditText
                if (polylineList != null && polylineList.size() > 0) {
                    for (Polyline polyline : polylineList) {
                        polyline.remove();
                    }
                    polylineList.clear();
                }
                //xoa endMarker cu
                if (endMarker != null) {
                    endMarker.remove();
                }
                autocompleteList.setVisibility(View.VISIBLE);
                // Gọi hàm thực hiện autocomplete khi có sự thay đổi trong nội dung của EditText
                displayRecommendedPlaces(sequence.toString());
            }
            @Override
            public void afterTextChanged(Editable e) {
            }
        });

        autocompleteList.setOnItemClickListener((adapterView, view, i, l) -> {
            // Lấy thông tin về địa điểm được chọn
            AutocompletePrediction selectedPrediction = mPredictions.get(i);
            String placeId = selectedPrediction.getPlaceId();

            // Khai báo danh sách các trường cần lấy thông tin
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

            // Lấy thông tin địa điểm
            mClient.fetchPlace(FetchPlaceRequest.builder(placeId, fields).build())
                    .addOnSuccessListener(response -> {
                        if(myLocation!=null) {
                            Place place = response.getPlace();
                            // Lấy kinh độ và vĩ độ của địa điểm và thêm vào biến end
                            endLocation = place.getLatLng();
                            destinationName=place.getName();
                            searchLocation.setText(place.getName());
                            // Nếu muốn hiển thị địa điểm trên bản đồ, có thể thêm đoạn mã sau:
                            endMarker = androidMap.addMarker(new MarkerOptions().position(endLocation).title(place.getName()).icon(setDestinationIcon(HomeMapsActivity.this, R.drawable.baseline_location_on_24)));
                            androidMap.moveCamera(CameraUpdateFactory.newLatLng(endLocation));
                            if (polylineList != null && polylineList.size() > 0) {
                                for (Polyline polyline : polylineList) {
                                    polyline.remove();
                                }
                                polylineList.clear();
                            }
                            startLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                            drawPaths(startLocation, endLocation);
                            //Tinh khoang cach hai vi tri
                            float[] distance = new float[1];
                            Location.distanceBetween(myLocation.getLatitude(), myLocation.getLongitude(), endLocation.latitude, endLocation.longitude, distance);
                            EditText distanceText = findViewById(R.id.distance);
                            float distanceInKm = distance[0] / 1000.0f;
                            distance1=String.format("%.1f", distanceInKm);
                            distanceText.setText(distance1+ " km");
                            autocompleteList.setVisibility(View.GONE);
                        }
                        else{
                            Toast.makeText(HomeMapsActivity.this, "Click 'Get Position' button first" , Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(exception -> {
                        // Xử lý lỗi khi lấy thông tin địa điểm
                        exception.printStackTrace();
                    });
        });
    }
    public BitmapDescriptor setDestinationIcon(Activity mapActivity, int iconID) {
        Drawable icon = ActivityCompat.getDrawable(mapActivity, iconID);
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        Bitmap destinationBitmap = Bitmap.createBitmap(icon.getIntrinsicWidth(), icon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas destinationCanvas = new Canvas(destinationBitmap);
        icon.draw(destinationCanvas);
        return BitmapDescriptorFactory.fromBitmap(destinationBitmap);
    }
    @SuppressLint("MissingPermission")
    public void getCurrentPosition(View view) {
        customer.getLastLocation().addOnSuccessListener(location -> {
            myLocation = location;
            LatLng latLng = new LatLng(location.getLatitude(),
                    location.getLongitude());
            androidMap.addMarker(new MarkerOptions()
                    .position(latLng));
            androidMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            androidMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        });
    }
    public void drawPaths(LatLng currentLocation, LatLng destination) {
        if (currentLocation != null && destination != null) {
            createRouting(currentLocation, destination);
        } else {
            Toast.makeText(HomeMapsActivity.this, "Unable to get the destination location", Toast.LENGTH_LONG).show();
        }
    }

    private void createRouting(LatLng startLocation, LatLng endLocation) {
        Routing route = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .alternativeRoutes(true)
                .withListener(this)
                .key("AIzaSyB_sIZ6bziC7B8OVm8o6AuCrEcJXFbSKk0")
                .waypoints(startLocation, endLocation)
                .build();
        route.execute();
    }
    private void displayRecommendedPlaces(String q) {
        // Tạo AutocompleteRequest
        AutocompleteSessionToken autocompleteSessionToken = AutocompleteSessionToken.newInstance();
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(q)
                .setSessionToken(autocompleteSessionToken)
                .setTypeFilter(TypeFilter.ADDRESS)
                .build();

        // Gọi API autocomplete
        mClient.findAutocompletePredictions(request).addOnSuccessListener((autocompleteResponse) -> {
            mPredictions = autocompleteResponse.getAutocompletePredictions();

            // Xử lý danh sách kết quả autocomplete ở đây
            mPlacesList.clear(); // Xóa dữ liệu cũ
//Them prediction vao placeList
            for (AutocompletePrediction autocompletePrediction : mPredictions) {
                mPlacesList.add(autocompletePrediction.getFullText(null).toString());
            }

            mAdapter.notifyDataSetChanged();
        });
    }

    private void requestLocationAccess(){
        ActivityCompat.requestPermissions(HomeMapsActivity.this, new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION}, 123);
    }
    @SuppressLint({"MissingPermission", "RestrictedApi"})
    private void updateCustomerLocation() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(19 * 1000);
        mLocationRequest.setFastestInterval(9 * 1000);
        customer.requestLocationUpdates(mLocationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult onLocationResult) {
                super.onLocationResult(onLocationResult);
            }
        }, null);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        requestLocationAccess();
        customer = LocationServices.getFusedLocationProviderClient(HomeMapsActivity.this);
        androidMap = googleMap;
        androidMap.getUiSettings().setScrollGesturesEnabled(true);
        androidMap.getUiSettings().setZoomControlsEnabled(true);
        updateCustomerLocation();
    }
    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int effectiveRoute) {
        if (polylineList != null) {
            polylineList.clear();
        }
        PolylineOptions customPolylineOptions = new PolylineOptions();


        polylineList = new ArrayList<>();
        for (int a = 0; a <route.size(); a+=1) {
            if (a == effectiveRoute) {
                customPolylineOptions.addAll(route.get(effectiveRoute).getPoints());
                customPolylineOptions.width(5);
                customPolylineOptions.color(getResources().getColor(androidx.cardview.R.color.cardview_dark_background));
                Polyline polyline = androidMap.addPolyline(customPolylineOptions);
                polylineList.add(polyline);
            }
        }
    }

    @Override
    public void onRoutingCancelled() {

    }
}