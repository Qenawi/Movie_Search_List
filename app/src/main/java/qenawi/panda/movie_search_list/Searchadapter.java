package qenawi.panda.movie_search_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import io.reactivex.annotations.NonNull;
import qenawi.panda.movie_search_list.network.Movies_data;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public class Searchadapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<Movies_data> movies;
    private Context c;
    private OnSrelection Call_back;

    public Searchadapter(@NonNull List<Movies_data> movies, OnSrelection Call_back) {
        this.movies = movies;
        this.Call_back = Call_back;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.search_result_item, parent, false);
        c = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Movies_data movie = movies.get(position);
        OnBind(movie, holder);

    }
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void replaceData(List<Movies_data> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<Movies_data> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public Movies_data getItem(int position) {
        if (position < 0 || position >= movies.size()) {
            throw new InvalidParameterException("INVALID IDX");
        }
        return movies.get(position);
    }

    public void clearData() {
        movies.clear();
        notifyDataSetChanged();
    }

    private void OnBind(Movies_data data, MyViewHolder viewHolder)
    {
        viewHolder.MainTextView.setText(data.getName());
/*
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.dummy_img_jop_order);
        requestOptions.error(R.drawable.personal_icon);
        Glide.with(c).setDefaultRequestOptions(requestOptions).load(data.getProfilePath()).into(viewHolder.profileImg);
*/
    }

    public List<Movies_data> getItems() {
        return movies;
    }


    public interface OnSrelection
    {
        void selection(int id);
    }
}
