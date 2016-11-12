package com.exotikosteam.exotikos.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exotikosteam.exotikos.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subjects.PublishSubject;

public class SampleCardFragment extends Fragment implements ExpandableCard {

    private View rootView;

    private String cardName;
    private String relativeTime;

    // Bindings
    @BindView(R.id.tvCardName) TextView tvCardName;
    @BindView(R.id.tvTime) TextView tvTime;
    @BindView(R.id.rlCardContents)
    ExpandableRelativeLayout rlCardContents;

    // Event topics
    private final PublishSubject<SampleCardFragment> titleClickSubject = PublishSubject.create();

    public static SampleCardFragment newInstance(String name, String time, boolean isCollapsed) {

        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("time", time);
        args.putBoolean("collapsed", isCollapsed);

        SampleCardFragment fragment = new SampleCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sample_card, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardName = getArguments().getString("name");
        relativeTime = getArguments().getString("time");

        tvCardName.setText(cardName);
        tvTime.setText(relativeTime);
//        rlCardContents.setVisibility(getArguments().getBoolean("collapsed") ? View.GONE : View.VISIBLE);

        setupListeners();
    }

    private void setupListeners() {
        tvCardName.setOnClickListener(v -> titleClickSubject.onNext(this));
    }

    public PublishSubject<SampleCardFragment> getTitleClickSubject() {
        return titleClickSubject;
    }

    @Override
    public void expand() {
        if (!isDetached()) {
            rlCardContents.expand();
        }
    }

    @Override
    public void collapse() {
        if (!isDetached()) {
            rlCardContents.collapse();
        }
    }

    @Override
    public void toggle() {
        if (!isDetached()) {
            rlCardContents.toggle();
        }
    }
}