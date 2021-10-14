package com.owl.android_simple;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Notification;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Description
 *
 * @author zhangyunxiang Date 2019/1/3 10:53
 */
public class RxJava1Act extends AppCompatActivity {

  public static final String TAG = "zyx";

  //  public static void main(String[] args) {
  //    RxJava1Activity rxJava1Activity = new RxJava1Activity();
  //    // rxJava1Activity.subscription();
  //    rxJava1Activity.doSomething();
  //  }

  // Observer 也总是会先被转换成一个 Subscriber 再使用
  // Subscriber 新增 onStart()，它在订阅开始时间还未发送前调用

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    doSomething();
  }

  // Observable.subscribe() 得到一个 Subscription 对象
  // 当被观察者全部执行完后，会自动解除订阅
  private void subscription() {
    Subscription subscription =
        Observable.from(new ArrayList<>())
            .subscribe(
                new Action1<Object>() {
                  @Override
                  public void call(Object o) {}
                });
    System.out.println(subscription.isUnsubscribed());
    subscription.unsubscribe();
    System.out.println(subscription.isUnsubscribed());
  }

  // 使用 concat 和 merge 时第 1 个被观察者发送 Error 事件后，第 2 个被观察者则不会继续发送事件
  // 组合(多个被观察者)一起发送数据，合并后按发送顺序(串行)执行
  // 上一个 Observable 执行完下一个才能执行
  // 123 789 456 10 11 12
  private void concat() {
    Observable.concat(
            Observable.just(1, 2, 3),
            Observable.just(7, 8, 9),
            Observable.just(4, 5, 6),
            Observable.just(10, 11, 12))
        .subscribe(
            new Observer<Integer>() {
              @Override
              public void onNext(Integer value) {
                System.out.println("接收到了事件" + value);
              }

              @Override
              public void onError(Throwable e) {
                System.out.println("对Error事件作出响应");
              }

              @Override
              public void onCompleted() {
                System.out.println("对Complete事件作出响应");
              }
            });
  }

  // 组合(多个被观察者)一起发送数据，合并后按时间线(并行)执行
  // 谁先执行完谁先通知
  // 456 123
  private void merge() {
    Observable<Integer> observable1 = Observable.just(1, 2, 3).delay(3, TimeUnit.SECONDS);
    Observable<Integer> observable2 = Observable.just(4, 5, 6).delay(1, TimeUnit.SECONDS);

    Observable.merge(observable1, observable2)
        .subscribe(
            new Observer<Integer>() {

              @Override
              public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件" + value);
              }

              @Override
              public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
              }

              @Override
              public void onCompleted() {
                Log.d(TAG, "对Complete事件作出响应");
              }
            });
  }

  // concatDelayError() / mergeDelayError() 可以在其他观察者发送事件结束后再出发 onError
  private void concatArrayDelayError() {
    Observable.concatDelayError(Observable.just(Observable.just(3)))
        .subscribe(
            new Observer<Integer>() {
              @Override
              public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件" + value);
              }

              @Override
              public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
              }

              @Override
              public void onCompleted() {
                Log.d(TAG, "对Complete事件作出响应");
              }
            });
  }

  // 严格按照原先事件序列 进行对位合并，最终结果不受发射事件延迟的影响，双方必须都发射一个最新的数据才会发射
  // 最终合并的事件数量 = 多个被观察者（Observable）中数量最少的数量
  private static void zip() {
    Observable<Integer> obs1 =
        Observable.create(
                (Observable.OnSubscribe<Integer>)
                    subscriber -> {
                      try {
                        Log.d(TAG, "被观察者1发送了事件1");
                        subscriber.onNext(1);
                        Thread.sleep(1000);

                        Log.d(TAG, "被观察者1发送了事件2");
                        subscriber.onNext(2);
                        Thread.sleep(1000);

                        Log.d(TAG, "被观察者1发送了事件3");
                        subscriber.onNext(3);
                        Thread.sleep(1000);
                        subscriber.onCompleted();
                      } catch (InterruptedException e) {
                        e.printStackTrace();
                      }
                    })
            .subscribeOn(Schedulers.io());

    Observable<String> obs2 =
        Observable.create(
                (Observable.OnSubscribe<String>)
                    subscriber -> {
                      try {
                        Log.d(TAG, "被观察者2发送了事件A");
                        subscriber.onNext("A");
                        Thread.sleep(1000);

                        subscriber.onNext("B");
                        Log.d(TAG, "被观察者2发送了事件B");
                        Thread.sleep(1000);

                        Log.d(TAG, "被观察者2发送了事件C");
                        subscriber.onNext("C");
                        Thread.sleep(1000);

                        Log.d(TAG, "被观察者2发送了事件D");
                        subscriber.onNext("D");
                        Thread.sleep(1000);

                        subscriber.onCompleted();
                      } catch (InterruptedException e) {
                        e.printStackTrace();
                      }
                    })
            .subscribeOn(Schedulers.io());

    Observable.zip(
            obs1,
            obs2,
            new Func2<Integer, String, String>() {
              @Override
              public String call(Integer integer, String s) {
                return integer + s;
              }
            })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            new Subscriber<String>() {
              @Override
              public void onCompleted() {
                Log.d(TAG, "onComplete");
              }

              @Override
              public void onError(Throwable e) {
                Log.d(TAG, "onError");
              }

              @Override
              public void onNext(String o) {
                Log.d(TAG, "最终接收到的事件 =  " + o);
              }
            });
  }

  // CombineLatest（） = 按时间合并，即在同一个时间点上合并，最终结果受到发射延迟的影响
  // 将先发送了数据的 Observables 的最新（最后）一个数据与另外一个 Observable 发送的每个数据结合
  // 任意一个 Observable 发射数据，整体就会发射
  private static void combineLatest() {
    Observable<Integer> obs1 =
        Observable.create(
                (Observable.OnSubscribe<Integer>)
                    subscriber -> {
                      try {
                        Log.d(TAG, "被观察者1发送了事件1");
                        subscriber.onNext(1);
                        Thread.sleep(1000);

                        Log.d(TAG, "被观察者1发送了事件2");
                        subscriber.onNext(2);
                        Thread.sleep(1000);

                        Log.d(TAG, "被观察者1发送了事件3");
                        subscriber.onNext(3);
                        Thread.sleep(1000);
                        subscriber.onCompleted();
                      } catch (InterruptedException e) {
                        e.printStackTrace();
                      }
                    })
            .subscribeOn(Schedulers.io());

    Observable<String> obs2 =
        Observable.create(
                (Observable.OnSubscribe<String>)
                    subscriber -> {
                      try {
                        Log.d(TAG, "被观察者2发送了事件A");
                        subscriber.onNext("A");
                        Thread.sleep(1000);

                        subscriber.onNext("B");
                        Log.d(TAG, "被观察者2发送了事件B");
                        Thread.sleep(1000);

                        Log.d(TAG, "被观察者2发送了事件C");
                        subscriber.onNext("C");
                        Thread.sleep(1000);

                        Log.d(TAG, "被观察者2发送了事件D");
                        subscriber.onNext("D");
                        Thread.sleep(1000);

                        subscriber.onCompleted();
                      } catch (InterruptedException e) {
                        e.printStackTrace();
                      }
                    })
            .subscribeOn(Schedulers.io());

    Observable.combineLatest(
            obs1,
            obs2,
            new Func2<Integer, String, String>() {
              @Override
              public String call(Integer integer, String s) {
                return integer + s;
              }
            })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            new Subscriber<String>() {
              @Override
              public void onCompleted() {
                Log.d(TAG, "onComplete");
              }

              @Override
              public void onError(Throwable e) {
                Log.d(TAG, "onError");
              }

              @Override
              public void onNext(String o) {
                Log.d(TAG, "最终接收到的事件 =  " + o);
              }
            });
  }

  // collect()    将被观察者 Observable 发送的数据事件收集到一个数据结构里
  // startWith()/startWithArray() 在一个被观察者发送事件前，追加发送一些数据/一个新的被观察者
  // count()     统计被观察者发送事件的数量
  // retry()     重试，即当出现错误时，让被观察者（Observable）重新发射数据
  // repeat()    无条件地、重复发送 被观察者事件
  // reduce()    把被观察者发送的事件聚合成一个事件发送

  // BehaviorSubject : 被订阅之前的最后一个数据(如果订阅之前有事件，则会替换到默认设置的数据)，再接收订阅之后发射过来的数据
  // ReplaySubject :   不论订阅发生在什么时候，都发射全部数据; createWithSize(n) 只缓存订阅前最后发送的 n 条数据;createWithTime()
  // 缓存时间
  // PublishSubject :  Observer 只接收 PublishSubject 被订阅之后发送的数据
  // AsyncSubject :    Observer会接收 AsyncSubject 的 onComplete() 之前的最后一个数据
  private void BehaviorSubject() {
    BehaviorSubject<String> subject = BehaviorSubject.create("behaviorSubject1");
    subject.subscribe(
        new Action1<String>() {
          @Override
          public void call(String s) {
            Log.i(TAG, s);
          }
        },
        new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            Log.i(TAG, throwable.getMessage());
          }
        },
        () -> Log.i(TAG, "complete"));
    subject.onNext("behaviorSubject2");
    subject.onNext("behaviorSubject3");
    // 测试是否走 onError
    subject.onError(new Exception("aaaaaa"));
  }

  // do 系列方法，有执行的前后顺序
  // flatMap(新合并生成的事件序列顺序是无序的，即与旧序列发送事件的顺序无关) concatMap(有序)
  // doOnSubscribe 下一行用 subscribeOn 指定其执行的线程
  // doOnNext 上一行用 observeOn 指定其执行的线程
  // observeOn() 指定的是它之后的操作所在的线程。因此如果有多次切换线程的需求，只要在每个想要切换线程的位置调用一次 observeOn()
  // 当使用了多个 subscribeOn() 的时候，只有第一个 subscribeOn() 起作用
  private static void doSomething() {
    Observable.create(
            new Observable.OnSubscribe<Integer>() {
              @Override
              public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onCompleted();
              }
            })
        .doOnSubscribe(
            new Action0() {
              @Override
              public void call() {
                Log.i(TAG, "doOnSubscribe");
              }
            })
        // 执行 Next 事件前调用
        .doOnNext(
            new Action1<Integer>() {
              @Override
              public void call(Integer integer) {
                Log.i(TAG, "doOnNext1");
              }
            })
        .doOnNext(
            new Action1<Integer>() {
              @Override
              public void call(Integer integer) {
                Log.i(TAG, "doOnNext2");
              }
            })
        .doAfterTerminate(
            new Action0() {
              @Override
              public void call() {}
            })
        .doOnCompleted(
            new Action0() {
              @Override
              public void call() {}
            })
        .doOnError(
            new Action1<Throwable>() {
              @Override
              public void call(Throwable throwable) {}
            })
        .doOnUnsubscribe(
            new Action0() {
              @Override
              public void call() {}
            })
        .doOnTerminate(
            new Action0() {
              @Override
              public void call() {}
            })
        // 当 Observable 每发送 1 次数据事件就会调用 1 次
        .doOnEach(
            new Action1<Notification<? super Integer>>() {
              @Override
              public void call(Notification<? super Integer> notification) {}
            })
        .flatMap(
            new Func1<Integer, Observable<String>>() {
              @Override
              public Observable<String> call(Integer s) {
                //                final List<String> list = new ArrayList<>();
                //                for (int i = 0; i < 3; i++) {
                //                  list.add("我是事件 " + s + " 拆分后的子事件" + i);
                //                }
                return Observable.just(String.valueOf(s));
              }
            })
        .doOnNext(
            new Action1<String>() {
              @Override
              public void call(String s) {
                Log.i(TAG, "doOnNext3");
              }
            })
        .subscribe(
            new Subscriber<String>() {
              @Override
              public void onNext(String s) {
                Log.i(TAG, "onNext : " + s);
              }

              @Override
              public void onStart() {
                super.onStart();
                Log.i(TAG, "onStart");
              }

              @Override
              public void onCompleted() {
                Log.i(TAG, "onCompleted");
              }

              @Override
              public void onError(Throwable e) {
                Log.i(TAG, "onError");
              }
            });
  }

  // 遇到错误时，发送 1 个新的 Observable
  private static void onExceptionResumeNext() {
    Observable.create(
            new Observable.OnSubscribe<Integer>() {
              @Override
              public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onError(new Exception("发生错误了"));
              }
            })
        .onExceptionResumeNext(Observable.just(33))
        .subscribe(
            new Subscriber<Integer>() {
              @Override
              public void onCompleted() {
                Log.d(TAG, "对Complete事件作出响应");
              }

              @Override
              public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
              }

              @Override
              public void onNext(Integer integer) {
                Log.d(TAG, "接收到了事件" + integer);
              }
            });
  }

  // 遇到错误时，发送 1 个新的 Observable
  private static void onErrorResumeNext() {
    Observable.create(
            new Observable.OnSubscribe<Integer>() {
              @Override
              public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onError(new Throwable("发生错误了"));
              }
            })
        .onErrorResumeNext(
            new Func1<Throwable, Observable<? extends Integer>>() {
              @Override
              public Observable<? extends Integer> call(Throwable throwable) {
                // 1. 捕捉错误异常
                Log.e(TAG, "在onErrorReturn处理了错误: " + throwable.toString());
                return Observable.just(11, 22);
              }
            })
        .subscribe(
            new Subscriber<Integer>() {
              @Override
              public void onCompleted() {
                Log.d(TAG, "对Complete事件作出响应");
              }

              @Override
              public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
              }

              @Override
              public void onNext(Integer integer) {
                Log.d(TAG, "接收到了事件" + integer);
              }
            });
  }

  // 遇到错误时，发送 1 个特殊事件 & 正常终止
  private static void onErrorReturn() {
    Observable.create(
            new Observable.OnSubscribe<Integer>() {
              @Override
              public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onError(new Throwable("发生错误了"));
              }
            })
        .onErrorReturn(
            new Func1<Throwable, Integer>() {
              @Override
              public Integer call(Throwable throwable) {
                Log.e(TAG, "在onErrorReturn处理了错误: " + throwable.toString());
                return 666;
              }
            })
        .subscribe(
            new Subscriber<Integer>() {
              @Override
              public void onCompleted() {
                Log.d(TAG, "对Complete事件作出响应");
              }

              @Override
              public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
              }

              @Override
              public void onNext(Integer integer) {
                Log.d(TAG, "接收到了事件" + integer);
              }
            });
  }

  // 定期从 被观察者（Obervable）需要发送的事件中 获取一定数量的事件 & 放到缓存区中，最终发送
  private static void buffer() {
    Observable.just(1, 2, 3, 4, 5)
        .buffer(2, 2)
        // 设置缓存区大小 & 步长
        // 缓存区大小 = 每次从被观察者中获取的事件数量
        // 步长 = 每次获取新事件的数量
        .subscribe(
            new Observer<List<Integer>>() {
              @Override
              public void onNext(List<Integer> stringList) {
                System.out.println(" 缓存区里的事件数量 = " + stringList.size());
                for (Integer value : stringList) {
                  System.out.println(" 事件 = " + value);
                }
              }

              @Override
              public void onCompleted() {}

              @Override
              public void onError(Throwable e) {}
            });
  }

  // 延迟发射
  public static void delay() {
    Observable.just(1, 2, 3)
        .delay(3, TimeUnit.SECONDS)
        .subscribe(
            new Observer<Integer>() {

              @Override
              public void onNext(Integer value) {
                System.out.println(" 事件 = " + value);
              }

              @Override
              public void onError(Throwable e) {}

              @Override
              public void onCompleted() {}
            });
  }
}
