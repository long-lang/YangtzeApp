package com.sweethearts.ui.fragment;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    protected boolean isVisible = false;
    protected boolean isLoadFinish = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    private void onVisible(){
        lazyLoad();
    }
    protected abstract void lazyLoad();

    private void onInvisible(){}
}
