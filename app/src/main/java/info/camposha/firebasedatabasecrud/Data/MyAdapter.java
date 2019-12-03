package info.camposha.firebasedatabasecrud.Data;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import info.camposha.firebasedatabasecrud.Helpers.FilterHelper;
import info.camposha.firebasedatabasecrud.Helpers.Utils;
import info.camposha.firebasedatabasecrud.R;
import info.camposha.firebasedatabasecrud.Views.DetailActivity;

import static info.camposha.firebasedatabasecrud.Helpers.Utils.searchString;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
implements Filterable {

    private final Context c;
    private final int mBackground;
    private final int[] mMaterialColors;
    public List<Scientist> scientists;
    private List<Scientist> filterList;
    private FilterHelper filterHelper;

    interface ItemClickListener {
        void onItemClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
     View.OnClickListener {
        private final TextView nameTxt;
        private final TextView mDescriptionTxt;
        private final TextView galaxyTxt;
        private final MaterialLetterIcon mIcon;
        private ItemClickListener itemClickListener;

        ViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.mMaterialLetterIcon);
            nameTxt = itemView.findViewById(R.id.mNameTxt);
            mDescriptionTxt = itemView.findViewById(R.id.mDescriptionTxt);
            galaxyTxt = itemView.findViewById(R.id.mGalaxyTxt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }

        void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }

    public MyAdapter(Context mContext, List<Scientist> scientists) {
        this.c = mContext;
        this.scientists = scientists;
        this.filterList = scientists;
        TypedValue mTypedValue = new TypedValue();
        c.getTheme().resolveAttribute(R.attr.selectableItemBackground,
         mTypedValue, true);
        mMaterialColors = c.getResources().getIntArray(R.array.colors);
        mBackground = mTypedValue.resourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.model, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get current scientist
        final Scientist s = scientists.get(position);

        //bind data to widgets
        holder.nameTxt.setText(s.getName());
        holder.mDescriptionTxt.setText(s.getDescription());
        holder.galaxyTxt.setText(s.getGalaxy());
        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(2);
        holder.mIcon.setLetterSize(25);
        holder.mIcon.setShapeColor(mMaterialColors[new Random().nextInt(
            mMaterialColors.length)]);
        holder.mIcon.setLetter(s.getName());

        if(position % 2 == 0){
            holder.itemView.setBackgroundColor(Color.parseColor("#efefef"));
        }

        //get name and galaxy
        String name = s.getName().toLowerCase(Locale.getDefault());
        String galaxy = s.getGalaxy().toLowerCase(Locale.getDefault());

        //highlight name text while searching
        if (name.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = name.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.nameTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.nameTxt.setText(spanString);
        } else {
            //Utils.show(ctx, "Search string empty");
        }

        //highligh galaxy text while searching
        if (galaxy.contains(searchString) && !(searchString.isEmpty())) {

            int startPos = galaxy.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.galaxyTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.galaxyTxt.setText(spanString);
        }

        //open detailactivity when clicked
        holder.setItemClickListener(pos -> Utils.sendScientistToActivity(c, s,
         DetailActivity.class));
    }

    @Override
    public int getItemCount() {
        return scientists.size();
    }

    @Override
    public Filter getFilter() {
        if(filterHelper==null){
            filterHelper=FilterHelper.newInstance(filterList,this);
        }
        return filterHelper;
    }
}
//end