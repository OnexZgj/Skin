package com.onexzgj.skin.skin;

/**
 * view的属性bean
 * @author onexzgj
 */
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public SkinViewAttr(int id, String attrName, String typeName, String entryName) {
        this.id = id;
        this.attrName = attrName;
        this.typeName = typeName;
        this.entryName = entryName;
    }
}
