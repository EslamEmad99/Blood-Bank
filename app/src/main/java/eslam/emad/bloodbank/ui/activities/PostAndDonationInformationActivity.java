package eslam.emad.bloodbank.ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.emad.bloodbank.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import eslam.emad.bloodbank.data.models.CustomMapFragment;

public class PostAndDonationInformationActivity extends AppCompatActivity {

    @BindView(R.id.activity_post_and_donation_information_imgv)
    ImageView imgv;
    @BindView(R.id.activity_post_and_donation_information_tv1)
    TextView tv1;
    @BindView(R.id.activity_post_and_donation_information_tv2)
    TextView tv2;
    @BindView(R.id.activity_post_and_donation_information_tv3)
    TextView tv3;
    @BindView(R.id.activity_post_and_donation_information_tv4)
    TextView tv4;
    @BindView(R.id.activity_post_and_donation_information_tv5)
    TextView tv5;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    private GoogleMap mMap;
    CustomMapFragment mapFragment;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_and_donation_information);
        ButterKnife.bind(this);
        mapFragment = (CustomMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_post_and_donation_information_map);

        Intent intent = getIntent();

        if (intent.hasExtra("name")) {
            setTitle("Donation request data");
            imgv.setVisibility(View.GONE);
            tv1.setText("Name : " + intent.getStringExtra("name"));
            tv2.setText("Age : " + intent.getStringExtra("age"));
            tv3.setText("Phone : " + intent.getStringExtra("phone"));
            tv4.setText("Hospital address : " + intent.getStringExtra("hospital"));
            tv5.setText("Blood Type : " + intent.getStringExtra("blood"));

            latLng = new LatLng(Double.parseDouble(intent.getStringExtra("latitude")), Double.parseDouble(intent.getStringExtra("longitude")));
        }

        if (intent.hasExtra("post_image")) {
            setTitle("Post data");
            Glide
                    .with(getApplicationContext())
                    .load(intent.getStringExtra("post_image"))
                    .into(imgv);

            tv1.setText(intent.getStringExtra("title"));
            tv1.setTypeface(Typeface.DEFAULT_BOLD);
            tv2.setText(intent.getStringExtra("content"));
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            tv5.setVisibility(View.GONE);
            mapFragment.getView().setVisibility(View.GONE);
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                if (latLng != null) {
                    mMap.addMarker(new MarkerOptions().position(latLng).title("مكان الحالة"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mMap.getUiSettings().setZoomControlsEnabled(true);

                    mapFragment.setListener(new CustomMapFragment.OnTouchListener() {
                        @Override
                        public void onTouch() {
                            mScrollView.requestDisallowInterceptTouchEvent(true);
                        }
                    });
                }
            }
        });
    }
}
