package tom.myrecyclerrefreshlayout.tips;

/**
 * Created by 3dium on 20.12.2017.
 */

public interface TipsHelper {

    void showEmpty();
    void hideEmpty();
    void showLoading(boolean firstPage);
    void hideLoading();
    void showError(boolean firstPage, Throwable error);
    void hideError();
    void showHasMore();
    void hideHasMore();
}
