package attendancetracker.ase.com.ase_attendancetracker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import attendancetracker.ase.com.ase_attendancetracker.R;
import attendancetracker.ase.com.ase_attendancetracker.model.Attendance;

/**
 * Created by Tobias on 12/22/2017.
 */

public class AttendancesListViewAdapter extends BaseAdapter {

    private List<Attendance> attendances;
    private Context context;

    public AttendancesListViewAdapter(Context context, List<Attendance> attendances) {
        this.attendances = attendances;
        this.context = context;
    }


    @Override
    public int getCount() {
        return attendances.size();
    }

    @Override
    public Object getItem(int position) {
        return attendances.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.attendance_list_item, parent, false);
        TextView tv = item.findViewById(R.id.item_header);
        Attendance attendance = (Attendance) getItem(position);
        tv.setText(attendance.toString());
        return item;
    }
}
