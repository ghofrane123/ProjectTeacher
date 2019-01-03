package wolfsoft.routes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import wolfsoft.routes.Comment;
import wolfsoft.routes.Entities.comments;
import wolfsoft.routes.Entities.teacher_cour;
import wolfsoft.routes.R;

/**
 * Created by G on 10/12/2017.
 */

public class CommentAdapter extends BaseAdapter {

    Context c;
    private List<comments> commentsList;


    public CommentAdapter(Context c, List<comments> commentsList) {
        this.c = c;
        this.commentsList = commentsList;
    }

    @Override
    public int getCount() {
        return commentsList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentsList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.model_comment, parent, false);
        }
        TextView nameTxt1 = (TextView) convertView.findViewById(R.id.com);
        TextView nameTxt2 = (TextView) convertView.findViewById(R.id.name);
        TextView nameTxt3 = (TextView) convertView.findViewById(R.id.lastname);


        // getting movie data for the row
        comments m = commentsList.get(position);
        final String mat1 = m.getName();
        final String mat2 = m.getLastname();
        final String mat3 = m.getUser_comment();
        nameTxt1.setText(mat3);
        nameTxt2.setText(mat2);
        nameTxt3.setText(mat1);



        return convertView;

    }

}


