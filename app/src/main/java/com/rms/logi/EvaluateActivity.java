package com.rms.logi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rms.model.Evaluacion;
import com.rms.model.Proposition;
import com.rms.model.Validations;

import java.io.IOException;
import java.util.ArrayList;

import static com.rms.model.Principal.mostrarTablasVariables;
import static com.rms.model.Principal.postfijo;

public class EvaluateActivity extends Activity {
    private static final String TAG = "EvaluateActivity";

    private EditText etProposition;
    private EditText etType;
    private EditText etPostfix;
    private Button btnEvaluate;
    private Button btnSave;
    private ListView lvList;
    private TableLayout tbTable;
    private Adapter adapter;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String userID = "";

    private String proposition = "";
    private String type = "";
    private String postfix = "";
    private int noColumns = 0;
    private int noRows = 0;
    private static ArrayList<ArrayList> finalVector;

    private CustomKeyboard mCustomKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);

        etProposition = (EditText) findViewById(R.id.etProposition);
        etType = (EditText) findViewById(R.id.etType);
        etPostfix = (EditText) findViewById(R.id.etPostfix);
        btnEvaluate = (Button) findViewById(R.id.btnEvaluate);
        btnSave = (Button) findViewById(R.id.btnSave);
//        tbTable = (TableLayout) findViewById(R.id.tbTable);
        lvList = (ListView) findViewById(R.id.lvList);
        ArrayAdapter<String> adapter;

        //Keyboard
        mCustomKeyboard = new CustomKeyboard(this, R.id.vwKeyboard, R.xml.keyboard);
        mCustomKeyboard.registerEditText(R.id.etProposition);

        //Dialog
        final ProgressDialog progressDialog = new ProgressDialog(EvaluateActivity.this, R.style.AppTheme_Dark_Dialog);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        if (intent.getSerializableExtra("proposition") != null) {
            String prop = (String) intent.getSerializableExtra("proposition");
            etProposition.setText(prop);
            Log.d(TAG, "proposition received:" + prop);
        }

        btnEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomKeyboard.hideCustomKeyboard();

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Evaluando...");
                progressDialog.show();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                evaluate();
                                progressDialog.dismiss();
                            }
                        }, 1000);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomKeyboard.hideCustomKeyboard();

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Guardando...");
                progressDialog.show();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                saveFirebase();
                                progressDialog.dismiss();
                            }
                        }, 1000);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // NOTE Trap the back key: when the CustomKeyboard is still visible hide it, only when it is invisible, finish activity
        if (mCustomKeyboard.isCustomKeyboardVisible()) {
            mCustomKeyboard.hideCustomKeyboard();
        } else {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
//            this.finish();
            //        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }

    }

    private void evaluate() {
        if (!etProposition.getText().toString().equals("")) {
            proposition = etProposition.getText().toString();

            Validations val = new Validations(proposition);
            boolean validate = val.validateProposition();
            boolean balanced = val.validateParenthesis();

            if (validate && balanced) {
                //Tablas
                try {
                    mostrarTablasVariables(proposition);
                } catch (Exception e) {
                    System.out.println("ERROR TABLA: " + e.getMessage());
                }

                //Evaluación
                try {
                    Evaluacion.evaluacionIniciar(proposition);
                    Evaluacion.evaluacionIniciar(proposition);
                    finalVector = Evaluacion.arrayTabla;

                    noColumns = finalVector.size();
                    noRows = finalVector.get(0).size();

                    System.out.println("NOCOLUMNAS: " + noColumns);
                    System.out.println("NOFILAS: " + noRows);
                    System.out.println("VECTOR FINAL: " + finalVector.toString());

                    String postfix = postfijo(proposition);
                    ArrayList<String> temp = new ArrayList<String>();
                    String str = "";
                    String separation = "\t\t\t";
                    for (int i = 0; i < postfix.length(); i++) {
                        str += postfix.charAt(i) + separation;
                    }
                    temp.add(str);
                    String[][] matrix = new String[noColumns][noRows];
                    for (int i = 0; i < noColumns; i++) {
                        for (int j = 0; j < noRows; j++) {
                            matrix[i][j] = finalVector.get(i).get(j).toString();
                        }
                    }
                    for (int i = 0; i < matrix[0].length; i++) {
                        str = "";
                        for (int j = 0; j < matrix.length; j++) {
                            str += matrix[j][i] + separation;
                            System.out.print(matrix[j][i] + " ");
                        }
                        temp.add(str);
                    }
                    System.out.println("LISTA: " + temp);


//                for (ArrayList aux : finalVector) {
//                    str = aux.toString();
//                    System.out.println("TEST: " + str);
//                    temp.add(str);
//                }

//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.textcenter, R.id.textItem, temp);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.textcenter, temp);
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, temp);

                    System.out.println(adapter.toString());
                    lvList.setAdapter(adapter);

                    final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lvList.getLayoutParams();
                    params.gravity = Gravity.CENTER_VERTICAL;
                    lvList.setLayoutParams(params);

                } catch (IOException e) {
                    System.out.println("ERROR EVALUACIÓN: " + e.getMessage());
                }
                //Tipo de evaluación
                try {
                    type = Evaluacion.evaluacionTipo();
                    etType.setText(type);
                    System.out.println("TIPO: " + type);
                } catch (Exception e) {
                    System.out.println("ERROR TIPO: " + e.getMessage());
                }
                //Postfijo
                try {
                    postfix = postfijo(proposition);
                    etPostfix.setText(postfix);
                    System.out.println("POSTFIJO: " + postfix);
                } catch (IOException e) {
                    System.out.println("ERROR POSTFIJO: " + e.getMessage());
                }
            } else if (!validate) {
                Toast.makeText(getApplicationContext(), R.string.proposition_invalid, Toast.LENGTH_SHORT).show();
            } else if (!balanced) {
                Toast.makeText(getApplicationContext(), R.string.proposition_unbalanced, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.proposition_incorrect, Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), R.string.empty_input, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveFirebase() {
        if (!etProposition.getText().toString().equals("")) {
            proposition = etProposition.getText().toString();

            final DatabaseReference ref = databaseReference.child("propositions").child(userID).child(proposition);
            final Proposition prop = new Proposition(userID, proposition);

            Validations val = new Validations(proposition);
            boolean validate = val.validateProposition();
            boolean balanced = val.validateParenthesis();

            if (validate && balanced) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!(dataSnapshot.child(prop.getProposition()).exists())) {
                            Log.d(TAG, dataSnapshot.child(prop.getProposition()).toString());
                            ref.setValue(prop.getProposition());  //overwrites
//                            DatabaseReference newRef = ref.push();  //creates duplicates
//                            newRef.setValue(prop.getProposition());
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.proposition_duplicate, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), R.string.proposition_duplicate, Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            } else if (!validate) {
                Toast.makeText(getApplicationContext(), R.string.proposition_invalid, Toast.LENGTH_SHORT).show();
            } else if (!balanced) {
                Toast.makeText(getApplicationContext(), R.string.proposition_unbalanced, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.proposition_incorrect, Toast.LENGTH_SHORT).show();
            }

//            DatabaseReference ref = databaseReference.child("propositions").child(userID);
//            DatabaseReference newRef = ref.push();
//            Proposition prop = new Proposition(userID, proposition);
//            newRef.setValue(prop.getProposition());
        }
    }
}
