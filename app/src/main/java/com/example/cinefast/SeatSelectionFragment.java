package com.example.cinefast;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SeatSelectionFragment extends Fragment {

    private TextView tvName,tvSeats,tvPrice;
    private GridLayout gridLayout;
    private List<TextView> selectedSeats=new ArrayList<>();
    private List<String> selectedSeatNames=new ArrayList<>();
    private List<String> occupiedSeats=new ArrayList<>();
    private int seatPrice=500;
    private String movieName="";
    private boolean isComingSoon=false;
    private String trailerUrl="";
    private String selectedDateTime="";

    public SeatSelectionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seat_selection,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        tvName=view.findViewById(R.id.tvName);
        tvSeats=view.findViewById(R.id.tvNumseats);
        tvPrice=view.findViewById(R.id.tvTotalPrice);
        gridLayout=view.findViewById(R.id.glSeats);
        ImageView ivBack=view.findViewById(R.id.IVBack);
        ivBack.setOnClickListener(v->requireActivity().getOnBackPressedDispatcher().onBackPressed());
    }
    public void setMovieData(String name,boolean isComingSoon,String trailerUrl) {
        this.movieName=name;
        this.isComingSoon=isComingSoon;
        this.trailerUrl=trailerUrl;
        if (getView()!=null) {
            tvName.setText(name);
            applyMovieTypeUI();
        }
    }

    private void applyMovieTypeUI() {
        if (getView()==null) return;
        MaterialButton btnSnacks=getView().findViewById(R.id.btnSnacks);
        MaterialButton btnBook=getView().findViewById(R.id.btnBookseats);
        selectedSeats.clear();
        selectedSeatNames.clear();
        selectedDateTime="";
        updateUI();
        if (!isComingSoon) {
            btnBook.setEnabled(false);
            btnSnacks.setEnabled(false);
        }
        initOccupiedSeats();
        for (int i=0;i< gridLayout.getChildCount();i++) {
            View seatView=gridLayout.getChildAt(i);
            if (!(seatView instanceof TextView)) continue;
            TextView seat=(TextView)seatView;
            String row=String.valueOf((char) ('A'+i/6));
            int col=(i%6)+1;
            String seatName=row+col;
            seat.setText("");
            if (occupiedSeats.contains(seatName)) {
                seat.setEnabled(false);
                seat.setBackgroundResource(R.drawable.seat_booked);
                seat.setAlpha(0.5f);
                seat.setOnClickListener(null);
            } else if (!isComingSoon) {
                seat.setEnabled(true);
                seat.setBackgroundResource(R.drawable.seat_available);
                seat.setAlpha(1f);
                seat.setOnClickListener(v->toggleSeat(seat,seatName,btnBook,btnSnacks));
            } else {
                seat.setEnabled(false);
                seat.setAlpha(0.5f);
                seat.setOnClickListener(null);
            }
        }
        if (isComingSoon) {
            btnBook.setText("Coming Soon");
            btnBook.setEnabled(false);
            btnSnacks.setText("Watch Trailer");
            btnSnacks.setOnClickListener(v->startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(trailerUrl))));
        } else {
            btnBook.setText("Book Seats");
            btnSnacks.setText("Proceed to Snacks");
            btnSnacks.setOnClickListener(v -> {
                if (selectedSeats.isEmpty()) {
                    Toast.makeText(getContext(), "Please select seats first", Toast.LENGTH_SHORT).show();
                    return;
                }
                showDateTimePickerForSnacks();
            });
            btnBook.setOnClickListener(v->{
                if (selectedSeats.isEmpty()) {
                    Toast.makeText(getContext(),"Please select seats first",Toast.LENGTH_SHORT).show();
                    return;
                }
                showDateTimePicker(btnBook,btnSnacks);
            });
        }
    }

    private String[] generateDates() {
        String[]dates=new String[5];
        java.util.Calendar cal=java.util.Calendar.getInstance();
        SimpleDateFormat dayFormat=new SimpleDateFormat("EEE",Locale.getDefault());
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMM",Locale.getDefault());
        for (int i=0;i<5;i++){
            if (i==0) {
                dates[i]="Today - "+dateFormat.format(cal.getTime());
            } else if (i==1) {
                dates[i]="Tomorrow - "+dateFormat.format(cal.getTime());
            } else {
                dates[i]=dayFormat.format(cal.getTime())+" - "+dateFormat.format(cal.getTime());
            }
            cal.add(java.util.Calendar.DAY_OF_YEAR,1);
        }
        return dates;
    }

    private void showDateTimePickerForSnacks() {
        String[] dates=generateDates();
        String[][] timesPerDate={
                {"6:00 PM","9:00 PM"},
                {"12:00 PM","3:00 PM","6:00 PM","9:00 PM"},
                {"12:00 PM","3:00 PM","6:00 PM","9:00 PM"},
                {"3:00 PM","6:00 PM","9:00 PM"},
                {"3:00 PM","6:00 PM","9:00 PM"}
        };
        new AlertDialog.Builder(requireContext())
                .setTitle("Select a date")
                .setItems(dates, (dialog,dateIndex) ->
                        new AlertDialog.Builder(requireContext())
                                .setTitle("Select a showtime")
                                .setItems(timesPerDate[dateIndex],(dialog2,timeIndex) -> {
                                    selectedDateTime=formatForStorage(dateIndex,timesPerDate[dateIndex][timeIndex]);
                                    if (getActivity() instanceof HomePage) {
                                        ArrayList<String>seatsToPass=new ArrayList<>(selectedSeatNames);
                                        int seatsTotal=selectedSeats.size()*seatPrice;
                                        ((HomePage) getActivity()).showSnacksFragment(
                                                movieName,
                                                seatsToPass,
                                                seatPrice,
                                                seatsTotal,
                                                selectedDateTime
                                        );
                                    }
                                })
                                .setNegativeButton("Back",(d,w) -> showDateTimePickerForSnacks())
                                .show()
                )
                .setNegativeButton("Cancel",null)
                .show();
    }
    private void showDateTimePicker(MaterialButton btnBook,MaterialButton btnSnacks) {
        String[] dates = generateDates();
        String[][] timesPerDate={
                {"6:00 PM", "9:00 PM"},
                {"12:00 PM", "3:00 PM", "6:00 PM", "9:00 PM"},
                {"12:00 PM", "3:00 PM", "6:00 PM", "9:00 PM"},
                {"3:00 PM", "6:00 PM", "9:00 PM"},
                {"3:00 PM", "6:00 PM", "9:00 PM"}
        };

        new AlertDialog.Builder(requireContext())
                .setTitle("Select a date")
                .setItems(dates,(dialog,dateIndex) ->
                        new AlertDialog.Builder(requireContext())
                                .setTitle("Select a showtime")
                                .setItems(timesPerDate[dateIndex],(dialog2,timeIndex)->{
                                    selectedDateTime=formatForStorage(dateIndex,timesPerDate[dateIndex][timeIndex]);
                                    if (getActivity() instanceof HomePage) {
                                        ArrayList<String> seatsToPass=new ArrayList<>(selectedSeatNames);
                                        int total=selectedSeats.size()*seatPrice;
                                        Toast.makeText(getContext(),"Booked for "+dates[dateIndex]+" at "+timesPerDate[dateIndex][timeIndex],
                                                Toast.LENGTH_LONG).show();
                                        ((HomePage) getActivity()).showTicketSummaryFragment(
                                                movieName,seatsToPass,seatPrice,new ArrayList<>(),selectedDateTime);
                                        ((HomePage) getActivity()).saveLastBooking(movieName,selectedSeats.size(),total);
                                    }
                                })
                                .setNegativeButton("Back",(d,w)-> showDateTimePicker(btnBook,btnSnacks))
                                .show()
                )
                .setNegativeButton("Cancel",null)
                .show();
    }
    private String formatForStorage(int dateIndex, String time) {
        java.util.Calendar cal=java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_YEAR, dateIndex);
        SimpleDateFormat dateSdf=new SimpleDateFormat("dd.MM.yyyy",Locale.getDefault());
        String datePart=dateSdf.format(cal.getTime());
        String timePart=to24Hour(time);
        return datePart + ", " + timePart;
    }

    private String to24Hour(String time) {
        try {
            SimpleDateFormat from=new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
            SimpleDateFormat to=new SimpleDateFormat("HH:mm",Locale.getDefault());
            return to.format(from.parse(time));
        } catch (Exception e) {
            return time;
        }
    }
    private void toggleSeat(TextView seat,String seatName,MaterialButton btnBook,MaterialButton btnSnacks) {
        if (selectedSeats.contains(seat)) {
            selectedSeats.remove(seat);
            selectedSeatNames.remove(seatName);
            seat.setBackgroundResource(R.drawable.seat_available);
        } else {
            selectedSeats.add(seat);
            selectedSeatNames.add(seatName);
            seat.setBackgroundResource(R.drawable.seat_selected);
        }
        updateUI();
        boolean hasSelection=!selectedSeats.isEmpty();
        btnBook.setEnabled(hasSelection);
        btnSnacks.setEnabled(hasSelection);
    }
    private void initOccupiedSeats() {
        occupiedSeats.clear();
        occupiedSeats.add("A1");
        occupiedSeats.add("B3");
        occupiedSeats.add("C5");
    }

    private void updateUI() {
        int count=selectedSeats.size();
        int total=count * seatPrice;
        tvSeats.setText("Seats: " + count);
        tvPrice.setText("Total: Rs " + total);
    }
}