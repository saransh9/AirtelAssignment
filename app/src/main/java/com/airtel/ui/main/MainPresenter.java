package com.airtel.ui.main;

import com.airtel.data.ApiCalls;
import com.airtel.data.pojo.ApiData;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by saransh on 23/03/18.
 */

public class MainPresenter implements MainPresenterContract {

    MainActivity view;

    ApiCalls apiCalls;
    private CompositeDisposable disposable;

    @Inject
    public MainPresenter(ApiCalls apiCalls) {
        this.apiCalls = apiCalls;

        disposable = new CompositeDisposable();

    }

    public void setView(MainActivity view) {
        this.view = view;
    }

    @Override
    public void fetchData(String PageNo) {
        view.showLoader();

        disposable.add(apiCalls.fetchNewest(PageNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<ApiData>() {
                    @Override
                    public void onNext(ApiData data) {
                        if (view != null) {
                            view.hideLoader();
                            view.dataFetched(false, data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null) {
                            view.hideLoader();
                            // view.showError();
                            view.dataFetched(true, null);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

    @Override
    public void fetchHighestRatedData(String PageNo) {
        view.showLoader();

        disposable.add(apiCalls.fetchHightestRated(PageNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<ApiData>() {
                    @Override
                    public void onNext(ApiData data) {
                        if (view != null) {
                            view.hideLoader();
                            view.dataFetched(false, data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null) {
                            view.hideLoader();
                            // view.showError();
                            view.dataFetched(true, null);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

}
