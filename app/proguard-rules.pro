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

-keep class ch.dreipol.multiplatform.reduxsample.shared.delight.** { *; }
-keep class ch.dreipol.multiplatform.reduxsample.shared.network.dtos.** { *; }

# keep for AboutLibraries auto detection
-keep class .R
-keep class **.R$* {
    <fields>;
}

# kotlinx serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class ch.redcross.volunteers.transport.**$$serializer { *; }
-keep class ch.redcross.volunteers.transport.** {
    kotlinx.serialization.KSerializer serializer(...);
}
# Ktor
-keep class io.ktor.** { *; }
-keep class kotlinx.coroutines.** { *; }
-keep class kotlin.collections.** { *; }

-keep class kotlin.reflect.** { *; }
