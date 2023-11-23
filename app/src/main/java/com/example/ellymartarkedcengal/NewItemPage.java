package com.example.ellymartarkedcengal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewItemPage extends AppCompatActivity {

    private static final int PICK_PHOTO_REQUEST = 1;

    private ImageView imageViewItem;
    private Button buttonPickPhoto, buttonSaveItem;
    private EditText editTextItemName, editTextItemPrice, editTextItemDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_page);

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
                saveItem();
            }
        });
    }
    private void saveItem() {
        // Implement logic to save item details, including photo URI
        String itemName = editTextItemName.getText().toString();
        String itemPrice = editTextItemPrice.getText().toString();
        String itemDescription = editTextItemDescription.getText().toString();

    }
}
