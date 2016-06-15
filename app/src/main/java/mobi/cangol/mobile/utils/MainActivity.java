package mobi.cangol.mobile.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    private static final String TAG="LAME";
    TextView textView;
    LameUtils lameUtils;
    File raw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= (TextView) findViewById(R.id.textView);
        lameUtils=new LameUtils();
        printLog("version="+lameUtils.getVersion());
        printLog("ABI="+lameUtils.getSystemABI());
        copyAssetsFileToAppData("test.raw",Environment.getExternalStorageDirectory().getPath()+ "/test.raw");
        raw=new File(Environment.getExternalStorageDirectory().getPath()+ "/test.raw");
        printLog("file="+raw.getAbsolutePath());
        printLog("exists="+raw.exists());
        AsyncTask<String,Void,Boolean> asyncTask=new AsyncTask<String,Void,Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                return lameUtils.raw2mp3(params[0],params[0].replace("raw","mp3"));
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                printLog("result="+result);
            }
        };
        asyncTask.execute(raw.getAbsolutePath());
    }

    void printLog(String str){
        Log.d(TAG,str);
        textView.append("\n"+str);
    }

    private void copyAssetsFileToAppData(String assetsPath, String savePath) {
        try {
            File dstFile = new File(savePath);
            if (!dstFile.getParentFile().exists()) {
                dstFile.getParentFile().mkdirs();
            }
            if (dstFile.exists())
                dstFile.delete();
            dstFile.createNewFile();
            int bytesum = 0;
            int byteread = 0;
            InputStream inStream = this.getResources().getAssets().open(assetsPath);
            FileOutputStream fs = new FileOutputStream(dstFile);
            byte[] buffer = new byte[1024];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
            Log.d(TAG, "copy " + assetsPath + " --> " + savePath);
        } catch (IOException e) {
            Log.e(TAG, "copy file error!");
            Log.e(TAG, "IOException", e);
        }
    }
}
