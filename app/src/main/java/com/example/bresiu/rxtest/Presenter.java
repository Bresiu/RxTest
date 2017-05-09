package com.example.bresiu.rxtest;

import android.support.annotation.NonNull;
import android.util.Log;
import com.jakewharton.rxbinding2.InitialValueObservable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.concurrent.TimeUnit;

class Presenter {
  private CompositeDisposable disposables;
  private MainView view;

  Presenter(MainView view) {
    this.view = view;
    disposables = new CompositeDisposable();
  }

  void dispose() {
    Log.d("BRS", "dispose");
    disposables.dispose();
  }

  void initTextObservable(InitialValueObservable<CharSequence> charSequence) {
    disposables.add(
        charSequence.skipInitialValue().switchMap(new Function<CharSequence, Observable<String>>() {
          @Override public Observable<String> apply(@NonNull CharSequence charSequence)
              throws Exception {
            return Observable.just(charSequence.toString()).filter(new Predicate<String>() {
              @Override public boolean test(@NonNull String str) throws Exception {
                return str.length() > 3;
              }
            }).delay(300, TimeUnit.MILLISECONDS);
          }
        }).subscribe(new Consumer<String>() {
          @Override public void accept(String str) throws Exception {
            Log.d("BRS", "Str: " + str);
          }
        }));
  }
}