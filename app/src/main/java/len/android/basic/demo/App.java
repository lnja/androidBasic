package len.android.basic.demo;

import com.orhanobut.logger.*;
import len.android.basic.AppBase;
import len.tools.android.AndroidUtils;
import len.tools.android.Log;
import len.tools.android.StorageUtils;
import len.tools.android.extend.LnjaCsvFormatStrategy;

public class App extends AppBase {

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = AndroidUtils.getProcessName(this, android.os.Process.myPid());
        if (processName != null) {
            boolean defaultProcess = processName.equals(getPackageName());
            if (defaultProcess) {
                // TODO init default process here
                initLog();
            } else if (processName.endsWith(":other")) {
                // TODO init other process here
            }
        }
    }

    private void initLog(){
        if(BuildConfig.DEBUG){
            Log.init("lnja",android.util.Log.VERBOSE);
        }else {
            Log.init("lnja",android.util.Log.INFO);
        }

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("lnja")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                if(BuildConfig.DEBUG){
                    return true;
                }else {
                    if(priority < Logger.INFO){
                        return false;
                    }else {
                        return true;
                    }
                }
            }
        });
        FormatStrategy csvFormatStrategy = LnjaCsvFormatStrategy.newBuilder()
                .tag("lnja")
                .logPath(StorageUtils.getExtendDir(this,"logs").getAbsolutePath())
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(csvFormatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                if(BuildConfig.DEBUG){
                    return true;
                }else {
                    return false;
                }
            }
        });
    }
}
