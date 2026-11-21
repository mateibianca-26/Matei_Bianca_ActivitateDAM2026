package com.example.lab8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class ImagineAdapter extends BaseAdapter {
    private Context ctx;
    private int resursaLayout;
    private List<ImaginiMobilier> lista;

    public ImagineAdapter(Context ctx, int resursaLayout, List<ImaginiMobilier> lista){
        this.ctx = ctx;
        this.resursaLayout = resursaLayout;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(ctx);
        convertView = inflater.inflate(resursaLayout, parent, false);

        ImageView ivImagine = convertView.findViewById(R.id.imagine_IV);
        TextView tvDescriere = convertView.findViewById(R.id.descriere_TV);

        ImaginiMobilier item = (ImaginiMobilier) getItem(position);

        ivImagine.setImageBitmap(item.getImagine());
        tvDescriere.setText(item.getTextAfisat());

        return convertView;

    }
}
