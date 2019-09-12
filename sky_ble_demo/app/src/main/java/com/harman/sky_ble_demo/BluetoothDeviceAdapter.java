package com.harman.sky_ble_demo;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BluetoothDeviceAdapter extends BaseAdapter {
    private List<BluetoothDevice> datas = new ArrayList<>();
    private Context mContext;

    public BluetoothDeviceAdapter(Context ctx) {
        this.mContext = ctx;

    }

    public void addDevice(BluetoothDevice d) {
        if (!datas.contains(d)) {
            datas.add(d);
            notifyDataSetChanged();
        }


    }

    private boolean contains(BluetoothDevice d) {
        for (BluetoothDevice temp : datas) {
            if (temp.getAddress().equals(d.getAddress())) {
                return true;
            }

        }

        return false;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position).getAddress();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder;
        if (null == convertView) {
            vHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
            vHolder.textView1 = convertView.findViewById(R.id.textview1);
            vHolder.textView2 = convertView.findViewById(R.id.textview2);


            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }

        BluetoothDevice bluetoothDevice = datas.get(position);

        if (bluetoothDevice != null) {
            vHolder.textView1.setText(bluetoothDevice.getName());
            vHolder.textView2.setText(bluetoothDevice.getAddress());
        }

        return convertView;
    }

    public static class ViewHolder {
        TextView textView1, textView2;
    }


}
