package javadevs.javadevs;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<developer> mDeveloper;
    private static final String _URL ="https://api.github.com/search/users?q=language:java+location:lagos,nigeria";


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        LoadData();
        Toast.makeText(getContext(), "am here ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mrecycle);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDeveloper = new ArrayList<>();//create a list of type developer Class
        mAdapter = new adapt(mDeveloper,getContext());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }



    public void LoadData() {
        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Loading");
        mProgressDialog.show();
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
                                developer person = new developer(
                                        SingleDevObj.getString("login"),
                                        SingleDevObj.getString("html_url"),
                                        SingleDevObj.getString("avatar_url")
                                );
                                Log.w(TAG, "onResponse: what i get"+person.getImageUrl() );
                                mDeveloper.add(person);
                            }
                            mAdapter = new adapt(mDeveloper,getContext());
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
                        Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue mRequestQueue =  Volley.newRequestQueue(getContext());
        mRequestQueue.add(mStringRequest);
    }

}
