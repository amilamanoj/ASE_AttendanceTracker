package attendancetracker.ase.com.ase_attendancetracker.view.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import attendancetracker.ase.com.ase_attendancetracker.R;
import attendancetracker.ase.com.ase_attendancetracker.model.AttendanceDetails;

/**
 * Created by Sangeeta on 19-01-2018.
 */

    public class ClassScheduleListAdapter extends RecyclerView.Adapter<ClassScheduleListAdapter.ClassScheduleHolder> {

    private ArrayList<AttendanceDetails> attendanceDetailsList;
    public ClassScheduleListAdapter(ArrayList<AttendanceDetails> attendanceDetailsList)
    {
        this.attendanceDetailsList = attendanceDetailsList;
    }

    @Override
    public ClassScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutorial_list_card_view_layout,parent,false);
        ClassScheduleHolder viewHolder = new ClassScheduleHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ClassScheduleHolder holder, int position) {
        AttendanceDetails attendanceDetails = attendanceDetailsList.get(position);
        holder.weekNumber.setText("Week "+attendanceDetails.getWeekId());
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd, yyyy hh:mm a");
        holder.date.setText(sdf.format(attendanceDetails.getDate()));
        holder.location.setText("01.11.018, Seminarraum (5611.01.018)");
        if(attendanceDetails.isAttended())
        {
            holder.presentedImg.setVisibility(View.VISIBLE);
            holder.presentedText.setVisibility(View.VISIBLE);
        }
        else {
            holder.presentedImg.setVisibility(View.INVISIBLE);
            holder.presentedText.setVisibility(View.INVISIBLE);
        }

        if (attendanceDetails.getDate().compareTo(new Date()) > 0)
        {
            holder.presentOrAbsentImg.setImageResource(R.drawable.not_yet_held_icon);
            holder.prentOrAbsentText.setText("Not yet held");
        }
        else if(attendanceDetails.isAttended())
        {
            holder.presentOrAbsentImg.setImageResource(R.drawable.present_icon);
            holder.prentOrAbsentText.setText("Attendend");
        }
        else {
            holder.presentOrAbsentImg.setImageResource(R.drawable.absent_icon);
            holder.prentOrAbsentText.setText("Not Attendend");
        }
    }

    @Override
    public int getItemCount() {
        if (attendanceDetailsList != null)
            return attendanceDetailsList.size();
        else
            return 0;

    }

    public static class ClassScheduleHolder extends RecyclerView.ViewHolder{

        TextView weekNumber, date, location, presentedText, prentOrAbsentText;
        ImageView presentedImg, presentOrAbsentImg;
        public ClassScheduleHolder(View itemView) {
            super(itemView);
            weekNumber = itemView.findViewById(R.id.weekNumber);
            date = itemView.findViewById(R.id.date);
            location = itemView.findViewById(R.id.location);
            presentedText = itemView.findViewById(R.id.presented_text);
            prentOrAbsentText = itemView.findViewById(R.id.present_text);
            presentedImg = itemView.findViewById(R.id.presented_icon);
            presentOrAbsentImg = itemView.findViewById(R.id.present_icon);
        }
    }
}
