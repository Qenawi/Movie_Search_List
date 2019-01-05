package qenawi.panda.movie_search_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.rx.ImageView;


public class MyViewHolder2 extends RecyclerView.ViewHolder
{

    @BindView(R.id.photo)
    ImageView img_view;
    public MyViewHolder2(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
