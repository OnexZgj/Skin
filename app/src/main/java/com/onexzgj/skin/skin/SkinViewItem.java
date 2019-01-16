package com.onexzgj.skin.skin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

/**
 * @author onexzgj
 * 要换肤的view以及属性集合
 */
public class SkinViewItem {

    private View view;
    private ArrayList<SkinViewAttr> attrs;

    public SkinViewItem(View view, ArrayList<SkinViewAttr> attrs) {
        this.view = view;
        this.attrs = attrs;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public ArrayList<SkinViewAttr> getAttrs() {
        return attrs;
    }

    public void setAttrs(ArrayList<SkinViewAttr> attrs) {
        this.attrs = attrs;
    }

    /**
     * view执行自己的换肤方法
     */
    public void apply() {

        for (SkinViewAttr skinViewAttr: attrs) {
            if ("background".equals(skinViewAttr.getAttrName()) ){
                //如果是backgroud 改变属性
                if ("drawable".equals(skinViewAttr.getTypeName())){

                    view.setBackgroundDrawable(SkinManager.getInstance().getDrawable(skinViewAttr.getId()));

                }else if ("color".equals(skinViewAttr.getTypeName())){
                    view.setBackgroundColor(SkinManager.getInstance().getColor(skinViewAttr.getId()));
                }

            }else if ("textColor".equals(skinViewAttr.getAttrName()) && view instanceof TextView){
                //textview只改变字体

                ((TextView) view).setTextColor(SkinManager.getInstance().getColor(skinViewAttr.getId()));
            }else if( "src".equals(skinViewAttr.getAttrName())){
                //如果是backgroud 改变属性
                if ("drawable".equals(skinViewAttr.getTypeName())){
                    if (view instanceof ImageView) {

//                        ((ImageView)view).setBackgroundDrawable(SkinManager.getInstance().getDrawable(skinViewAttr.getId()));
                        ((ImageView)view).setImageDrawable(SkinManager.getInstance().getDrawable(skinViewAttr.getId()));
                    }
                }else if ("color".equals(skinViewAttr.getTypeName())){
                    view.setBackgroundColor(SkinManager.getInstance().getColor(skinViewAttr.getId()));
                }
            }else if ("titleTextColor".equals(skinViewAttr.getTypeName())){
                if (view instanceof Toolbar){
                    ((Toolbar)view).setTitleTextColor(SkinManager.getInstance().getColor(skinViewAttr.getId()));
                }
            }else if("textColor".equals(skinViewAttr.getTypeName())){
                if (view instanceof TextView) {
                    ((TextView)view).setTextColor(SkinManager.getInstance().getColor(skinViewAttr.getId()));
                }
            }
        }
    }
}
