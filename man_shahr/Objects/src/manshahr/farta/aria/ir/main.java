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
public anywheresoftware.b4a.objects.ImageViewWrapper _gif_splash = null;
public anywheresoftware.b4a.agraham.gifdecoder.GifDecoder _gif = null;
public anywheresoftware.b4a.objects.Timer _timer = null;
public static String _gif_name = "";
public static int _frame = 0;
public anywheresoftware.b4j.object.JavaObject _jo = null;
public manshahr.farta.aria.ir.index _index = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (index.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 31;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 32;BA.debugLine="Activity.LoadLayout(\"splash\")";
mostCurrent._activity.LoadLayout("splash",mostCurrent.activityBA);
 //BA.debugLineNum = 33;BA.debugLine="jo.InitializeContext";
mostCurrent._jo.InitializeContext(processBA);
 //BA.debugLineNum = 34;BA.debugLine="jo.RunMethodJO(\"getWindow\", Null).RunMethod(\"setS";
mostCurrent._jo.RunMethodJO("getWindow",(Object[])(anywheresoftware.b4a.keywords.Common.Null)).RunMethod("setStatusBarColor",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (137),(int) (181),(int) (89)))});
 //BA.debugLineNum = 35;BA.debugLine="If File.Exists(File.DirInternal,gif_name) = False";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),mostCurrent._gif_name)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 36;BA.debugLine="File.Copy(File.DirAssets,gif_name,File.DirIntern";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._gif_name,anywheresoftware.b4a.keywords.Common.File.getDirInternal(),mostCurrent._gif_name);
 };
 //BA.debugLineNum = 38;BA.debugLine="timer.Initialize(\"timer\",0)";
mostCurrent._timer.Initialize(processBA,"timer",(long) (0));
 //BA.debugLineNum = 39;BA.debugLine="ShowGifAnimation1(gif_name)";
_showgifanimation1(mostCurrent._gif_name);
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 46;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 42;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 23;BA.debugLine="Private gif_splash As ImageView";
mostCurrent._gif_splash = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim gif As GifDecoder";
mostCurrent._gif = new anywheresoftware.b4a.agraham.gifdecoder.GifDecoder();
 //BA.debugLineNum = 25;BA.debugLine="Dim timer As Timer";
mostCurrent._timer = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 26;BA.debugLine="Dim gif_name As String = \"splash.gif\"";
mostCurrent._gif_name = "splash.gif";
 //BA.debugLineNum = 27;BA.debugLine="Dim Frame As Int";
_frame = 0;
 //BA.debugLineNum = 28;BA.debugLine="Dim jo As JavaObject";
mostCurrent._jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
index._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _showgifanimation1(String _filename) throws Exception{
 //BA.debugLineNum = 50;BA.debugLine="Sub ShowGifAnimation1(filename As String )";
 //BA.debugLineNum = 51;BA.debugLine="Try";
try { //BA.debugLineNum = 52;BA.debugLine="gif.DisposeFrames";
mostCurrent._gif.DisposeFrames();
 //BA.debugLineNum = 53;BA.debugLine="gif.Load(File.DirInternal, filename)";
mostCurrent._gif.Load(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),_filename);
 //BA.debugLineNum = 54;BA.debugLine="timer.Interval = gif.Delay(0)";
mostCurrent._timer.setInterval((long) (mostCurrent._gif.Delay((int) (0))));
 //BA.debugLineNum = 55;BA.debugLine="gif_splash.Bitmap = gif.Frame(0)";
mostCurrent._gif_splash.setBitmap(mostCurrent._gif.Frame((int) (0)));
 //BA.debugLineNum = 56;BA.debugLine="gif_splash.Gravity = Gravity.FILL";
mostCurrent._gif_splash.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 57;BA.debugLine="timer.Enabled = True";
mostCurrent._timer.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 } 
       catch (Exception e9) {
			processBA.setLastException(e9); //BA.debugLineNum = 59;BA.debugLine="Msgbox(LastException,\"خطا\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getObject()),BA.ObjectToCharSequence("خطا"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public static String  _timer_tick() throws Exception{
 //BA.debugLineNum = 63;BA.debugLine="Sub timer_Tick";
 //BA.debugLineNum = 64;BA.debugLine="Try";
try { //BA.debugLineNum = 65;BA.debugLine="timer.Enabled = False";
mostCurrent._timer.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 66;BA.debugLine="Frame = Frame + 1";
_frame = (int) (_frame+1);
 //BA.debugLineNum = 67;BA.debugLine="If Frame >= gif.FrameCount Then";
if (_frame>=mostCurrent._gif.getFrameCount()) { 
 //BA.debugLineNum = 68;BA.debugLine="timer.Enabled=False";
mostCurrent._timer.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 69;BA.debugLine="StartActivity(index)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._index.getObject()));
 //BA.debugLineNum = 70;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 72;BA.debugLine="timer.Interval = gif.Delay(Frame)";
mostCurrent._timer.setInterval((long) (mostCurrent._gif.Delay(_frame)));
 //BA.debugLineNum = 73;BA.debugLine="gif_splash.Bitmap = gif.Frame(Frame)";
mostCurrent._gif_splash.setBitmap(mostCurrent._gif.Frame(_frame));
 //BA.debugLineNum = 74;BA.debugLine="timer.Enabled = True";
mostCurrent._timer.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 } 
       catch (Exception e13) {
			processBA.setLastException(e13); //BA.debugLineNum = 76;BA.debugLine="timer.Enabled = False";
mostCurrent._timer.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 77;BA.debugLine="Msgbox(LastException,\"خطا\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getObject()),BA.ObjectToCharSequence("خطا"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
}
