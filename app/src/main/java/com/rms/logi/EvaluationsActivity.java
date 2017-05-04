package com.rms.logi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EvaluationsActivity extends AppCompatActivity {
    private static final String TAG = "EvalautionsActivity";

    private ListView lvList;

    private DatabaseReference databaseReference;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluations);

        lvList = (ListView) findViewById(R.id.lvList);

        userID = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        retrievePropositions();
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
//        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    private void retrievePropositions() {
        DatabaseReference postRef = databaseReference.child("propositions").child(userID);

        FirebaseListAdapter adapter = new FirebaseListAdapter<String>(this, String.class, android.R.layout.simple_list_item_1, postRef) {
            @Override
            protected void populateView(View v, String prop, int position) {
                ((TextView) v.findViewById(android.R.id.text1)).setText(prop);
            }
        };

        lvList.setAdapter(adapter);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = lvList.getItemAtPosition(position);
                String prop = obj.toString();

                Intent intent = new Intent(getApplicationContext(), EvaluateActivity.class);
                intent.putExtra("proposition", prop);
                startActivity(intent);
                finish();

                Toast.makeText(getBaseContext(), prop, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
