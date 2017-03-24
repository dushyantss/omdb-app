package com.dushyantss.omdbapp;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dushyantss.omdbapp.models.Model;
import com.dushyantss.omdbapp.networking.OMDBService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.name)
    TextInputEditText mNameText;

    @BindView(R.id.poster)
    ImageView mPoster;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.rating)
    TextView mRating;

    @BindView(R.id.genre)
    TextView mGenre;

    @BindView(R.id.release_date)
    TextView mReleaseDate;

    @BindView(R.id.plot)
    TextView mPlot;

    @BindView(R.id.card_parent)
    View mCardParent;

    @BindView(R.id.type)
    Spinner mType;

    private OMDBService mSearchService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupSpinner();

        initializeSearchService();
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"movie", "series"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mType.setAdapter(adapter);
    }

    private void initializeSearchService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mSearchService = retrofit.create(OMDBService.class);
    }

    @OnClick(R.id.search)
    public void search(View view){
        searchAndUpdateView();
    }

    @OnEditorAction(R.id.name)
    public boolean onEditorAction(TextInputEditText v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            searchAndUpdateView();
            return true;
        }
        return false;
    }

    private void searchAndUpdateView(){

        hideKeyboard();

        mCardParent.setVisibility(View.GONE);

        Log.e("Main", mType.getSelectedItem().toString());

        Call<Model> call = mSearchService.search(mNameText.getText().toString(), mType.getSelectedItem().toString());

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model model = response.body();
                if (model.getTitle() != null && !model.getTitle().isEmpty()) {
                    updateView(model);
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.network_failed, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void updateView(Model model){

        mCardParent.setVisibility(View.VISIBLE);

        // we update the poster
        Glide.with(this)
                .load(model.getPoster())
                .asBitmap()
                .into(mPoster);

        // we update the text fields
        mTitle.setText(model.getTitle());
        mRating.setText(model.getRating() + "/10");
        mGenre.setText(model.getGenre());
        mReleaseDate.setText(model.getReleaseDate());
        mPlot.setText(model.getPlotShort());
    }
}
