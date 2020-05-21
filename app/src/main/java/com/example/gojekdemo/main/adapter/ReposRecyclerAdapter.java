package com.example.gojekdemo.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.gojekdemo.R;
import com.example.gojekdemo.model.RepoClassModel;
import com.example.gojekdemo.util.ExpandListener;
import com.example.gojekdemo.util.ExpandableLinearLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class ReposRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @Inject
    RequestManager requestManager;
    private List<RepoClassModel> repoClassModelArrayList = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PostViewHolder) holder).bind(repoClassModelArrayList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return repoClassModelArrayList.size();
    }

    public void setRepoClassModelArrayList(List<RepoClassModel> repoClassModelArrayList) {
        this.repoClassModelArrayList = repoClassModelArrayList;
        notifyDataSetChanged();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView titleText;
        AppCompatTextView subTitleText;
        AppCompatImageView userImageView;
        ExpandableLinearLayout expandableLinearLayout;
        AppCompatTextView chineseTitleText;
        AppCompatTextView circleText;
        AppCompatTextView starText;
        AppCompatTextView forkText;
        AppCompatImageView circleTextImageView;
        AppCompatImageView starTextImageView;
        AppCompatImageView forkTextImageView;
View rowDivider;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.text_title);
            chineseTitleText = itemView.findViewById(R.id.text_title_chinese);
            starText = itemView.findViewById(R.id.star_image_text);
            circleText = itemView.findViewById(R.id.circle_image_text);
            forkText = itemView.findViewById(R.id.fork_image_text);
            subTitleText = itemView.findViewById(R.id.sub_title_text);
            userImageView = itemView.findViewById(R.id.user_image);
            starTextImageView = itemView.findViewById(R.id.star_image);
            forkTextImageView = itemView.findViewById(R.id.fork_image);
            circleTextImageView = itemView.findViewById(R.id.circle_image);
            expandableLinearLayout = itemView.findViewById(R.id.expand_linear_layout);
            rowDivider=itemView.findViewById(R.id.row_divider);


        }

        private void initializeClicks(final RepoClassModel repoClassModel, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (expandableLinearLayout.isExpanded()) {
                        expandableLinearLayout.setExpanded(false);

                        expandableLinearLayout.toggle();
                        repoClassModelArrayList.get(getAdapterPosition()).setExpanded(false);
                    } else {
                        expandableLinearLayout.setExpanded(true);
                        repoClassModelArrayList.get(getAdapterPosition()).setExpanded(true);
                        expandableLinearLayout.toggle();
                        rowDivider.setVisibility(View.GONE);
                        changeStateOfItemsInLayout(repoClassModel,position);
                    }
                    notifyDataSetChanged();
                }
            });
        }

        public void bind(RepoClassModel repoClassModel,int poistion) {
            titleText.setText(repoClassModel.getAuthor());
            subTitleText.setText(repoClassModel.getName());
            initializeClicks(repoClassModel,poistion);
            if (repoClassModel.getAvatar()==null) {
                userImageView.setImageResource(R.drawable.circle);
            }else{
                Glide
                        .with(context)
                        .load(repoClassModel.getAvatar())
                        .centerCrop()
                        .apply(RequestOptions.circleCropTransform())
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(userImageView);
            }


            chineseTitleText.setText(repoClassModel.getBuiltBy().get(0).getHref());
            starText.setText(repoClassModel.getStars()+"");
            forkText.setText(repoClassModel.getForks()+"");
            circleText.setText(repoClassModel.getLanguage());
            starTextImageView.setImageResource(R.drawable.star_yellow_16);
            forkTextImageView.setImageResource(R.drawable.fork_black_16);
            circleTextImageView.setImageResource(R.drawable.circle);

            if (repoClassModel.isExpanded()) {
                expandableLinearLayout.setVisibility(View.VISIBLE);
                expandableLinearLayout.setExpanded(true);
                rowDivider.setVisibility(View.GONE);

            } else {
                expandableLinearLayout.setVisibility(View.GONE);
                expandableLinearLayout.setExpanded(false);
                rowDivider.setVisibility(View.VISIBLE);
            }
        }
    }

    private void changeStateOfItemsInLayout(RepoClassModel repoClassModel, int p) {
        for (int x = 0; x < repoClassModelArrayList.size(); x++) {
            if (x == p) {
               repoClassModel.setExpanded(true);
                //Since this is the tapped item, we will skip
                //the rest of loop for this item and set it expanded
                continue;
            }
            repoClassModelArrayList.get(x).setExpanded(false);
        }
    }
}






