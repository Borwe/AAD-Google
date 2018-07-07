package com.google.developer.bugmaster.data;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.developer.bugmaster.InsectDetailsActivity;
import com.google.developer.bugmaster.R;

/**
 * RecyclerView adapter extended with project-specific required methods.
 */

public class InsectRecyclerAdapter extends
        RecyclerView.Adapter<InsectRecyclerAdapter.InsectHolder> {

    public InsectRecyclerAdapter(Cursor mCursor){
        super();
        this.mCursor=mCursor;
    }

    public void updateCursor(Cursor mCursor){
        this.mCursor=mCursor;
        notifyDataSetChanged();
    }

    /* ViewHolder for each insect item */
    public class InsectHolder extends RecyclerView.ViewHolder{

        public TextView bug_common_name,bug_science_name,bug_danger_level;
        public LinearLayout bug_holder;

        public InsectHolder(View itemView) {
            super(itemView);
            bug_common_name= (TextView) itemView.findViewById(R.id.bug_common_name);
            bug_science_name= (TextView) itemView.findViewById(R.id.bug_science_name);
            bug_danger_level= (TextView) itemView.findViewById(R.id.bug_danger_level);
            bug_holder= (LinearLayout) itemView.findViewById(R.id.bug_item_holder);
        }
    }

    private Cursor mCursor;

    @Override
    public InsectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View bugs_row= LayoutInflater.
                from(parent.getContext()).inflate(R.layout.bug_item,parent,false);
        return new InsectHolder(bugs_row);
    }

    @Override
    public void onBindViewHolder(final InsectHolder holder, int position) {
        final Insect insect=getItem(position);

        holder.bug_common_name.setText(insect.name);
        holder.bug_science_name.setText(insect.scientificName);

        String[] dangerColors=holder.bug_danger_level.getResources().getStringArray(R.array.dangerColors);
        Drawable background=holder.bug_danger_level.getResources().getDrawable(R.drawable.background_danger);
        background.mutate().setColorFilter(Color.parseColor(dangerColors[insect.dangerLevel-1]),
                PorterDuff.Mode.SRC_IN);
        holder.bug_danger_level.setBackground(background);
        holder.bug_danger_level.setText(insect.dangerLevel+"");

        holder.bug_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showDetails=new Intent(holder.bug_holder.getContext(), InsectDetailsActivity.class);
                showDetails.putExtra("insect",insect);
                holder.bug_holder.getContext().startActivity(showDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    /**
     * Return the {@link Insect} represented by this item in the adapter.
     *
     * @param position Adapter item position.
     *
     * @return A new {@link Insect} filled with this position's attributes
     *
     * @throws IllegalArgumentException if position is out of the adapter's bounds.
     */
    public Insect getItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new IllegalArgumentException("Item position is out of adapter's range");
        } else if (mCursor.moveToPosition(position)) {
            return new Insect(mCursor);
        }
        return null;
    }
}
