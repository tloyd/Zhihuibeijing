package cc.springwind.zhihuibeijing.base.impl.menu;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cc.springwind.zhihuibeijing.R;
import cc.springwind.zhihuibeijing.base.BaseMenuDetailPager;
import cc.springwind.zhihuibeijing.domain.PhotoBean;
import cc.springwind.zhihuibeijing.global.Constants;
import cc.springwind.zhihuibeijing.utils.CacheUtil;

/**
 * @author: HeFan
 * @date: 2016/8/15 13:52
 */
public class PhotosDetailPager extends BaseMenuDetailPager implements View.OnClickListener {

    private ListView lvPhoto;
    private GridView gvPhoto;
    private ImageButton ibPhotoChange;
    private ArrayList<PhotoBean.PhotoNews> mPhotoList;

    public PhotosDetailPager(Activity activity, ImageButton ibPhotoChange) {
        super(activity);
        this.ibPhotoChange = ibPhotoChange;
        this.ibPhotoChange.setOnClickListener(this);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.detail_pager_photo, null);
        lvPhoto = (ListView) view.findViewById(R.id.lv_photo);
        gvPhoto = (GridView) view.findViewById(R.id.gv_photo);
        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtil.getCache(mActivity, Constants.PHOTO_URL);
        if (!TextUtils.isEmpty(cache)) {
            processData(cache);
        }
        getDataFromServer();
    }

    private void getDataFromServer() {
        x.http().get(new RequestParams(Constants.PHOTO_URL), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                processData(result);
                CacheUtil.setCache(mActivity, Constants.PHOTO_URL, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mActivity, ex.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(mActivity, cex.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String cache) {
        Gson gson = new Gson();
        PhotoBean photoBean = gson.fromJson(cache, PhotoBean.class);

        mPhotoList = photoBean.data.news;
        PhotoAdapter photoAdapter = new PhotoAdapter();

        lvPhoto.setAdapter(photoAdapter);
        gvPhoto.setAdapter(photoAdapter);
    }

    class PhotoAdapter extends BaseAdapter {
        private final ImageOptions.Builder builder;

        public PhotoAdapter() {
            builder = new ImageOptions.Builder();
            builder.setFailureDrawableId(R.drawable.pic_item_list_default)
                    .setLoadingDrawableId(R.drawable.pic_item_list_default);
        }

        @Override
        public int getCount() {
            return mPhotoList.size();
        }

        @Override
        public PhotoBean.PhotoNews getItem(int position) {
            return mPhotoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_item_photo, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PhotoBean.PhotoNews item = getItem(position);
            holder.tvTitle.setText(item.title);
            x.image().bind(holder.ivPic, item.listimage, builder.build());
            return convertView;
        }

        class ViewHolder {
            @InjectView(R.id.iv_pic)
            ImageView ivPic;
            @InjectView(R.id.tv_title)
            TextView tvTitle;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }

    private boolean isListView = true;

    @Override
    public void onClick(View v) {
        if (isListView) {
            gvPhoto.setVisibility(View.VISIBLE);
            lvPhoto.setVisibility(View.GONE);
            ibPhotoChange.setImageResource(R.drawable.icon_pic_list_type);
            isListView = false;
        } else {
            lvPhoto.setVisibility(View.VISIBLE);
            gvPhoto.setVisibility(View.GONE);
            ibPhotoChange.setImageResource(R.drawable.icon_pic_grid_type);
            isListView = true;
        }
    }
}
