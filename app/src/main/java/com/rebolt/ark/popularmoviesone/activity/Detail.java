package com.rebolt.ark.popularmoviesone.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.NullDecoder;
import com.rebolt.ark.popularmoviesone.R;
import com.rebolt.ark.popularmoviesone.adapter.MovieAdapter;
import com.rebolt.ark.popularmoviesone.model.Movie;
import com.rebolt.ark.popularmoviesone.model.MoviesResponse;
import com.rebolt.ark.popularmoviesone.model.ReviewModel;
import com.rebolt.ark.popularmoviesone.model.ReviewResponse;
import com.rebolt.ark.popularmoviesone.model.VideoModel;
import com.rebolt.ark.popularmoviesone.model.VideoResponse;
import com.rebolt.ark.popularmoviesone.rest.ApiClient;
import com.rebolt.ark.popularmoviesone.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail extends AppCompatActivity {
    private List<ReviewModel> reviewModel;
    private List<VideoModel> videoModels;
    ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);

    private final static String API_KEY = "bb7151040747a331befa1dec25400c7b";
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        Bundle b = getIntent().getExtras();
        final Movie obj =
                b.getParcelable("name_of_extra");

        Call<ReviewResponse> call2 = apiService.getReviewDetails(obj.getId(),API_KEY);
        call2.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                int statuscode = response.code();
                reviewModel = response.body().getResults();
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }

        });
        Call<VideoResponse> call = apiService.getVideoDetails(obj.getId(),API_KEY);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                int statuscode = response.code();
                videoModels = response.body().getResults();

                LinearLayout layout = (LinearLayout) findViewById(R.id.linear_trailers);
                layout.setOrientation(LinearLayout.VERTICAL);

               LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,100);
                       LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout row2 = new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams params3= new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, 3);


                for(int j=1;j<=videoModels.size();j++)
                {
                    LinearLayout row = new LinearLayout(getApplicationContext());
                    row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    TextView textView = new TextView(getApplicationContext());
                    Button btnTag = new Button(getApplicationContext());

                    btnTag.setLayoutParams(params);
                    params.setMarginStart(150);
                    params2.setMarginStart(50);
                    textView.setLayoutParams(params2);

                    btnTag.setId(j+1);
                    textView.setId(j*10+50);

                    textView.setTextColor(Color.BLACK);

                    textView.setText(videoModels.get(j-1).getName());
                    textView.setTextSize(15);
                    btnTag.setBackgroundResource(R.drawable.play_drawable);

                    row.addView(btnTag);
                    row.addView(textView);

                    layout.addView(row);

                    if(j==videoModels.size())
                    {
                        params3.setMarginStart(100);
                        params3.setMarginEnd(100);
                        row2.setLayoutParams(params3);
                        row2.setBackgroundColor(Color.parseColor("#285A8C"));
                        layout.addView(row2);
                    }
                }
                TextView textView = new TextView(getApplicationContext());
                textView.setText("Reviews: ");
                textView.setLayoutParams(params2);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(17);
                textView.setTypeface(null, Typeface.BOLD);
                params2.setMarginStart(100);
                layout.addView(textView);

                if(reviewModel!= null)
                    if(reviewModel.size()>0)
                {
                    TextView textView1 = new TextView(getApplicationContext());
                    textView1.setText(reviewModel.get(0).getContent());
                    textView1.setTextColor(Color.GRAY);
                    textView1.setLayoutParams(params2);
                    params2.setMarginStart(100);
                    params2.setMarginEnd(100);

                    layout.addView(textView1);

                }
                for(int k=1;k<=videoModels.size();k++)
                {
                    Button button = (Button) findViewById(k+1);
                    final int finalK = k-1;
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoModels.get(finalK).getKey()));
                            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://www.youtube.com/watch?v=" + videoModels.get(finalK).getKey()));
                            try {
                                startActivity(appIntent);
                            } catch (ActivityNotFoundException ex) {
                                startActivity(webIntent);
                            }
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });



        ImageView backdrop;
        backdrop = (ImageView) findViewById(R.id.backdrop);

        ImageView poster;
        poster = (ImageView) findViewById(R.id.poster_detail);


        TextView title;
        title = (TextView) findViewById(R.id.title_detail);

        TextView date;
        date = (TextView) findViewById(R.id.date_detail);

        TextView overview;
        overview = (TextView) findViewById(R.id.description_detail);


        Log.d("THis",obj.getBackdropPath());
        Glide.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w780"+obj.getBackdropPath()).into(backdrop);
        Glide.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185"+obj.getPosterPath()).into(poster);
        title.setText(obj.getTitle());
        date.setText(obj.getReleaseDate());
        overview.setText(obj.getOverview());



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
