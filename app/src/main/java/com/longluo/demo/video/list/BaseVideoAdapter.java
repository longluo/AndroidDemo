package com.longluo.demo.video.list;

import java.io.File;
import java.util.*;

import android.widget.ProgressBar;

import qsbk.app.QsbkApp;
import qsbk.app.R;
import qsbk.app.ad.feedsad.FeedsAdUtils;
import qsbk.app.model.Article;
import qsbk.app.model.ImageSize;
import qsbk.app.utils.*;
import qsbk.app.video.IVideoPlayTaskParam;
import qsbk.app.video.VideoLoopStatistics;
import qsbk.app.video.VideoPlayView;
import qsbk.app.video.VideoPlayView.OnVideoPlayViewClickListener;
import qsbk.app.video.VideoPlayView.VideoPlayControllerParams;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;
import android.widget.TextView;

import qsbk.app.video.VideoStore;

public class BaseVideoAdapter extends ArticleAdapter implements OnVideoPlayViewClickListener {

    @Override
    protected int getArticleLayout() {
        return R.layout.layout_article_item;
    }


    public BaseVideoAdapter(Activity context, ListView listView,
                            ArrayList<Object> dataSource, String votePoint, String scenariosName) {
        super(context, listView, dataSource, votePoint, scenariosName);
    }

    @Override
    protected void initArticleContent(final Article article, final ViewHolder holder) {
        super.initArticleContent(article, holder);
        holder.videoPlayer.reset();

        //去除标记
        holder.play.setTag(null);

        if (article.isVideoArticle()) {
            if (holder.videoProgress != null) {
                holder.videoProgress.setVisibility(View.GONE);
            }
            holder.videoPlayer.setVisibility(View.VISIBLE);
            holder.videoPlayer.setOnVideoPlayViewClickListener(this);
            holder.setVideoPlayTask(article);
            holder.play.setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holder.startPlayVideo();
                        }
                    }
            );
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.videoPlayer.setVisibility(View.GONE);
            holder.play.setVisibility(View.GONE);
            holder.play.setOnClickListener(null);
            if (holder.videoProgress != null) {
                holder.videoProgress.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void ensureImageSize(Article article, ViewHolder holder) {
        if (article.isVideoArticle()) {
            int[] sizes = FeedsAdUtils.getRequestWidthAndMaxPixcel();
            int mRequestWidth = sizes[0];
            int mMaxHeight = sizes[1];
            ImageSize imageSize = null;
            if (article.absPicHeight != 0 && article.absPicWidth != 0) {
                imageSize = new ImageSize(article.absPicWidth, article.absPicHeight);
            } else {
                imageSize = new ImageSize(mRequestWidth, (mMaxHeight * 4) / 9);
            }
            setImageLayoutParams(holder.imageView, imageSize, mRequestWidth, mMaxHeight);
            return;
        }
        super.ensureImageSize(article, holder);
    }

    @Override
    protected void initProgressBar(Article article, ViewHolder holder) {
        if (!article.isVideoArticle()) {
            super.initProgressBar(article, holder);
            return;
        }
        if (TextUtils.isEmpty(article.absPicPath) || "null".equalsIgnoreCase(article.absPicPath)) {
            holder.imageView.setTag(null);
            holder.progress.setTag(null);
        } else {
            holder.progress.setTag(article.absPicPath);
            holder.imageView.setTag(holder.progress);
        }
    }

    @Override
    protected void initImage(Article article, ViewHolder holder) {
        if (article.isVideoArticle()) {
            final String absPic = article.absPicPath;
            if (!TextUtils.isEmpty(absPic) && !"null".equalsIgnoreCase(absPic)) {// 有图
                holder.imageLayout.setVisibility(View.VISIBLE);
                holder.imageView.setVisibility(View.VISIBLE);
                if (doNotLoadImageDirectly()) {// 仅在wifi加载
                    holder.imageLoading.setVisibility(View.VISIBLE);
                    ((TextView) holder.imageLoading).setText("点击加载");
                } else {
                    holder.imageLoading.setVisibility(View.GONE);
                }
                loadImage(article.id, absPic, holder.imageView,
                        holder.imageLoading);
            } else {// 无图
                holder.imageView.setVisibility(View.GONE);
                holder.imageLayout.setVisibility(View.GONE);
            }

        } else {
            super.initImage(article, holder);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d("on stop in baseVideo adapter");
        VideoPlayTask.stopPlayByList(this.mScenario);
    }


    /**
     * Useful for ArticleAdapter item child view {@link ViewHolder} that has the same parent.
     * <ViewGroup>
     * <View id=image></View>
     * <View id=play_video></View>
     * <VideoView />
     * <ViewGroup>
     * Effected on id {@link qsbk.app.R.id#image} and {@link qsbk.app.R.id#play_video}
     *
     * @param player
     */
    public static void stop(VideoPlayView player, boolean pauseOnly, boolean showPlayIcon) {
        if (player != null) {
            ViewParent parent = player.getParent();
            if (parent != null) {
                ViewGroup viewGroup = (ViewGroup) parent;
                View image = viewGroup.findViewById(R.id.image);
                View play = viewGroup.findViewById(R.id.play_video);
                if (image != null) {
                    image.setVisibility(View.VISIBLE);
                }
                if (play != null) {
                    LogUtil.d("play is visible:");
                    if (showPlayIcon) {
                        play.setVisibility(View.VISIBLE);
                    } else {
                        play.setVisibility(View.GONE);
                    }
                }
            }
            if (pauseOnly) {
                player.pause();
            } else {
                player.reset();
            }
        }
    }

    @Override
    protected void initVote(Article article, ViewHolder holder) {
        super.initVote(article, holder);
        if (article.isVideoArticle()) {
            String text = article.getLoopString();
            if (!text.startsWith(" ")) {
                text = " " + text;
            }
            holder.loop.setText(text);
        } else {
            holder.loop.setText("");
        }
    }


    /**
     * 播放任务
     */
    public static class VideoPlayTask implements VideoStore.VideoStoreListener, OnPreparedListener, OnCompletionListener {

        // 这个player 是播放器
        private VideoPlayView mPlayer;

        // 缓存最后的一次播放的player
        //public static VideoPlayView mLastPlayer;
        public static VideoPlayTask mLastPlayedTask;

        public static Set<VideoPlayTask> mPipedPlayList = new HashSet<VideoPlayTask>();

        // view
        private VideoPlayControllerParams mParams;

        private OnPreparedListener mPreparedListener;

        private View playIcon;
        private View coverImage;
        private ProgressBar videoProgress;
        private TextView loopText;
        private Article article;

        private boolean isCanceled = false;

        private TimeDelta td = new TimeDelta();

        private void cancelLast() {
            LogUtil.d("cancel last task");
            //取消掉之前的task
            Object obj = this.mPlayer.getTag();
            if (obj != null) {
                VideoPlayTask task = (VideoPlayTask) obj;
                task.cancel();
            }
            this.mPlayer.setTag(this);
        }

        public VideoPlayTask(VideoPlayView player, VideoPlayControllerParams params) {
            this(player, params, null);
        }

        private VideoPlayTask(VideoPlayView player, VideoPlayControllerParams params, OnPreparedListener listener) {
            this.mPlayer = player;
            this.mParams = params;
            this.mPreparedListener = listener;
            cancelLast();
        }

        public String scenario;

        //ProgressBar pb,View coverImage,View playIcon
        public void setView(IVideoPlayTaskParam params) {
            //this.mPlayIcon =playIcon;
            this.videoProgress = params.getProgressBar();

            if (this.videoProgress != null) {
                this.videoProgress.setTag(this.mParams.path);
                hideProgress();
            }

            this.playIcon = params.getPlayBtn();
            if (this.playIcon != null) {
                this.playIcon.setTag(this);
                this.playIcon.setVisibility(QsbkApp.getInstance().isAutoPlayVideo() ? View.GONE : View.VISIBLE);
            }

            this.article = params.getArticle();
            this.loopText = params.getLoopText();
            if (this.loopText != null) {
                this.loopText.setTag(this);
            }

            this.coverImage = params.getCoverIamge();

            this.scenario = params.getScenario();

            LogUtil.d("set view:" + this.toString());
        }

        public void autoSetVolume() {
            VideoPlayView.autoSetVolume(mPlayer);
        }

        public Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                LogUtil.d("td run start:" + td.getDelta());
                startPlayAsyn();
            }
        };

        private boolean isVideoOK = false;

        @Override
        public void onVideoOK(String url, File filePath) {
            LogUtil.d("td video ok:" + td.getDelta());
            isVideoOK = true;
            LogUtil.d("video is ok:" + url + " filepath:" + filePath + " " + this.toString());
            if (isCanceled) {
                LogUtil.d("video is canceled");
                return;
            }

            VideoPlayControllerParams param = (VideoPlayControllerParams) this.mParams.clone();
            param.path(filePath.getAbsolutePath());

            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnCompletionListener(this);
            LogUtil.d("on video ok and stop old:");

            if (mLastPlayedTask != null && mLastPlayedTask != this) {
                LogUtil.d("on video ok");
                mLastPlayedTask.stopPlayAndResetView();
            }

            LogUtil.d("td stop:" + td.getDelta());

            mPipedPlayList.remove(this);
            mLastPlayedTask = this;
            mPlayer.setVisibility(View.VISIBLE);
            mPlayer.setParams(param);
        }

        @Override
        public void onVideoFail(String url, String error) {
            if (HttpUtils.isNetworkConnected(QsbkApp.mContext)) {
                ToastUtil.Short("下载视频文件失败!");
            } else {
                ToastUtil.Short("网络未连接，下载视频文件失败!");
            }
            this.hideProgress();
            if (!QsbkApp.getInstance().isAutoPlayVideo()) {
                this.showPlayIcon();
            }
        }

        private boolean isProgressValid() {
            return !isCanceled && this.videoProgress != null && this.mParams != null && this.videoProgress.getTag().equals(this.mParams.path);
        }

        private void showProgress(int progress) {
            if (isProgressValid()) {
                this.videoProgress.setVisibility(View.VISIBLE);
                this.videoProgress.setProgress(progress);
            }
        }

        private void hideProgress() {
            if (isProgressValid()) {
                this.videoProgress.setVisibility(View.GONE);
            }
        }

        private static final Random sRandom = new Random();

        private void addLoopAnimationDelay(final int randomCount) {
            sHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (loopText != null && loopText.getTag() == VideoPlayTask.this) {
                        loopText.setText(article.getLoopString());
                    }
//					ToastAndDialog.makeTextVideoAdapter(loopText, "+"+randomCount, 300, loopText.getContext().getResources().getColor(R.color.ic_action_bar_item_normal));
                }
            }, sRandom.nextInt(3000));
        }

        // 主要要在onComplete里面调用
        private void addLoop() {
            if (loopText != null
                    && loopText.getTag() == this
                    && article != null) {
                final int randomCount = article.generateLoopRandom();
                article.loop += randomCount;
                addLoopAnimationDelay(randomCount);
                VideoLoopStatistics.getInstance().loopBatch(article.id, randomCount);
            }
        }

        @Override
        public void onVideoProgress(String url, int current, int total) {
            int progress = Math.round((float) (current) / total * 100);
            if (isVideoOK || current == total) {
                hideProgress();
            } else {
                showProgress(progress);
            }
        }


        /**
         * 异步开始播放
         */
        private void startPlayAsyn() {
            LogUtil.d("td start play async:" + td.getDelta());
            if (isCanceled) {
                LogUtil.d("cancel start play async ");
                return;
            }
            LogUtil.d("start play async");
            VideoStore.instance().getVideo(
                    new VideoStore.IVideoCache() {

                        @Override
                        public String getUrl() {
                            return VideoPlayTask.this.mParams.path;
                        }

                        @Override
                        public String getKey() {
                            return VideoPlayTask.this.article.id;
                        }
                    }, this);
            mPipedPlayList.add(this);
        }

        private void hidePlayIcon() {
            LogUtil.d("hide play icon:" + this.toString());
            if (this.playIcon != null) {
                this.playIcon.setVisibility(View.GONE);
            }
        }

        /**
         * 播放
         *
         * @param
         */
        public void startPlay() {
            td.renew();
            isCanceled = false;
            hidePlayIcon();
            if (isPaused) {
                this.mPlayer.play();
            } else {
                pipeToPlay(this, 0);
            }
        }

        public void delayPlay() {
            td.renew();
            isCanceled = false;
            hidePlayIcon();
            pipeToPlay(this, 150);
        }

        public void cancel() {
            LogUtil.d("cancel in VideoAdapter");
            VideoStore.instance().cancel(new VideoStore.IVideoCache() {

                @Override
                public String getUrl() {
                    return VideoPlayTask.this.mParams.path;
                }

                @Override
                public String getKey() {
                    return VideoPlayTask.this.article.id;
                }
            });
            cancelPlay(this);
            isCanceled = true;
        }

        public static android.os.Handler sHandler = new android.os.Handler(QsbkApp.getInstance().getMainLooper());

        private static void pipeToPlay(VideoPlayTask task, long delay) {
            sHandler.postDelayed(task.mRunnable, delay);
        }

        private static void cancelPlay(VideoPlayTask task) {
            sHandler.removeCallbacks(task.mRunnable);
        }

        private void hideCover() {
            if (coverImage != null) {
                coverImage.setVisibility(View.INVISIBLE);
            }
        }

        private void showCover() {
            if (coverImage != null) {
                coverImage.setVisibility(View.VISIBLE);
            }
        }

        boolean isPrepared = false;

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            LogUtil.d("td on prepared:" + td.getDelta());
            isPrepared = true;
            if (isCanceled) {
                return;
            }
            hidePlayIcon();
            hideCover();
            hideProgress();
            addLoop();
            LogUtil.d("on prepared called:");
            if (this.mPreparedListener != null) {
                this.mPreparedListener.onPrepared(mediaPlayer);
            }
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            LogUtil.d("onCompletion called:");
            addLoop();
        }


        /**
         * 按照相应的list来停止play，如果这个task是这个list的，我们就停止
         * 目前首页的list才使用这个
         */
        public static void stopPlayByList(String listName) {
            if (mLastPlayedTask != null && TextUtils.equals(listName, mLastPlayedTask.scenario)) {
                LogUtil.d("stop play gloably");
                mLastPlayedTask.stopPlayAndResetView();
                mLastPlayedTask = null;
            }

            if (mPipedPlayList.size() > 0) {
                List<VideoPlayTask> tasks = new LinkedList<VideoPlayTask>();
                for (VideoPlayTask task : mPipedPlayList) {
                    if (TextUtils.equals(listName, task.scenario)) {
                        task.cancel();
                        tasks.add(task);
                    }
                }
                if (tasks.size() > 0) {
                    mPipedPlayList.removeAll(tasks);
                }
            }

        }

        /**
         * 播放任务的全局播放控制
         * //TODO
         */
        public static void stopGlobalPlay() {
            LogUtil.d("stop play globaly");
            if (mLastPlayedTask != null) {
                LogUtil.d("stop play gloably");
                mLastPlayedTask.stopPlayAndResetView();
                mLastPlayedTask = null;
            }
            if (mPipedPlayList.size() > 0) {
                for (VideoPlayTask task : mPipedPlayList) {
                    task.cancel();
                }
                mPipedPlayList.clear();
            }
        }

        private boolean isPaused = false;

        /**
         * 用户点击停止需要使用
         * TODO
         */
        public void clickToStop() {
            LogUtil.d("click to stop");
            if (mLastPlayedTask != this) {
                mLastPlayedTask.stopPlayAndResetView();
                mLastPlayedTask = null;
                this.stopPlayAndResetView();
            } else {
                if (isPrepared) {
                    isPaused = true;
                    this.mPlayer.pause();
                    showPlayIcon();
                }
            }
        }

        /**
         * 重新设置窗口
         */
        public void stopPlayAndResetView() {
            isPaused = false;
            isPrepared = false;
            LogUtil.d("stop play and rest view:" + this);
            if (!QsbkApp.getInstance().isAutoPlayVideo()) {
                showPlayIcon();
            }
            showCover();
            mPlayer.reset();
            /*
			sHandler.postDelayed(new Runnable() {
				 @Override
				 public void run() {

				 }
		 	},50);
		 	*/
        }

        private void showPlayIcon() {
            LogUtil.d("show play icon" + this.toString());
            if (this.playIcon != null && this.playIcon.getTag() == this) {
                this.playIcon.setVisibility(View.VISIBLE);
            }
        }

    }

    public static class PreparedListener implements OnPreparedListener {

        private View mPlayBtn;
        private View mForeground;
        private Object mTag;

        public PreparedListener(View playBtn, View foreground, Object tag) {
            this.mPlayBtn = playBtn;
            this.mTag = tag;
            this.mForeground = foreground;
            mPlayBtn.setTag(tag);
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            LogUtil.d("VideoPlayTask:" + "mp ");
            if (mPlayBtn != null
                    && mTag != null
                    && mTag.equals(mPlayBtn.getTag())) {
                mPlayBtn.setVisibility(View.GONE);
                mForeground.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public String toString() {
            return super.toString() + ", " + mPlayBtn + ", tag=" + mTag;
        }

    }

    @Override
    public void onClick(VideoPlayView player) {

        /**
         * videoPlayView 被点击的情况
         */
        if (player != null && player.getTag() != null) {
            VideoPlayTask task = (VideoPlayTask) player.getTag();
            task.clickToStop();
        }
		/*
		LogUtil.d("on click viewoplay");
		if(VideoPlayTask.mLastPlayedTask.mPlayer == player) {
			stop(player, true);
		} else {
			stop(player, true);
			stop(VideoPlayTask.mLastPlayedTask.mPlayer, false);
		}
		*/
    }

}
