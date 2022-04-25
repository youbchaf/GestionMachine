package ma.emsi.gestionmachine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ma.emsi.gestionmachine.beans.User;

public class Authentification extends AppCompatActivity implements View.OnClickListener {

    EditText usernameU,passwordU,label;
    Button login;
    RequestQueue queue;
    String getUrl = "http://10.0.2.2:8090/users";
    List<String> users;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String USER_KEY = "user_key";
    public static final String PASSWORD_KEY = "password_key";
    SharedPreferences sharedpreferences;
    String user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        usernameU = findViewById(R.id.idEdtUserName);
        passwordU = findViewById(R.id.idEdtPassword);
        login = findViewById(R.id.idBtnLogin);
        label = findViewById(R.id.label);
        login.setOnClickListener(this);
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(USER_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user != null && password != null) {
            Intent i = new Intent(Authentification.this, MainActivity.class);
            startActivity(i);
        }
    }
    @Override
    public void onClick(View view) {
        queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, getUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("ReTAG", response.toString());
                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                List<User> list = new ArrayList<User>();
                JSONArray array = null;
                try {
                    array = obj.getJSONObject("_embedded").getJSONArray("users");
                    Log.d("ReTAG", array.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for(int i = 0 ; i < array.length() ; i++){
                    String username = null;
                    try {
                        username = array.getJSONObject(i).getString("username");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String password = null;
                    try {
                        password = array.getJSONObject(i).getString("password");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    list.add(new User(username,password));


                }

                for (User i : list){
                    if(usernameU.getText().toString().equals(i.getUsername()) && passwordU.getText().toString().equals(i.getPassword()))
                    {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(USER_KEY,usernameU.toString());
                        editor.putString(PASSWORD_KEY, passwordU.toString());
                        editor.apply();
                        Intent intent= new Intent(Authentification.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        return;

                    }
                    else {
                        label.setText("Verifier les info !");
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        })
        {


        };

        queue.add(request);

    }








}