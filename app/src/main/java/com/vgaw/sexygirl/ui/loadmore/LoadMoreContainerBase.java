package com.vgaw.sexygirl.ui.loadmore;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;

/**
 * @author huqiu.lhq
 */
public abstract class LoadMoreContainerBase extends LinearLayout implements LoadMoreContainer {

    private AbsListView.OnScrollListener mOnScrollListener;
    private LoadMoreUIHandler mLoadMoreUIHandler;
    private LoadMoreHandler mLoadMoreHandler;

    private boolean mIsLoading;
    private boolean mHasMore = false;
    private boolean mAutoLoadMore = true;
    private boolean mLoadError = false;

    private boolean mListEmpty = true;
    private boolean mShowLoadingForFirstPage = false;
    private View mFooterView;

    private AbsListView mAbsListView;
    private View mUpView;
    private boolean isLastUp = false;
    private int lastItemIndex = 0;


    public LoadMoreContainerBase(Context context) {
        super(context);
    }

    public LoadMoreContainerBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAbsListView = retrieveAbsListView();
        init();
    }

    /**
     * @deprecated It's totally wrong. Use {@link #useDefaultFooter} instead.
     */
    @Deprecated
    public void useDefaultHeader() {
        useDefaultFooter();
    }

    public void useDefaultFooter() {
        LoadMoreDefaultFooterView footerView = new LoadMoreDefaultFooterView(getContext());
        footerView.setVisibility(GONE);
        setLoadMoreView(footerView);
        setLoadMoreUIHandler(footerView);
    }

    public void setUpView(View v){
        this.mUpView = v;
        mUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAbsListView.smoothScrollToPosition(0);
            }
        });
    }

    private void init() {

        if (mFooterView != null) {
            addFooterView(mFooterView);
        }

        mAbsListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            private boolean mIsEnd = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (null != mOnScrollListener) {
                    mOnScrollListener.onScrollStateChanged(view, scrollState);
                }
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (mIsEnd) {
                        onReachBottom();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mUpView != null){
                    // 上滑消失
                    if ((firstVisibleItem > lastItemIndex || firstVisibleItem == 0) && !isLastUp) {
                        isLastUp = true;
                        animateUp(false);
                    }
                    // 下滑出现
                    else if (firstVisibleItem < lastItemIndex && isLastUp) {
                        isLastUp = false;
                        animateUp(true);
                    }
                    lastItemIndex = firstVisibleItem;
                }

                if (null != mOnScrollListener) {
                    mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
                if (firstVisibleItem + visibleItemCount >= totalItemCount - 1) {
                    mIsEnd = true;
                } else {
                    mIsEnd = false;
                }
            }
        });
    }

    private void animateUp(boolean isShow) {
        PropertyValuesHolder alphaHolder;
        PropertyValuesHolder yHolder;
        int translationY = mUpView.getHeight() + ((ViewGroup.MarginLayoutParams)mUpView.getLayoutParams()).bottomMargin;
        if (isShow) {
            alphaHolder = PropertyValuesHolder.ofFloat("alpha", 0, 1);
            yHolder = PropertyValuesHolder.ofFloat("translationY", translationY, 0);
        } else {
            alphaHolder = PropertyValuesHolder.ofFloat("alpha", 1, 0);
            yHolder = PropertyValuesHolder.ofFloat("translationY", 0, translationY);
        }
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mUpView, alphaHolder, yHolder);
        animator.setInterpolator(new OvershootInterpolator());
        animator.setDuration(400);
        animator.start();
    }

    private void tryToPerformLoadMore() {
        if (mIsLoading) {
            return;
        }

        // no more content and also not load for first page
        if (!mHasMore && !(mListEmpty && mShowLoadingForFirstPage)) {
            return;
        }

        mIsLoading = true;

        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoading(this);
        }
        if (null != mLoadMoreHandler) {
            mLoadMoreHandler.onLoadMore(this);
        }
    }

    private void onReachBottom() {
        // if has error, just leave what it should be
        if (mLoadError) {
            return;
        }
        if (mAutoLoadMore) {
            tryToPerformLoadMore();
        } else {
            if (mHasMore) {
                mLoadMoreUIHandler.onWaitToLoadMore(this);
            }
        }
    }

    @Override
    public void setShowLoadingForFirstPage(boolean showLoading) {
        mShowLoadingForFirstPage = showLoading;
    }

    @Override
    public void setAutoLoadMore(boolean autoLoadMore) {
        mAutoLoadMore = autoLoadMore;
    }

    @Override
    public void setOnScrollListener(AbsListView.OnScrollListener l) {
        mOnScrollListener = l;
    }

    @Override
    public void setLoadMoreView(View view) {
        // has not been initialized
        if (mAbsListView == null) {
            mFooterView = view;
            return;
        }
        // remove previous
        if (mFooterView != null && mFooterView != view) {
            removeFooterView(view);
        }

        // add current
        mFooterView = view;
        mFooterView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToPerformLoadMore();
            }
        });

        addFooterView(view);
    }

    @Override
    public void setLoadMoreUIHandler(LoadMoreUIHandler handler) {
        mLoadMoreUIHandler = handler;
    }

    @Override
    public void setLoadMoreHandler(LoadMoreHandler handler) {
        mLoadMoreHandler = handler;
    }

    /**
     * page has loaded
     *
     * @param emptyResult
     * @param hasMore
     */
    @Override
    public void loadMoreFinish(boolean emptyResult, boolean hasMore) {
        mLoadError = false;
        mListEmpty = emptyResult;
        mIsLoading = false;
        mHasMore = hasMore;

        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadFinish(this, emptyResult, hasMore);
        }
    }

    @Override
    public void loadMoreError(int errorCode, String errorMessage) {
        mIsLoading = false;
        mLoadError = true;
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadError(this, errorCode, errorMessage);
        }
    }

    protected abstract void addFooterView(View view);

    protected abstract void removeFooterView(View view);

    protected abstract AbsListView retrieveAbsListView();
}