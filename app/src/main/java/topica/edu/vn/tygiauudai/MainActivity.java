package topica.edu.vn.tygiauudai;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import topica.edu.vn.adapter.TyGiaAdapter;
import topica.edu.vn.model.TyGia;

public class MainActivity extends AppCompatActivity {
    ListView lvTyGia;
    ArrayList<TyGia>dsTygia;
    TyGiaAdapter tyGiaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

    }

    private void addEvents() {

    }

    private void addControls() {
        lvTyGia=findViewById(R.id.lvTyGia);
        dsTygia=new ArrayList<TyGia>();
        tyGiaAdapter =new TyGiaAdapter(MainActivity.this,R.layout.item,dsTygia);
        lvTyGia.setAdapter(tyGiaAdapter);
        Tygiatask tygiatask=new Tygiatask();
        tygiatask.execute();

    }
    class Tygiatask extends AsyncTask<Void,Void,ArrayList<TyGia>>
    {

        @Override
        protected ArrayList<TyGia> doInBackground(Void... voids) {
            ArrayList<TyGia> ds=new ArrayList<TyGia>();
            try {
                URL url=new URL("https://www.dongabank.com.vn/exchange/export");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json; charset=utf-8");
                connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                connection.setRequestProperty("Accept", "*/*");
                InputStream is= connection.getInputStream();
                InputStreamReader isr=new InputStreamReader(is,"UTF-8");
                BufferedReader br=new BufferedReader(isr);
                String line=br.readLine();
                StringBuilder builder=new StringBuilder();
                while (line!=null )
                {
                    builder.append(line);
                    line=br.readLine();
                }
                String json=builder.toString();
                json=json.replace("(", "");
                json=json.replace(")","");
                JSONObject jsonObject=new JSONObject(json);
                JSONArray jsonArray= jsonObject.getJSONArray("items");
                for (int i=0;i<jsonArray.length();i++) {


                    JSONObject item = jsonArray.getJSONObject(i);
                    TyGia tiGia = new TyGia();
                    if(item.has("type"))
                        tiGia.setType(item.getString("type"));
                    if(item.has("imageurl"))
                    {
                        tiGia.setImgeurl(item.getString("imageurl"));
                        url=new URL(tiGia.getImgeurl());
                        connection= (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                        connection.setRequestProperty("Accept", "*/*");
                        Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                        tiGia.setBitmap(bitmap);
                    }
                    if(item.has("muatienmat"))
                    {
                        tiGia.setMuatienmat(item.getString("muatienmat"));
                    }
                    if(item.has("muack")) {
                        tiGia.setMuack(item.getString("muack"));
                    }
                    if(item.has("bantienmat")) {
                        tiGia.setBantienmat(item.getString("bantienmat"));
                    }
                    if(item.has("banck")) {
                        tiGia.setBanck(item.getString("banck"));
                    }
                    ds.add(tiGia);

                }

            }
            catch (Exception ex)
            {
                Log.e("Loi",ex.toString());
            }
            return ds;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tyGiaAdapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<TyGia> tyGias) {
            super.onPostExecute(tyGias);
            tyGiaAdapter.clear();
            tyGiaAdapter.addAll(tyGias);
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }
    }

}