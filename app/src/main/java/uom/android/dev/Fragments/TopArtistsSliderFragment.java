package uom.android.dev.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import uom.android.dev.LastFMSearchService;
import uom.android.dev.LastFmJson.TopArtist;
import uom.android.dev.R;


public class TopArtistsSliderFragment extends Fragment {

    private SliderLayout sliderShow;
    private List<TopArtist> topArtists;
    private CompositeDisposable mCompositeSubscription;
    private LastFMSearchService searchService;
    private static final long TIMEOUT_SECONDS = 30;

    public TopArtistsSliderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_artists_slider, container, false);
        mCompositeSubscription = new CompositeDisposable();
        sliderShow = (SliderLayout) rootView.findViewById(R.id.top_artists_slider);
        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Top);
        loadTopArtists();
        return rootView;
    }

    @Override
    public void onStop(){
        sliderShow.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onDestroy(){
        mCompositeSubscription.dispose();
        super.onDestroy();
    }


    private void loadTopArtists(){

        searchService = new LastFMSearchService();

        Flowable<List<TopArtist>> fetchDataObservable = searchService.getTopArtists();

        mCompositeSubscription.add(fetchDataObservable
                .timeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<List<TopArtist>>() {

                    @Override
                    public void onNext(List<TopArtist> topArtists){
                        TopArtistsSliderFragment.this.topArtists = topArtists;
                    }

                    @Override
                    public void onError(Throwable e){

                        Toast.makeText(getActivity(), "Download Error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                        for(TopArtist topArtist : topArtists){
                            sliderShow.addSlider(new TextSliderView(getActivity())
                                    .description(topArtist.getName())
                                    .image(topArtist.getmImage())
                                    .setScaleType(BaseSliderView.ScaleType.Fit));
                        }
                    }
                })
        );
    }
}
