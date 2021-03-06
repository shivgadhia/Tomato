package com.shivgadhia.android.tomato.loaders;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import com.shivgadhia.android.tomato.models.ImageModel;
import com.shivgadhia.android.tomato.persistance.Posts.PostReader;

import java.util.ArrayList;

public class PostLoader implements LoaderManager.LoaderCallbacks<Cursor> {
    ArrayList<ImageModel> imageModels;
    public static final int LOADER_ID = 123;

    private LoaderManager loaderManager;
    private final PostReader postReader;
    private Context context;
    private DataUpdatedListener dataUpdatedListener;

    public interface DataUpdatedListener {
        void dataUpdated(ArrayList<ImageModel> list);
    }

    public PostLoader(Context context, LoaderManager lm, PostReader postReader, DataUpdatedListener dataUpdatedListener) {
        loaderManager = lm;
        this.postReader = postReader;
        this.context = context;
        this.dataUpdatedListener = dataUpdatedListener;
    }

    public void initLoader() {
        destroyLoader();
        loaderManager.initLoader(LOADER_ID, null, this);
    }

    public void destroyLoader() {
        loaderManager.destroyLoader(LOADER_ID);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return postReader.getAll(context);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        imageModels = postReader.populateListWith(data);
        dataUpdatedListener.dataUpdated(imageModels);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        imageModels.clear();
    }
}
