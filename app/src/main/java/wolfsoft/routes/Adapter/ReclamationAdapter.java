package wolfsoft.routes.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import wolfsoft.routes.Entities.CourByUser;
import wolfsoft.routes.Entities.reclamation;
import wolfsoft.routes.Entities.teacher_cour;
import wolfsoft.routes.FetchReclamation;
import wolfsoft.routes.R;

/**
 * Created by G on 05/01/2018.
 */

public class ReclamationAdapter extends BaseAdapter {

    Context c;
    private List<reclamation> reclamationList;

    public ReclamationAdapter(Context c, List<reclamation> reclamationList) {
        this.c = c;
        this.reclamationList = reclamationList;
    }


    @Override
    public int getCount() {
        return reclamationList.size();
    }


    @Override
    public Object getItem(int position) {
        return reclamationList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.reclamation_model,parent,false);
        }

        TextView cour= (TextView) convertView.findViewById(R.id.textView4);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView14);


        reclamation m = reclamationList.get(position);
        final int rating = m.getRating();
        final String description = m.getDescription();

        cour.setText(description);

        if(rating == 1 )

        {
            image.setImageResource(R.drawable.poor1);
        }

        if(rating == 2 )

        {
            image.setImageResource(R.drawable.poor2);
        }
        if(rating == 3 )

        {
            image.setImageResource(R.drawable.poor3);
        }
        if(rating == 4 )

        {
            image.setImageResource(R.drawable.poor4);
        }

        if(rating == 5 )

        {
            image.setImageResource(R.drawable.poor5);
        }


        return convertView;
    }


}
