package wolfsoft.routes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import wolfsoft.routes.Entities.CourByUser;
import wolfsoft.routes.Entities.reclamation;
import wolfsoft.routes.R;

/**
 * Created by G on 11/01/2018.
 */

public class HistoriqueAdapter extends BaseAdapter {


    Context c;
    private List<CourByUser> historiquelist;

    public HistoriqueAdapter(Context c, List<CourByUser> historiquelist) {
        this.c = c;
        this.historiquelist = historiquelist;
    }


    @Override
    public int getCount() {
        return historiquelist.size();
    }


    @Override
    public Object getItem(int position) {
        return historiquelist.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.model_historique,parent,false);
        }


         TextView textView1= (TextView) convertView.findViewById(R.id.text1);
         TextView textView2= (TextView) convertView.findViewById(R.id.text2);
         TextView textView3= (TextView) convertView.findViewById(R.id.text3);
         TextView textView4= (TextView) convertView.findViewById(R.id.text4);


        ImageView user_image= (ImageView) convertView.findViewById(R.id.imageView11);

        CourByUser m = historiquelist.get(position);
        final String cour = m.getMatiere();
        final String nom = m.getRole();
        final String prenom = m.getLastname();
        final String email_user = m.getEmail();
        final String price = m.getPrice();
        final String photo = m.getImage();

        textView1.setText("you are able to learn :"+cour);
        textView2.setText("you had payed :"+price);
        textView3.setText("name : "+nom+" Last name :"+prenom);
        textView4.setText("email :"+email_user);

        Picasso.with(c)
                .load(photo)
                .placeholder(R.drawable.profileimage)
                .error(R.drawable.profileimage)
                .resize(300,300)
                .into(user_image);

        return convertView;
    }

}
