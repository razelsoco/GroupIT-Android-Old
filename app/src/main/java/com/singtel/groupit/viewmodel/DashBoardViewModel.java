package com.singtel.groupit.viewmodel;

import android.databinding.ObservableField;
import android.support.v4.app.Fragment;
import android.view.View;

import com.singtel.groupit.model.DataManager;
import com.singtel.groupit.GroupITApplication;
import com.singtel.groupit.R;
import com.singtel.groupit.model.TestUserResponse;
import com.singtel.groupit.uiutil.UiUtils;
import com.singtel.groupit.util.GroupITSharedPreferences;
import com.singtel.groupit.view.fragment.MenuNotesFragment;
import com.singtel.groupit.view.fragment.SettingsFragment;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by razelsoco on 21/6/16.
 */

public class DashBoardViewModel implements ViewModel {
    public ObservableField<String> userName;
    public ObservableField<String> userGroup;

    GroupITSharedPreferences pref;
    DataManager dataManager;
    Subscription subscription;


    private Fragment mFragment;

    public DashBoardViewModel(Fragment fragment) {
        this.userName =  new ObservableField<>("Razel S");
        this.userGroup =  new ObservableField<>("Group IT");
        this.mFragment = fragment;
        this.dataManager = GroupITApplication.get(fragment.getContext()).getComponent().dataManager();
        this.pref = GroupITApplication.get(fragment.getContext()).getComponent().sharedPreferences();
        loadUser();
    }

    public void onNotesClick(View view){
        UiUtils.replaceFragmentRightIn(
                mFragment.getActivity(), MenuNotesFragment.class.getName(),
                MenuNotesFragment.newInstance(), R.id.menu_frame);
    }

    public void onSettingsClick(View view){
        UiUtils.replaceFragmentRightIn(
                mFragment.getActivity(), SettingsFragment.class.getName(),
                SettingsFragment.newInstance(), R.id.menu_frame);
    }

    public void loadUser(){
        subscription = dataManager.getUser(pref.getUserToken(this.mFragment.getContext())).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribe(new Subscriber<TestUserResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TestUserResponse testUserResponse) {
                        userName.set(testUserResponse.user.getName());
                        userGroup.set(testUserResponse.user.getRole());

                        //TODO save to preferences or DB?
                    }
                });
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
    }
}
