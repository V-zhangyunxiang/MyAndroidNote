package com.xiaozhu.testproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
 * @author zhangyunxiang
 * Date 2019/1/3 10:53
 */
public class RxJavaActivity extends AppCompatActivity {
    private static final String TAG = "zyx";

    public static void main(String[] args) {
        //onCreate(null);
        //bufferTest();
        //delay();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_vp_four);
        BehaviorSubject();
    }
    //------------------RxJava1---------------------------------------------------------

    // Observable.subscribe() Rx1 得到一个 Subscription 对象；Rx2 则是 Disposable 对象
    private void subscription() {
        Subscription subscription = Observable.from(new ArrayList<>()).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {

            }
        });
        System.out.println(subscription.isUnsubscribed());
        subscription.unsubscribe();
        System.out.println(subscription.isUnsubscribed());
    }

    //使用 concat 和 merge 时第 1 个被观察者发送 Error 事件后，第 2 个被观察者则不会继续发送事件

    //组合(多个被观察者)一起发送数据，合并后按发送顺序(串行)执行
    private void concat() {
        Observable.concat(Observable.just(1, 2, 3),
                Observable.just(4, 5, 6),
                Observable.just(7, 8, 9),
                Observable.just(10, 11, 12))
                .subscribe(new Observer<Integer>() {
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

    //组合(多个被观察者)一起发送数据，合并后按时间线(并行)执行
    private void merge() {
        Observable.merge(
                Observable.interval(100, 1000, TimeUnit.MILLISECONDS),
                Observable.interval(300, 1000, TimeUnit.MILLISECONDS))
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onNext(Long value) {
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
                .subscribe(new Observer<Integer>() {
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

    //严格按照原先事件序列 进行对位合并，最终结果不受发射事件延迟的影响
    //最终合并的事件数量 = 多个被观察者（Observable）中数量最少的数量
    private static void zip() {
        Observable<Integer> obs1 = Observable.create((Observable.OnSubscribe<Integer>) subscriber -> {
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
        }).subscribeOn(Schedulers.io());

        Observable<String> obs2 = Observable.create(
                (Observable.OnSubscribe<String>) subscriber -> {
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
                }).subscribeOn(Schedulers.io());

        Observable.zip(obs1, obs2, new Func2<Integer, String, String>() {
            @Override
            public String call(Integer integer, String s) {
                return integer + s;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
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

    //CombineLatest（） = 按时间合并，即在同一个时间点上合并，最终结果受到发射延迟的影响
    //将先发送了数据的 Observables 的最新（最后）一个数据与另外一个 Observable 发送的每个数据结合
    private static void combineLatest() {
        Observable<Integer> obs1 = Observable.create((Observable.OnSubscribe<Integer>) subscriber -> {
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
        }).subscribeOn(Schedulers.io());

        Observable<String> obs2 = Observable.create(
                (Observable.OnSubscribe<String>) subscriber -> {
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
                }).subscribeOn(Schedulers.io());

        Observable.combineLatest(obs1, obs2, new Func2<Integer, String, String>() {
            @Override
            public String call(Integer integer, String s) {
                return integer + s;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
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

    // reduce（）但本质都是前 2 个数据聚合，然后与后 1 个数据继续进行聚合，依次类推
    //Observable.just(1,2,3,4)
    //                .reduce(new BiFunction<Integer, Integer, Integer>() {
    //                    // 在该复写方法中复写聚合的逻辑
    //                    @Override
    //                    public Integer apply(@NonNull Integer s1, @NonNull Integer s2) throws Exception {
    //                        Log.e(TAG, "本次计算的数据是： "+s1 +" 乘 "+ s2);
    //                        return s1 * s2;
    //                        // 本次聚合的逻辑是：全部数据相乘起来
    //                        // 原理：第1次取前2个数据相乘，之后每次获取到的数据 = 返回的数据x原始下1个数据每
    //                    }
    //                }).subscribe(new Consumer<Integer>() {
    //            @Override
    //            public void accept(@NonNull Integer s) throws Exception {
    //                Log.e(TAG, "最终计算的结果是： "+s);
    //
    //            }
    //        });

    //collect()    将被观察者Observable发送的数据事件收集到一个数据结构里
    //startWith()/startWithArray() 在一个被观察者发送事件前，追加发送一些数据/一个新的被观察者
    //count()      统计被观察者发送事件的数量
    //retry（）     重试，即当出现错误时，让被观察者（Observable）重新发射数据
    //repeat（）    无条件地、重复发送 被观察者事件

    //BehaviorSubject : Observer 会接收到 BehaviorSubject 被订阅之前的最后一个数据(如果订阅之前有事件，则会替换到默认设置的数据)，再接收订阅之后发射过来的数据
    //ReplaySubject : 不论订阅发生在什么时候，都发射全部数据; createWithSize(n) 只缓存订阅前最后发送的 n 条数据;createWithTime() 缓存时间
    //PublishSubject : Observer 只接收 PublishSubject 被订阅之后发送的数据
    //AsyncSubject :   Observer会接收 AsyncSubject 的 onComplete() 之前的最后一个数据
    private void BehaviorSubject() {
        BehaviorSubject<String> subject = BehaviorSubject.create("behaviorSubject1");
        subject.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
            }
        }, () -> Log.i(TAG, "complete"));
        subject.onNext("behaviorSubject2");
        subject.onNext("behaviorSubject3");
        //测试是否走 onError
        subject.onError(new Exception("aaaaaa"));
    }

    //do 系列方法，有执行的前后顺序  flatMap(无序) concatMap(有序)
    //doOnSubscribe 下一行用 subscribeOn 指定其执行的线程
    //doOnNext 上一行用 observeOn 指定其执行的线程
    private void doSomething() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onCompleted();
            }
        }).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                Log.i(TAG, "doOnSubscribe");
            }
        }).doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "doOnNext1");
            }
        }).doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "doOnNext2");
            }
        }).doAfterTerminate(new Action0() {
            @Override
            public void call() {

            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {

            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {

            }
        }).doOnTerminate(new Action0() {
            @Override
            public void call() {

            }
        }).doOnEach(new Action1<Notification<? super Integer>>() {
            @Override
            public void call(Notification<? super Integer> notification) {

            }
        }).flatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer s) {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + s + "拆分后的子事件" + i);
                }
                return Observable.from(list);
            }
        }).subscribe(new Subscriber<String>() {
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

    //遇到错误时，发送 1 个新的 Observable
    private static void onExceptionResumeNext() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onError(new Exception("发生错误了"));
            }
        }).onExceptionResumeNext(
                Observable.just(33)
        ).subscribe(new Subscriber<Integer>() {
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

    //遇到错误时，发送 1 个新的 Observable
    private static void onErrorResumeNext() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onError(new Throwable("发生错误了"));
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends Integer>>() {
            @Override
            public Observable<? extends Integer> call(Throwable throwable) {
                // 1. 捕捉错误异常
                Log.e(TAG, "在onErrorReturn处理了错误: " + throwable.toString());
                return Observable.just(11, 22);
            }
        }).subscribe(new Subscriber<Integer>() {
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

    //遇到错误时，发送 1 个特殊事件 & 正常终止
    private static void onErrorReturn() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onError(new Throwable("发生错误了"));
            }
        }).onErrorReturn(new Func1<Throwable, Integer>() {
            @Override
            public Integer call(Throwable throwable) {
                Log.e(TAG, "在onErrorReturn处理了错误: " + throwable.toString());
                return 666;
            }
        }).subscribe(new Subscriber<Integer>() {
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

    //定期从 被观察者（Obervable）需要发送的事件中 获取一定数量的事件 & 放到缓存区中，最终发送
    private static void buffer() {
        Observable.just(1, 2, 3, 4, 5)
                .buffer(2, 2)
                // 设置缓存区大小 & 步长
                // 缓存区大小 = 每次从被观察者中获取的事件数量
                // 步长 = 每次获取新事件的数量
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> stringList) {
                        System.out.println(" 缓存区里的事件数量 = " + stringList.size());
                        for (Integer value : stringList) {
                            System.out.println(" 事件 = " + value);
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                });
    }

    //延迟发射
    public static void delay() {
        Observable.just(1, 2, 3)
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onNext(Integer value) {
                        System.out.println(" 事件 = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onCompleted() {
                    }
                });

//      ------------------------RxJava2-------------------------------------------------
//        io.reactivex.Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onComplete();
//                emitter.onNext(3);
//            }
//        }).concatArrayDelayError().subscribe(new Observer<Integer>() {
//            private Disposable disposable;
//            private int i;
//            private CompositeDisposable compositeDisposable = new CompositeDisposable();
//
//            @Override
//            public void onSubscribe(Disposable d) {
//
//                disposable = d;
//                compositeDisposable.add(disposable);
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//
////                i++;
////                if (i == 2) {
////                    disposable.dispose();
////                }
////                Log.d(TAG, "isDisposed: " + disposable.isDisposed());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                compositeDisposable.clear();
//            }
//
//            @Override
//            public void onComplete() {
//            }
//        });
        // new consumer 自定义接收哪些事件,subscribe 可设置接收多个 consumer 事件
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                Log.d(TAG, "emit 1");
//                emitter.onNext(1);
//                Log.d(TAG, "emit 2");
//                emitter.onNext(2);
//                emitter.onComplete();
//                Log.d(TAG, "emit 3");
//                emitter.onNext(3);
//            }
//        }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.d(TAG, "accept:" + integer);
//            }
//        });

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                Log.d(TAG, "emit 1");
//                emitter.onNext(1);
//                Log.d(TAG, "emit 2");
//                emitter.onNext(2);
//                emitter.onComplete();
//                Log.d(TAG, "emit 3");
//                emitter.onNext(3);
//            }
//        }).subscribe(
//        new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//
//            }
//        });

//        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                Log.d(TAG, "emit 1");
//                emitter.onNext(1);
//                Thread.sleep(1000);
//
//                Log.d(TAG, "emit 2");
//                emitter.onNext(2);
//                Thread.sleep(1000);
//
//                Log.d(TAG, "emit 3");
//                emitter.onNext(3);
//                Thread.sleep(1000);
//
//                Log.d(TAG, "emit 4");
//                emitter.onNext(4);
//                Thread.sleep(1000);
//
//                Log.d(TAG, "emit complete1");
//                emitter.onComplete();
//            }
//        }).subscribeOn(Schedulers.io());
//
//        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                Log.d(TAG, "emit A");
//                emitter.onNext("A");
//                Thread.sleep(1000);
//
//                Log.d(TAG, "emit B");
//                emitter.onNext("B");
//                Thread.sleep(1000);
//
//                Log.d(TAG, "emit C");
//                emitter.onNext("C");
//                Thread.sleep(1000);
//
//                Log.d(TAG, "emit complete2");
//                emitter.onComplete();
//            }
//        }).subscribeOn(Schedulers.io());
//
//        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
//            @Override
//            public String apply(Integer integer, String s) throws Exception {
//                return integer + s;
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "onSubscribe");
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.d(TAG, "onNext: " + s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "onComplete");
//            }
//        });

//        Flowable.create(new FlowableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
//                Log.d(TAG, "emit 1");
//                emitter.onNext(1);
//                Log.d(TAG, "emit 2");
//                emitter.onNext(2);
//                Log.d(TAG, "emit 3");
//                emitter.onNext(3);
//                Log.d(TAG, "emit complete");
//                emitter.onComplete();
        //Flowable 水缸默认大小是 128 k ；BackpressureStrategy.BUFFER 不限制水缸大小
        //非自己 create 出来的 Flowable 使用下面 3 个代替 Flowable 参数
        //onBackpressureBuffer()
        //onBackpressureDrop()
        //onBackpressureLatest()

        //获取下游处理能力,下游每消费 96 个事件便会自动触发内部的 request() 去设置上游的 requested 的值
        //emitter.requested();
//                 for (int i = 0; i < 129; i++) {
//                     Log.d(TAG, "emit " + i);
//                     emitter.onNext(i);
//                 }
//             }
//         }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
//             private Subscription mSubscription;

//             @Override
//             public void onSubscribe(Subscription s) {
//                 Log.d(TAG, "onSubscribe");
//                 mSubscription = s;
//                 //响应式拉取 : 光下游自己控制能接收几个不能算响应式，上游也要知道下游有多大的处理能力
//                 //可多次调用 request
// //              mSubscription.request(1);
//             }

//             @Override
//             public void onNext(Integer integer) {
//                 Log.d(TAG, "onNext: " + integer);
//             }

//             @Override
//             public void onError(Throwable t) {
//                 Log.w(TAG, "onError: ", t);
//                 mSubscription.cancel();
//             }

//             @Override
//             public void onComplete() {
//                 Log.d(TAG, "onComplete");
//             }
//         });

    }

}