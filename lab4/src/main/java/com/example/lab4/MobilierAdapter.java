package com.example.lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MobilierAdapter extends BaseAdapter {

    private List<Mobilier> lista;
    private Context ctx;
    private int resursaLayout;

    @Override
    public int getCount() {
        return lista.size();
    }

    public MobilierAdapter(List<Mobilier> lista, Context ctx, int resursaLayout) {
        this.lista = lista;
        this.ctx = ctx;
        this.resursaLayout = resursaLayout;
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
        View v = inflater.inflate(resursaLayout, parent, false);

        TextView tvDenumire = v.findViewById(R.id.item_denumire_TV);
        TextView tvPret = v.findViewById(R.id.item_pret_TV);
        TextView tvDetalii = v.findViewById(R.id.item_detalii_TV);

        Mobilier m = (Mobilier) getItem(position);

        tvDenumire.setText(m.getDenumire());
        tvPret.setText(m.getPret() + " RON");
        tvDetalii.setText("Material " + m.getMaterial() + "| Montaj: " + (m.isMontajInclus() ? "Da" : "Nu"));

        return v;
    }
}
