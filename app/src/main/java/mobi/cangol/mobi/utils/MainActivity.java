package mobi.cangol.mobi.utils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import mobi.cangol.mobile.utils.LameUtils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView= (TextView) findViewById(R.id.textView);
        LameUtils lameUtils=new LameUtils();
        textView.setText("lame version: "+lameUtils.getVersion());
    }
}
