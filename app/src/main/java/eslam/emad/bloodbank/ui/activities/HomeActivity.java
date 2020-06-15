package eslam.emad.bloodbank.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.emad.bloodbank.R;

import eslam.emad.bloodbank.ui.fragments.DonationRequestFragment;
import eslam.emad.bloodbank.ui.fragments.HomeFragment;
import eslam.emad.bloodbank.ui.fragments.MoreFragment;
import eslam.emad.bloodbank.ui.fragments.NotificationFragment;
import eslam.emad.bloodbank.ui.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    int counter = 0;
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNav = findViewById(R.id.activity_home_bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_home:
                       selectedFragment = new HomeFragment();
                       counter = 0;
                        break;

                    case R.id.nav_more:
                        selectedFragment = new MoreFragment();
                        counter =1;
                        break;

                    case R.id.nav_notificatio:
                        selectedFragment = new NotificationFragment();
                        counter = 2;
                        break;

                    case R.id.nav_donation_request:
                        selectedFragment = new DonationRequestFragment();
                        counter = 3;
                        break;
                }

                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_home_fragment_container,
                        selectedFragment).commit();

                return true;
            }
        });

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_home_fragment_container,
                    new HomeFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (counter !=0){
            bottomNav.setSelectedItemId(R.id.nav_home);
        } else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }
}
