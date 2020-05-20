package com.sweethearts.ui.activity;




import android.os.Bundle;
import android.widget.LinearLayout;
import com.lib.loading.LVBlock;
import com.sweethearts.R;
import com.sweethearts.Utils.MyUtils;
import com.sweethearts.presenter.CetPresenter;
import com.sweethearts.ui.activity.base.BaseActivity;
import com.sweethearts.ui.view.CetView;
import com.sweethearts.url.Url;
import androidx.appcompat.widget.Toolbar;

public class CetActivity extends BaseActivity implements CetView {

    private String CET_DATE;
    private Toolbar toolbar;
    private LinearLayout container;
    private CetPresenter presenter;
    private LVBlock loading;

    public String url;
    public String index_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cet);
        init();
        MyUtils.setToolbarBackToHome(this, toolbar);
    }

    @Override
    public void findViews() {
        toolbar = findViewById(R.id.toolbar);
        container = findViewById(R.id.cet_container);
        loading = findViewById(R.id.loading);
    }

    @Override
    public void setEvents() {
        index_url = Url.Yangtzeu_Cet_Index1;
        url = Url.Yangtzeu_Cet1;
        presenter = new CetPresenter(this, this);
        // cet历史记录
        presenter.getCetHistoryGrade();
        loading.startAnim();
    }




    @Override
    public String getCetInfo() {
        return CET_DATE;
    }


    @Override
    public void setCetInfo(String CET_DATE) {
        this.CET_DATE = CET_DATE;
    }

    @Override
    public LinearLayout getCetHistoryContainer() {
        return container;
    }


    @Override
    public String getIndexUrl() {
        return index_url;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
