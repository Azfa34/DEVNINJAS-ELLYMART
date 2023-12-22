package com.example.ellymartarkedcengal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

    private LinearLayout notificationContainer;
    private AlertDialog alertDialog;
    private int newCardCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationContainer = findViewById(R.id.notificationContainer);

        addInitialNotificationCard();

        Button btnClearNotifications = findViewById(R.id.btnClearNotifications);

        btnClearNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearNotifications();
            }
        });
    }

    private void showAddNotificationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_notification, null);

        EditText editTextTopic = dialogView.findViewById(R.id.editTextTopic);
        EditText editTextContent = dialogView.findViewById(R.id.editTextContent);

        builder.setView(dialogView)
                .setTitle("Add Notification")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Extract the entered data
                        String title = editTextTopic.getText().toString().trim();
                        String content = editTextContent.getText().toString().trim();

                        // Create and display a new card with the entered data
                        createNewCard(title, content);
                    }
                })
                .setNegativeButton("Cancel", null);

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void createNewCard(String title, String content) {
        View cardView = getLayoutInflater().inflate(R.layout.card_notification_new, null);

        TextView notificationTextView = cardView.findViewById(R.id.notificationTextView);
        notificationTextView.setText(title + "\n" + content);

        Button btnSeeDetails = cardView.findViewById(R.id.btnSeeDetails);
        btnSeeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationDetailsDialog(title, content);
            }
        });

        notificationContainer.addView(cardView, notificationContainer.getChildCount() - 1);

        newCardCount++;
    }

    private void showNotificationDetailsDialog(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_notification, null);

        TextView textViewTopic = dialogView.findViewById(R.id.textViewTopic);
        TextView textViewContent = dialogView.findViewById(R.id.textViewContent);

        textViewTopic.setText(title);
        textViewContent.setText(content);

        Button btnClose = dialogView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        builder.setView(dialogView)
                .setTitle("Notification Details");

        alertDialog = builder.create();
        alertDialog.show();
    }
    private void addInitialNotificationCard() {
        View initialCardView = getLayoutInflater().inflate(R.layout.card_notification, null);
        ImageButton addButton = initialCardView.findViewById(R.id.imageButtonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNotificationDialog();
            }
        });

        notificationContainer.addView(initialCardView);
    }

    private void clearNotifications() {
        int childCount = notificationContainer.getChildCount();
        if (childCount > 2) {
            for (int i = childCount - 2; i >= 1; i--) {
                notificationContainer.removeViewAt(i);
            }
            newCardCount = 0;
        }
    }
}