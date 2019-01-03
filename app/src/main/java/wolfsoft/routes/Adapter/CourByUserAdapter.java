package wolfsoft.routes.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.simplify.payments.domain.Payment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import wolfsoft.routes.Comment;
import wolfsoft.routes.Entities.CourByUser;
import wolfsoft.routes.Main3Activity;
import wolfsoft.routes.R;
import wolfsoft.routes.Reclamation;

import static android.R.attr.width;
import static wolfsoft.routes.R.attr.height;
import static wolfsoft.routes.R.id.fill;



/**
 * Created by G on 21/11/2017.
 */

public class CourByUserAdapter extends BaseAdapter  implements Filterable {


    Context c;
    private List<CourByUser> courByUseritem;
    private List<CourByUser> worldpopulationlist = null;
    private List<CourByUser> test;





    private FriendFilter friendFilter;






    public CourByUserAdapter(Context c, List<CourByUser> courByUseritem) {
        this.c = c;
        this.courByUseritem = courByUseritem;
    }



    public int getCount() {
        return courByUseritem.size();
    }



    public Object getItem(int position) {
        return courByUseritem.get(position);
    }


    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.model1,parent,false);
        }
        TextView name= (TextView) convertView.findViewById(R.id.name);
        TextView lastname= (TextView) convertView.findViewById(R.id.lastname);
        TextView email_user= (TextView) convertView.findViewById(R.id.email);

        final TextView nombre= (TextView) convertView.findViewById(R.id.nombre);
        final TextView etudiant= (TextView) convertView.findViewById(R.id.nombre);
        TextView cour= (TextView) convertView.findViewById(R.id.cour);
        TextView price= (TextView) convertView.findViewById(R.id.price);

        TextView Rating= (TextView) convertView.findViewById(R.id.rating);
        TextView Hour= (TextView) convertView.findViewById(R.id.heure);


        ImageView imgb= (ImageView) convertView.findViewById(R.id.imageView);
        ImageView image_profile= (ImageView) convertView.findViewById(R.id.imageView3);

        Button notification= (Button) convertView.findViewById(R.id.button5);


        ImageView im3 = (ImageView)convertView.findViewById(R.id.imageView5);
        ImageView im4 = (ImageView)convertView.findViewById(R.id.imageView12);
        ImageView im5 = (ImageView)convertView.findViewById(R.id.imageView13);




        // getting cour I9 data for the row
        CourByUser m = courByUseritem.get(position);




        final String matiere = m.getMatiere();
        final String prix = m.getPrice();
        final  String description = m.getDescription();
        final  String date = m.getCreated_at();
        final  String email = m.getEmail();
        final  String conncted = m.getIs_logged();
        final  String telephone = m.getTelephone();
        final  String ID = m.getCid();
        final  String photo = m.getImage();
        final  String name_teacher = m.getRole();
        final  String NUMBER = m.getEtudiant();
        final  String last = m.getLastname();
        final  String rating = m.getRating();
        final  String img = m.getImage();
        final  String unique = m.getUser_id();


        notification.setText("reserved :"+NUMBER+"+");





        final  String hour = m.getHeure();

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (NUMBER.equals("0"))
                {
                    Toast.makeText(c, "cour completed", Toast.LENGTH_SHORT)
                            .show();
                }
                else
                {



                    // Create custom dialog object
                    final Dialog dialog = new Dialog(c);

                    // Include dialog.xml file
                    dialog.setContentView(R.layout.confirm);
                    // Set dialog title
                    dialog.setTitle("make your choice");
                    Button yes = (Button) dialog.findViewById(R.id.btnyes);
                    Button no = (Button) dialog.findViewById(R.id.btnno);
                    dialog.show();


                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }




            }
        });



        Picasso.with(c)
                .load(photo)
                .placeholder(R.drawable.profileimage)
                .error(R.drawable.profileimage)
                .resize(300,300)
                .into(image_profile);





        email_user.setText("email:"+email);
        name.setText("name:"+name_teacher);
        lastname.setText("lastname:"+last);
        Hour.setText("number of hour:"+hour);
        cour.setText("cour :"+matiere);
        etudiant.setText("number of student:"+NUMBER);
        Rating.setText("rating :"+rating+"%");
        price.setText("price :"+prix+"§");





        if(conncted.equals("0"))
        {
            imgb.setImageResource(R.drawable.offline);
        }
        if(conncted.equals("1"))
        {
            imgb.setImageResource(R.drawable.online);
        }




        final CourByUser s= (CourByUser) this.getItem(position);
        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,s.getImage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(c,Comment.class);
                intent.putExtra("cour_matiere", matiere);
                intent.putExtra("cour_price", prix);
                intent.putExtra("cour_description",description);
                intent.putExtra("telephone",telephone);
                intent.putExtra("email",email);
                intent.putExtra("rating",rating);
                intent.putExtra("heure",hour);
                intent.putExtra("name",name_teacher);
                intent.putExtra("lastname",last);
                intent.putExtra("heure",hour);
                intent.putExtra("number",NUMBER);
                intent.putExtra("id",ID);
                intent.putExtra("image",img);
                c.startActivity(intent);


            }
        });


        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (friendFilter == null) {
            friendFilter = new FriendFilter();
        }

        return friendFilter;
    }

    private class FriendFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<CourByUser> tempList = new ArrayList<CourByUser>();

                // search content in friend list
                for (CourByUser user : courByUseritem) {
                    if (user.getMatiere().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(user);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {

                filterResults.count = courByUseritem.size();
                filterResults.values = courByUseritem;


            }

            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            courByUseritem = (ArrayList<CourByUser>) results.values;
            notifyDataSetChanged();
        }

    }



}

