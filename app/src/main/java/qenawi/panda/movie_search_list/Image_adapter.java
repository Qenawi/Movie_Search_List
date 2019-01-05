package qenawi.panda.movie_search_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import io.reactivex.annotations.NonNull;
import qenawi.panda.movie_search_list.MyViewHolder2;
import qenawi.panda.movie_search_list.R;
import qenawi.panda.movie_search_list.network.Profile;
import qenawi.panda.movie_search_list.network.Profile;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public class Image_adapter extends RecyclerView.Adapter<MyViewHolder2> {
    private List<Profile> movies;
    private Context c;
    private OnSrelection Call_back;

    public Image_adapter(@NonNull List<Profile> movies, OnSrelection Call_back) {
        this.movies = movies;
        this.Call_back = Call_back;
    }

    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);
        c = parent.getContext();
        return new MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder2 holder, int position)
    {
        Profile movie = movies.get(position);
        OnBind(movie, holder);

    }
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void replaceData(List<Profile> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<Profile> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public Profile getItem(int position) {
        if (position < 0 || position >= movies.size()) {
            throw new InvalidParameterException("INVALID IDX");
        }
        return movies.get(position);
    }

    public void clearData() {
        movies.clear();
        notifyDataSetChanged();
    }

    private void OnBind(Profile data, MyViewHolder2 viewHolder)
    {


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.carbon_defaultdrawable);
        requestOptions.error(R.drawable.carbon_defaultdrawable);
        Glide.with(c).setDefaultRequestOptions(requestOptions).load(Constants.Movies.getImageBaseUrl("w500")+data.getFilePath()).into(viewHolder.img_view);

    }

    public List<Profile> getItems() {
        return movies;
    }


    public interface OnSrelection
    {
        void selection(int id);
    }
}
