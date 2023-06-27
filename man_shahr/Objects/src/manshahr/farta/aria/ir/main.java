package manshahr.farta.aria.ir;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "manshahr.farta.aria.ir", "manshahr.farta.aria.ir.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, true))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "manshahr.farta.aria.ir", "manshahr.farta.aria.ir.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "manshahr.farta.aria.ir.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (main) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.Timer _timer = null;
public anywheresoftware.b4j.object.JavaObject _jo = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _splash = null;
public anywheresoftware.b4a.objects.AnimationWrapper _anim = null;
public anywheresoftware.b4a.objects.AnimationWrapper _anim_move = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _text_splash = null;
public manshahr.farta.aria.ir.index _index = null;
public manshahr.farta.aria.ir.animactivity _animactivity = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (index.mostCurrent != null);
return vis;}
public static void  _activity_create(boolean _firsttime) throws Exception{
ResumableSub_Activity_Create rsub = new ResumableSub_Activity_Create(null,_firsttime);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_Create extends BA.ResumableSub {
public ResumableSub_Activity_Create(manshahr.farta.aria.ir.main parent,boolean _firsttime) {
this.parent = parent;
this._firsttime = _firsttime;
}
manshahr.farta.aria.ir.main parent;
boolean _firsttime;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = -1;
 //BA.debugLineNum = 38;BA.debugLine="timer.Initialize(\"timer\",2300)";
parent.mostCurrent._timer.Initialize(processBA,"timer",(long) (2300));
 //BA.debugLineNum = 39;BA.debugLine="timer.Enabled=True";
parent.mostCurrent._timer.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 41;BA.debugLine="Activity.LoadLayout(\"splash\")";
parent.mostCurrent._activity.LoadLayout("splash",mostCurrent.activityBA);
 //BA.debugLineNum = 43;BA.debugLine="anim_move.InitializeTranslate(\"anim\",0,15%y,0,0)";
parent.mostCurrent._anim_move.InitializeTranslate(mostCurrent.activityBA,"anim",(float) (0),(float) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (15),mostCurrent.activityBA)),(float) (0),(float) (0));
 //BA.debugLineNum = 44;BA.debugLine="anim_move.Duration = 400";
parent.mostCurrent._anim_move.setDuration((long) (400));
 //BA.debugLineNum = 45;BA.debugLine="anim_move.RepeatCount = 0";
parent.mostCurrent._anim_move.setRepeatCount((int) (0));
 //BA.debugLineNum = 46;BA.debugLine="anim_move.Start(splash)";
parent.mostCurrent._anim_move.Start((android.view.View)(parent.mostCurrent._splash.getObject()));
 //BA.debugLineNum = 47;BA.debugLine="text_splash.Visible= False";
parent.mostCurrent._text_splash.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 48;BA.debugLine="Sleep(400)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (400));
this.state = 1;
return;
case 1:
//C
this.state = -1;
;
 //BA.debugLineNum = 49;BA.debugLine="anim.InitializeAlpha(\"anim\",0,1)";
parent.mostCurrent._anim.InitializeAlpha(mostCurrent.activityBA,"anim",(float) (0),(float) (1));
 //BA.debugLineNum = 50;BA.debugLine="anim.Duration = 500";
parent.mostCurrent._anim.setDuration((long) (500));
 //BA.debugLineNum = 51;BA.debugLine="anim.RepeatCount = 0";
parent.mostCurrent._anim.setRepeatCount((int) (0));
 //BA.debugLineNum = 52;BA.debugLine="anim.Start(text_splash)";
parent.mostCurrent._anim.Start((android.view.View)(parent.mostCurrent._text_splash.getObject()));
 //BA.debugLineNum = 53;BA.debugLine="text_splash.Visible= True";
parent.mostCurrent._text_splash.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 55;BA.debugLine="jo.InitializeContext";
parent.mostCurrent._jo.InitializeContext(processBA);
 //BA.debugLineNum = 56;BA.debugLine="jo.RunMethodJO(\"getWindow\", Null).RunMethod(\"setS";
parent.mostCurrent._jo.RunMethodJO("getWindow",(Object[])(anywheresoftware.b4a.keywords.Common.Null)).RunMethod("setStatusBarColor",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (137),(int) (181),(int) (89)))});
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 72;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public static String  _anim_animationend() throws Exception{
 //BA.debugLineNum = 59;BA.debugLine="Sub anim_AnimationEnd";
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 26;BA.debugLine="Dim timer As Timer";
mostCurrent._timer = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 27;BA.debugLine="Dim jo As JavaObject";
mostCurrent._jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 28;BA.debugLine="Private splash As ImageView";
mostCurrent._splash = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim anim As Animation";
mostCurrent._anim = new anywheresoftware.b4a.objects.AnimationWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim anim_move As Animation";
mostCurrent._anim_move = new anywheresoftware.b4a.objects.AnimationWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private text_splash As ImageView";
mostCurrent._text_splash = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
index._process_globals();
animactivity._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static String  _splash_click() throws Exception{
 //BA.debugLineNum = 82;BA.debugLine="Private Sub splash_Click";
 //BA.debugLineNum = 83;BA.debugLine="anim.Start(splash)";
mostCurrent._anim.Start((android.view.View)(mostCurrent._splash.getObject()));
 //BA.debugLineNum = 84;BA.debugLine="anim.Start(text_splash)";
mostCurrent._anim.Start((android.view.View)(mostCurrent._text_splash.getObject()));
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static String  _timer_tick() throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Sub timer_Tick";
 //BA.debugLineNum = 77;BA.debugLine="StartActivity(index)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._index.getObject()));
 //BA.debugLineNum = 78;BA.debugLine="AnimActivity.setAnimActivity(\"fade1\", \"fade2\")";
mostCurrent._animactivity._setanimactivity /*String*/ (mostCurrent.activityBA,"fade1","fade2");
 //BA.debugLineNum = 79;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
}
