package vn.edu.poly.manager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.poly.manager.Model.GalleryContructor;
import vn.edu.poly.manager.R;


public class GalleryAdapterListview extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<GalleryContructor> arrayList;

    public GalleryAdapterListview(Context context, ArrayList<GalleryContructor> arrayList) {
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
            convertView = inflater.inflate(R.layout.custom_listview_gallery,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img_listview_customgallery);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GalleryContructor contructor = arrayList.get(position);
        Picasso.get()
                .load(contructor.getImgGallery())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(viewHolder.imageView);
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
    }
}
