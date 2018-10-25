package vn.edu.poly.manager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.poly.manager.Model.MySiteContructor;
import vn.edu.poly.manager.R;

public class MySiteAdapter extends BaseAdapter {
    Context context;
    ArrayList<MySiteContructor> arrayList;
    LayoutInflater inflater;

    public MySiteAdapter(Context context, ArrayList<MySiteContructor> arrayList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.lst_mysite,null);
            viewHolder = new ViewHolder();
            viewHolder.TitleSite = (TextView) convertView.findViewById(R.id.txt_title_LstMySite);
            viewHolder.LinkSite = (TextView) convertView.findViewById(R.id.txt_site_LstMySite);
            viewHolder.ImgNext = (ImageView) convertView.findViewById(R.id.img_next_MysiteLst);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MySiteContructor contructor = arrayList.get(position);
        viewHolder.TitleSite.setText(contructor.getTitleSite());
        viewHolder.LinkSite.setText(contructor.getLinkeSite());
        viewHolder.ImgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView TitleSite,LinkSite;
        ImageView ImgNext;
    }


}
