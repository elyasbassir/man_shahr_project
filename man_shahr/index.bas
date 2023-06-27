B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=11.5
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: false
	#IncludeTitle: false
	
#End Region



Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub


Sub Globals
	Dim anim As Animation
	Dim anim_hidden As Animation
	Dim anim_show As Animation
	Private slider As WebView
	Private Image_loading As ImageView
	Private loading As Panel
End Sub



Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("index")
	slider.LoadUrl("https://man-shahr.farta-aria.ir/slider/cd1d28dcc4617861e67b4f9dac096bbec895e4f7")
	slider.ZoomEnabled = False
	
	anim_hidden.InitializeAlpha("anim_hidden",1,0)
	anim_hidden.Duration = 600
	anim_hidden.RepeatCount=0
	
	anim.InitializeTranslate("anim",2%x,1%y,3%x,4%y)
	anim.Duration=1000
	anim.RepeatMode = anim.REPEAT_REVERSE
	anim.RepeatCount=-1
	anim.Start(Image_loading)
End Sub


Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub




Private Sub slider_PageFinished (Url As String)
	anim_hidden.Start(loading)
	anim_hidden.Start(Image_loading)
	Sleep(600)
	loading.Visible=False
	Image_loading.Visible=False
End Sub