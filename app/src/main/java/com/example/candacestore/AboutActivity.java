package com.example.candacestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    ImageView wazeIcon,phoneIcon,facebookIcon,instagramIcon;
    TextView text,text2;
    String write,write2;
    Button backtohome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        show();

    }
    public void show(){
        wazeIcon = findViewById(R.id.wazeIcon);
        phoneIcon = findViewById(R.id.phoneIcon);
        facebookIcon = findViewById(R.id.facebookIcon);
        instagramIcon = findViewById(R.id.instagramIcon);
        backtohome = findViewById(R.id.backtohome);
        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backtomain();
            }
        });
        text = findViewById(R.id.text);
        write = "Candace Store is a popular Middle Eastern dessert";
        text.setText(write);
        text2 = findViewById(R.id.text2);
        write2 = "Kunafa:\n" +
                "Description: Kunafa is a famous Middle Eastern dessert, consisting of thin strands of pastry filled with cheese or cream and covered with layers of pastry and sugar syrup. It is considered a delicious treat often served at special occasions and celebrations.\n\n" +

                "Katayef/Qatayef:\n" +
                "Description: Thin pastry dough pockets filled with nuts, cheese, or cream, then folded and either fried or baked. Traditionally enjoyed during Ramadan, served with syrup or honey.\n\n" +

                "Kulaj:\n" +
                "Description: Layers of thin pastry dough layered with sugar and nuts, such as almonds, then baked. Typically served as a traditional dessert after being toasted at events. Without ingredients";;
        text2.setText(write2);
        wazeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gowaze();
            }
        });
        phoneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });
        facebookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPageFacebook();
            }
        });
        instagramIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPageInstagram();
            }
        });
    }

    private void backtomain() {
        Intent intent = new Intent(AboutActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void openPageInstagram() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/sleman_8e/?next=%2F"));
        startActivity(intent);
    }

    private void openPageFacebook() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=61554628557119"));
        startActivity(intent);
    }

    private void call() {

        Intent intent = new Intent(Intent.ACTION_DIAL);
        String phoneNumber = "tel:" + "046587245";
        intent.setData(Uri.parse(phoneNumber));
        startActivity(intent);
    }

    private void gowaze() {
        double userLatitude = 32.42333601;
        double userLongitude = 	35.04324645;
        // locatin for baqa
        String uri = "waze://?ll=" + userLatitude + "," + userLongitude + "&navigate=yes";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

        startActivity(intent);
    }
}