package attendancetracker.ase.com.ase_attendancetracker.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import attendancetracker.ase.com.ase_attendancetracker.R;
import attendancetracker.ase.com.ase_attendancetracker.model.AttendanceDetails;

/**
 * Created by Tobias on 12/22/2017.
 */

public class AttendancesListViewAdapter extends BaseAdapter {

    private List<AttendanceDetails> attendanceDetails;
    private Context context;

    public AttendancesListViewAdapter(Context context, List<AttendanceDetails> attendanceDetails) {
        this.attendanceDetails = attendanceDetails;
        this.context = context;
    }


    @Override
    public int getCount() {
        return attendanceDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return attendanceDetails.get(position);
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
        AttendanceDetails attendanceDetails = (AttendanceDetails) getItem(position);
        tv.setText(attendanceDetails.toString());
        return item;
    }
}
