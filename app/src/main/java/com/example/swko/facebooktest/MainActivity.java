package com.example.swko.facebooktest;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//facebook
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

//mongodb
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private GpsInfo gps;
    private Button gps_btn;
    private TextView gps_txt;

    MongoClientURI uri = new MongoClientURI( "127.0.0.1:27017/woox" );
    MongoClient mongoClient = new MongoClient(uri);
    MongoDatabase db = mongoClient.getDatabase(uri.getDatabase());

//    MongoCollection<Document> collection = db.getCollection("mycoll");
//
//    // insert a document
//    Document document = new Document("x", 1)
//    collection.insertOne(document);
//    document.append("x", 2).append("y", 3);
//
//// replace a document
//    collection.replaceOne(Filters.eq("_id", document.get("_id")), document);
//
//    // find documents
//    List<Document> foundDocument = collection.find().into(new ArrayList<Document>());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //commit test Kwook
        AppEventsLogger.activateApp(this);

        gps_btn = (Button) findViewById(R.id.gps_btn);
        gps_txt = (TextView) findViewById(R.id.gps_txt);


        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        // GPS 정보를 보여주기 위한 이벤트 클래스 등록
        gps_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                gps = new GpsInfo(MainActivity.this);
                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    gps_txt.setText(String.valueOf(latitude)+String.valueOf(longitude));

                    Toast.makeText(
                            getApplicationContext(),
                            "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,
                            Toast.LENGTH_LONG).show();
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
            }
        });
    }
}
