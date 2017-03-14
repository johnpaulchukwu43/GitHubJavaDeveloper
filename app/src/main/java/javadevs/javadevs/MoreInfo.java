package javadevs.javadevs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MoreInfo extends AppCompatActivity {
    private static final String LOG_TAG = MoreInfo.class.getSimpleName() ;
    ImageView mImageView;
    TextView usernameTxt;
    TextView ProfileTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        mImageView = (ImageView) findViewById(R.id.moreImage);
        usernameTxt = (TextView) findViewById(R.id.MoreUsername);
        ProfileTxt = (TextView) findViewById(R.id.moreLink);



        usernameTxt.setText(getIntent().getStringExtra("username"));
      
        Picasso.with(this)
                .load(getIntent().getStringExtra("image"))
                .into(mImageView);
        ProfileTxt.setText(getIntent().getStringExtra("profile_url"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Inflater = getMenuInflater();
        Inflater.inflate(R.menu.share,menu);
        MenuItem mitem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(mitem);
        if(shareActionProvider!=null){
            shareActionProvider.setShareIntent(sharedIntent());
        }
        else{
            Log.d(LOG_TAG, "onCreateOptionsMenu: NULL SharedIntent");
        }
        return true;
    }

    private Intent sharedIntent(){
    Intent shared = new Intent((Intent.ACTION_SEND));
        String userInfoSend = "Check out this awesome developer @"+getIntent().getStringExtra("username")+getIntent().getStringExtra("profile_url");
        shared.setType("text/plain");
        shared.putExtra( Intent.EXTRA_TEXT,userInfoSend);
        shared.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        return shared;

    }

    public void launchBrowserOnRequest(View V){
        try {
            String link = getIntent().getStringExtra("profile_url");
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }






}