package com.example.cinefast;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TicketSummaryFragment extends Fragment {

    private ArrayList<String> selectedSeats;
    private ArrayList<String> snacks;
    private int seatPrice;
    private String movieName;

    public TicketSummaryFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ticket_summary,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        if (snacks==null) snacks=new ArrayList<>();
        if (movieName==null) movieName="N/A";
        updateTicketInfo(movieName,selectedSeats,seatPrice,snacks);
        view.findViewById(R.id.btnSend).setOnClickListener(v ->shareTicket());
        ImageView ivBack=view.findViewById(R.id.IVBack);
        ivBack.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

    }
    private void shareTicket()
    {
        int numTickets=(selectedSeats != null)?selectedSeats.size():0;
        int seatTotal=numTickets*seatPrice;
        int snacksTotal=0;
        if (snacks!=null) {
            for (String s:snacks)
            {
                String[] parts=s.split(" x");
                String name=parts[0];
                int qty=(parts.length>1)?Integer.parseInt(parts[1]):1;
                snacksTotal+=getSnackPrice(name)*qty;
            }
        }
        int totalPrice=seatTotal+snacksTotal;
        String ticketText="Movie: "+(movieName!=null?movieName:"N/A")+"\n"+"Tickets Booked: "+numTickets+"\n"+"Total Price: Rs "+totalPrice;
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, ticketText);
        startActivity(Intent.createChooser(intent,"Share Ticket Via"));
    }
    private int getSnackPrice(String name) {
        switch (name) {
            case "Popcorn":return 499;
            case "Nachos":return 799;
            case "Soft Drinks":return 599;
            case "Candy Mix":return 699;
            default:return 0;
        }
    }
    public void setData(String movieName,ArrayList<String> selectedSeats,int seatPrice,ArrayList<String> snacks) {
        this.movieName=movieName;
        this.selectedSeats=selectedSeats;
        this.seatPrice=seatPrice;
        this.snacks=snacks;

        if (getView()!=null)
        {
            updateTicketInfo(movieName,selectedSeats,seatPrice,snacks);
        }
        saveLastBooking();
    }
    private void saveLastBooking()
    {
        int seatTotal=(selectedSeats!=null?selectedSeats.size():0)*seatPrice;
        int snacksTotal=0;
        if (snacks!=null)
        {
            for (String s:snacks)
            {
                String[] parts=s.split(" x");
                String name=parts[0];
                int qty=(parts.length>1)?Integer.parseInt(parts[1]):1;
                snacksTotal+=getSnackPrice(name)*qty;
            }
        }
        int finalTotal=seatTotal+snacksTotal;
        if (getActivity()!=null) {
            getActivity().getSharedPreferences("LAST_BOOKING", Context.MODE_PRIVATE)
                    .edit()
                    .putString("movieName",movieName)
                    .putInt("seats",selectedSeats!=null?selectedSeats.size():0)
                    .putInt("total",finalTotal)
                    .apply();
        }
    }
    public void updateTicketInfo(String movieName,ArrayList<String> selectedSeats,int seatPrice,ArrayList<String>snacks) {
        View view=getView();
        if (view==null)return;
        LinearLayout llTickets=view.findViewById(R.id.llTickets);
        LinearLayout llSnacks=view.findViewById(R.id.llSnacks);
        LinearLayout llGrandTotal=view.findViewById(R.id.llGrandTotal);
        TextView tvName=view.findViewById(R.id.tvName1);
        tvName.setText(movieName);
        llTickets.removeAllViews();
        if (selectedSeats!=null && !selectedSeats.isEmpty())
        {
            for (String seat:selectedSeats) {
                LinearLayout rowLayout=new LinearLayout(getContext());
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                TextView tvSeat=new TextView(getContext());
                String row=seat.substring(0, 1);
                String col=seat.substring(1);
                tvSeat.setText("Row " + row + ", Seat " + col);
                tvSeat.setTextColor(Color.WHITE);
                tvSeat.setTextSize(18f);
                tvSeat.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                TextView tvPrice=new TextView(getContext());
                tvPrice.setText("Rs " + seatPrice);
                tvPrice.setTextColor(Color.WHITE);
                tvPrice.setTextSize(18f);
                tvPrice.setGravity(Gravity.END);
                tvPrice.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                rowLayout.addView(tvSeat);
                rowLayout.addView(tvPrice);
                llTickets.addView(rowLayout);
            }
        } else {
            TextView tvNoSeats=new TextView(getContext());
            tvNoSeats.setText("No seats selected");
            tvNoSeats.setTextColor(Color.WHITE);
            tvNoSeats.setTextSize(18f);
            llTickets.addView(tvNoSeats);
        }
        llSnacks.removeAllViews();
        int snacksTotal=0;
        if (snacks!=null && !snacks.isEmpty())
        {
            for (String s:snacks) {
                String[] parts=s.split(" x");
                String snackName=parts[0];
                int quantity=parts.length>1?Integer.parseInt(parts[1]):1;
                int pricePerItem=0;
                switch (snackName) {
                    case "Popcorn":pricePerItem = 499; break;
                    case "Nachos":pricePerItem = 799; break;
                    case "Soft Drinks":pricePerItem = 599; break;
                    case "Candy Mix":pricePerItem = 699; break;
                }
                int totalPrice=pricePerItem*quantity;
                snacksTotal+=totalPrice;
                LinearLayout rowLayout=new LinearLayout(getContext());
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                TextView tvSnackName=new TextView(getContext());
                tvSnackName.setText(snackName + " x" + quantity);
                tvSnackName.setTextColor(Color.WHITE);
                tvSnackName.setTextSize(18f);
                tvSnackName.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                TextView tvSnackPrice=new TextView(getContext());
                tvSnackPrice.setText("Rs "+totalPrice);
                tvSnackPrice.setTextColor(Color.WHITE);
                tvSnackPrice.setTextSize(18f);
                tvSnackPrice.setGravity(Gravity.END);
                tvSnackPrice.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                rowLayout.addView(tvSnackName);
                rowLayout.addView(tvSnackPrice);
                llSnacks.addView(rowLayout);
            }
        } else {
            TextView tvNoSnacks=new TextView(getContext());
            tvNoSnacks.setText("No snacks selected");
            tvNoSnacks.setTextColor(Color.WHITE);
            tvNoSnacks.setTextSize(18f);
            llSnacks.addView(tvNoSnacks);
        }

        llGrandTotal.removeAllViews();
        TextView tvTotal=new TextView(getContext());
        int total=(selectedSeats!=null?selectedSeats.size():0)*seatPrice+snacksTotal;
        tvTotal.setText("Total: Rs "+total);
        tvTotal.setTextColor(Color.WHITE);
        tvTotal.setTextSize(20f);
        llGrandTotal.addView(tvTotal);
    }
}