package com.shivgadhia.android.tomato.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.shivgadhia.android.tomato.ImageModel;
import com.shivgadhia.android.tomato.fragments.OneImagePageFragment;
import com.shivgadhia.android.tomato.fragments.ThreeImagePageFragment;
import com.shivgadhia.android.tomato.fragments.TwoImagePageFragment;
import com.shivgadhia.android.tomato.loaders.PostLoader;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Posts.PostReader;
import uk.co.senab.photoview.sample.HackyViewPager;

import java.util.ArrayList;

public class PagesActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<ArrayList<ImageModel>> {

    private HackyViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionbar();

        mViewPager = new HackyViewPager(this);
        mViewPager.setId(0x7F04FFF0);
        mViewPager.setOffscreenPageLimit(1);

        setContentView(mViewPager);
        initLoader();
    }

    private void setupActionbar() {
        getActionBar().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
    }

    public void initLoader() {
        LoaderManager lm = getLoaderManager();
        lm.destroyLoader(PostLoader.LOADER_ID);
        lm.initLoader(PostLoader.LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList<ImageModel>> onCreateLoader(int id, Bundle args) {
        PostReader postReader = new PostReader(new DatabaseReader(getContentResolver()));
        return new PostLoader(this, postReader);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ImageModel>> loader, ArrayList<ImageModel> data) {
        SamplePagerAdapter samplePagerAdapter = new SamplePagerAdapter(getSupportFragmentManager(), data.size());
        mViewPager.setAdapter(samplePagerAdapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ImageModel>> loader) {
    }

    private class SamplePagerAdapter extends FragmentStatePagerAdapter {

        private int size;

        public SamplePagerAdapter(FragmentManager fm, int size) {
            super(fm);
            this.size = size;
        }

        @Override
        public Fragment getItem(int position) {
            int index = (int) (Math.random() * 3);
            switch (index) {
                default:
                case 0:
                    return ThreeImagePageFragment.newInstance(position);
                case 1:
                    return OneImagePageFragment.newInstance(position);
                case 2:
                    return TwoImagePageFragment.newInstance(position);
            }
        }

        @Override
        public int getCount() {
            return (int) Math.ceil(size / 3);
        }
    }
}