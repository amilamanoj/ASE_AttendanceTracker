package attendancetracker.ase.com.ase_attendancetracker.view.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import attendancetracker.ase.com.ase_attendancetracker.R;

public class SchedulePieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_pie_chart);
        setUpPie();
    }

    private void setUpPie() {
        List<PieEntry> pieEntry = new ArrayList<PieEntry>();
        pieEntry.add(new PieEntry(40, "Attendend"));
        pieEntry.add(new PieEntry(30, "NotAttended"));
        pieEntry.add(new PieEntry(30, "Not yet Held"));

        PieDataSet pieDataSet = new PieDataSet(pieEntry,"");
        pieDataSet.setColors(Color.rgb(51,204,51),Color.rgb(204,51,51),Color.rgb(51,89,204));


        PieChart pieChart = (PieChart) findViewById(R.id.pie_chart);
        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(10,15,10,15);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.getDescription().setEnabled(false);

        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);


        pieChart.setData(pieData);
        pieChart.invalidate();


    }
}
