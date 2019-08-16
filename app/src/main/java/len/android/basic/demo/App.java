package len.android.basic.demo;

import len.android.basic.AppBase;
import len.tools.android.AndroidUtils;
import len.tools.android.Log;

public class App extends AppBase {

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = AndroidUtils.getProcessName(this, android.os.Process.myPid());
        if (processName != null) {
            boolean defaultProcess = processName.equals(getPackageName());
            if (defaultProcess) {
                Log.enableLog(BuildConfig.DEBUG);
                // TODO init default process here
            } else if (processName.endsWith(":other")) {
                // TODO init other process here
            }
        }
    }
}
