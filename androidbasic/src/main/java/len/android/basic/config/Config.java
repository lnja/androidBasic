package len.android.basic.config;

public class Config {

    public static final int PHOTO_COMPRESS_WIDTH = 720;
    public static final int PHOTO_COMPRESS_HEIGHT = 1280;
    public static final int PHOTO_COMPRESS_WIDTH_BIG = 1080;
    public static final int PHOTO_COMPRESS_HEIGHT_BIG = 1920;
    public static final int PHOTO_REQUST = 100;
    public static String APP_STORAGE_DIR = "len/android/basic";
    public static String APP_IMAGE_SAVE_DIR = APP_STORAGE_DIR + "/image";

    public static int LIST_REQ_PAGE_START_NUM = 1;

    public static void setAppStorageDir(String appStorageDir) {
        APP_STORAGE_DIR = appStorageDir;
    }

    public static void setAppImageSaveDir(String appImageSaveDir) {
        APP_IMAGE_SAVE_DIR = appImageSaveDir;
    }

    public static void setListReqPageStartNum(int listReqPageStartNum) {
        LIST_REQ_PAGE_START_NUM = listReqPageStartNum;
    }
}