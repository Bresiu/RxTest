package com.example.bresiu.rxtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding2.widget.RxTextView;

public class MainActivity extends AppCompatActivity implements MainView {
  private Presenter presenter;

  @BindView(R.id.navigation) BottomNavigationView navigation;
  @BindView(R.id.message) TextView mTextMessage;
  @BindView(R.id.edit_text) EditText mEditText;
  @BindView(R.id.progress) ProgressBar mProgressBar;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
      new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
            case R.id.navigation_home:
              mTextMessage.setText(R.string.title_home);
              return true;
            case R.id.navigation_dashboard:
              mTextMessage.setText(R.string.title_dashboard);
              return true;
            case R.id.navigation_notifications:
              mTextMessage.setText(R.string.title_notifications);
              return true;
          }
          return false;
        }
      };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    presenter = new Presenter(this);
    presenter.initTextObservable(RxTextView.textChanges(mEditText));
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.dispose();
  }

  @Override public void showProgressBar() {
    mProgressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideProgressBar() {
    mProgressBar.setVisibility(View.GONE);
  }
}
