/**
 *   Nextcloud Android client application
 *
 *   @author Andy Scherzinger
 *   Copyright (C) 2016 Andy Scherzinger
 *   Copyright (C) 2016 Nextcloud
 *
 *   This program is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU AFFERO GENERAL PUBLIC LICENSE
 *   License as published by the Free Software Foundation; either
 *   version 3 of the License, or any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU AFFERO GENERAL PUBLIC LICENSE for more details.
 *
 *   You should have received a copy of the GNU Affero General Public
 *   License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.owncloud.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.owncloud.android.R;
import com.owncloud.android.datamodel.MediaFolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to display all auto-synced folders and/or instant upload media folders.
 */
public class FolderSyncAdapter extends SectionedRecyclerViewAdapter<FolderSyncAdapter.MainViewHolder>
        implements View.OnClickListener, View.OnTouchListener {

    private static final String TAG = FolderSyncAdapter.class.getSimpleName();

    private final Context mContext;
    private final int mGridWidth;
    private final ClickListener mListener;
    private final List<MediaFolder> mMediaFolders;
    private final RecyclerView mRecyclerView;

    public FolderSyncAdapter(Context context, int gridWidth, ClickListener listener, RecyclerView recyclerView) {
        mContext = context;
        mGridWidth = gridWidth * 2;
        mListener = listener;
        mMediaFolders = new ArrayList<>();
        mRecyclerView = recyclerView;
    }

    public void setMediaFolders(List<MediaFolder> mediaFolders) {
        mMediaFolders.clear();
        mMediaFolders.addAll(mediaFolders);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public int getSectionCount() {
        return mMediaFolders.size();
    }

    @Override
    public int getItemCount(int section) {
        return mMediaFolders.get(section).filePaths.size();
    }

    @Override
    public void onBindHeaderViewHolder(MainViewHolder holder, int section) {
        holder.title.setText(mMediaFolders.get(section).folder.substring(mMediaFolders.get(section).folder
                .lastIndexOf("/")+1, mMediaFolders.get(section).folder.length()));
        holder.syncStatusButton.setVisibility(View.VISIBLE);
        holder.syncStatusButton.setTag(section);
        holder.syncStatusButton.setOnTouchListener(this);
        holder.menuButton.setVisibility(View.VISIBLE);
        holder.menuButton.setTag(section);
        holder.menuButton.setOnTouchListener(this);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int section, int relativePosition, int absolutePosition) {
        final Context c = holder.itemView.getContext();

        /**
        if (BitmapUtils.isImage(file)){
            // Thumbnail in Cache?
            Bitmap thumbnail = ThumbnailsCacheManager.getBitmapFromDiskCache(
                    String.valueOf(file.hashCode())
            );
            if (thumbnail != null){
                fileIcon.setImageBitmap(thumbnail);
            } else {

                // generate new Thumbnail
                if (allowedToCreateNewThumbnail) {
                    final ThumbnailsCacheManager.ThumbnailGenerationTask task =
                            new ThumbnailsCacheManager.ThumbnailGenerationTask(fileIcon);
                    if (thumbnail == null) {
                        if (BitmapUtils.isVideo(file)) {
                            thumbnail = ThumbnailsCacheManager.mDefaultVideo;
                        } else {
                            thumbnail = ThumbnailsCacheManager.mDefaultImg;
                        }
                    }
                    final ThumbnailsCacheManager.AsyncThumbnailDrawable asyncDrawable =
                            new ThumbnailsCacheManager.AsyncThumbnailDrawable(
                                    mContext.getResources(),
                                    thumbnail,
                                    task
                            );
                    fileIcon.setImageDrawable(asyncDrawable);
                    task.execute(file);
                    Log_OC.v(TAG, "Executing task to generate a new thumbnail");

                } // else, already being generated, don't restart it
            }
        } else {
            fileIcon.setImageResource(MimetypeIconUtil.getFileTypeIconId(null, file.getName()));
        }
        */
        holder.image.setImageResource(R.drawable.file_image);

        /**
        if (res == 0) {
            holder.image.setBackgroundColor(Color.parseColor("#40000000"));
        } else {
            Glide.with(c)
                    .fromResource()
                    .load(res)
                    .into(holder.image);
        }
         */

        //holder.itemView.setTag(String.format(Locale.getDefault(), "%d:%d:%d", section, relativePos, absolutePos));
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                viewType == VIEW_TYPE_HEADER ?
                        R.layout.folder_sync_item_header : R.layout.grid_sync_item, parent, false);
        return new MainViewHolder(v);
    }

    public interface ClickListener {
        void onClick(View view, int section, int relative, int absolute);
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {

        public MainViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
            menuButton = (ImageButton) itemView.findViewById(R.id.syncStatusButton);
            syncStatusButton = (ImageButton) itemView.findViewById(R.id.settingsButton);
        }

        final ImageView image;
        final TextView title;
        final ImageButton menuButton;
        final ImageButton syncStatusButton;
    }
}
