package qenawi.panda.movie_search_list;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;
import carbon.Carbon;
import carbon.component.TextRow;
import carbon.recycler.RowListAdapter;
import carbon.widget.LinearLayout;
import carbon.widget.RecyclerView;
import carbon.widget.SearchEditText;
import com.annimon.stream.Stream;
import qenawi.panda.a_predator.network_Handeler.A_Predator_NWM;
import qenawi.panda.a_predator.network_Handeler.A_Predator_NetWorkManger;
import qenawi.panda.a_predator.network_Handeler.A_Predator_Throwable;
import qenawi.panda.a_predator.network_Handeler.CService_DBase;
import qenawi.panda.movie_search_list.network.Movies_Response;
import qenawi.panda.movie_search_list.network.Movies_data;
import retrofit2.HttpException;
import timber.log.Timber;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Search_Activity extends AppCompatActivity
{
    public static final String ApiKey = "7d1dd3f8f443ecde27c323485a170bdc";
    private A_Predator_NetWorkManger workManger;
    Searchadapter fruitAdapter;
    SwipeRefreshLayout swipeRefresh;
    LinearLayout searchEditText;
    View searchButton;
    carbon.widget.rx.TextView empty_text;
    SearchEditText searchEdit;
    private RecyclerView recyclerView;
    int page = 1;
    int SearchFlag = 0;
    boolean is_lastPage = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }
    private void initView()
    {
        workManger = new A_Predator_NetWorkManger(this);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        empty_text=findViewById(R.id.dummy_text);
        recyclerView = findViewById(R.id.recyclerView);
        searchEdit = findViewById(R.id.searchEdit);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        fruitAdapter = new Searchadapter(new ArrayList<Movies_data>(), new Searchadapter.OnSrelection()
        {
            @Override
            public void selection(int id)
            {
                Intent intent =new Intent(Search_Activity.this, DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id",id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(fruitAdapter);

        recyclerView.setPagination(new RecyclerView.Pagination(layoutManager) {
            @Override
            protected boolean isLoading() {
                return swipeRefresh.isRefreshing();
            }

            @Override
            protected boolean isLastPage() {
                return is_lastPage;
            }

            @Override
            protected void loadNextPage() {
                swipeRefresh.setRefreshing(true);
                if (SearchFlag == 0)
                    Search_Activity.this.loadNextPage();
                else Search_Activity.this.LoadSearchNextPage();

            }
        });

        swipeRefresh.setOnRefreshListener(() ->
        {
            fruitAdapter.replaceData(new ArrayList<>());
            page = 1;
            SearchFlag = 0;
            loadNextPage();

        });
        searchEditText = findViewById(R.id.searchbar);
        searchButton = findViewById(R.id.search);
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                page = 1;
                SearchFlag = 1;
                LoadSearchNextPage();
                return false;
            }
        });
        OpenAnmiation();
        CloseAnmiation();
        loadNextPage();
    }

    private void loadNextPage() {


        HashMap<String, String> Headrs = new HashMap<String, String>();
        HashMap<String, Object> Body = new HashMap<String, Object>() {
            {
                put("api_key", ApiKey);
                put("language", "en");
                put("page", page);

            }
        };
        String Url = "https://api.themoviedb.org/3/person/popular";
        workManger.FetchData(new Movies_Response(), Headrs, Url, Body, new A_Predator_NWM.RequistResuiltCallBack() {
            @Override
            public <T extends CService_DBase> void Sucess(T Resposne) {
                Movies_Response data = (Movies_Response) Resposne;








                if (!data.getResults().isEmpty())
                {
                    if (page > 1)
                        fruitAdapter.updateData(data.getResults());
                    else fruitAdapter.replaceData(data.getResults());

                    page++;
                } else
                    {
                    is_lastPage = true;
                     }

                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void Faild(Throwable error) {
                is_lastPage = true;
                error.printStackTrace();
                if (error instanceof IOException) {
                    if (error instanceof SocketTimeoutException) {
                        Toast.makeText(Search_Activity.this, "Time Out Exception", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Search_Activity.this, "Pleas Check Your Connection", Toast.LENGTH_SHORT).show();

                    }

                } else if (error instanceof HttpException) {

                    A_Predator_Throwable a_predator_throwable = A_Predator_NetWorkManger.Handel_HttpExeption((HttpException) error);
                    Toast.makeText(Search_Activity.this, a_predator_throwable.getWrapedMesg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Search_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
                swipeRefresh.setRefreshing(false);
            }
        });

    }

    private void LoadSearchNextPage()
    {
        HashMap<String, String> Headrs = new HashMap<String, String>();
        HashMap<String, Object> Body = new HashMap<String, Object>() {
            {
                put("api_key", ApiKey);
                put("language", "en");
                put("query", searchEdit.getText().toString());
                put("page", page);
            }
        };
        String Url = "https://api.themoviedb.org/3/search/person";
        workManger.FetchData(new Movies_Response(), Headrs, Url, Body, new A_Predator_NWM.RequistResuiltCallBack() {
            @Override
            public <T extends CService_DBase> void Sucess(T Resposne)
            {
                Movies_Response data = (Movies_Response) Resposne;
                if (!data.getResults().isEmpty()) {
                    if (page > 1)
                        fruitAdapter.updateData(data.getResults());
                    else fruitAdapter.replaceData(data.getResults());
                    page++;
                } else {
                    is_lastPage = true;
                }

                swipeRefresh.setRefreshing(false);
            }
            @Override
            public void Faild(Throwable error)
            {
                is_lastPage = true;
                error.printStackTrace();
                if (error instanceof IOException) {
                    if (error instanceof SocketTimeoutException) {
                        Toast.makeText(Search_Activity.this, "Time Out Exception", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Search_Activity.this, "Pleas Check Your Connection", Toast.LENGTH_SHORT).show();

                    }

                } else if (error instanceof HttpException) {

                    A_Predator_Throwable a_predator_throwable = A_Predator_NetWorkManger.Handel_HttpExeption((HttpException) error);
                    Toast.makeText(Search_Activity.this, a_predator_throwable.getWrapedMesg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Search_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    void CloseAnmiation() {
        findViewById(R.id.close).setOnClickListener(v ->
        {
            int[] setLocation = new int[2];
            searchEditText.getLocationOnScreen(setLocation);
            int[] sbLocation = new int[2];
            searchButton.getLocationOnScreen(sbLocation);
            Animator animator = searchEditText.createCircularReveal(sbLocation[0] - setLocation[0] + v.getWidth() / 2, searchEditText.getHeight() / 2, searchEditText.getWidth(), 0);
            animator.setInterpolator(new FastOutSlowInInterpolator());
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    searchEditText.setVisibility(View.INVISIBLE);
                }
            });
            animator.start();
        });
    }

    void OpenAnmiation() {
        searchButton.setOnClickListener(v -> {
            searchEditText.setVisibility(View.VISIBLE);
            int[] setLocation = new int[2];
            searchEditText.getLocationOnScreen(setLocation);
            int[] sbLocation = new int[2];
            searchButton.getLocationOnScreen(sbLocation);
            Animator animator = searchEditText.createCircularReveal(sbLocation[0] - setLocation[0] + v.getWidth() / 2, searchEditText.getHeight() / 2, 0, searchEditText.getWidth());
            animator.setInterpolator(new FastOutSlowInInterpolator());
            animator.start();
        });
    }

}
