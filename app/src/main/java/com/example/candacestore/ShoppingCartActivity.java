package com.example.candacestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.Manifest;


public class ShoppingCartActivity extends AppCompatActivity {

    TextView orderDetailsTextView, deliveryTimeTextView;
    Button chooseDeliveryTimeButton, placeOrderButton,backtohome;

    String selectedSweets, selectedExtras, selectedQuantity;
    double totalPrice;
    String deliveryTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        show();
        givemedata();

    }

    public void show(){
        orderDetailsTextView = findViewById(R.id.orderDetailsTextView);
        deliveryTimeTextView = findViewById(R.id.deliveryTimeTextView);
        chooseDeliveryTimeButton = findViewById(R.id.chooseDeliveryTimeButton);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        backtohome = findViewById(R.id.backtohome);

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });
        chooseDeliveryTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeliveryTimeDialog();
            }
        });

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });
    }

    private void backToMain() {
        Intent intent = new Intent(ShoppingCartActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void givemedata() {
        SharedPreferences sharedPreferences = getSharedPreferences("Candy", MODE_PRIVATE);
        selectedSweets = sharedPreferences.getString("selectedSweets", "");
        selectedExtras = sharedPreferences.getString("selectedExtras", ""); // Use getString() directly
        selectedQuantity = sharedPreferences.getString("selectedQuantity", "");
        totalPrice = sharedPreferences.getFloat("totalPrice",0); // Use getFloat() instead of getDouble()

        String orderDetails = "\n" + "Selected Sweets: " + selectedSweets + "\n" +
                "Selected Extras: " + selectedExtras + "\n" +
                "Quantity: " + selectedQuantity + "\n" +
                "Total Price: $" + totalPrice + "\n";
        orderDetailsTextView.setText(orderDetails);
    }

    private void showDeliveryTimeDialog() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                ShoppingCartActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        calendar.set(Calendar.MINUTE, minute);

                                        @SuppressLint("SimpleDateFormat")
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm", Locale.getDefault());
                                        deliveryTime = dateFormat.format(calendar.getTime());
                                        deliveryTimeTextView.setText(deliveryTime);
                                    }
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                android.text.format.DateFormat.is24HourFormat(ShoppingCartActivity.this) // Determine if 24-hour mode should be used
                        );
                        timePickerDialog.show();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // Set minimum date to today
        datePickerDialog.show();
    }


    private void placeOrder() {
        if (deliveryTime != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
            String customerPhoneNumber = sharedPreferences.getString("phone", "");

            if (!customerPhoneNumber.isEmpty()) {
                sendSMS(customerPhoneNumber);
            } else {
                Toast.makeText(this, "Phone number not found!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendSMS(String customerPhoneNumber) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        } else {
            String message = "Thank you for your order!\n" +
                    "Your order of " + selectedSweets + " with " + selectedExtras + " has been confirmed.\n" +
                    "Delivery scheduled for: " + deliveryTime;

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(customerPhoneNumber, null, message, null, null);
            Toast.makeText(this, "SMS sent successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                placeOrder();
            } else {
                Toast.makeText(this, "SMS permission denied. Cannot send SMS.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
