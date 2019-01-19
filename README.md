# Skin
网易云音乐换肤方案
高仿网易云音乐换肤，插件化下载皮肤包，无需Activity的重启直接实现无缝切换。
先看看效果图：
![device (2).gif](https://upload-images.jianshu.io/upload_images/5249989-a5231638fa29522b.gif?imageMogr2/auto-orient/strip)

由于gif大小有限制，可以扫描二维码，安装体验
![image.png](https://upload-images.jianshu.io/upload_images/5249989-d61e5905f91f7a62.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 先简单描述一下网易云音乐换肤的大体过程：
##### 1.先去下载一个主题包，这个主题包其实是一个apk文件，里面包含要换皮肤的资源文件
##### 2.通过AssetManager，通过反射以及PackageName，获取到下载下来皮肤包的Resource，拿到Resource对象，即可以拿到res下面的任何资源文件
##### 3.获取View的属性以及相关的资源文件名称，去皮肤包中的Resource中进行匹配，如果皮肤包中匹配成功，则应用皮肤包中的资源文件，实现换肤。

   
在详细实现之前，先介绍LayoutInflater，因为它在换肤中非常重要：它的作用是：将布局XML文件实例化为其对应的View对象。所以可以通过LayoutInflater获取到创建了多少View以及View对应的属性。

这里有鸿洋大佬的一篇关于介绍LayoutInflater 写的很详细，也非常全面https://blog.csdn.net/lmj623565791/article/details/51503977



## 1、通过反射获取过系统状态栏的高度
在开始之前，先举个例子，是否这样获取过系统状态栏的高度
```
  int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
  int dimensionPixelSize = getResources().getDimensionPixelSize(identifier);
  Log.d(TAG, "onCreate: " +dimensionPixelSize);
```
![image.png](https://upload-images.jianshu.io/upload_images/5249989-4d89058f19fa3429.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2、通过Resource获取系统任何资源文件
如果了解上面的代码是如何获取到系统的status_bar_height的高度，那么获取资源文件的drawable、color、dimen都是按照上面的方法获取到的。那么要实现换肤，只需要获取到Resource对象就可以。

换肤核心代码:
```
    //皮肤包apk的路径
    String apkPath = FileUtils.copyAssetsToCache(mContext, apkName);
    AssetManager manager = AssetManager.class.newInstance();
    Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
    method.invoke(manager,apkPath);

    //当前应用的resources对象，获取到屏幕相关的参数和配置
    Resources res = mContext.getResources();

    //getResources()方法通过  AssetManager的addAssetPath方法，构造出Resource对象，由于是Library层的代码，所以需要用到反射
    mSkinResources = new Resources(manager, res.getDisplayMetrics(), res.getConfiguration());
    int identifier = mSkinResources.getIdentifier("status_bar_height", "dimen", "android");

```
## 3、详细实现

要实现换肤，则需要知道当前页面中有多少View和属性需要替换掉，所以不能使用系统的LayoutInflate.Factory去创建View,而是使用自定义的LayoutInflate.Factory,这里使用SkinFactory来重写系统的LayoutInflate.Factor以及自实现View的创建，这里直接贴出代码：
```
    private View createView(String name, Context context, AttributeSet attributeSet) {
        View view = null;
        if (name.contains(".")) {  //view的名称中带点，说明是自定义View
            view = SkinUtils.crateView(name, context, attributeSet);
        } else {  //系统的View,直接通过反射创建
            for (int i = 0; i < sClassPrefixList.length; i++) { //比如android.widget.Button
                String viewName = sClassPrefixList[i] + name;
                view = SkinUtils.crateView(viewName, context, attributeSet);
                if (view != null) {
                    break;
                }
            }
        }
        return view;
    }
```
## 4、通过反射构造方法创建出View对象
SkinUtils中的crateView的实现
```
    public static View crateView(String name, Context context, AttributeSet attributeSet){
        View view=null;
        try {
            Class<?> aClass = context.getClassLoader().loadClass(name);
            Constructor<?> constructor = aClass.getConstructor(Context.class, AttributeSet.class);
            view = (View) constructor.newInstance(context, attributeSet);
        } catch (Exception e) {
            return  null;
        }
        return view;
    }
```

创建好View后，紧接着需要知道当前View的是否包含需要替换的属性，LayoutFctory中的onCreateView()方法中的AttributeSet中包含该View的所有的属性即资源值等相关信息。先打印出AttributeSet，你就会明白，如下所示:
```
 @Override
public View onCreateView(String name, Context context, AttributeSet attributeSet) {

//TODO 接管View的创建

 for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
            //获取到属性的name,value
            String attrName = attributeSet.getAttributeName(i);   //background
            String attrValue = attributeSet.getAttributeValue(i);  //@12346622

            if (attrValue.contains("@")) {
                int id = Integer.parseInt(attrValue.substring(1));   // 即R资源中对应的Id @12346622
                String typeName = context.getResources().getResourceTypeName(id);  //drawable
                String entryName = context.getResources().getResourceEntryName(id);  //bg
            }
  }
}
```
![image.png](https://upload-images.jianshu.io/upload_images/5249989-832979b92321c6fe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

通过如上方法获取到当前view的所有的属性的名称和资源值以及资源类型，但是这里是获取到的是该View所有的属性的名称和资源值，显然用不了这么多，所以需要过滤掉用不到属性(layout_width,layout_height等)，仔细观察，只有attrValue是@符号开头的，才是资源文件引用，所以过滤出有@符号的属性名和资源值

## 5、封装View的属性
使用SkinViewAttr来封装需要替换的资源，将创建的View，进行统计需要换肤的属性即 attrValue值中带有@符号，如下所示:
```
public class SkinViewAttr {
    /**
     * view对应的id
     */
    private int id;
    /**
     * 属相的name  backgroud
     */
    private String attrName;

    /**
     * 属性对应的值得name   eg: drawable color
     */
    private String typeName;

    /**
     * 对应drawable下面的文件的名称  //btn_bg
     */
    private String entryName;
    //省略get/set方法
    ......
}
```
当然这是一条属性，一个View对应很多的属性，所以使用SkinViewItem类，来记录View和属性的关系，即：
```
public class SkinViewItem {

    private View view;
    private ArrayList<SkinViewAttr> attrs;
   
    ......
}
```

到这里，在SkinFactory中得到了页面中的所有属性值中带@值得View及相关属性。完整代码:
 ```
package com.onexzgj.skin.skin;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

/**
 * 自定义View的填充factory
 *
 * @author onexzgj
 */
public class SkinFactory implements LayoutInflater.Factory {

    private AppCompatActivity mActivity;
    private ArrayList<SkinViewItem> viewItems = new ArrayList<>();

    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit.",
            "android.support.v7.widget."
    };

    public SkinFactory(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attributeSet) {
        View view = createView(name, context, attributeSet);
        if (view != null) {
            passSkinViewAttr(view, context, attributeSet);
        }
        return view;
    }

    /**
     * 开始换肤的方法
     */
    public void apply() {
        for (int i = 0; i < viewItems.size(); i++) {
            SkinViewItem skinViewItem = viewItems.get(i);
            skinViewItem.apply();
        }
    }


    /**
     * 2,解析反射生成的View的属性
     *
     * @param view
     * @param context
     * @param attributeSet android:background=@drawable/bg
     */
    private void passSkinViewAttr(View view, Context context, AttributeSet attributeSet) {
        ArrayList<SkinViewAttr> viewAttrs = new ArrayList<>();
        for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
            //获取到属性的name,value
            String attrName = attributeSet.getAttributeName(i);   //background
            String attrValue = attributeSet.getAttributeValue(i);  //@12346622

            if (attrValue.contains("@")) {
                int id = Integer.parseInt(attrValue.substring(1));   // 即R资源中对应的Id @12346622
                String typeName = context.getResources().getResourceTypeName(id);  //drawable
                String entryName = context.getResources().getResourceEntryName(id);  //bg
                SkinViewAttr attr = new SkinViewAttr(id, attrName, typeName, entryName);
                viewAttrs.add(attr);
            }

            if (!viewAttrs.isEmpty()) {
                //假如说属性不为空,将View即属性集合记录起来
                viewItems.add(new SkinViewItem(view, viewAttrs));
            }

        }
    }

    /**
     * 1,通过反射创建View
     *
     * @param name         view的名称
     * @param context
     * @param attributeSet view的属性
     * @return
     */
    private View createView(String name, Context context, AttributeSet attributeSet) {
        View view = null;
        if (name.contains(".")) {  //view的名称中带点，说明是自定义View
            view = SkinUtils.crateView(name, context, attributeSet);
        } else {  //系统的View,直接通过反射创建
            for (int i = 0; i < sClassPrefixList.length; i++) { //比如android.widget.Button
                String viewName = sClassPrefixList[i] + name;
                view = SkinUtils.crateView(viewName, context, attributeSet);
                if (view != null) {
                    break;
                }
            }
        }
        return view;
    }
}
```
得到了页面中所有要换肤的View以及属性，接下来使用SkinViewItem .apply()方法进行触发换肤,即开篇提到的，获取到皮肤包的Resource对象，通过比较属性值，获取到皮肤包相关资源，然后设置给View，这里只添加了2种实现，当然可以根据实际业务需要，支持更多属性的换肤。
```
    public void apply() {
        for (SkinViewAttr skinViewAttr: attrs) {
            if ("background".equals(skinViewAttr.getAttrName())){
                //如果是backgroud 改变属性
                if ("drawable".equals(skinViewAttr.getTypeName())){

                    view.setBackgroundDrawable(SkinManager.getInstance().getDrawable(skinViewAttr.getId()));

                }else if ("color".equals(skinViewAttr.getTypeName())){
                    view.setBackgroundColor(SkinManager.getInstance().getColor(skinViewAttr.getId()));
                }

            }else if ("textColor".equals(skinViewAttr.getAttrName()) && view instanceof TextView){
                //textview只改变字体
                ((TextView) view).setTextColor(SkinManager.getInstance().getColor(skinViewAttr.getId()));
            }
        }
    }
```
最后贴出SkinManager的实现,获取皮肤包资源文件以及相应的资源：
```
package com.onexzgj.skin.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.onexzgj.skin.utils.FileUtils;

import java.lang.reflect.Method;

public class SkinManager {
    private static final SkinManager ourInstance = new SkinManager();
    /**
     * 皮肤包的包名
     */
    private String mPackageName;

    public static SkinManager getInstance() {
        return ourInstance;
    }


    private Context mContext;

    public Resources mSkinResources;

    private String apkPath;

    private SkinManager() {
    }

    public void init(Context context){
        mContext=context.getApplicationContext();
    }


    public boolean loadSkin(String apkName){

        String apkPath = FileUtils.copyAssetsToCache(mContext, apkName);
        try {
            AssetManager manager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(manager,apkPath);

            //当前应用的resources对象，获取到屏幕相关的参数和配置
            Resources res = mContext.getResources();

            //getResources()方法通过  AssetManager的addAssetPath方法，构造出Resource对象，由于是Library层的代码，所以需要用到反射
            mSkinResources = new Resources(manager, res.getDisplayMetrics(), res.getConfiguration());

            mPackageName = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                    PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES).packageName;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //通过ID获取drawable对象
    public Drawable getDrawable(int id ){
        Drawable drawable = mContext.getResources().getDrawable(id);
        if (mSkinResources!=null){
            String name = mContext.getResources().getResourceEntryName(id);
            int resId = mSkinResources.getIdentifier(name, "drawable", mPackageName);
            if (resId>0){
                return mSkinResources.getDrawable(resId);
            }
        }
        return drawable ;
    }

    //通过ID获取颜色值
    public int getColor(int id ){
        int color = mContext.getResources().getColor(id);
        if (mSkinResources!=null){
            String name = mContext.getResources().getResourceEntryName(id);
            int resId = mSkinResources.getIdentifier(name, "color", mPackageName);
            if (resId>0){
                return mSkinResources.getColor(resId);
            }
        }
        return color ;
    }

    public int getColorPrimaryDark() {
        try {
            if (mSkinResources != null) {

                int identify = mSkinResources.getIdentifier("colorPrimaryDark", "color", mPackageName);
                return mSkinResources.getColor(identify);
            }
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return -1;
    }
}
```
## 6、项目地址及说明及存在问题

![image.png](https://upload-images.jianshu.io/upload_images/5249989-0a09a7350f9c252b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

Github地址: [https://github.com/OnexZgj/Skin](https://github.com/OnexZgj/Skin)


这里只是插件换肤的一个思想以及简单实现，需要优化的地方有很多，待后期更行

## 7、关于我:
- [CSDN](https://blog.csdn.net/qq_15988951)
- [简书](https://www.jianshu.com/u/a72b21d4d650)



























