package com.varscon.apitester.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.varscon.apitester.GlideApp;
import com.varscon.apitester.Model.Contact;
import com.varscon.apitester.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.PollViewHolder> {
    public static final String TAG = ContactAdapter.class.getSimpleName();

     class PollViewHolder extends RecyclerView.ViewHolder {

        private final TextView mFirstName, mSecondName;
        private final ImageView mContactDisplay;
        private final RelativeLayout mCoverLayout;


        private PollViewHolder(View itemView) {
            super(itemView);

            mContactDisplay = itemView.findViewById(R.id.profile_image);
            mFirstName = itemView.findViewById(R.id.first_name);
            mSecondName = itemView.findViewById(R.id.second_name);
            mCoverLayout = itemView.findViewById(R.id.cover_layout);
        }

         public void setImage(String avatar) {
             GlideApp
                     .with(mContext)
                     .load(avatar)
                     .centerCrop()
                     .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.drawable.loading_spinner)
                     .into(mContactDisplay);
         }
     }

    private final LayoutInflater mInflater;
    private List<Contact> mContacts; // Cached copy of polls
    private Context mContext;
    private int[] colourCatalogue;


    public ContactAdapter(Context context) {
        mContacts = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        colourCatalogue = mContext.getResources().getIntArray(R.array.androidcolors);
    }

    public void setContacts(List<Contact> contacts){
//        this.mContacts = words;
//        this.mContacts.clear();
        this.mContacts.addAll(contacts);

        notifyDataSetChanged();

//        Log.d("Adapter", "setPolls: " + mContacts.get(0).getPollTitle());
    }

    @NonNull
    @Override
    public PollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_view, parent, false);
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.recyclerview_item, parent, false);


        Log.d("onCreateViewHolder", "onCreateViewHolder: " + view.getTransitionName());
        return new PollViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PollViewHolder holder, int position) {
                    Log.d("View", "onBindViewHolder: " + position);
            Contact current = this.mContacts.get(position);
            int itemId = current.getId();
        Random r = new Random();
        int index = r.nextInt(colourCatalogue.length);

            holder.mCoverLayout.setBackgroundColor(colourCatalogue[index]);
            holder.mFirstName.setText(current.getFirstName() + " ");
            holder.mSecondName.setText(current.getLastName());
            holder.setImage(current.getAvatar());
            holder.mContactDisplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }

            });
            holder.mFirstName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    Intent intent = new Intent(mContext, PollActivity.class);
//                    intent.putExtra("pollId", pollId);
//                    Log.d("Adapter", "onClick: " + pollId);
//                    mContext.startActivity(intent);
                }
            });

    }

    @Override
    public int getItemCount() {
        if (mContacts != null)
            return mContacts.size();
        else return 0;
    }


}
