# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class com.startapp.** {
      *;
}

-keep class com.truenet.** {
      *;
}

-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,LineNumberTable, *Annotation*, EnclosingMethod
-dontwarn android.webkit.JavascriptInterface
-dontwarn com.startapp.**

-dontwarn org.jetbrains.annotations.**
-keep class com.aviary.android.feather.headless.AviaryEffect
-keep class com.aviary.android.feather.headless.media.ExifInterfaceWrapper
-keep class com.aviary.android.feather.headless.moa.Moa
-keep class com.aviary.android.feather.headless.moa.MoaResult
-keep class com.aviary.android.feather.headless.moa.MoaHD
-keep class com.aviary.android.feather.headless.filters.NativeFilterProxy
-keep class com.aviary.android.feather.headless.utils.CameraUtils
-keep class com.aviary.android.feather.headless.gl.GLUtils

-keep class com.aviary.android.feather.opengl.AviaryGLSurfaceView
-keep class com.aviary.android.feather.widget.ScrollerRunnable
-keep class com.aviary.android.feather.library.services.BaseContextService
-keep class it.sephiroth.android.library.imagezoom.easing.Easing
-keep class com.aviary.android.feather.library.external.tracking.TrackerFactory
-keep class com.aviary.android.feather.library.tracking.AbstractTracker

-keep interface com.aviary.android.feather.library.services.IAviaryController
-keep interface com.aviary.android.feather.headless.filters.IFilter
-keep interface com.aviary.android.feather.widget.ScrollerRunnable$ScrollableView

-keep class * extends com.aviary.android.feather.library.tracking.AbstractTracker
-keep class * extends com.aviary.android.feather.headless.filters.IFilter
-keep class * extends com.aviary.android.feather.headless.filters.INativeFilter
-keep class * implements com.aviary.android.feather.library.services.IAviaryController
-keep class * extends com.aviary.android.feather.library.services.BaseContextService
-keep class * implements com.aviary.android.feather.widget.ScrollerRunnable$ScrollableView { *; }
-keep class * implements java.lang.Runnable

-keep class it.sephiroth.android.library.imagezoom.ImageViewTouchBase
-keep class * extends it.sephiroth.android.library.imagezoom.ImageViewTouchBase

-keepclasseswithmembers class * {
	protected <init>( com.aviary.android.feather.library.services.BaseContextService );
}

-keepclasseswithmembers class * {
	public <init>( com.aviary.android.feather.library.services.IAviaryController );
}

-keepclassmembers class com.aviary.android.feather.library.tracking.AbstractTracker { *; }

-keepclassmembers interface it.sephiroth.android.library.imagezoom.easing.Easing { *; }

-keepclassmembers class com.aviary.android.feather.library.external.tracking.TrackerFactory { *; }

-keepclassmembers class * implements it.sephiroth.android.library.imagezoom.easing.Easing { *; }

# This class should not be obfuscated at all
-keepclassmembers class com.aviary.android.feather.headless.moa.MoaResult {
	public java.lang.String inputString;
	public android.graphics.Bitmap inputBitmap;
	public java.lang.String outputString;
	public android.graphics.Bitmap outputBitmap;
	public volatile int active;
	public void cancel();
	public synchronized void execute();
}

# Keep all classes with a native method

-keepclassmembers class * {
   public static native <methods>;
   private static native <methods>;
   static native <methods>;
   private native <methods>;
   native <methods>;
}

-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**