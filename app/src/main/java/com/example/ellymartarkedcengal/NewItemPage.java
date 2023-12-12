package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ellymartarkedcengal.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewItemPage extends AppCompatActivity {

    private static final int PICK_PHOTO_REQUEST = 1;
    private DatabaseReference databaseReference;

    private ImageView imageViewItem;
    private Button buttonPickPhoto, buttonSaveItem;
    private EditText editTextItemName, editTextItemPrice, editTextItemDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_page);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        imageViewItem = findViewById(R.id.imageViewItem);
        buttonPickPhoto = findViewById(R.id.buttonPickPhoto);
        buttonSaveItem = findViewById(R.id.buttonSaveItem);
        editTextItemName = findViewById(R.id.editTextItemName);
        editTextItemPrice = findViewById(R.id.editTextItemPrice);
        editTextItemDescription = findViewById(R.id.editTextItemDescription);
        buttonPickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewItem.setImageResource(R.drawable.upload);
            }
        });
        buttonSaveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewItem();
            }
        });
    }

    private void saveNewItem() {

        String itemName = editTextItemName.getText().toString();
        double itemPrice = Double.parseDouble(editTextItemPrice.getText().toString());
        String itemDescription = editTextItemDescription.getText().toString();

        // Check if itemPrice is not empty before parsing
        if (!itemName.isEmpty()) {

            // Use the productId as the key for the product
            Products newItem = new Products(itemName, itemPrice, itemDescription,0);
            DatabaseReference newProductRef = databaseReference.child("products").push();
            String productId = newProductRef.getKey();
            newItem.setProductId(productId);

            // Save the new item to the "products" node with the generated productId
            newProductRef.setValue(newItem)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(NewItemPage.this, "Item saved successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(NewItemPage.this, "Error saving item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(NewItemPage.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }


}
