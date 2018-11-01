package vn.edu.poly.manager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.poly.manager.Model.LietKeGalleryContructor;
import vn.edu.poly.manager.R;

public class LietKeAdapter extends BaseAdapter {
    ArrayList<LietKeGalleryContructor> arrayList;
    Context context;
    LayoutInflater inflater;

    public LietKeAdapter(ArrayList<LietKeGalleryContructor> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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
            convertView = inflater.inflate(R.layout.custom_lst_lietke,null);
            viewHolder.imageView = convertView.findViewById(R.id.img_lietke);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LietKeGalleryContructor contructor = arrayList.get(position);
        Picasso.get()
                .load(contructor.getImages())
                .into(viewHolder.imageView);


        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
    }


}
