package vn.edu.poly.manager.Adapter;

import android.content.Context;
import android.util.Log;
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
    LayoutInflater layoutInflater;
    String notificationsCount;
    String NOTIFICATIONS = "Notifications";

    public MenuAdapter(Context context, List<MenuModel> modelList, String notificationsCount) {
        this.context = context;
        this.modelList = modelList;
        this.notificationsCount = notificationsCount;
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
        TextView txt_title_menu, txt_badge_menu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        MenuModel menuModel = modelList.get(position);
        Log.d("MENUADAPTER", menuModel.getTitle());
        if (convertView == null){
            viewHolder = new ViewHolder();
            if (!menuModel.getTitle().equalsIgnoreCase(NOTIFICATIONS)){
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.row_menu, null);
            } else {
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.item_notification_menu, null);
                viewHolder.txt_badge_menu = convertView.findViewById(R.id.txt_badge_menu);
            }
            viewHolder.txt_title_menu = convertView.findViewById(R.id.txt_title_menu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (menuModel.getTitle().equalsIgnoreCase(NOTIFICATIONS)){
            if (!notificationsCount.equalsIgnoreCase("0")){
                viewHolder.txt_badge_menu.setText(notificationsCount);
            } else {
                viewHolder.txt_badge_menu.setVisibility(View.GONE);
            }
        }
        viewHolder.txt_title_menu.setText(menuModel.getTitle());
        return convertView;
    }
}
