package com.baway.weekthree.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.baway.weekthree.R;
import com.baway.weekthree.adapter.MyAdapter;
import com.baway.weekthree.model.bean.JavaBean;
import com.baway.weekthree.presenter.DataPresenter;
import com.baway.weekthree.util.FrescoUtil;
import com.baway.weekthree.view.DataView;
import com.youth.banner.Banner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tv.danmaku.ijk.media.widget.media.IjkVideoView;
import zlc.season.rxdownload.DownloadStatus;
import zlc.season.rxdownload.RxDownload;

public class MainActivity extends AppCompatActivity implements DataView {

    private List<String> imageList = new ArrayList<>();
    private ProgressBar mPb;
    /**
     * 下载:0%
     */
    private TextView mTvInfo;
    private LinearLayout mLin;
    private Banner mBanner;
    private IjkVideoView mIjkPlayer;
    private RecyclerView mRv;
    private MyAdapter adapter;
    private Subscription subscription;
    private String title;
    private DownloadStatus status;

    private Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            status = (DownloadStatus) msg.obj;
            long totalSize = status.getTotalSize();
            long downloadSize = status.getDownloadSize();
            String percent = status.getPercent();
            long l = totalSize / 100;
            int downsize = (int) (downloadSize / l);
            if (downloadSize <= totalSize) {
                mPb.setProgress(downsize);
                mTvInfo.setText(percent);
            }

            if (totalSize == downloadSize) {
                Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                mPb.setProgress(0);
            }

        }
    };
    private VideoView mVv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mRv.setLayoutManager(new LinearLayoutManager(this));
        DataPresenter dataPresenter = new DataPresenter(this);
        dataPresenter.relevance("vedio");


    }

    //暂停
    private boolean flag = false;//是否在下载

    @Override
    public void getData(JavaBean javaBean) {
        final List<JavaBean.DataBean> list = javaBean.data;
        for (int i = 0; i < list.size(); i++) {
            String image_url = list.get(i).image_url;
            imageList.add(image_url);
        }
        mBanner.setImages(imageList).setImageLoader(new FrescoUtil()).start();
        adapter = new MyAdapter(MainActivity.this, list);
        mRv.setAdapter(adapter);
        adapter.setOnClickItem(new MyAdapter.OnClickItem() {

            @Override
            public void onClick(int position) {
                title = list.get(position).title;
                Toast.makeText(MainActivity.this, "开始", Toast.LENGTH_SHORT).show();
                File file = new File("/storage/emulated/legacy/Download/" + title + "music.mp4");
                if (file.exists()) {
                    //加载视屏的路径
                    mVv.setVideoPath(file.getAbsolutePath());
                    //获取控制器
                    MediaController controller = new MediaController(MainActivity.this);
                    //设置控制器
                    mVv.setMediaController(controller);
                    //绑定视图
                    controller.setAnchorView(mVv);
                    mVv.requestFocus();
                    //播放
                    mVv.start();
                }else{
                    Toast.makeText(MainActivity.this,"请下载",Toast.LENGTH_SHORT).show();
                }
               /* mIjkPlayer.setVisibility(View.VISIBLE);
                IjkMediaPlayer.loadLibrariesOnce(null);
                IjkMediaPlayer.native_profileBegin("libijkplayer.so");
                AndroidMediaController controller = new AndroidMediaController(MainActivity.this, false);
                mIjkPlayer.setMediaController(controller);
                mIjkPlayer.setVideoURI(Uri.parse(vedio_url));
                mIjkPlayer.start();*/
                //设置最大线程
                //设置下载失败重试次
                String vedio_url = list.get(position).vedio_url;

                subscription = RxDownload.getInstance()
                        .maxThread(3)     //设置最大线程
                        .maxRetryCount(10) //设置下载失败重试次数
                        .download(vedio_url, list.get(position).title + "music.mp4", null)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<DownloadStatus>() {

                            @Override
                            public void onCompleted() {
                                mVv.requestFocus();
                                //播放
                                mVv.start();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(final DownloadStatus status) {
                                Message message = new Message();
                                message.obj = status;
                                handler.sendMessage(message);
                            }
                        });

            }

            @Override
            public void onLongClick(int position) {
                Toast.makeText(MainActivity.this, "暂停", Toast.LENGTH_SHORT).show();
                //mIjkPlayer.setVisibility(View.GONE);
                //mIjkPlayer.pause();
                //UserBeanDao userBeanDao = MyApplication.getInstances().getDaoSession().getUserBeanDao();
                if (subscription != null && !subscription.isUnsubscribed()) {
                    subscription.unsubscribe();
                }
                /*UserBean userBean = new UserBean(downloadSize);
                userBeanDao.insert(userBean);*/

            }
        });
    }

    private void initView() {
        mPb = (ProgressBar) findViewById(R.id.pb);
        mTvInfo = (TextView) findViewById(R.id.tv_info);
        mLin = (LinearLayout) findViewById(R.id.lin);
        mBanner = (Banner) findViewById(R.id.banner);
        mIjkPlayer = (IjkVideoView) findViewById(R.id.ijkPlayer);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mVv = (VideoView) findViewById(R.id.vv);
    }
}
