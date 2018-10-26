package vn.edu.poly.manager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.edu.poly.manager.Model.SettingModel;
import vn.edu.poly.manager.R;

public class SettingAdapter extends BaseAdapter {

    List<SettingModel> settingModelList;
    Context context;

    public SettingAdapter(List<SettingModel> settingModelList, Context context) {
        this.settingModelList = settingModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return settingModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return settingModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView txt_description_setting, txt_title_setting;
        ImageView img_setting;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_item_setting, null);
            viewHolder = new ViewHolder();
            viewHolder.txt_title_setting = convertView.findViewById(R.id.txt_title_setting);
            viewHolder.txt_description_setting = convertView.findViewById(R.id.txt_description_setting);
            viewHolder.img_setting = convertView.findViewById(R.id.img_setting);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SettingModel settingModel = settingModelList.get(position);
        viewHolder.txt_title_setting.setText(settingModel.getDisplay_name());
        viewHolder.txt_description_setting.setText(settingModel.getValue());
        if (settingModel.getType().equals("image") && settingModel.getValue().length() > 0){
            Picasso.get().load(settingModel.getValue()).into(viewHolder.img_setting);
        }
        return convertView;
    }
}
