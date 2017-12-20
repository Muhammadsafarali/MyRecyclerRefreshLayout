package tom.myrecyclerrefreshlayout;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.dinuscxj.refresh.RecyclerRefreshLayout;

import tom.myrecyclerrefreshlayout.adapter.RecyclerListAdapter;
import tom.myrecyclerrefreshlayout.model.CursorModel;
import tom.myrecyclerrefreshlayout.tips.TipsHelper;

/**
 * Created by 3dium on 20.12.2017.
 */

public abstract class RecyclerFragment<MODEL extends CursorModel> extends Fragment {
    private boolean mIsLoading;

    private RecyclerView mRecyclerView;
    private RecyclerRefreshLayout mRecyclerRefreshLayout;

    private TipsHelper mTipsHelper;
//    private HeaderViewRecyclerAdapter mHeaderAdapter;
    private RecyclerListAdapter<MODEL, ?> mOriginAdapter;

    private InteractionListener mInteractionListener;

    

    public void refresh() {
        if (isFirstPage()) {
            mTipsHelper.showLoading(true);
        } else {
            mRecyclerRefreshLayout.setRefreshing(true);
        }

        requestRefresh();
    }

    public boolean isFirstPage() {
        return mOriginAdapter.getItemCount() <= 0;
    }

    public class RefreshEventDetector implements RecyclerRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            requestRefresh();
        }
    }

    public class AutoLoadEventDetector extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView view, int dx, int dy) {
            RecyclerView.LayoutManager manager = view.getLayoutManager();
            if (manager.getChildCount() > 0) {
                int count = manager.getItemCount();
                int last = ((RecyclerView.LayoutParams) manager
                .getChildAt(manager.getChildCount() - 1).getLayoutParams()).getViewAdapterPosition();

                if (last == count - 1 && !mIsLoading && mInteractionListener != null) {
                    requestMore();
                }
            }
        }
    }

    private void requestRefresh() {
        if (mInteractionListener != null && !mIsLoading) {
            mIsLoading = true;
            mInteractionListener.requestRefresh();
        }
    }

    private void requestMore() {
        if (mInteractionListener != null && mInteractionListener.hasMore() && !mIsLoading) {
           mIsLoading = true;
           mInteractionListener.requestMore();
        }
    }

    public abstract class InteractionListener {
        public void requestRefresh() {
            requestComplete();

            if (mOriginAdapter.isEmpty()) {
                mTipsHelper.showEmpty();
            } else if (hasMore()) {
                mTipsHelper.showHasMore();
            } else {
                mTipsHelper.hideHasMore();
            }
        }

        public void requestMore() {
            requestComplete();
        }

        public void requestFailure() {
            requestComplete();
            mTipsHelper.showError(isFirstPage(), new Exception("net error"));
        }

        protected void requestComplete() {
            mIsLoading = false;

            if (mRecyclerRefreshLayout != null) {
                mRecyclerRefreshLayout.setRefreshing(false);
            }

            mTipsHelper.hideError();
            mTipsHelper.hideEmpty();
            mTipsHelper.hideLoading();
        }

        protected boolean hasMore() {
            return mOriginAdapter.getItem(mOriginAdapter.getItemCount() - 1).hasMore();
        }
    }
}
