package com.example.ibrahim.messages.View;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ibrahim.messages.Adapter.MessageAdapter;
import com.example.ibrahim.messages.Model.Message;
import com.example.ibrahim.messages.Model.SpreadSheet;
import com.example.ibrahim.messages.R;
import com.example.ibrahim.messages.Retrofit.MessageRequest;
import com.example.ibrahim.messages.Retrofit.RetrofitConnection;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {


    List<SpreadSheet.FeedBean.EntryBean> enryList= new ArrayList<>();

    List<Message> messageList = new ArrayList<>();
    Message messageObject ;

    MessageAdapter adapter ;
    RecyclerView recyclerView ;

    MarkerOptions markerOptions;
    double longitude = 0;
    double latitude = 0;

    List<Double> lngList =new ArrayList<>();
    List<Double> latList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initListView();

        fetchMessages();

    } // function of onCreate


    private void fetchMessages()
    {
        Retrofit retrofit = RetrofitConnection.getInstance();
        MessageRequest messageRequest = retrofit.create(MessageRequest.class);

        Call<SpreadSheet> getMessagesCall = messageRequest.getMessages();

        getMessagesCall.enqueue(new Callback<SpreadSheet>() {
            @Override
            public void onResponse(Call<SpreadSheet> call, Response<SpreadSheet> response) {

                enryList = response.body().getFeed().getEntry();
                Log.e("QP","Success : "+enryList.size());

                for(int i=0;i<enryList.size();i++)
                {
                    try {

                        String jsonSentiment []= enryList.get(i).getContent().get$t().split("sentiment");
                        String jsonMessage []= enryList.get(i).getContent().get$t().split("message");

                        String sentiment = jsonSentiment[1].replace(":","");

                        String messageMap [] = jsonMessage[2].split("sentiment");
                        String message = messageMap[0].replace(":","");

                        getLatLngFromAddress(message);

                        messageObject = new Message(message,sentiment);
                        messageList.add(messageObject);
                        adapter.notifyDataSetChanged();


                    } catch (Exception e) {
                        Log.e("QP","exception json : "+e.toString());
                    }

                }
            }

            @Override
            public void onFailure(Call<SpreadSheet> call, Throwable t) {

                Log.e("QP","Connection Faild : "+t.toString());
            }
        });


    } // function of fetchMessages

    private  void  initListView()
    {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MessageAdapter(getApplicationContext(),messageList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


    } // function of initListView

   private void getLatLngFromAddress(String address)
   {
       Geocoder coder = new Geocoder(this);
       try {
           ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(address, 50);
           for(Address add : adresses){

                    longitude = add.getLongitude();
                    latitude = add.getLatitude();

                    if(add != null) {
                        latList.add(latitude);
                        lngList.add(longitude);
                        callMap();


                        Log.e("QP", "address : " + add.getLocality() + " : " + latitude + " : " + longitude);
                    }
           }
       } catch (IOException e) {
           Log.e("QP","GeoCode Exception : "+e.toString());
       }
   }

    private  void callMap()
    {
        Log.i("QP","map loaded");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    } // function of callMap

    @Override
    public void onMapReady(GoogleMap googleMap) {

        for(int i=0;i<latList.size()-1;i++) {
            markerOptions = new MarkerOptions();
            LatLng latLng = new LatLng(latList.get(i), lngList.get(i));

            //Log.e("QP", "address OnMapReady: " + " : " + latList.get(i) + " : " + lngList.get(i));
            markerOptions.position(latLng);

            String sentiment = messageList.get(i).getSentiment().trim();
            if(sentiment.equals("Positive"))
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            else if(sentiment.equals("Negative"))
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            else if (sentiment.equals("Neutral"))
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            markerOptions.title("Message");

            googleMap.addMarker(markerOptions);
        }
    }
} // class of MainActivity
