package com.example.camilo.reddit.utilities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.camilo.reddit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Camilo on 15/02/2017.
 */

public class AdapterJSON extends BaseAdapter implements ListAdapter{
    private final Activity activity;
    private final JSONArray jsonArray;

    public AdapterJSON(Activity activity, JSONArray jsonArray) {

        assert activity != null;
        assert jsonArray != null;

        this.activity = activity;
        this.jsonArray = jsonArray;
    }

    @Override
    public int getCount() {
        if (jsonArray == null)
            return 0;
        else
            return jsonArray.length();
    }


    @Override
    public Object getItem(int position) {
        if (jsonArray== null)
            return null;
        else
            return jsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        JSONObject jsonObject = (JSONObject) getItem(position);
            return jsonObject.optLong("id");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = activity.getLayoutInflater().inflate(R.layout.advertise, null);

        TextView tvDisplayName = (TextView) convertView.findViewById(R.id.tvDisplayName);
        TextView tvPublicDescription = (TextView) convertView.findViewById(R.id.tvPublicDescription);
        TextView tvSubscribers = (TextView) convertView.findViewById(R.id.tvSubscribers);
        ImageView ivHeader = (ImageView) convertView.findViewById(R.id.ivHeader);

        JSONObject jsonObject = (JSONObject) getItem(position);
        if(jsonObject != null){

            try {
                JSONObject jsonObjectData = jsonObject.getJSONObject("data");

                String displayName = jsonObjectData.getString("display_name");
                String publicDescription = jsonObjectData.getString("public_description");
                String subscribers = jsonObjectData.getString("subscribers");
                String urlImage = jsonObjectData.getString("header_img");

               new DownloadImageTask(ivHeader)
                        .execute(urlImage);

                tvDisplayName.setText(displayName);
                tvPublicDescription.setText(publicDescription);
                tvSubscribers.setText("Inscritos:\n"+subscribers);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }

}
