package com.rms.logi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;

import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Bind(R.id.btnNewEvaluation)
    Button btnNewEvaluation;
    @Bind(R.id.btnOpen)
    Button btnOpen;
    @Bind(R.id.btnLogout)
    Button btnLogout;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Log.d(TAG, "user " + user.getUid());
        }

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.AppTheme_Dark_Dialog);

        btnNewEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EvaluateActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Recuperando proposiciones...");
                progressDialog.show();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), EvaluationsActivity.class);
                                startActivity(intent);
                                finish();
                                progressDialog.dismiss();
                            }
                        }, 1000);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(getString(R.string.logging_out));
                progressDialog.show();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                FirebaseAuth.getInstance().signOut();
                                LoginManager.getInstance().logOut();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                progressDialog.dismiss();
                            }
                        }, 1000);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.AppTheme_Dark_Dialog);

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            AboutActivity.Show(this);
            return true;
        }
        if (id == R.id.logout) {
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getString(R.string.logging_out));
            progressDialog.show();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            FirebaseAuth.getInstance().signOut();
                            LoginManager.getInstance().logOut();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            progressDialog.dismiss();
                        }
                    }, 1000);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
        if (id == R.id.exit) {
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getString(R.string.exit));
            progressDialog.show();
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            finishAffinity();
                            progressDialog.dismiss();
                        }
                    }, 1000);
        }
        return super.onOptionsItemSelected(item);
    }
}
