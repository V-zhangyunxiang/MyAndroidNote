package com.owl.android_simple.customview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import com.owl.android_simple.R;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;

/**
 * Description
 *
 * @author zhangyunxiang Date 2021/1/26 15:28
 */
public class DrawableTestActivity extends AppCompatActivity {
  Subscription subscription;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.drawable_test_main);
    // setBitmapDrawable();
    // setClipDrawable();
    // ShapeDrawable 常用 XML 声明
    // setLayerListDrawable();
  }

  public void setBitmapDrawable() {
    ImageView imageView = findViewById(R.id.image_bitmap);
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallhaven_doe);
    BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
    bitmapDrawable.getBitmap(); // BitmapDrawable 转换为 bitmap
    /*
     * 启用或禁用抗锯齿, 旋转时，可以使用抗锯齿来平滑位图的边缘, 默认值是 false
     * 开启后会让图片变得光滑，但是可以忽略的降低图片的清晰度
     * */
    bitmapDrawable.setAntiAlias(true);
    /*
      启用或禁用位图抖动,默认值为 true
    * 当图片的像素配置和手机屏幕的像素配置不一致时，开启这个可以让高质量的图片在低质量的屏幕上还有较好的显示效果
    */
    bitmapDrawable.setDither(true);
    /*
     * 启用或禁用位图过滤,当位图缩小或拉伸以平滑其外观时，将使用过滤,默认值为 true
     * 图片尺寸被拉伸或者被压缩时候，可以较好的保持显示
     */
    bitmapDrawable.setFilterBitmap(true);
    /*
     * 定义位图的重力; 如果位图比容器小，则重力表示绘图在其容器中的位置
     */
    bitmapDrawable.setGravity(Gravity.CENTER);
    /*
    平铺模式，当启用平铺模式时，重复位图;启用平铺模式时会忽略重力。 默认值是“禁用”
    clamp:复制边缘颜色。图片四周的像素会扩散到周围

    disabled:不要平铺位图。 这是默认值。

    mirror:在水平和竖直方向上的镜面投影效果

    repeat:在水平和竖直方向上的平铺效果
    */
    bitmapDrawable.setTileModeXY(TileMode.MIRROR, TileMode.MIRROR);
    imageView.setImageDrawable(bitmapDrawable);
  }

  /** 针对自身进行裁剪复制显示 setLevel [0-10000] */
  public void setClipDrawable() {
    ImageView imageView = findViewById(R.id.image_clip);
    ClipDrawable clipDrawable = (ClipDrawable) imageView.getDrawable();
    clipDrawable.setLevel(0);
    subscription =
        Observable.interval(0, 1, TimeUnit.SECONDS)
            .map(time -> time * 200)
            .subscribe(
                time -> {
                  Log.i("zyx", "time= " + time);
                  if (time >= 10000) {
                    subscription.unsubscribe();
                  }
                  clipDrawable.setLevel(time.intValue());
                });
  }

  /** 层列表图像 Layer List，可以使用多张图片合成一张图片 */
  public void setLayerListDrawable() {
    Button mChangePhotoBtn = findViewById(R.id.btn_change_photo);
    ImageView mPhotoIv = findViewById(R.id.image_layerList);

    mChangePhotoBtn.setOnClickListener(
        v -> {
          LayerDrawable layerDrawable =
              (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.drawable_layerlist_photo);
          // 获取替换的图片
          Drawable drawable = ContextCompat.getDrawable(this, R.drawable.drawable_test_girl);
          // 找到layer_drawable布局中需要更换的item，并替换成对应的图片
          layerDrawable.setDrawableByLayerId(R.id.layer_photo, drawable);
          mPhotoIv.setImageDrawable(layerDrawable);
        });
  }
}
