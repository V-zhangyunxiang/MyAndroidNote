package com.owl.android_simple;

/**
 * Description
 *
 * @author zhangyunxiang Date 2021/7/12 15:09
 */
// public class OkHttpManager {
//  private static final int DEFAULT_TIME_OUT = 10;
//  private static final int DEFAULT_READ_TIME_OUT = 40;
//  private static final int DEFAULT_WRITE_TIME_OUT = 10;
//  private static final String TAG = "live";
//  private static final String NETWORK = "net-message";
//  private static final String CHANNEL = BuildConfig.INTERNATIONAL ? "googleplay" : "local";
//  private final OkHttpClient mOkHttpClient;
//  private Request request;
//
//  private static class SingletonHolder {
//    private static final OkHttpManager INSTANCE = new OkHttpManager();
//  }
//
//  public static OkHttpManager getInstance() {
//    return SingletonHolder.INSTANCE;
//  }
//
//  private OkHttpManager() {
//    mOkHttpClient =
//        new OkHttpClient.Builder()
//            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
//            .writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS)
//            .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
//            .addInterceptor(getHeaderInBuilder().build())
//            .addInterceptor(new RequestLogInterceptor())
//            .build();
//  }
//
//  public Builder addDefaultHeader(Builder b) {
//    if (b == null) {
//      b = new Builder();
//    } else {
//      b =
//          b.header("Accept", "*/*")
//              .header("User-Agent", Network.getUA())
//              .header("Accept-Language", Network.language())
//              .header("X-Channel", CHANNEL)
//              .header("X-Putong-Client-Id", BuildConfig.CLIENT_ID)
//              .header(
//                  "X-Live-Region-Tag",
//                  Putong.liveRegionTag == LiveRegionTag.unknown_
//                      ? ""
//                      : Putong.liveRegionTag.toString())
//              .header("appBuild", CoreDevice.getAppBuild())
//              .header(Network.AUTH, Network.AUTH_BEFORE_SIGN_UP);
//      if (Act.foreground_() == null) {
//        b.addHeader("Client-State", "background");
//      }
//      b.addHeader("X-TT-ClientInfo", Network.getXttClientInfo());
//      if (Putong.location != null) {
//        Location l = Putong.location.location_();
//        if (l != null) {
//          b.header("Geolocation", Network.formatLocation(l));
//        }
//      }
//    }
//    return b;
//  }
//
//  public static HeaderParamsInterceptor.Builder getHeaderInBuilder() {
//    return new HeaderParamsInterceptor.Builder();
//  }
//
//  /** 读取新增的 header */
//  private static class HeaderParamsInterceptor implements Interceptor {
//    public final Map<String, String> mHeaderParamsMap = new HashMap<>();
//
//    @NotNull
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//      final Request originalRequest = chain.request();
//      if (mHeaderParamsMap.size() > 0) {
//        Request.Builder requestBuilder = originalRequest.newBuilder();
//        requestBuilder.method(originalRequest.method(), originalRequest.body());
//        for (Map.Entry<String, String> params : mHeaderParamsMap.entrySet()) {
//          requestBuilder.header(params.getKey(), params.getValue());
//        }
//        return chain.proceed(requestBuilder.build());
//      } else {
//        return chain.proceed(originalRequest);
//      }
//    }
//
//    public static class Builder {
//      HeaderParamsInterceptor mHeaderParamsInterceptor;
//
//      public Builder() {
//        mHeaderParamsInterceptor = new HeaderParamsInterceptor();
//      }
//
//      public Builder addHeaderParams(String key, String value) {
//        mHeaderParamsInterceptor.mHeaderParamsMap.put(key, value);
//        return this;
//      }
//
//      public Builder addHeaderParams(String key, int value) {
//        return addHeaderParams(key, String.valueOf(value));
//      }
//
//      public Builder addHeaderParams(String key, float value) {
//        return addHeaderParams(key, String.valueOf(value));
//      }
//
//      public Builder addHeaderParams(String key, long value) {
//        return addHeaderParams(key, String.valueOf(value));
//      }
//
//      public Builder addHeaderParams(String key, double value) {
//        return addHeaderParams(key, String.valueOf(value));
//      }
//
//      public HeaderParamsInterceptor build() {
//        return mHeaderParamsInterceptor;
//      }
//    }
//  }
//
//  private static class RequestLogInterceptor implements Interceptor {
//    @NotNull
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//      Request request = chain.request();
//      Response response = chain.proceed(request);
//      LogUtils.d(TAG, "-----------请求的 URL-------------");
//      LogUtils.d(TAG, request.url().toString());
//      LogUtils.d(TAG, "-----------请求头信息-------------");
//      Headers headers = request.headers();
//      for (int i = 0, count = headers.size(); i < count; i++) {
//        LogUtils.d(TAG, "key= " + headers.name(i) + "value= " + headers.value(i));
//      }
//      LogUtils.d(TAG, "-----------Response 信息-------------");
//      LogUtils.d(
//          TAG,
//          "code = "
//              + response.code()
//              + "\n"
//              + "message= "
//              + response.message()
//              + "\n"
//              + "body= "
//              + response.body());
//      return chain.proceed(request);
//    }
//  }
//
//  /**
//   * get 请求
//   *
//   * @param url 当前请求的 url(httpUrl 格式)
//   */
//  public Observable<BLiveEnvelope> buildLiveReadReq(HttpUrl url) {
//    return Observable.create(
//            (OnSubscribe<BLiveEnvelope>)
//                subscriber -> {
//                  Response response = null;
//                  Request realRequest = null;
//                  request = addDefaultHeader(new Builder()).url(url).get().build();
//                  try {
//                    realRequest =
//                        NetworkTrackHelper.maybeAddNetworkTrackTagBeforeCall(
//                            Putong.network.maybeUpdateRequestBeforeCall(request), ReportType.API);
//                    response = mOkHttpClient.newCall(realRequest).execute();
//                    if (response.isSuccessful() && !subscriber.isUnsubscribed()) {
//                      Putong.network.updateServerTime(response.header("Date"));
//                      Gson gson =
//                          new GsonBuilder()
//                              .registerTypeAdapter(Double.class, new LocationDeserializer())
//                              .create();
//                      subscriber.onNext(
//                          gson.fromJson(response.body().string(), BLiveEnvelope.class));
//                    } else if (!response.isSuccessful()) {
//                      LogUtils.d(
//                          NETWORK,
//                          "response fail : "
//                              + response.code()
//                              + ", api error : "
//                              + response.message());
//                      subscriber.onError(new Throwable(response.message()));
//                    }
//                  } catch (Exception e) {
//                    LogUtils.d(
//                        NETWORK,
//                        "request error : "
//                            + (realRequest == null ? null : realRequest.url())
//                            + ", api error : "
//                            + e.getMessage());
//                    subscriber.onError(e);
//                  } finally {
//                    CloseHelper.closeQuietly(response);
//                  }
//                })
//        .compose(Rxu.itm());
//  }
//
//  /**
//   * post 请求
//   *
//   * @param url 当前请求的 url(httpUrl 格式)
//   */
//  public Observable<BLiveEnvelope> buildLivePostReq(HttpUrl url, String jsonBody) {
//    return Observable.create(
//            (OnSubscribe<BLiveEnvelope>)
//                subscriber -> {
//                  Response response = null;
//                  request =
//                      addDefaultHeader(new Builder())
//                          .url(url)
//                          .post(RequestBody.create(Network.JSON, jsonBody))
//                          .build();
//                  try {
//                    response = mOkHttpClient.newCall(request).execute();
//                    if (response.isSuccessful() && !subscriber.isUnsubscribed()) {
//                      subscriber.onNext(
//                          new Gson().fromJson(response.body().toString(), BLiveEnvelope.class));
//                    } else if (!response.isSuccessful()) {
//                      LogUtils.d(
//                          NETWORK,
//                          "response fail : "
//                              + response.code()
//                              + ", api error : "
//                              + response.message());
//                      subscriber.onError(new Throwable(response.message()));
//                    }
//                  } catch (IOException e) {
//                    LogUtils.d(
//                        NETWORK,
//                        "request error : "
//                            + (request == null ? null : request.url())
//                            + ", api error : "
//                            + e.getMessage());
//                    subscriber.onError(e);
//                  } finally {
//                    CloseHelper.closeQuietly(response);
//                  }
//                })
//        .compose(Rxu.itm());
//  }
//
//  /**
//   * patch 请求
//   *
//   * @param url 当前请求的 url(httpUrl 格式)
//   */
//  public Observable<BLiveEnvelope> buildLivePatchReq(HttpUrl url, String jsonBody) {
//    return Observable.create(
//            (OnSubscribe<BLiveEnvelope>)
//                subscriber -> {
//                  Response response = null;
//                  request =
//                      addDefaultHeader(new Builder())
//                          .url(url)
//                          .patch(RequestBody.create(Network.JSON, jsonBody))
//                          .build();
//                  try {
//                    response = mOkHttpClient.newCall(request).execute();
//                    if (response.isSuccessful() && !subscriber.isUnsubscribed()) {
//                      subscriber.onNext(
//                          new Gson().fromJson(response.body().toString(), BLiveEnvelope.class));
//                    } else if (!response.isSuccessful()) {
//                      LogUtils.d(
//                          NETWORK,
//                          "response fail : "
//                              + response.code()
//                              + ", api error : "
//                              + response.message());
//                      subscriber.onError(new Throwable(response.message()));
//                    }
//                  } catch (IOException e) {
//                    LogUtils.d(
//                        NETWORK,
//                        "request error : "
//                            + (request == null ? null : request.url())
//                            + ", api error : "
//                            + e.getMessage());
//                    subscriber.onError(e);
//                  } finally {
//                    CloseHelper.closeQuietly(response);
//                  }
//                })
//        .compose(Rxu.itm());
//  }
//
//  /**
//   * put 请求
//   *
//   * @param url 当前请求的 url(httpUrl 格式)
//   */
//  public Observable<BLiveEnvelope> buildLivePutReq(HttpUrl url, String jsonBody) {
//    return Observable.create(
//            (OnSubscribe<BLiveEnvelope>)
//                subscriber -> {
//                  Response response = null;
//                  request =
//                      addDefaultHeader(new Builder())
//                          .url(url)
//                          .put(RequestBody.create(Network.JSON, jsonBody))
//                          .build();
//                  try {
//                    response = mOkHttpClient.newCall(request).execute();
//                    if (response.isSuccessful() && !subscriber.isUnsubscribed()) {
//                      subscriber.onNext(
//                          new Gson().fromJson(response.body().toString(), BLiveEnvelope.class));
//                    } else if (!response.isSuccessful()) {
//                      LogUtils.d(
//                          NETWORK,
//                          "response fail : "
//                              + response.code()
//                              + ", api error : "
//                              + response.message());
//                      subscriber.onError(new Throwable(response.message()));
//                    }
//                  } catch (IOException e) {
//                    LogUtils.d(
//                        NETWORK,
//                        "request error : "
//                            + (request == null ? null : request.url())
//                            + ", api error : "
//                            + e.getMessage());
//                    subscriber.onError(e);
//                  } finally {
//                    CloseHelper.closeQuietly(response);
//                  }
//                })
//        .compose(Rxu.itm());
//  }
// }
