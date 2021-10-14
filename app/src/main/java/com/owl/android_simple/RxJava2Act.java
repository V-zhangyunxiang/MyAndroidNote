package com.owl.android_simple;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Description
 *
 * @author zhangyunxiang Date 2021/7/7 16:32
 */
public class RxJava2Act extends AppCompatActivity {
  private static final String TAG = "zyx";
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  // 1. just(T...)：直接将传入的参数依次发送出来
  // 2. from(T[]) / fromIterable / fromArray : 将传入的数组 / Iterable 拆分成具体对象后，依次发送出来
  // 3. defer : 被订阅时，才动态创建被观察者对象发射事件
  // 4. timer : 延迟指定时间后，发射事件
  // 5. interval: 每隔一段时间就发射事件，也能设置延迟
  // 6. intervalRange: 每隔指定时间发射事件，可指定发射的数量
  //    (3,10,2,1, TimeUnit.SECONDS)
  //    从 3 开始，一共发射 10 个事件，延迟 2s 后，每隔 1s 发射一次
  // 7. range：连续发射 1 个事件，可指定范围，与 intervalRange 区别在于无延迟
  // 8. rangeLong: 区别在于支持 long

  // 上游 onComplete 之后的事件将会继续发送, 而下游收到 onComplete 事件之后将不再继续接收事件
  // 上游 onError 之后的事件将继续发送, 而下游收到 onError 事件之后将不再继续接收事件
  // 上游可以不发送 onComplete 或 onError
  // onComplete 和 onError 必须唯一并且互斥
  // 调用 dispose() 并不会导致上游不再继续发送事件,上游会继续发送剩余的事件,但下游不再接收
  // 下游 onSubscribe() 最先被调用

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  private void simple1() {
    Observable.create(
            new ObservableOnSubscribe<Integer>() {
              @Override
              public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onComplete();
                emitter.onNext(3);
              }
            })
        .subscribe(
            new Observer<Integer>() {
              private Disposable disposable;

              @Override
              public void onSubscribe(@NonNull Disposable d) {
                // 最先被回调
                disposable = d;
                compositeDisposable.add(disposable);
              }

              @Override
              public void onNext(@NonNull Integer integer) {
                if (integer == 2) {
                  disposable.dispose();
                }
                Log.d(TAG, "isDisposed: " + disposable.isDisposed());
              }

              @Override
              public void onError(@NonNull Throwable e) {
                compositeDisposable.clear();
              }

              @Override
              public void onComplete() {}
            });
  }

  private void simple2() {
    // new consumer 自定义接收哪些事件,subscribe 可设置接收多个 consumer 事件
    Disposable d =
        Observable.create(
                (ObservableOnSubscribe<Integer>)
                    emitter -> {
                      Log.d(TAG, "emit 1");
                      emitter.onNext(1);
                      Log.d(TAG, "emit 2");
                      emitter.onNext(2);
                      emitter.onComplete();
                      Log.d(TAG, "emit 3");
                      emitter.onNext(3);
                    })
            .subscribe(integer -> Log.d(TAG, "accept:" + integer));

    Disposable d2 =
        Observable.create(
                (ObservableOnSubscribe<Integer>)
                    emitter -> {
                      Log.d(TAG, "emit 1");
                      emitter.onNext(1);
                      Log.d(TAG, "emit 2");
                      emitter.onNext(2);
                      emitter.onComplete();
                      Log.d(TAG, "emit 3");
                      emitter.onNext(3);
                    })
            .subscribe(integer -> {}, throwable -> {});
    compositeDisposable.addAll(d, d2);
  }

  protected void simple3() {
    Observable<Integer> observable1 =
        Observable.create(
                new ObservableOnSubscribe<Integer>() {
                  @Override
                  public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                    for (int i = 0; ; i++) { // 无限循环发事件
                      emitter.onNext(i);
                    }
                  }
                })
            .subscribeOn(Schedulers.io());

    Observable<String> observable2 =
        Observable.create(
                new ObservableOnSubscribe<String>() {
                  @Override
                  public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    emitter.onNext("A");
                    emitter.onComplete();
                  }
                })
            .subscribeOn(Schedulers.io());

    Observable.zip(
            observable1,
            observable2,
            new BiFunction<Integer, String, String>() {
              @Override
              public String apply(Integer integer, String s) throws Exception {
                return integer + s;
              }
            })
        .subscribe(
            new Observer<String>() {
              @Override
              public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
              }

              @Override
              public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
              }

              @Override
              public void onError(Throwable e) {
                Log.d(TAG, "onError");
              }

              @Override
              public void onComplete() {
                Log.d(TAG, "onComplete");
              }
            });
  }

  // 上游变成了 Flowable, 下游变成了 Subscriber
  // 下游的 onSubscribe 方法中传给我们的不再是 Disposable了, 而是 Subscription
  // Subscription.request 消费上游任务，并告知上游下游能处理任务的个数，多次调用会叠加
  // 在下游不消费的情况下，上游默认最大存储个数为 128
  // 下游每消费 96 个事件便会自动触发内部的 request() 去设置上游的 requested(96) 的值, 上游最终发射数量是 128 + 96
  private void simple4() {
    Flowable<Integer> upstream =
        Flowable.create(
                new FlowableOnSubscribe<Integer>() {
                  @Override
                  public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                    Log.d(TAG, "emit 1");
                    emitter.onNext(1);
                    Log.d(TAG, "emit 2");
                    emitter.onNext(2);
                    Log.d(TAG, "emit 3");
                    emitter.onNext(3);
                    Log.d(TAG, "emit complete");
                    emitter.onComplete();
                    emitter.requested();
                  }
                },
                BackpressureStrategy.ERROR)
            .subscribeOn(Schedulers.io()); // 增加了一个参数

    Subscriber<Integer> downstream =
        new Subscriber<Integer>() {
          Subscription ss;

          @Override
          public void onSubscribe(Subscription s) {
            Log.d(TAG, "onSubscribe");
            // 告诉上游下游的处理个数
            s.request(Long.MAX_VALUE);
            ss = s;
          }

          @Override
          public void onNext(Integer integer) {
            Log.d(TAG, "onNext: " + integer);
          }

          @Override
          public void onError(Throwable t) {
            Log.w(TAG, "onError: ", t);
          }

          @Override
          public void onComplete() {
            Log.d(TAG, "onComplete");
          }
        };
    upstream.observeOn(AndroidSchedulers.mainThread()).subscribe(downstream);
  }
}
