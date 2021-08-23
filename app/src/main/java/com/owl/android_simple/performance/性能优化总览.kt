package com.owl.android_simple.performance

/**
 *
 *  性能优化难题
 *    性能表现差
 *      App 启动慢、卡顿、丢帧
 *      内存占用高，抖动频繁
 *      耗电、网络请求慢
 *      崩溃率、异常率
 *    线上问题无从追查
 *    性能优化的长期开销大
 *  项目初期
 *    只关心崩溃率，不采集性能数据
 *    没有性能检测手段，无排查手段
 *  项目壮大期
 *    指标采集，不够全和深入
 *    接入成熟 APM，排查手段单一
 *    线下检测、优化方案不成型
 *  项目成熟期
 *    重点关注性能问题，数据丰富
 *    线上线下一套完善的解决方案
 *    自建 APM，新产品快速接入
 *  线上线下
 *    误区：对线上不重视
 *    线下预防，线上监控
 *    线下可用黑科技
 *  为什么要自建 APM
 *    成熟 APM 通用，但不满足个性化需求
 *    外部 APM 与内部系统难打通，有时间成本
 *    数据必须掌握在自己手里
 *  Crash 平台
 *    bugly 为代表
 *     数据采集、上报率高
 *     Java、Native 崩溃采集
 *     项目初期建议接入
 *  APM 平台
 *    听云
 *      通用的性能解决方案，数据采集完善
 *      方便接入、不满足个性化
 *      壮大期建议接入
 *  自建解决方案
 *     美团、携程、360等
 * */