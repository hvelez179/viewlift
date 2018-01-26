package com.example.android.viewliftapp;

import android.app.ProgressDialog;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private RecyclerView filmsView;
    private Adapter filmsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        new AsyncFetch().execute();
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog( MainActivity.this );
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage( "\tLoading..." );
            pdLoading.setCancelable( false );
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                url = new URL( "http://www.snagfilms.com/apis/films.json?limit=10" );

            } catch (MalformedURLException e) {

                e.printStackTrace();
                return e.toString();
            }

            try {

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);

            } catch (IOException e1) {

                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute( result );

            pdLoading.dismiss();
            List<DataFilms> data = new ArrayList<>(  );

            try {
                JSONArray jsonArray = new JSONArray( result );

                for (int i=0; i<jsonArray(); i++) () {

                    JSONObject json_data = jsonArray.getJSONObject( i );
                    DataFilms filmsData = new DataFilms();
                    filmsData.filmsTitle = json_data.getString( "title" );
                    filmsData.filmsImage = json_data.getString( "images" );
                    data.add( filmsData );
                }

                filmsView = findViewById( R.id.filmsView );
                filmsAdapter = new Adapter( MainActivity.this, data ) {
                    @Override
                    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

                    }

                    @Override
                    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

                    }

                    @Override
                    public int getCount() {
                        return 0;
                    }

                    @Override
                    public Object getItem(int i) {
                        return null;
                    }

                    @Override
                    public long getItemId(int i) {
                        return 0;
                    }

                    @Override
                    public boolean hasStableIds() {
                        return false;
                    }

                    @Override
                    public View getView(int i, View view, ViewGroup viewGroup) {
                        return null;
                    }

                    @Override
                    public int getItemViewType(int i) {
                        return 0;
                    }

                    @Override
                    public int getViewTypeCount() {
                        return 0;
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }
                };
                filmsView.setAdapter(AdapterFilms);
                filmsView.setLayoutManager( new LinearLayoutManager( MainActivity.this ));


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText( MainActivity.this, e.toString(), Toast.LENGTH_LONG ).show();
            }
        }
    }
}













