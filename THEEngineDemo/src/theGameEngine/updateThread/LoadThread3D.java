package theGameEngine.updateThread;

import theGameEngine.controllers.Controller3D;
import theGameEngine.main.Main;
import android.os.AsyncTask;

public class LoadThread3D extends AsyncTask<Controller3D, Void, Controller3D>
{

	@Override
	protected Controller3D doInBackground(Controller3D... params)
	{
		params[0].loadInitialData();
		return params[0];
	}
	
    protected void onPostExecute(Controller3D result)
    {
    	Main.setNewScene3D(result);
    }

	
}
