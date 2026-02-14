package com.example.Varsani.Clients.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Clients.BookingDetails;
import com.example.Varsani.Clients.Models.BookingHistoryModel;
import com.example.Varsani.R;

import java.util.List;

public class AdapterBookingHistory extends RecyclerView.Adapter<AdapterBookingHistory.BookingViewHolder> {
    private List<BookingHistoryModel> bookingList;
    private Context context;

    // Constructor that accepts context and booking list
    public AdapterBookingHistory(Context context, List<BookingHistoryModel> bookingList) {
        this.context = context; // Assign the passed context to the class context variable
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_order_card, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        // Get the booking item at the current position
        BookingHistoryModel booking = bookingList.get(position);

        // Set the booking details to the TextViews in the ViewHolder
        holder.bookingID.setText("Booking ID: " + booking.getBookingID());
        holder.course.setText("Course: " + booking.getCourse());
        holder.bookingDate.setText("Booking Date: " + booking.getBookingDate());

        // Set an OnClickListener to navigate to BookingDetails with all details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start BookingDetails activity
                Intent intent = new Intent(context, BookingDetails.class);

                // Passing all booking details as extras
                intent.putExtra("bookingID", booking.getBookingID());
                intent.putExtra("clientName", booking.getClientName());
                intent.putExtra("contact", booking.getPhoneNo());
                intent.putExtra("course", booking.getCourse());
                intent.putExtra("studyMode", booking.getStudyMode());
                intent.putExtra("fee", booking.getFee());
                intent.putExtra("duration", booking.getDuration());
                intent.putExtra("startingDate", booking.getStartingDate());
                intent.putExtra("paymentMode", booking.getPaymentMethod());
                intent.putExtra("paymentCode", booking.getPaymentCode());
                intent.putExtra("bookingDate", booking.getBookingDate());
                intent.putExtra("status", booking.getBookingStatus());
                intent.putExtra("remarks", booking.getRemarks());

                // Start the BookingDetails activity
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size(); // Return the total number of items in the list
    }

    // ViewHolder class to hold references to each item view
    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView bookingID, course, bookingDate;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the TextViews
            bookingID = itemView.findViewById(R.id.bookingID);
            course = itemView.findViewById(R.id.course);
            bookingDate = itemView.findViewById(R.id.bookingDate);
        }
    }
}
