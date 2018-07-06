package com.google.developer.bugmaster;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.developer.bugmaster.data.Insect;

import java.io.IOException;
import java.io.InputStream;

public class InsectDetailsActivity extends AppCompatActivity {

    TextView bug_common_name,bug_science_name,bug_classification;
    LinearLayout bug_rating_images;
    ImageView bug_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Implement layout and display insect details
        setContentView(R.layout.insect_details_activity);

        bug_common_name= (TextView) findViewById(R.id.bug_common_name);
        bug_science_name= (TextView) findViewById(R.id.bug_scientific_name);
        bug_classification= (TextView) findViewById(R.id.bug_classification);
        bug_rating_images= (LinearLayout) findViewById(R.id.bug_rating_images);
        bug_image= (ImageView) findViewById(R.id.bug_image);

        TextView tv4=(TextView)findViewById(R.id.textView4);
        TextView tv5= (TextView) findViewById(R.id.textView5);

        tv4.setText(getString(R.string.dangerLevel));
        tv5.setText(getString(R.string.dangerLevelInfo));

        Intent i=getIntent();
        Insect insect=i.getParcelableExtra("insect");

        bug_common_name.setText(insect.name);
        bug_science_name.setText(insect.scientificName);
        bug_classification.setText("Classification: "+insect.classification);

        try {
            InputStream is=getAssets().open(insect.imageAsset);
            Drawable d=Drawable.createFromStream(is,null);
            bug_image.setImageDrawable(d);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int j=0;j<10;j++){
            if(j<insect.dangerLevel){
                ImageView bug=new ImageView(InsectDetailsActivity.this);
                bug.setImageDrawable(getResources().getDrawable(R.drawable.ic_bug_full));
                bug_rating_images.addView(bug,bug_rating_images.getLayoutParams());
            }else{
                ImageView bug=new ImageView(InsectDetailsActivity.this);
                bug.setImageDrawable(getResources().getDrawable(R.drawable.ic_bug_empty));
                bug_rating_images.addView(bug,bug_rating_images.getLayoutParams());
            }
        }
    }
}
