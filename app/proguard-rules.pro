# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/code1_310170244/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


-dontnote **ILicensingService

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder* { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class android.support.v8.renderscript.** { *; }

-keepclasseswithmembers class * {
     public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
     public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep public class javax.net.ssl.**
-keepclassmembers public class javax.net.ssl.** {
   *;
}

-keepclassmembers class net.hockeyapp.android.UpdateFragment {
   *;
}

-keep class android.support.v4.** { *; }

##---------------Begin: proguard registration.configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

##---------------End: proguard registration.configuration for Gson  ----------

# Ignore google libs
-dontwarn com.google.**

