package io.github.teamcharisma.farmsaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CardBuilder {
    ViewGroup mParent;
    Context mContext;
    View mCard;

    public CardBuilder(Context context, ViewGroup parent) {
        mContext = context;
        mParent = parent;
        mCard = LayoutInflater.from(context).inflate(R.layout.layout_card_category, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = lp.rightMargin = lp.bottomMargin = lp.topMargin = 16;
        mCard.setLayoutParams(lp);
    }

    public void setTitle(String title)
    {
        TextView tv = mCard.findViewById(R.id.categoryTitle);
        tv.setText(title);
    }

    public void setImage(int imageSrc)
    {
        ImageView iv = mCard.findViewById(R.id.categoryIcon);
        iv.setImageResource(imageSrc);
    }

    public void setGraph(View graph)
    {
        LinearLayout l = mCard.findViewById(R.id.categoryGraph);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 600);
        lp.weight = 1;
        graph.setLayoutParams(lp);
        l.addView(graph,0);
    }

    public void build()
    {
        mParent.addView(mCard);
    }
}
