package vn.edu.poly.manager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import vn.edu.poly.manager.Model.ContactsModel;
import vn.edu.poly.manager.R;

public class ContactAdapter extends BaseAdapter {

    List<ContactsModel> contactsModelList;
    Context context;
    LayoutInflater layoutInflater;

    public ContactAdapter(List<ContactsModel> contactsModelList, Context context) {
        this.contactsModelList = contactsModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contactsModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactsModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView txt_title_item_contact, txt_email_item_contact,
                txt_phone_item_contact, txt_dateTime_item_contact;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_item_contact, null);
            viewHolder = new ViewHolder();
            viewHolder.txt_title_item_contact = convertView.findViewById(R.id.txt_title_item_contact);
            viewHolder.txt_email_item_contact = convertView.findViewById(R.id.txt_email_item_contact);
            viewHolder.txt_phone_item_contact = convertView.findViewById(R.id.txt_phone_item_contact);
            viewHolder.txt_dateTime_item_contact = convertView.findViewById(R.id.txt_dateTime_item_contact);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ContactsModel contactsModel = contactsModelList.get(position);
        viewHolder.txt_title_item_contact.setText(contactsModel.getTitle());
        viewHolder.txt_email_item_contact.setText(contactsModel.getEmail() + " - ");
        viewHolder.txt_phone_item_contact.setText(contactsModel.getPhone());

        String dateTime = contactsModel.getCreate_at();
        StringTokenizer tk = new StringTokenizer(dateTime);
        String dateT = tk.nextToken();
        String timeT = tk.nextToken();
        String time = timeT;
        String date = dateT;
        SimpleDateFormat dateFormatTime = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat dateFormatTime2 = new SimpleDateFormat("hh:mm");
        SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatDate2 = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date timeFormat = dateFormatTime.parse(time);
            time = dateFormatTime2.format(timeFormat);
            Date dateFormat = dateFormatDate.parse(date);
            date = dateFormatDate2.format(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.txt_dateTime_item_contact.setText(time + " " + date);
        return convertView;
    }
}
