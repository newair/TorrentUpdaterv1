package newair.org.torrentupdaterandfilter.RSS;

import android.content.Context;

import java.util.TimerTask;

public class ScheduleActivator extends TimerTask {

    static boolean firstTime = true;
    private Context context;

    public ScheduleActivator(Context context) {
        this.context = context;
    }

    @Override
    public void run() {

       /* RSSReaderEx reader = RSSReaderEx.getInstance();
        reader.setContext(context);
        reader.Readfeed();*/

    }

}