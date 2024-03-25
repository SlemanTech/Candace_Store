package com.example.candacestore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CandyActivity extends AppCompatActivity {

    Button backtohome, Sweets, Extras, Shoppingcart, Cancelorder;
    EditText requiredquantity;
    String selectSweets, selectedQuantity;
    StringBuilder selectedExtras;
    private static final String SELECTED_SWEETS_KEY = "selectedSweets";
    private static final String SELECTED_EXTRAS_KEY = "selectedExtras";
    private static final String SELECTED_QUANTITY_KEY = "selectedQuantity";
    private static final String TOTAL_PRICE_KEY = "totalPrice";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candy);
        show();
    }

    private void show() {
        backtohome = findViewById(R.id.backtohome);
        Sweets = findViewById(R.id.Sweets);
        Extras = findViewById(R.id.Extras);
        Shoppingcart = findViewById(R.id.Shoppingcart);
        requiredquantity = findViewById(R.id.requiredquantity);
        Cancelorder = findViewById(R.id.Cancelorder);
        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

        Sweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseSweets();
            }
        });

        Extras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseExtras();
            }
        });

        Shoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToShoppingCart();
            }
        });
        Cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder();
            }
        });
    }

    private void backToMain() {
        Intent intent = new Intent(CandyActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void chooseSweets() {
        final String[] ramadanSweets = {"Kunafa", "Katayef", "Kulaj"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Ramadan Sweet");
        builder.setSingleChoiceItems(ramadanSweets, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectSweets = ramadanSweets[which];
            }
        });
        builder.setPositiveButton("Done", null);
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void chooseExtras() {
        final String[] extrasOptions = {"Nuts", "Cheese", "Cream", "Double Cream"};
        final boolean[] checkedItems = new boolean[extrasOptions.length];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Extras");

        builder.setMultiChoiceItems(extrasOptions, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedItems[which] = isChecked;
            }
        });

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedExtras = new StringBuilder();
                for (int i = 0; i < extrasOptions.length; i++) {
                    if (checkedItems[i]) {
                        selectedExtras.append(extrasOptions[i]).append(", ");
                    }
                }
                if (selectedExtras.length() > 0) {
                    selectedExtras.delete(selectedExtras.length() - 2, selectedExtras.length()); // Remove the last ", "
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void saveToShoppingCart() {
        selectedQuantity = requiredquantity.getText().toString().trim();
        if (!selectedQuantity.isEmpty()) {
            String extras = selectedExtras != null ? selectedExtras.toString() : "";

            SharedPreferences sharedPreferences = getSharedPreferences("Candy", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SELECTED_SWEETS_KEY, selectSweets);
            editor.putString(SELECTED_EXTRAS_KEY, extras);
            editor.putString(SELECTED_QUANTITY_KEY, selectedQuantity);
            double price = TotalPrice();
            editor.putFloat(TOTAL_PRICE_KEY, (float) price);
            editor.apply();

            Intent intent = new Intent(CandyActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(CandyActivity.this, "Please enter the required quantity", Toast.LENGTH_SHORT).show();
        }
    }


    private double TotalPrice() {
        double sweetPrice = 0;
        if (selectSweets != null) {
            switch (selectSweets) {
                case "Kunafa":
                    sweetPrice = 1.5;
                    break;
                case "Katayef":
                    sweetPrice = 2.0;
                    break;
                case "Kulaj":
                    sweetPrice = 1.8;
                    break;
            }
        }

        double extraPrice = 0;
        if (selectedExtras != null) {
            extraPrice = selectedExtras.toString().split(", ").length * 0.5; // Assuming each extra costs 0.5$
        }

        int quantity = Integer.parseInt(selectedQuantity);
        double totalPrice = (sweetPrice + extraPrice) * quantity;

        return totalPrice;
    }

    private void cancelOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancel Order");
        builder.setMessage("Are you sure you want to cancel the order?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectSweets = null;
                selectedExtras = null;
                selectedQuantity = null;
                requiredquantity.setText("");
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}
