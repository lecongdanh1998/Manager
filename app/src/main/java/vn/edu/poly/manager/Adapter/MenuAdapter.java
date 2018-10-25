package vn.edu.poly.manager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import vn.edu.poly.manager.Model.MenuModel;
import vn.edu.poly.manager.R;

public class MenuAdapter extends BaseAdapter {

    Context context;
    List<MenuModel> modelList;
    int layout;
    LayoutInflater layoutInflater;

    public MenuAdapter(Context context, List<MenuModel> modelList, int layout) {
        this.context = context;
        this.modelList = modelList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView txt_title_menu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            viewHolder = new ViewHolder();
            viewHolder.txt_title_menu = convertView.findViewById(R.id.txt_title_menu);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MenuModel menuModel = modelList.get(position);
        viewHolder.txt_title_menu.setText(menuModel.getTitle());

        return convertView;
    }
}
