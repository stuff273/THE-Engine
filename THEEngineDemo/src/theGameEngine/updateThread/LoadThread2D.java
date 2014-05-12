package theGameEngine.updateThread;

import theGameEngine.controllers.Controller2D;
import theGameEngine.main.Main;
import android.os.AsyncTask;

public class LoadThread2D extends AsyncTask<Controller2D, Void, Controller2D>
{

	@Override
	protected Controller2D doInBackground(Controller2D... params)
	{
		params[0].loadInitialData();
		return params[0];
	}
	
    protected void onPostExecute(Controller2D result)
    {
    	Main.setNewScene2D(result);
    }

}
