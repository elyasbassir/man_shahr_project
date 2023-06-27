package manshahr.farta.aria.ir.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_index{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[index/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 4;BA.debugLine="header.SetLeftAndRight(0%x,100%x)"[index/General script]
views.get("header").vw.setLeft((int)((0d / 100 * width)));
views.get("header").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
//BA.debugLineNum = 5;BA.debugLine="header.Height = 50dip"[index/General script]
views.get("header").vw.setHeight((int)((50d * scale)));
//BA.debugLineNum = 7;BA.debugLine="slider.SetLeftAndRight(0%x,100%x)"[index/General script]
views.get("slider").vw.setLeft((int)((0d / 100 * width)));
views.get("slider").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
//BA.debugLineNum = 8;BA.debugLine="slider.SetTopAndBottom(7%y,35%y)"[index/General script]
views.get("slider").vw.setTop((int)((7d / 100 * height)));
views.get("slider").vw.setHeight((int)((35d / 100 * height) - ((7d / 100 * height))));
//BA.debugLineNum = 10;BA.debugLine="items.SetLeftAndRight(0%x,100%x)"[index/General script]
views.get("items").vw.setLeft((int)((0d / 100 * width)));
views.get("items").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
//BA.debugLineNum = 11;BA.debugLine="items.SetTopAndBottom(35%y,100%y)"[index/General script]
views.get("items").vw.setTop((int)((35d / 100 * height)));
views.get("items").vw.setHeight((int)((100d / 100 * height) - ((35d / 100 * height))));
//BA.debugLineNum = 13;BA.debugLine="logo.SetLeftAndRight(44%x,56%x)"[index/General script]
views.get("logo").vw.setLeft((int)((44d / 100 * width)));
views.get("logo").vw.setWidth((int)((56d / 100 * width) - ((44d / 100 * width))));
//BA.debugLineNum = 14;BA.debugLine="logo.Height = 45dip"[index/General script]
views.get("logo").vw.setHeight((int)((45d * scale)));
//BA.debugLineNum = 15;BA.debugLine="logo.Top = 2.5dip"[index/General script]
views.get("logo").vw.setTop((int)((2.5d * scale)));
//BA.debugLineNum = 16;BA.debugLine="logo.Width =45dip"[index/General script]
views.get("logo").vw.setWidth((int)((45d * scale)));
//BA.debugLineNum = 21;BA.debugLine="city_name.SetLeftAndRight(56%x,97%x)"[index/General script]
views.get("city_name").vw.setLeft((int)((56d / 100 * width)));
views.get("city_name").vw.setWidth((int)((97d / 100 * width) - ((56d / 100 * width))));
//BA.debugLineNum = 22;BA.debugLine="city_name.Height=50dip"[index/General script]
views.get("city_name").vw.setHeight((int)((50d * scale)));
//BA.debugLineNum = 24;BA.debugLine="account.SetLeftAndRight(3%x,42%x)"[index/General script]
views.get("account").vw.setLeft((int)((3d / 100 * width)));
views.get("account").vw.setWidth((int)((42d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 25;BA.debugLine="account.Height=50dip"[index/General script]
views.get("account").vw.setHeight((int)((50d * scale)));
//BA.debugLineNum = 28;BA.debugLine="loading.SetLeftAndRight(0%x,100%x)"[index/General script]
views.get("loading").vw.setLeft((int)((0d / 100 * width)));
views.get("loading").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
//BA.debugLineNum = 29;BA.debugLine="loading.SetTopAndBottom(0%y,100%y)"[index/General script]
views.get("loading").vw.setTop((int)((0d / 100 * height)));
views.get("loading").vw.setHeight((int)((100d / 100 * height) - ((0d / 100 * height))));
//BA.debugLineNum = 32;BA.debugLine="Image_loading.SetLeftAndRight(20%x,80%x)"[index/General script]
views.get("image_loading").vw.setLeft((int)((20d / 100 * width)));
views.get("image_loading").vw.setWidth((int)((80d / 100 * width) - ((20d / 100 * width))));
//BA.debugLineNum = 33;BA.debugLine="Image_loading.SetTopAndBottom(30%y,70%y)"[index/General script]
views.get("image_loading").vw.setTop((int)((30d / 100 * height)));
views.get("image_loading").vw.setHeight((int)((70d / 100 * height) - ((30d / 100 * height))));

}
}