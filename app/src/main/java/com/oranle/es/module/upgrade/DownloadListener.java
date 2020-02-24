package com.oranle.es.module.upgrade;

public interface DownloadListener {
    void onStart();//下载开始

    void onProgress(int progress, long current, long total);//下载进度

    void onFinish(String path);//下载完成

    void onFail(String errorInfo);//下载失败
}