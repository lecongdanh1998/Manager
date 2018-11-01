package vn.edu.poly.manager.Adapter;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.poly.manager.Model.CategoryContructor;
import vn.edu.poly.manager.R;

public class CategoryAdapter extends BaseAdapter {
    LayoutInflater inflater ;
    Context context;
    ArrayList<CategoryContructor> arrayList;

    public CategoryAdapter(Context context, ArrayList<CategoryContructor> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_category,null);
            viewHolder.txtiD = convertView.findViewById(R.id.txtId);
            viewHolder.txtName = convertView.findViewById(R.id.txtName);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CategoryContructor contructor = arrayList.get(position);
        viewHolder.txtiD.setText(contructor.getIdCategory());
        viewHolder.txtName.setText(contructor.getNaemCategory());


        return convertView;
    }
    class ViewHolder{
        TextView txtiD,txtName;
    }
}
