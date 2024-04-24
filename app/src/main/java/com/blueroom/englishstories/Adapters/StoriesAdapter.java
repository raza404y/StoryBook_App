package com.blueroom.englishstories.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blueroom.englishstories.Admob;
import com.blueroom.englishstories.R;
import com.blueroom.englishstories.Read_Story_Activity;
import com.blueroom.englishstories.StoriesActivity;
import com.blueroom.englishstories.databinding.StoriesRvLayoutBinding;
import com.blueroom.englishstories.models.StoriesModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

    ArrayList<StoriesModel> namesList;
    Context context;
    boolean isLiked;
    ProgressDialog dialog;

    public StoriesAdapter(ArrayList<StoriesModel> namesList, Context context) {
        this.namesList = namesList;
        this.context = context;
    }

    @NonNull
    @Override
    public StoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stories__rv_layout, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    @Override
    public void onBindViewHolder(@NonNull StoriesAdapter.ViewHolder holder, int position) {
        StoriesModel model = namesList.get(position);
        holder.binding.storyTitle.setText(model.getStoryTitle());
      //  holder.binding.storyText.setText(model.getStoryText());
        holder.binding.storyText.setText(model.getShortenedStoryText());


//        holder.binding.like.setOnClickListener(view -> {
//
//            isLiked = !isLiked;
//            if (isLiked) {
//                holder.binding.like.setImageResource(R.drawable.heart_outline);
//            } else {
//                holder.binding.like.setImageResource(R.drawable.heart_filled);
//            }
//        });



        holder.itemView.setOnClickListener(view -> {

            dialog = new ProgressDialog(context,R.style.AppCompatAlertDialogStyle);
            dialog.setMessage("Please wait a second...");
            dialog.show();

            if (Admob.mInterstitialAd != null) {
                Admob.mInterstitialAd.show((Activity) context);
                Admob.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        dialog.dismiss();

                        FirebaseDatabase.getInstance().getReference().child("full_screen_multiple_load").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    boolean loadAgainAd = Boolean.TRUE.equals(snapshot.getValue(boolean.class));
                                    if (loadAgainAd) {
                                        Admob.loadEnter(context);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                                Log.d("Tag", "onCancelled: "+error.getMessage());
                            }
                        });
                        Admob.mInterstitialAd = null;
                        Intent intent = new Intent(context, Read_Story_Activity.class);
                        intent.putExtra("name", model.getStoryTitle());
                        intent.putExtra("stxt", model.getStoryText());
                        if (context instanceof Activity) {
                            Activity activity = (Activity) context;
                            activity.startActivity(intent);
                        }

                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Admob.mInterstitialAd = null;
                        dialog.dismiss();
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                        Admob.mInterstitialAd = null;
                        dialog.dismiss();
                    }
                });

            } else {
                Intent intent = new Intent(context, Read_Story_Activity.class);
                intent.putExtra("name", model.getStoryTitle());
                intent.putExtra("stxt", model.getStoryText());
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    activity.startActivity(intent);
                }
                Admob.mInterstitialAd = null;
                dialog.dismiss();
            }
        });

//
//        holder.itemView.setOnClickListener(view -> {
//
//            Intent intent = new Intent(context, Read_Story_Activity.class);
//            intent.putExtra("name", model.getStoryTitle());
//            intent.putExtra("stxt", model.getStoryText());
//            context.startActivity(intent);
//            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        });
    }
    @Override
    public int getItemCount() {
        return namesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        StoriesRvLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = StoriesRvLayoutBinding.bind(itemView);
        }
    }
}
