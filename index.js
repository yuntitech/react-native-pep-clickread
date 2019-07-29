import { DeviceEventEmitter, NativeModules, Platform } from 'react-native';

const { RNPepClickread } = NativeModules;

export type GradeBean = {
	gradename: string,
	grade: Array<TermBean>,
};

export type TermBean = {
	term: string,
	textbooks: Array<TextbooksBean>,
};

export type TextbooksBean = {
	term: string,
	book_id: string,
	book_name: string,
	book_url: string,
	catalog_url: string,
	download_url: string,
	evaluation_support: boolean,
	grade: string,
	icon_url: string,
	modify_time: string,
	preview_url: string,
	size: number,
	term: string,
	version: string,
	isHeader: boolean,
	type: string,
	time_consume_sum: number,
	track_count: number,
	powertime: string,
	download_state: number,
	heard_num: number,
	heard_time: number,
	subState: number,
	test_flag: boolean,
	hasTest: boolean,
	is_practise: boolean,
	ex_pages: number,
	titlePages: number,
	titlePrefix: string,
	titleOffset: number,
	subject_id: string,
	total_words: number,
	book_url_hd: string,
	download_url_hd: string,
	size_hd: string,
};

export const STATE_PEP_CLICKREAD = {
	STATE_ADD: 6, //开始下载
	STATE_ERROR: 5, //下载失败
	INTERFACE_ERROR: 7, //下载接口访问失败
	STATE_WAIT: 1, //暂停中
	STATE_DOWNLOAD: 2, //下载中
	STATE_UNZIP: 3, //解压中
	STATE_SUCCESS: 4, //下载成功
};

export type ProgressData = {
	book_id: string,
	progress: number,
	state: number,
	stateTips: string,
};

/**
 * @param appKey​ 分配的平台key​必填
 */
const init = (appKey: string) => {
	RNPepClickread.init(appKey);
};

/**
 * 第三方用户鉴权、绑定设备.
 * 对接平台可在APP启动或用户订单状态发生变化时调用该方法
 *
 * @param userId  用户ID
 * @param promise 网络请求回调
 *                success网络请求成功后回调（包括当前用户购买的书本和绑定的设备列表）
 *                fail网络请求失败后回调
 */
const syncOrder = async (userId: string): Promise<string> => {
	return RNPepClickread.syncOrder(userId);
};

/**
 * 在同步订单接口成功的回调里回传的json里含有此用户的所有绑定设备列表。
 * @return devices list json
 */
const getDevices = async (): Promise<string> => {
	return RNPepClickread.getDevices();
};

/**
 * @param userId            (用户id)
 * @param list(集合，里面放有设备id) ​​如List<String> list = new ArrayList<>();
 *                          list.add(dev_id_1); 解绑设备id为dev_id_1的设备
 *                          list.add(dev_id_2); 解绑设备id为dev_id_2的设备
 * @param promise           (回调接口)
 */
const removeDevices = async (userId: string, list: Array<string>): Promise<string> => {
	return RNPepClickread.removeDevices(userId, list);
};

/**
 * 获取书籍信息
 *
 * 根据book_id返回教材信息
 */
const getBookItemById = async (userId: string, bookId: string): Promise<TextbooksBean> => {
	return RNPepClickread.getBookItemById(userId, bookId);
};

/**
 * 获取书籍列表
 *
 * @param userId 用户id
 */
const getAllBookList = async (userId: string): Promise<TextbooksBean[]> => {
	return RNPepClickread.getAllBookList(userId);
};

/**
 * 打开课本
 *
 * @param textbooksBean          (BookList.TextbooksBean对象)
 * @param forExperience          是否为体验模式
 * @param num                    打开页码只有已购买状态才成能成功打开该页码
 */
const openBook = async (params: {
	textbooksBean: TextbooksBean,
	forExperience: boolean,
	num: number,
}): Promise<void> => {
	return RNPepClickread.openBook(params.textbooksBean, params.forExperience, params.num);
};

/**
 * 下载课本
 *
 * @param textbooksBean BookList.TextbooksBean对象
 */
const download = (textbooksBean: TextbooksBean) => {
	RNPepClickread.download(textbooksBean);
};

/**
 * 暂停下载
 *
 * @param bookId 书籍id
 */
const pauseDownloadOfBookID = (bookId: string) => {
	RNPepClickread.pauseDownloadOfBookID(bookId);
};

/**
 * 继续下载
 *
 * @param bookId 书籍id
 */
const continueDownloadOfBookID = (bookId: string) => {
	RNPepClickread.continueDownloadOfBookID(bookId);
};

/**
 * 删除课本
 *
 * @param bookId (教材id)
 * @return true：删除成功  false：删除失败
 */
const deleteBook = async (bookId: string): Promise<boolean> => {
	return RNPepClickread.deleteBook(bookId);
};

/**
 * 获取缓存大小
 *
 * @return cacheSize (缓存大小  单位为字节)
 */
const getCacheDirSize = async (): Promise<number> => {
	return RNPepClickread.getCacheDirSize();
};

/**
 * 清除缓存
 *
 * @return isClean是否清除成功
 */
const deleteCacheDir = async (): Promise<boolean> => {
	return RNPepClickread.deleteCacheDir();
};

/**
 * 判断书本是否已经下载
 *
 * @param textbooksBean
 */
const isDownloaded = async (textbooksBean: TextbooksBean): Promise<boolean> => {
	return RNPepClickread.isDownloaded(textbooksBean);
};

/**
 * 判断书本是否有更新
 *
 * @param textbooksBean (BookList.TextbooksBean对象)
 * @return true：有更新  false：没有更新
 */
const hasUpdate = async (textbooksBean: TextbooksBean): Promise<boolean> => {
	return RNPepClickread.hasUpdate(textbooksBean);
};

/**
 * 获取书本状态（是否已经订购）
 */
const hasBuy = async (bookId: string): Promise<boolean> => {
	return RNPepClickread.hasBuy(bookId);
};

/***
 * 获取目录
 *
 * @param bookId 书籍id
 * @return CatalogList json
 */
const getBookCatalogById = async (bookId: string): Promise<string> => {
	return RNPepClickread.getBookCatalogById(bookId);
};

const EVENT_PROGRESS = 'RNPepClickReadProgress';
const addProgressListener = (onProgress: (data: ProgressData) => void) => {
	DeviceEventEmitter.addListener(EVENT_PROGRESS, onProgress);
};

const removeProgressListener = (onProgress: (data: ProgressData) => void) => {
	DeviceEventEmitter.removeListener(EVENT_PROGRESS, onProgress);
};

export default {
	init,
	syncOrder,
	getDevices,
	removeDevices,
	getBookItemById,
	getAllBookList,
	openBook,
	download,
	pauseDownloadOfBookID,
	continueDownloadOfBookID,
	deleteBook,
	getCacheDirSize,
	deleteCacheDir,
	isDownloaded,
	hasUpdate,
	hasBuy,
	getBookCatalogById,
	addProgressListener,
	removeProgressListener,
};
