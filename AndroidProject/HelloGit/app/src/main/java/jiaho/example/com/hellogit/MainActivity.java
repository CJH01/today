package jiaho.example.com.hellogit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MyService.startMyService(this);
        //在IntentService中执行耗时操作，并且执行多个任务
        MyIntentService.startActionFoo(this,"A","A");
        MyIntentService.startActionBaz(this,"B","B");
    }
}
