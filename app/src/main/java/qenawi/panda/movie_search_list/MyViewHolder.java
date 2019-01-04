package qenawi.panda.movie_search_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name)
    TextView MainTextView;
    public MyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
