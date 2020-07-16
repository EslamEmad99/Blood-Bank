package eslam.emad.bloodbank.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.emad.bloodbank.R;

import eslam.emad.bloodbank.ui.fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity {
    LoginFragment loginFragment;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginFragment = new LoginFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_login_fragment_container,
                    loginFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        count = getSupportFragmentManager().getBackStackEntryCount();
        Toast.makeText(this, String.valueOf(count), Toast.LENGTH_SHORT).show();
        if (count == 0) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }// else if (count == 2) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.activity_login_fragment_container,
//                    loginFragment).commit();
//        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
