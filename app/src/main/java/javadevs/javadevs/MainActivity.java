package javadevs.javadevs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<developer>mDeveloper;
    public AlertDialog.Builder mAlertDialog;
    public  AlertDialog mAlert;
    //url to query for java developers in lagos on github
    private static final String _URL ="https://api.github.com/search/users?q=language:java+location:lagos,nigeria";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAlertDialog = new AlertDialog.Builder(this);
        mAlertDialog.setMessage("No Network Connection,put on wifi or mobile data and try again");
        mAlertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        mAlertDialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                successLoad();
            }
        });
        mAlertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        mAlert = mAlertDialog.create();
         mRecyclerView = (RecyclerView) findViewById(R.id.mrecycle);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDeveloper = new ArrayList<>();//create a list of type developer Class
        mAdapter = new adapt(mDeveloper,this);
        mRecyclerView.setAdapter(mAdapter);
        successLoad();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater Inflater = getMenuInflater();
        Inflater.inflate(R.menu.share,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_refresh:
                successLoad();
        }
        return super.onOptionsItemSelected(item);
    }



    //Method to Fetch the Data
    public void LoadData() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading Feeds..Please Wait");
        mProgressDialog.show();
        //using volley stringRequest to fetch json data
        StringRequest mStringRequest = new StringRequest(Request.Method.GET,_URL,

                //Listener to make callbacks when data is gotten successfully
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            mProgressDialog.dismiss();
                            Log.i(TAG, "onResponse: what i get"+response );
                            JSONObject mJsonObject = new JSONObject(response);


                            JSONArray mJsonArray = mJsonObject.getJSONArray("items");

                            for(int i=0;i< mJsonArray.length();i++){
                                JSONObject SingleDevObj = mJsonArray.getJSONObject(i);
                                //pass the data into developer class instance 
                                developer person = new developer(
                                        SingleDevObj.getString("login"),
                                        SingleDevObj.getString("avatar_url"),
                                        SingleDevObj.getString("html_url")

                                );
                                Log.w(TAG, "onResponse: what i get"+person.getImageUrl() );
                                mDeveloper.add(person);
                            }
                            mAdapter = new adapt(mDeveloper,getApplicationContext());
                            mRecyclerView.setAdapter(mAdapter);

                        }
                        catch(Exception ex){
                            ex.printStackTrace();
                        }

                    }
                },

                //Listener to make callbacks when there is an error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Connection Failed..Refresh to try Again",Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }
                });
        
        RequestQueue mRequestQueue =  Volley.newRequestQueue(getApplicationContext());
        //Add the StringRequest above to the RequestQueue
        mRequestQueue.add(mStringRequest);
    }

    //check for wifi Connection
    public boolean CheckForWifiNetwork(){
        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return wifi;
    }
    
    //check for Mobile Data Connection
    public boolean CheckForMobileNetwork(){
        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return mobile;

    }

    //this function calls the LoadData if there is either wifi or mobile connection or else shows the custom dialog message
    public void successLoad(){
        if(CheckForMobileNetwork() || CheckForWifiNetwork()){
            LoadData();
        }
        else{
            mAlert.show();

        }
    }
}

