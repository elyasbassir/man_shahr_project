package manshahr.farta.aria.ir.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_splash{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[splash/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 5;BA.debugLine="Panel1.SetLeftAndRight(0,100%x)"[splash/General script]
views.get("panel1").vw.setLeft((int)(0d));
views.get("panel1").vw.setWidth((int)((100d / 100 * width) - (0d)));
//BA.debugLineNum = 6;BA.debugLine="Panel1.SetTopAndBottom(0,100%y)"[splash/General script]
views.get("panel1").vw.setTop((int)(0d));
views.get("panel1").vw.setHeight((int)((100d / 100 * height) - (0d)));
//BA.debugLineNum = 8;BA.debugLine="gif_splash.SetLeftAndRight(10%x,90%x)"[splash/General script]
views.get("gif_splash").vw.setLeft((int)((10d / 100 * width)));
views.get("gif_splash").vw.setWidth((int)((90d / 100 * width) - ((10d / 100 * width))));
//BA.debugLineNum = 9;BA.debugLine="gif_splash.SetTopAndBottom(10%y,90%y)"[splash/General script]
views.get("gif_splash").vw.setTop((int)((10d / 100 * height)));
views.get("gif_splash").vw.setHeight((int)((90d / 100 * height) - ((10d / 100 * height))));

}
}