package vn.edu.poly.manager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.poly.manager.Model.POSTContructor;
import vn.edu.poly.manager.R;
import vn.edu.poly.manager.Model.POSTContructor;
import vn.edu.poly.manager.R;

public class POSTAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<POSTContructor> arrayList;

    public POSTAdapter(Context context, ArrayList<POSTContructor> arrayList) {
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
            convertView = inflater.inflate(R.layout.custom_lst_post,null);
            viewHolder.imgTitle = (ImageView) convertView.findViewById(R.id.img_title_post);
            viewHolder.imgPeople = (ImageView) convertView.findViewById(R.id.img_logo);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txt_title_post);
            viewHolder.txtTime = (TextView) convertView.findViewById(R.id.txt_time_post);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        POSTContructor contructor = arrayList.get(position);
        viewHolder.txtTitle.setText(contructor.getTxt_title());
        viewHolder.txtTime.setText(contructor.getTxt_Time());
        Picasso.get()
                .load(contructor.getImg_title())
                .resize(350,200)
                .placeholder(R.drawable.gallery100)
                .error(R.drawable.gallery100)
                .into(viewHolder.imgTitle);
        Picasso.get()
                .load(contructor.getImg_people())
                .placeholder(R.drawable.person40)
                .error(R.drawable.person40)
                .into(viewHolder.imgPeople);
        return convertView;
    }
    class ViewHolder{
        TextView txtTitle,txtTime;
        ImageView imgTitle,imgPeople;
    }
}
