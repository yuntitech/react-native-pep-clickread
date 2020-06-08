
package com.pep.clickread;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.util.ArrayList;
import java.util.List;


public class RNPepClickreadModule extends ReactContextBaseJavaModule {

    public static final String EVENT_PROGRESS = "RNPepClickReadProgress";
    private static final String EVENT_ExperienceDidEnded = "PepBookExperienceDidEnded";
    private final ReactApplicationContext reactContext;
    private DeviceEventManagerModule.RCTDeviceEventEmitter eventEmitter;


    public RNPepClickreadModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNPepClickread";
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
    }

    /**
     * @param appKey​ 分配的平台key​必填
     */
    @ReactMethod
    public void init(String appKey) {
    }


    /**
     * 第三方用户鉴权、绑定设备.
     * 对接平台可在APP启动或用户订单状态发生变化时调用该方法
     *
     * @param userId  用户ID
     * @param promise 网络请求回调
     *                success网络请求成功后回调（包括当前用户购买的书本和绑定的设备列表）
     *                fail网络请求失败后回调
     */
    @ReactMethod
    public void syncOrder(String userId, final Promise promise) {
    }

    /**
     * 在同步订单接口成功的回调里回传的json里含有此用户的所有绑定设备列表。
     */
    @ReactMethod
    public void getDevices(Promise promise) {
    }

    /**
     * @param userId            (用户id)
     * @param list(集合，里面放有设备id) ​​如List<String> list = new ArrayList<>();
     *                          list.add(dev_id_1); 解绑设备id为dev_id_1的设备
     *                          list.add(dev_id_2); 解绑设备id为dev_id_2的设备
     * @param promise           (回调接口)
     */
    @ReactMethod
    public void removeDevices(String userId, ReadableArray list, final Promise promise) {
    }

    /**
     * 获取书籍信息
     * <p>
     * 根据book_id返回教材信息
     */
    @ReactMethod
    public void getBookItemById(String userId, String bookId, final Promise promise) {
    }

    @ReactMethod
    public void getBookUnitAndTitle() {

    }

    /**
     * 获取书籍列表
     *
     * @param userId 用户id
     */
    @ReactMethod
    public void getAllBookList(String userId, final Promise promise) {
    }

    /**
     * 打开课本
     *
     * @param textbooksBean          (BookList.TextbooksBean对象)
     * @param forExperience（是否为体验模式）
     * @param num                    打开页码只有已购买状态才成能成功打开该页码
     */
    @ReactMethod
    public void openBook(ReadableMap textbooksBean, boolean forExperience, int num, Promise promise) {
    }

    /**
     * 下载课本
     *
     * @param textbooksBean BookList.TextbooksBean对象
     */
    @ReactMethod
    public void download(ReadableMap textbooksBean) {
    }

    /**
     * 暂停下载
     *
     * @param bookId 书籍id
     */
    @ReactMethod
    public void pauseDownloadOfBookID(String bookId) {
    }

    /**
     * 继续下载
     *
     * @param bookId 书籍id
     */
    @ReactMethod
    public void continueDownloadOfBookID(String bookId) {
    }

    /**
     * 删除课本
     *
     * @param bookId (教材id)
     * @return true：删除成功  false：删除失败
     */
    @ReactMethod
    public void deleteBook(String bookId, Promise promise) {
    }

    /**
     * 获取缓存大小
     *
     * @return cacheSize (缓存大小  单位为字节)
     */
    @ReactMethod
    public void getCacheDirSize(Promise promise) {
    }

    /**
     * 清除缓存
     *
     * @return isClean是否清除成功
     */
    @ReactMethod
    public void deleteCacheDir(Promise promise) {
    }

    /**
     * 判断书本是否已经下载
     *
     * @param textbooksBean
     */
    @ReactMethod
    public void isDownloaded(ReadableMap textbooksBean, Promise promise) {
    }

    /**
     * 判断书本是否有更新
     *
     * @param textbooksBean (BookList.TextbooksBean对象)
     * @return true：有更新  false：没有更新
     */
    @ReactMethod
    public void hasUpdate(ReadableMap textbooksBean, Promise promise) {
    }

    /**
     * 获取书本状态（是否已经订购）
     */
    @ReactMethod
    public void hasBuy(String bookId, Promise promise) {
    }

    /***
     * 获取目录
     *
     * @param bookId 书籍id
     */
    @ReactMethod
    public void getBookCatalogById(String bookId, final Promise promise) {
    }
}

