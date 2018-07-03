package com.example.danesh.tamrincardview.Activity.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danesh.tamrincardview.Modal.LessonClass;
import com.example.danesh.tamrincardview.Modal.MyDatabaseClass;
import com.example.danesh.tamrincardview.Modal.myBaseAdapter;
import com.example.danesh.tamrincardview.R;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    List<LessonClass> lessonList;
    ListView listView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lessonList = MyDatabaseClass.getInstance(getApplicationContext()).getAllLessonData();

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new myBaseAdapter(this, lessonList));
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("myText"));
        setSupportActionBar(toolbar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Main2Activity.this, MediaActivity.class);


                LessonClass temp = lessonList.get(i);
                temp.getContentLesson();

                //Toast.makeText(getApplicationContext(), "nameLesson:  " + temp.getContentLesson(), Toast.LENGTH_SHORT).show();


                intent.putExtra("contentLesson", temp.getContentLesson());
                intent.putExtra("namelesson",temp.getNameLesson());

                startActivity(intent);
            }
        });
    }
}



/*
class  singleRow{
    String title;
    int image;

    public singleRow(String s, int i) {
        this.title=s;
        this.image=i;
    }
}*/

/*
class masoudAdapter extends BaseAdapter{
    ArrayList<singleRow>list;
    Context context;
    masoudAdapter(Context context){
        this.context=context;
        list=new ArrayList<singleRow>();
        Resources res=context.getResources();
        String[] title=res.getStringArray(R.array.title);
        int[] image={R.drawable.sorry100, R.drawable.condolence101, R.drawable.jealus100, R.drawable.compliment100, R.drawable.revenge100, R.drawable.inlows100, R.drawable.traditionalwedding100, R.drawable.fistfight100, R.drawable.peoplesmood, R.drawable.scandal100};
        for (int i=0;i<10;i++)
        {
            list.add (new singleRow (title[i],image[i]));
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View row=  inflater.inflate(R.layout.single_row,viewGroup,false);
        TextView title=(TextView) row.findViewById(R.id.textView);
        ImageView imageView=(ImageView) row.findViewById(R.id.imageView);
        singleRow temp=list.get(i);
        title.setText(temp.title);
        imageView.setImageResource(temp.image);

        return row;
    }
}*/
