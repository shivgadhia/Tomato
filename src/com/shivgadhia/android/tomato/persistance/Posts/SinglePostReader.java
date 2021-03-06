package com.shivgadhia.android.tomato.persistance.Posts;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Tables;
import com.shivgadhia.android.tomato.persistance.TomatoProvider;

public class SinglePostReader extends PostReader {
    private String postId;

    public SinglePostReader(DatabaseReader databaseReader, String postId) {
        super(databaseReader);
        this.postId = postId;
    }

    @Override
    protected Cursor getCursor() {
        return databaseReader.getAllFrom(Tables.TBL_POSTS, Tables.Posts._ID + "=?", new String[]{postId});
    }

    @Override
    public Loader<Cursor> getAll(Context context) {
        return new CursorLoader(context, Uri.parse(TomatoProvider.AUTHORITY + Tables.TBL_POSTS), null, Tables.Posts._ID + "=?", new String[]{postId}, Tables.Posts.COL_POST_DATE + " DESC");
    }
}
