package com.sweethearts.ui.activity.base;


import androidx.fragment.app.Fragment;

import com.sweethearts.model.imodel.IBaseMode;

public abstract class BaseFragment extends Fragment implements IBaseMode {
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