# RetainedHeadlessFragment
Android Retained Headless Fragment. Addressing stale activity reference issue when rotating device by retaining a headless fragment.

Reference: http://developer.android.com/intl/es/guide/topics/resources/runtime-changes.html

If restarting your activity requires that you recover large sets of data, re-establish a network connection, or perform other intensive operations, then a full restart due to a configuration change might be a slow user experience. Also, it might not be possible for you to completely restore your activity state with the Bundle that the system saves for you with the onSaveInstanceState() callback—it is not designed to carry large objects (such as bitmaps) and the data within it must be serialized then deserialized, which can consume a lot of memory and make the configuration change slow. In such a situation, you can alleviate the burden of reinitializing your activity by retaining a Fragment when your activity is restarted due to a configuration change. This fragment can contain references to stateful objects that you want to retain.

When the Android system shuts down your activity due to a configuration change, the fragments of your activity that you have marked to retain are not destroyed. You can add such fragments to your activity to preserve stateful objects.

To retain stateful objects in a fragment during a runtime configuration change:

    Extend the Fragment class and declare references to your stateful objects.
    Call setRetainInstance(boolean) when the fragment is created.
    Add the fragment to your activity.
    Use FragmentManager to retrieve the fragment when the activity is restarted.


Developed with Android Studio 1.5.1

 compileSdkVersion 23
 buildToolsVersion "23.0.2"
 minSdkVersion 15
 targetSdkVersion 23

Cheers!
