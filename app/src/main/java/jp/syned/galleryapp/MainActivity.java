package jp.syned.galleryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private GridLayoutManager layoutManager;
    private ApiInterface apiInterface;
    private RecycleAdapter adapter;

    private int page_number = 1;
    private int item_count = 7;


    //Var for pags
    private boolean isLoading = true;
    private int pastVisibleItems,visibleItemCount,totallItemCount,previous_total = 0;
    private int view_threshold = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        apiInterface = ApiClient.getApiclient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        Call<List<DataResponse>> call = apiInterface.getImages(page_number,item_count);

        call.enqueue(new Callback<List<DataResponse>>() {
            @Override
            public void onResponse(Call<List<DataResponse>> call, Response<List<DataResponse>> response) {
                List<Images> images = response.body().get(1).getImages();
                adapter = new RecycleAdapter(images,MainActivity.this);
                recyclerView.setAdapter(adapter);
                Toast.makeText(MainActivity.this,"Fist page is loader..",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<DataResponse>> call, Throwable t) {

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = layoutManager.getChildCount();
                totallItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if(dy>0){
                    if(isLoading){
                        if(totallItemCount>previous_total){
                            isLoading = false;
                            previous_total = totallItemCount;
                        }
                    }
                    if(!isLoading&&(totallItemCount-visibleItemCount)<=(pastVisibleItems+view_threshold)){
                        page_number++;
                        System.out.println(page_number);
                        performPagaination();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void performPagaination(){
        progressBar.setVisibility(View.VISIBLE);
        Call<List<DataResponse>> call = apiInterface.getImages(page_number,item_count);

        call.enqueue(new Callback<List<DataResponse>>() {
            @Override
            public void onResponse(Call<List<DataResponse>> call, Response<List<DataResponse>> response) {

                if(response.body().get(0).getStatus().equals("ok")){
                    List<Images> images = response.body().get(1).getImages();
                    adapter.addImages(images);
                    Toast.makeText(MainActivity.this,"Page "+page_number+" is loader..",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"No more Images avalable...",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<DataResponse>> call, Throwable t) {

            }
        });
    }
}
