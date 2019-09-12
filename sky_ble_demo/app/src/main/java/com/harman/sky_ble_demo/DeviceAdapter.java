package com.harman.sky_ble_demo;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends BaseAdapter {
    private List<Device> datas = new ArrayList<>();
    private Context mContext;

    public DeviceAdapter(Context ctx) {
        this.mContext = ctx;

    }

    public void addDevice(Device d) {
        if(!datas.contains(d)){
            datas.add(d);
            notifyDataSetChanged();
        }


    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
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
            Device device = datas.get(position);
            if (device != null) {
                BluetoothDevice bluetoothDevice = device.getBluetoothDevice();
                if (bluetoothDevice != null) {
                    vHolder.textView1.setText(bluetoothDevice.getName());
                    vHolder.textView2.setText(bluetoothDevice.getAddress());
                }
            }
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public static class ViewHolder {
        TextView textView1, textView2;
    }
}
