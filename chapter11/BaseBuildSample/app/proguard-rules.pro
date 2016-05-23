# 在这里添加项目的代码混淆规则
# 混淆规则请参考:http://proguard.sourceforge.net/index.html#manual/usage.html

##################### 一般使用默认 #####################

# 不使用大小写混合类名,混淆后的类名为小写
-dontusemixedcaseclassnames
# 混淆第三方库
-dontskipnonpubliclibraryclasses
# 混淆时记录日志,有助于排查错误
-verbose
# 代码混淆使用的算法.
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
# 代码混淆压缩比,值在0-7之间,默认为5.
-optimizationpasses 5
# 优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification

##################### 不混淆 #####################

# 这些类不混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v4.app.DialogFragment
-keep public class * extends com.actionbarsherlock.app.SherlockListFragment
-keep public class * extends com.actionbarsherlock.app.SherlockFragment
-keep public class * extends com.actionbarsherlock.app.SherlockFragmentActivity
-keep public class * extends android.app.Fragment
-keep public class com.android.vending.licensing.ILicensingService

# Native方法不混淆
-keepclasseswithmembernames class * {
 native <methods>;
}

# 自定义组件不混淆
-keep public class * extends android.view.View {
 public <init>(android.content.Context);
 public <init>(android.content.Context, android.util.AttributeSet);
 public <init>(android.content.Context, android.util.AttributeSet, int);
 public void set*(...);
}


# 自定义控件类和类的成员不混淆(所有指定的类和类成员是要存在)
-keepclasseswithmembers class * {
 public <init>(android.content.Context, android.util.AttributeSet);
}

# 同上
-keepclasseswithmembers class * {
 public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 自定义控件类不混淆
-keepclassmembers class * extends android.app.Activity {
 public void *(android.view.View);
}

# 枚举类不被混淆
-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}

# android.os.Parcelable的子类不混淆
-keep class * implements android.os.Parcelable {
 public static final android.os.Parcelable$Creator *;
}

# 资源类不混淆
-keepclassmembers class **.R$* {
 public static <fields>;
}

##################### 第三方库不混淆 #####################

# 保留第三方库android.support.v4不被混淆
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
# 打包时忽略警告
-dontwarn android.support.**

# 如果你的项目中使用了第三方库,需要参考官方文档的说明来进行混淆配置
# 例如: 百度地图的配置 参考:http://developer.baidu.com/map/sdkandev-question.htm
#-keep class com.baidu.** { *; }
#-keep class vi.com.gdi.bgl.android.**{*;}

# 例如: 支付宝的混淆 参考: https://doc.open.alipay.com/doc2/detail.htm?treeId=59&articleId=103683&docType=1
#-libraryjars libs/alipaySDK-20150602.jar
#
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IAlixPay$Stub{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
#-keep class com.alipay.sdk.app.PayTask{ public *;}
#-keep class com.alipay.sdk.app.AuthTask{ public *;}
