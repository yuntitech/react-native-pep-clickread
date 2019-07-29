
package com.pep.clickread;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.alibaba.fastjson.JSON;
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
import com.rjsz.frame.diandu.PRDownloaderManager;
import com.rjsz.frame.diandu.PRSDKManager;
import com.rjsz.frame.diandu.PRViewManager;
import com.rjsz.frame.diandu.bean.BookList;
import com.rjsz.frame.diandu.bean.CataLogBean;
import com.rjsz.frame.diandu.bean.PRDownloadInfo;
import com.rjsz.frame.diandu.callback.ReqCallBack;
import com.rjsz.frame.diandu.config.PRStateCode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class RNPepClickreadModule extends ReactContextBaseJavaModule {

    public static final String EVENT_PROGRESS = "RNPepClickReadProgress";
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
        this.eventEmitter = reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
        EventBus.getDefault().register(this);
    }

    /**
     * @param appKey​ 分配的平台key​必填
     */
    @ReactMethod
    public void init(String appKey) {
        PRSDKManager.getInstance().init((Application) reactContext.getApplicationContext(), appKey);
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
        PRSDKManager.getInstance().syncOrder(userId, new ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) {
                String json = (String) result;
                promise.resolve(json);
            }

            @Override
            public void onReqFailed(int errCode, String errMsg) {
                promise.reject(String.valueOf(errCode), errMsg);
            }
        });
    }

    /**
     * 在同步订单接口成功的回调里回传的json里含有此用户的所有绑定设备列表。
     */
    @ReactMethod
    public void getDevices(Promise promise) {
        promise.resolve(JSON.toJSONString(PRSDKManager.getInstance().getDevices()));
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
        List<String> deviceList = new ArrayList<>();
        for (int n = 0; n < list.size(); n++) {
            deviceList.add(list.getString(n));
        }
        PRSDKManager.getInstance().removeDevices(userId, deviceList, new ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) {
                String json = (String) result;
                promise.resolve(json);
            }

            @Override
            public void onReqFailed(int errCode, String errMsg) {
                promise.reject(String.valueOf(errCode), errMsg);
            }
        });
    }

    /**
     * 获取书籍信息
     * <p>
     * 根据book_id返回教材信息
     */
    @ReactMethod
    public void getBookItemById(String userId, String bookId, final Promise promise) {
        PRSDKManager.getInstance().getBookItemById(userId, bookId, new ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) {
                BookList.TextbooksBean book = (BookList.TextbooksBean) result;
                promise.resolve(fromTextbooksBean(book));
            }

            @Override
            public void onReqFailed(int errCode, String errMsg) {
                promise.reject(String.valueOf(errCode), errMsg);
            }
        });
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
        PRSDKManager.getInstance().getAllBookList(userId, new ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) {
                BookList bookList = (BookList) result;
                if (bookList.booklist.size() == 0) {
                    promise.resolve(null);
                    return;
                }
                WritableArray books = Arguments.createArray();
                for (int i = 0; i < bookList.booklist.size(); i++) {
                    BookList.GradeBean gradeBean = bookList.booklist.get(i);
                    books.pushMap(fromGradeBean(gradeBean));
                }
                promise.resolve(books);
            }

            @Override
            public void onReqFailed(int errCode, String errMsg) {
                promise.reject(String.valueOf(errCode), errMsg);
            }
        });
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
        Activity activity = getCurrentActivity();
        if (activity != null) {
            PRViewManager.getInstance().openBook(activity, fromTextbooksMap(textbooksBean), forExperience, num);
        } else {
            promise.reject("-1", "activity is null");
        }
    }

    /**
     * 下载课本
     *
     * @param textbooksBean BookList.TextbooksBean对象
     */
    @ReactMethod
    public void download(ReadableMap textbooksBean) {
        PRDownloaderManager.getInstance().download(fromTextbooksMap(textbooksBean));
    }

    /**
     * 暂停下载
     *
     * @param bookId 书籍id
     */
    @ReactMethod
    public void pauseDownloadOfBookID(String bookId) {
        PRDownloaderManager.getInstance().pauseDownloadOfBookID(bookId);
    }

    /**
     * 继续下载
     *
     * @param bookId 书籍id
     */
    @ReactMethod
    public void continueDownloadOfBookID(String bookId) {
        PRDownloaderManager.getInstance().continueDownloadOfBookID(bookId);
    }

    /**
     * 删除课本
     *
     * @param bookId (教材id)
     * @return true：删除成功  false：删除失败
     */
    @ReactMethod
    public void deleteBook(String bookId, Promise promise) {
        boolean isDelete = PRDownloaderManager.getInstance().deleteBook(bookId);
        promise.resolve(isDelete);
    }

    /**
     * 获取缓存大小
     *
     * @return cacheSize (缓存大小  单位为字节)
     */
    @ReactMethod
    public void getCacheDirSize(Promise promise) {
        long cacheSize = PRSDKManager.getInstance().getCacheDirSize(getReactApplicationContext());
        promise.resolve(Double.valueOf(String.valueOf(cacheSize)));
    }

    /**
     * 清除缓存
     *
     * @return isClean是否清除成功
     */
    @ReactMethod
    public void deleteCacheDir(Promise promise) {
        boolean isClean = PRSDKManager.getInstance().deleteCacheDir(getReactApplicationContext());
        promise.resolve(isClean);
    }

    /**
     * 判断书本是否已经下载
     *
     * @param textbooksBean
     */
    @ReactMethod
    public void isDownloaded(ReadableMap textbooksBean, Promise promise) {
        boolean isDownloaded = PRDownloaderManager.getInstance()
                .isDownloaded(fromTextbooksMap(textbooksBean));
        promise.resolve(isDownloaded);
    }

    /**
     * 判断书本是否有更新
     *
     * @param textbooksBean (BookList.TextbooksBean对象)
     * @return true：有更新  false：没有更新
     */
    @ReactMethod
    public void hasUpdate(ReadableMap textbooksBean, Promise promise) {
        boolean hasUpdate = PRDownloaderManager.getInstance()
                .hasUpdate(fromTextbooksMap(textbooksBean));
        promise.resolve(hasUpdate);
    }

    /**
     * 获取书本状态（是否已经订购）
     */
    @ReactMethod
    public void hasBuy(String bookId, Promise promise) {
        boolean hasBuy = PRSDKManager.getInstance().hasBuy(bookId);
        promise.resolve(hasBuy);
    }

    /***
     * 获取目录
     *
     * @param bookId 书籍id
     */
    @ReactMethod
    public void getBookCatalogById(String bookId, final Promise promise) {
        PRSDKManager.getInstance().getBookCatalogById(bookId, new ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) {
                CataLogBean cataLogBean = (CataLogBean) result;
                promise.resolve(JSON.toJSONString(cataLogBean.getCatalogList()));
            }

            @Override
            public void onReqFailed(int errCode, String errMsg) {
                promise.reject(String.valueOf(errCode), errMsg);
            }
        });
    }

    /**
     * @param downloadInfo 下载书的回调
     */
    @Subscribe
    public void downloadEvent(PRDownloadInfo downloadInfo) {
        WritableMap writableMap = Arguments.createMap();
        writableMap.putString("book_id", downloadInfo.getItem().book_id);
        writableMap.putInt("progress", downloadInfo.progress);
        writableMap.putInt("state", downloadInfo.state);
        switch (downloadInfo.state) {
            case PRStateCode.STATE_ADD:
                writableMap.putString("stateTips", "开始下载");
                break;
            case PRStateCode.STATE_ERROR:
            case PRStateCode.INTERFACE_ERROR:
                writableMap.putString("stateTips", "下载失败");
                break;
            case PRStateCode.STATE_WAIT:
                writableMap.putString("stateTips", "暂停下载");
                break;
            case PRStateCode.STATE_DOWNLOAD:
                writableMap.putString("stateTips", "下载中");
                break;
            case PRStateCode.STATE_UNZIP:
                writableMap.putString("stateTips", "解压中");
                break;
            case PRStateCode.STATE_SUCCESS:
                writableMap.putString("stateTips", "下载成功");
                break;
        }
        eventEmitter.emit(EVENT_PROGRESS, writableMap);
    }

    private WritableMap fromGradeBean(BookList.GradeBean gradeBean) {
        WritableMap gradeMap = Arguments.createMap();
        gradeMap.putString("gradename", gradeBean.gradename);
        WritableArray gradeArr = Arguments.createArray();
        for (BookList.TermBean termBean : gradeBean.grade) {
            for (BookList.TextbooksBean textbooksBean : termBean.textbooks) {
                WritableMap textbooksMap = fromTextbooksBean(textbooksBean);
                textbooksMap.putString("term", termBean.term);
                gradeArr.pushMap(textbooksMap);
            }
        }
        gradeMap.putArray("data", gradeArr);
        return gradeMap;
    }

    private WritableMap fromTermBean(BookList.TermBean termBean) {
        WritableMap termMap = Arguments.createMap();
        termMap.putString("term", termBean.term);
        WritableArray textbooksArr = Arguments.createArray();
        for (BookList.TextbooksBean textbooksBean : termBean.textbooks) {
            textbooksArr.pushMap(fromTextbooksBean(textbooksBean));
        }
        termMap.putArray("data", textbooksArr);
        return termMap;
    }

    private WritableMap fromTextbooksBean(BookList.TextbooksBean textbooksBean) {
        WritableMap textbooksMap = Arguments.createMap();
        textbooksMap.putString("book_id", textbooksBean.book_id);
        textbooksMap.putString("book_name", textbooksBean.book_name);
        textbooksMap.putString("book_url", textbooksBean.book_url);
        textbooksMap.putString("catalog_url", textbooksBean.catalog_url);
        textbooksMap.putInt("download_state", textbooksBean.download_state);
        textbooksMap.putString("download_url", textbooksBean.download_url);
        textbooksMap.putString("download_urls", textbooksBean.getDownload_urls());
        textbooksMap.putBoolean("evaluation_support", textbooksBean.evaluation_support);
        textbooksMap.putInt("ex_pages", textbooksBean.ex_pages);
        textbooksMap.putString("grade", textbooksBean.grade);
        textbooksMap.putBoolean("hasTest", textbooksBean.hasTest);
        textbooksMap.putInt("heard_num", textbooksBean.heard_num);
        textbooksMap.putDouble("heard_time", textbooksBean.heard_time);
        textbooksMap.putString("icon_url", textbooksBean.icon_url);
        textbooksMap.putBoolean("isHeader", textbooksBean.isHeader);
        textbooksMap.putBoolean("is_practise", textbooksBean.is_practise);
        textbooksMap.putString("modify_time", textbooksBean.modify_time);
        textbooksMap.putString("preview_url", textbooksBean.preview_url);
        textbooksMap.putDouble("size", textbooksBean.size);
        textbooksMap.putInt("subState", textbooksBean.subState);
        textbooksMap.putString("subject_id", textbooksBean.subject_id);
        textbooksMap.putString("term", textbooksBean.term);
        textbooksMap.putBoolean("test_flag", textbooksBean.test_flag);
        textbooksMap.putInt("time_consume_sum", textbooksBean.time_consume_sum);
        textbooksMap.putInt("titleOffset", textbooksBean.titleOffset);
        textbooksMap.putInt("total_words", textbooksBean.total_words);
        textbooksMap.putInt("track_count", textbooksBean.track_count);
        textbooksMap.putString("version", textbooksBean.version);
        return textbooksMap;
    }

    private BookList.TextbooksBean fromTextbooksMap(ReadableMap textbooksMap) {
        BookList.TextbooksBean textbooksBean = new BookList.TextbooksBean();
        textbooksBean.book_id = textbooksMap.getString("book_id");
        textbooksBean.book_name = textbooksMap.getString("book_name");
        textbooksBean.book_url = textbooksMap.getString("book_url");
        textbooksBean.catalog_url = textbooksMap.getString("catalog_url");
        textbooksBean.download_state = textbooksMap.getInt("download_state");
        textbooksBean.download_url = textbooksMap.getString("download_url");
        textbooksBean.evaluation_support = textbooksMap.getBoolean("evaluation_support");
        textbooksBean.ex_pages = textbooksMap.getInt("ex_pages");
        textbooksBean.grade = textbooksMap.getString("grade");
        textbooksBean.hasTest = textbooksMap.getBoolean("hasTest");
        textbooksBean.heard_num = textbooksMap.getInt("heard_num");
        textbooksBean.heard_time = Double.valueOf(textbooksMap.getDouble("heard_time")).floatValue();
        textbooksBean.icon_url = textbooksMap.getString("icon_url");
        textbooksBean.isHeader = textbooksMap.getBoolean("isHeader");
        textbooksBean.is_practise = textbooksMap.getBoolean("is_practise");
        textbooksBean.modify_time = textbooksMap.getString("modify_time");
        textbooksBean.preview_url = textbooksMap.getString("preview_url");
        textbooksBean.size = textbooksMap.getDouble("size");
        textbooksBean.subState = textbooksMap.getInt("subState");
        textbooksBean.subject_id = textbooksMap.getString("subject_id");
        textbooksBean.term = textbooksMap.getString("term");
        textbooksBean.test_flag = textbooksMap.getBoolean("test_flag");
        textbooksBean.time_consume_sum = textbooksMap.getInt("time_consume_sum");
        textbooksBean.titleOffset = textbooksMap.getInt("titleOffset");
        textbooksBean.total_words = textbooksMap.getInt("total_words");
        textbooksBean.track_count = textbooksMap.getInt("track_count");
        textbooksBean.version = textbooksMap.getString("version");
        return textbooksBean;
    }

}
