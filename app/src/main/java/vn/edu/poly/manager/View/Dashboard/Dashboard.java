package vn.edu.poly.manager.View.Dashboard;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.manager.R;

public class Dashboard extends Fragment {

    private View view;
    private LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        lineChart = view.findViewById(R.id.chart_dashboard);

        ArrayList<Entry> yValuesPost = new ArrayList<>();
        yValuesPost.add(new Entry(0, 30f));
        yValuesPost.add(new Entry(1, 50f));
        yValuesPost.add(new Entry(2, 20f));
        yValuesPost.add(new Entry(3, 60f));
        yValuesPost.add(new Entry(4, 80f));
        yValuesPost.add(new Entry(5, 90f));
        yValuesPost.add(new Entry(6, 20f));
        yValuesPost.add(new Entry(7, 30f));
        yValuesPost.add(new Entry(8, 40f));
        yValuesPost.add(new Entry(9, 70f));
        yValuesPost.add(new Entry(10, 90f));
        yValuesPost.add(new Entry(11, 10f));

        ArrayList<Entry> yValuesContact = new ArrayList<>();
        yValuesContact.add(new Entry(0, 10f));
        yValuesContact.add(new Entry(1, 20f));
        yValuesContact.add(new Entry(2, 40f));
        yValuesContact.add(new Entry(3, 20f));
        yValuesContact.add(new Entry(4, 30f));
        yValuesContact.add(new Entry(5, 60f));
        yValuesContact.add(new Entry(6, 30f));
        yValuesContact.add(new Entry(7, 50f));
        yValuesContact.add(new Entry(8, 90f));
        yValuesContact.add(new Entry(9, 50f));
        yValuesContact.add(new Entry(10, 20f));
        yValuesContact.add(new Entry(11, 20f));

        LineDataSet lineDataPost = lineDataSet(yValuesPost, "Post", Color.BLUE);
        LineDataSet lineDataContact = lineDataSet(yValuesContact, "Contact", Color.GREEN);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(lineDataPost);
        sets.add(lineDataContact);

        LineData lineData = new LineData(sets);
        lineChart.setData(lineData);

        configChart(lineChart);
        return view;
    }

    public void configChart(LineChart lineChart) {
        lineChart.getLegend().setEnabled(false);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.getDescription().setEnabled(false);
        lineChart.setVisibleXRangeMaximum(5);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setEnabled(false);
        yAxisLeft.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "roboto_light.ttf"));

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setEnabled(false);
        yAxisRight.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "roboto_light.ttf"));

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "roboto_medium.ttf"));
        xAxis.setValueFormatter(new FommaterxAxis(xAxisValue()));
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    public LineDataSet lineDataSet(List<Entry> entries, String labels, int color) {
        LineDataSet lineDataSet = new LineDataSet(entries, labels);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setValueTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "roboto_light.ttf"));
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(color);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillAlpha(110);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setValueTextColor(color);
        lineDataSet.setCubicIntensity(0.2f);
        return lineDataSet;
    }

    public List<String> xAxisValue() {
        List<String> xAxis = new ArrayList<>();
        xAxis.add("Jan");
        xAxis.add("Feb");
        xAxis.add("Mar");
        xAxis.add("Apr");
        xAxis.add("May");
        xAxis.add("Jun");
        xAxis.add("Jul");
        xAxis.add("Aug");
        xAxis.add("Sep");
        xAxis.add("Oct");
        xAxis.add("Nov");
        xAxis.add("Dec");
        return xAxis;
    }

    public class FommaterxAxis implements IAxisValueFormatter {

        List<String> xAxisValue;

        public FommaterxAxis(List<String> xAxisValue) {
            this.xAxisValue = xAxisValue;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return xAxisValue.get((int) value);
        }
    }
}
