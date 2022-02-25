package topica.edu.vn.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import topica.edu.vn.model.TyGia;
import topica.edu.vn.tygiauudai.R;

public class TyGiaAdapter extends ArrayAdapter<TyGia> {
    Activity context;
    int resource;
    @NonNull List<TyGia> objects;

    public TyGiaAdapter(@NonNull Activity context, int resource, @NonNull List<TyGia> objects) {
        super(context, resource, objects);
        this.context=context;
        this.objects=objects;
        this.resource=resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View row=layoutInflater.inflate(this.resource,null);

        TextView txtMuatm=row.findViewById(R.id.txtMuatm);
        TextView txtMuack=row.findViewById(R.id.txtMuack);
        TextView txtBantm=row.findViewById(R.id.txtBantm);
        TextView txtBanck=row.findViewById(R.id.txtBanCk);
        ImageView imgHinh=row.findViewById(R.id.imgHinh);
        TextView txtType=row.findViewById(R.id.txtType);

        TyGia tyGia=this.objects.get(position);

        imgHinh.setImageBitmap(tyGia.getBitmap());
        txtType.setText(tyGia.getType());
        txtMuatm.setText(tyGia.getMuatienmat());
        txtMuack.setText(tyGia.getMuack());
        txtBantm.setText(tyGia.getBantienmat());
        txtBanck.setText(tyGia.getBanck());



        return row;

    }
}
