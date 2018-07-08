package com.worldmer.contantresolverexample.helper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.worldmer.contantresolverexample.R;
import com.worldmer.contantresolverexample.activity.EditActivity;
import com.worldmer.contantresolverexample.activity.MainActivity;
import com.worldmer.contantresolverexample.modal.Contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yagnik on 03-Jan-18.
 */

public class ContactAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    List<Contacts> contactList;
    Context context;

    public ContactAdapter(Context contaxt, List<Contacts> contactList) {
        this.contactList = new ArrayList<>();
        this.contactList = contactList;
        this.context = contaxt;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView tvName, tvPhone;
        Button btnDelete,btnEdit;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View rowView;
        rowView = inflater.inflate(R.layout.row_contact, null);
        holder.tvName = (TextView) rowView.findViewById(R.id.tvname);
        holder.tvPhone = (TextView) rowView.findViewById(R.id.tvphone);
        holder.btnEdit = (Button) rowView.findViewById(R.id.btnedit);
        holder.btnDelete = (Button) rowView.findViewById(R.id.btndelete);

        holder.tvName.setText(contactList.get(position).getName());
        holder.tvPhone.setText(contactList.get(position).getPhone());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent editIntent = new Intent(context, EditActivity.class);
                editIntent.putExtra("Index", contactList.get(position).getId());
                context.startActivity(editIntent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentResolveHelper contentResolveHelper = new ContentResolveHelper(context);
                contentResolveHelper.deleteContact(contactList.get(position).getId());
                Toast.makeText(context, contactList.get(position).getName()
                        + " Delete Successfully.", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(context, MainActivity.class);
                context.startActivity(mainIntent);
            }
        });
        return rowView;
    }
}