package com.example.danesh.tamrincardview.Modal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danesh.tamrincardview.Activity.Activity.Main2Activity;
import com.example.danesh.tamrincardview.R;

import java.util.ArrayList;
import java.util.List;

public class myBaseAdapter extends BaseAdapter {
    Context context;
    List<LessonClass> lessonList1;
    public myBaseAdapter(Context context, List<LessonClass> lessonList) {
        this.context=context;
        lessonList1=new ArrayList<LessonClass>();
        lessonList1=lessonList;
    }

    @Override
    public int getCount() {
        return lessonList1.size();
    }

    @Override
    public Object getItem(int i) {
        return lessonList1.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.single_row,viewGroup,false);
        TextView lesson_name= (TextView) row.findViewById(R.id.textView);
        TextView lesson_tagline= (TextView) row.findViewById(R.id.textView3);
        ImageView lesson_pic= (ImageView) row.findViewById(R.id.imageView);

        LessonClass temp=lessonList1.get(i);
        lesson_name.setText(Html.fromHtml(temp.getNameLesson()));
        lesson_tagline.setText(temp.getTaglineLesson());
        Bitmap bmp= BitmapFactory.decodeByteArray(temp.getPicLesson(), 0 , temp.getPicLesson().length);
        lesson_pic.setImageBitmap(bmp);
        return row;
    }
}
