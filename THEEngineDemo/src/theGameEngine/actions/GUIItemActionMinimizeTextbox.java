package theGameEngine.actions;

import theGameEngine.items.items2D.textboxes.Textbox;

public class GUIItemActionMinimizeTextbox extends GUIItemAction
{
		
	Textbox textbox;
	
	float minimizePositionX;
	float minimizePositionY;
	float minimizeScale;
	float minimizeSpeed;
	
	public GUIItemActionMinimizeTextbox(Textbox textbox, float minimizePositionX, float minimizePositionY, float minimizeScale, float minimizeSpeed) 
	{
		this.textbox = textbox;
		
		this.minimizePositionX = minimizePositionX;
		this.minimizePositionY = minimizePositionY;
		this.minimizeScale = minimizeScale;
		this.minimizeSpeed = minimizeSpeed;
	}

	@Override
	public void perform() 
	{
		if (textbox.getResizing())
			return;
		
		if (!textbox.getResized())
			textbox.setResizing(minimizePositionX,minimizePositionY,minimizeScale,minimizeSpeed);

		
		if (textbox.getResized())
			textbox.setRestore();
	}

}
