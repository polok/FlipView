FlipView
========
Android FlipView UI library

ABOUT
------

If you have a need to have a UI component which will act similar to toggle button or be like a view group with state checked/unchecked this is a right place for you.


GRADLE
------

```xml
dependencies {
    compile 'com.github.polok.flipview:library:1.0.0'
}
```

USAGE
------

```xml
    <com.github.polok.flipview.FlipView
        android:id="@+id/flip_animation_view_demo"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:descendantFocusability="blocksDescendants"
        app:is_checked="true"
        app:flip_view_front_layout="@layout/flip_text_view_front_layout"
        app:flip_view_back_layout="@layout/flip_text_view_back_layout"
        app:show_animations="false" />
```

API
------

```java
    public static interface FlipViewChangeListener {
        void onFlipViewClick(FlipView flipView, boolean isChecked);
    }
```


