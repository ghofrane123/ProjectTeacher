package wolfsoft.routes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import wolfsoft.routes.DeleteAndEdit;
import wolfsoft.routes.Entities.teacher_cour;
import wolfsoft.routes.R;

/**
 * Created by G on 19/11/2017.
 */

public  class CourAdapter extends BaseAdapter {
    Context c;
    private List<teacher_cour> couritem;



    public CourAdapter(Context c, List<teacher_cour> couritem) {
        this.c = c;
        this.couritem = couritem;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return couritem.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return couritem.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }



    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
             convertView= LayoutInflater.from(c).inflate(R.layout.model,parent,false);
        }
        TextView cour= (TextView) convertView.findViewById(R.id.cour);
        TextView prix= (TextView) convertView.findViewById(R.id.prix);
        TextView description= (TextView) convertView.findViewById(R.id.description);
        TextView heure= (TextView) convertView.findViewById(R.id.heure);
        TextView etudiant= (TextView) convertView.findViewById(R.id.etudiant);
        TextView created_at= (TextView) convertView.findViewById(R.id.date);


        // getting movie data for the row
        teacher_cour m = couritem.get(position);

        final String id = m.getId();
        final String mat = m.getMatiere();
        final String pri = m.getPrice();
        final  String des = m.getDescription();
        final String he = m.getHeure();
        final int et = m.getEtudiant();
        final  String da = m.getCreated_at();






        cour.setText("cour : "+mat);
        prix.setText("price: "+pri);
        description.setText("description: "+des);
        heure.setText("number of hour: "+he);
        etudiant.setText("number of student: "+et);
        created_at.setText("created_at: "+da);

        final teacher_cour s= (teacher_cour) this.getItem(position);
      //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,s.getMatiere(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(c,DeleteAndEdit.class);
                intent.putExtra("cour_id", id);
                intent.putExtra("cour_matiere", mat);
                intent.putExtra("cour_price", pri);
                intent.putExtra("cour_description",des);
                intent.putExtra("heure", he);
                intent.putExtra("etudiant", et);
                c.startActivity(intent);


            }
        });


        return convertView;
    }






}
