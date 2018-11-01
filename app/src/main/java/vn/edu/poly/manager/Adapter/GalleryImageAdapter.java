package vn.edu.poly.manager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.poly.manager.Model.LietKeGalleryContructor;

public class GalleryImageAdapter extends BaseAdapter {
    ArrayList<LietKeGalleryContructor> arrayList;
    Context context;
    LayoutInflater inflater;

    public GalleryImageAdapter(ArrayList<LietKeGalleryContructor> arrayList, Context context) {
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
        ImageView i = new ImageView(context);
        LietKeGalleryContructor contructor = arrayList.get(position);
        Picasso.get()
                .load(contructor.getImages())
                .into(i);
        i.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

//        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
    }
}
