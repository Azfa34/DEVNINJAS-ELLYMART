package com.example.ellymartarkedcengal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ScrollView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Cust_NotificationReport extends AppCompatActivity {
    private DatabaseReference databaseReference;

    private LinearLayout notificationContainer;
    private AlertDialog alertDialog;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_notification_report);
        databaseReference = FirebaseDatabase.getInstance().getReference("notifications");

        notificationContainer = findViewById(R.id.notificationContainerCustomer);
        scrollView = findViewById(R.id.scrollViewCustomer);

        Button btnClearNotifications = findViewById(R.id.btnClearNotificationsCustomer);

        btnClearNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearNotifications();
            }
        });
        displayNotificationsFromFirebase();
    }

    private void displayNotificationsFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot notificationSnapshot : dataSnapshot.getChildren()) {
                    String title = notificationSnapshot.child("title").getValue(String.class);
                    String content = notificationSnapshot.child("content").getValue(String.class);

                    // Create and display a card with the notification data
                    createNewCustomerCard(title, content);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void createNewCustomerCard(String title, String content) {
        View cardView = getLayoutInflater().inflate(R.layout.card_notification_customer, null);

        TextView notificationTextView = cardView.findViewById(R.id.notificationTextViewCustomer);
        notificationTextView.setText(title + "\n" + content);

        Button btnSeeDetails = cardView.findViewById(R.id.btnSeeDetailsCustomer);
        btnSeeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationDetailsDialog(title, content);
            }
        });

        notificationContainer.addView(cardView);
    }

    private void showNotificationDetailsDialog(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_cust_notification_details, null);

        TextView textViewTopic = dialogView.findViewById(R.id.textViewTopicDetailsCustomer);
        TextView textViewContent = dialogView.findViewById(R.id.textViewContentDetailsCustomer);

        textViewTopic.setText(title);
        textViewContent.setText(content);

        Button btnClose = dialogView.findViewById(R.id.btnCloseDetailsCustomer);
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

    private void clearNotifications() {
        LinearLayout notificationContainer = findViewById(R.id.notificationContainerCustomer);

        for (int i = 0; i < notificationContainer.getChildCount(); i++) {
            View childView = notificationContainer.getChildAt(i);
            if (!(childView instanceof Button)) {
                notificationContainer.removeView(childView);
            }
        }
    }
}