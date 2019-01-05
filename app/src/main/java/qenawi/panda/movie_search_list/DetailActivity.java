package qenawi.panda.movie_search_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.RecyclerView;
import carbon.widget.rx.ImageView;
import carbon.widget.rx.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import qenawi.panda.a_predator.network_Handeler.A_Predator_NWM;
import qenawi.panda.a_predator.network_Handeler.A_Predator_NetWorkManger;
import qenawi.panda.a_predator.network_Handeler.A_Predator_Throwable;
import qenawi.panda.a_predator.network_Handeler.CService_DBase;
import qenawi.panda.movie_search_list.network.Details_Response;
import qenawi.panda.movie_search_list.network.Imgs_Response;
import qenawi.panda.movie_search_list.network.Profile;
import retrofit2.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

import static qenawi.panda.movie_search_list.Search_Activity.ApiKey;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.details)
    TextView details;
    @BindView(R.id.main_image)
    ImageView main_image;
    @BindView(R.id.image_rv)
    RecyclerView image_rv;
    private A_Predator_NetWorkManger netWorkManger;
    private  Image_adapter adapter;
    int id = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);
        if (getIntent().getExtras()!=null&&getIntent().getExtras().containsKey("id"))
        {
            id=getIntent().getExtras().getInt("id",0);

        }
        initView();
    }

    private void initView()
    {
        netWorkManger=new A_Predator_NetWorkManger(this);
        adapter=new Image_adapter(new ArrayList<Profile>(), new Image_adapter.OnSrelection()
        {
            @Override
            public void selection(int id) {

            }
        });
        image_rv.setLayoutManager(new GridLayoutManager(DetailActivity.this,2));
        image_rv.setAdapter(adapter);
        getDetails();
        getImgs();
    }

    private void getImgs() {
        String Url = "https://api.themoviedb.org/3/person/" + id + "/images";
        HashMap<String, Object> Body = new HashMap<String, Object>() {
            {
                put("api_key", ApiKey);
                put("language", "en");

            }
        };
        netWorkManger.FetchData(new Imgs_Response(), new HashMap<String, String>(), Url, Body, new A_Predator_NWM.RequistResuiltCallBack() {
            @Override
            public <T extends CService_DBase> void Sucess(T Resposne)
            {
                Imgs_Response imgs_response = (Imgs_Response) Resposne;
                adapter.replaceData(imgs_response.getProfiles());
            }

            @Override
            public void Faild(Throwable error)
            {
                if (error instanceof IOException) {
                    if (error instanceof SocketTimeoutException) {
                        Toast.makeText(DetailActivity.this, "Time Out Exception", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(DetailActivity.this, "Pleas Check Your Connection", Toast.LENGTH_SHORT).show();

                    }

                } else if (error instanceof HttpException) {

                    A_Predator_Throwable a_predator_throwable = A_Predator_NetWorkManger.Handel_HttpExeption((HttpException) error);
                    Toast.makeText(DetailActivity.this, a_predator_throwable.getWrapedMesg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void getDetails() {
        String Url = "https://api.themoviedb.org/3/person/" + id;
        HashMap<String, Object> Body = new HashMap<String, Object>() {
            {
                put("api_key", ApiKey);
                put("language", "en");

            }
        };
        netWorkManger.FetchData(new Details_Response(), new HashMap<String, String>(), Url, Body, new A_Predator_NWM.RequistResuiltCallBack()
        {
            @Override
            public <T extends CService_DBase> void Sucess(T Resposne)
            {
                Details_Response details_response = (Details_Response) Resposne;

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.carbon_defaultdrawable);
                requestOptions.error(R.drawable.carbon_defaultdrawable);
                Glide.with(DetailActivity.this).setDefaultRequestOptions(requestOptions).load(Constants.Movies.getImageBaseUrl("w300")+details_response.getProfilePath()).into(main_image);
                details.setText("");
                details.append("Name : ");
                details.append("\n");
                details.append(details_response.getName() + " ");
                details.append("\n\n");
                details.append("Place Of Birth : ");
                details.append("\n");
                details.append(details_response.getPlaceOfBirth() + " ");
                details.append("\n\n");
                details.append("Bio : ");
                details.append("\n");
                details.append(details_response.getBiography() + " ");
                details.append("\n\n");
            }
            @Override
            public void Faild(Throwable error)
            {
                if (error instanceof IOException) {
                    if (error instanceof SocketTimeoutException) {
                        Toast.makeText(DetailActivity.this, "Time Out Exception", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(DetailActivity.this, "Pleas Check Your Connection", Toast.LENGTH_SHORT).show();

                    }

                } else if (error instanceof HttpException) {

                    A_Predator_Throwable a_predator_throwable = A_Predator_NetWorkManger.Handel_HttpExeption((HttpException) error);
                    Toast.makeText(DetailActivity.this, a_predator_throwable.getWrapedMesg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
