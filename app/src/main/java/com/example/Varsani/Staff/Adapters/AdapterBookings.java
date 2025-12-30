package com.example.Varsani.Staff.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Staff.Finance.OrderDetails;
import com.example.Varsani.Staff.Finance.PaymentDetails;
import com.example.Varsani.Staff.Models.BookingModel;
import com.example.Varsani.Staff.Models.ClientOrderModel;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import java.util.List;

public class AdapterBookings extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BookingModel> items;

    private Context ctx;
    ProgressDialog progressDialog;
//    private OnItemClickListener mOnItemClickListener;
//    private OnMoreButtonClickListener onMoreButtonClickListener;

    //

    private SessionHandler session;
    private UserModel user;
    private String clientId = "";
    private String orderID = "";

    public static final String TAG = "Orders adapter";

//    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
//        this.mOnItemClickListener = mItemClickListener;
//    }
//
//    public void setOnMoreButtonClickListener(final OnMoreButtonClickListener onMoreButtonClickListener) {
//        this.onMoreButtonClickListener = onMoreButtonClickListener;
//    }

    public AdapterBookings(Context context, List<BookingModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_bookingID,txv_client, txv_course,txv_status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_client =v.findViewById(R.id.txv_client);
            txv_bookingID =v.findViewById(R.id.txv_bookingID);
            txv_course = v.findViewById(R.id.txv_course);
            txv_status = v.findViewById(R.id.txv_status);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_booking_item, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final BookingModel o= items.get(position);

            view.txv_bookingID.setText("Booking ID: "+o.getBookingID());
            view.txv_client.setText("Client: " + o.getClientName());
            view.txv_course.setText("Course: "+o.getCourse());
            view.txv_status.setText("Status: "+o.getBookingStatus());


            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in=new Intent(ctx, PaymentDetails.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("bookingID", o.getBookingID());
                    in.putExtra("clientID",o.getClientID());
                    in.putExtra("clientName",o.getClientName());
                    in.putExtra("course",o.getCourse());
                    in.putExtra("studyMode",o.getStudyMode());
                    in.putExtra("fee",o.getFee());
                    in.putExtra("duration",o.getDuration());
                    in.putExtra("startingDate",o.getStartingDate());
                    in.putExtra("paymentMethod",o.getPaymentMethod());
                    in.putExtra("paymentCode",o.getPaymentCode());
                    in.putExtra("bookingDate",o.getBookingDate());
                    in.putExtra("phoneNo",o.getPhoneNo());
                    in.putExtra("bookingStatus",o.getBookingStatus());
                    ctx.startActivity(in);


                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

//    public interface OnItemClickListener {
//        void onItemClick(View view, ProductModal obj, int pos);
//    }
//
//    public interface OnMoreButtonClickListener {
//        void onItemClick(View view, ProductModal obj, MenuItem item);
//    }



}