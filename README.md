StepView
===

StepView is a step indicator like this: 

![Capture](./images/1.png)

## How to use

**Gradle dependency**
Make sure `jcenter` was declared in your root build.gradle file:
```groovy
repositories {
    jcenter()
}
```

Then add this dependency, modify the version to the latest version.
```groovy
dependencies {
    compile 'com.githang:stepview:0.1'
}
```

**Layout Code**
```xml
    <com.githang.stepview.StepView
        android:id="@+id/step_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@android:color/white"
        android:paddingBottom="8dp"
        android:paddingTop="8dp"/>
```

**Java Code**
```java
        mStepView = (StepView) findViewById(R.id.step_view);
        List<String> steps = Arrays.asList(new String[]{"输入手机", "验证手机", "设置密码", "注册成功"});
        mStepView.setSteps(steps);
```

## Support attributes:
You can use these attributes to custom the StepView:

```
    <attr name="svCircleColor" format="color"/>
    <attr name="svTextColor" format="color"/>
    <attr name="svSelectedColor" format="color"/>
    <attr name="svFillRadius" format="dimension"/>
    <attr name="svStrokeWidth" format="dimension"/>
    <attr name="svLineWidth" format="dimension"/>
    <attr name="svTextSize" format="dimension"/>
    <attr name="svDrawablePadding" format="dimension"/>
```
