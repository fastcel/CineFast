package com.example.cinefast;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private List<Booking> list;
    private Context context;
    public BookingAdapter(Context context,List<Booking> list) {
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view=LayoutInflater.from(context)
                .inflate(R.layout.item_booking,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        Booking b=list.get(position);
        holder.title.setText(b.getMovieName());
        holder.date.setText(b.getDateTime());
        holder.tickets.setText(b.getTickets()+" Tickets");
        int resId = context.getResources().getIdentifier(
                b.getPoster(),
                "drawable",
                context.getPackageName()
        );
        holder.poster.setImageResource(resId);
        holder.delete.setOnClickListener(v->showCancelDialog(b, position));
    }
    private void showCancelDialog(Booking booking, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Cancel Booking")
                .setMessage("Are you sure you want to cancel this booking?")
                .setPositiveButton("Yes",(dialog,which) -> {

                    if (!isFutureBooking(booking.getDateTime())) {
                        Toast.makeText(
                                context,
                                "Cannot cancel past booking",
                                Toast.LENGTH_SHORT
                        ).show();
                        return;
                    }
                    FirebaseDatabase.getInstance()
                            .getReference("bookings")
                            .child(booking.getId())
                            .removeValue();
                    list.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(
                            context,
                            "Booking Cancelled Successfully",
                            Toast.LENGTH_SHORT
                    ).show();
                })
                .setNegativeButton("No",null)
                .show();
    }
    private boolean isFutureBooking(String dateTime) {
        try {
            SimpleDateFormat sdf=new SimpleDateFormat(
                    "dd.MM.yyyy, HH:mm",
                    Locale.getDefault()
            );
            Date bookingDate=sdf.parse(dateTime);
            return bookingDate.after(new Date());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView poster,delete;
        TextView title,date,tickets;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster=itemView.findViewById(R.id.imgPoster);
            delete=itemView.findViewById(R.id.btnDelete);
            title=itemView.findViewById(R.id.txtTitle);
            date=itemView.findViewById(R.id.txtDate);
            tickets=itemView.findViewById(R.id.txtTickets);
        }
    }
}