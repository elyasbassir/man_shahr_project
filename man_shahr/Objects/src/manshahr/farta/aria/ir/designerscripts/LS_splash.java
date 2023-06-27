package manshahr.farta.aria.ir.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_splash{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panel1").vw.setLeft((int)(0d));
views.get("panel1").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("panel1").vw.setTop((int)(0d));
views.get("panel1").vw.setHeight((int)((100d / 100 * height) - (0d)));
views.get("splash").vw.setLeft((int)((25d / 100 * width)));
views.get("splash").vw.setWidth((int)((75d / 100 * width) - ((25d / 100 * width))));
views.get("splash").vw.setTop((int)((23d / 100 * height)));
views.get("splash").vw.setHeight((int)((47d / 100 * height) - ((23d / 100 * height))));
views.get("splash").vw.setHeight((int)((views.get("splash").vw.getWidth())));
views.get("text_splash").vw.setLeft((int)((26d / 100 * width)));
views.get("text_splash").vw.setWidth((int)((74d / 100 * width) - ((26d / 100 * width))));
views.get("text_splash").vw.setTop((int)((55d / 100 * height)));
views.get("text_splash").vw.setHeight((int)((65d / 100 * height) - ((55d / 100 * height))));

}
}