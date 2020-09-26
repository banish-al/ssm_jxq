package com.zilong.vo.systemVo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 表名 :  menu<br/>
 */
@JsonIgnoreProperties(value = "handler")
public class Menu implements Serializable {

    private Integer id;
    private String text;
    private String url;
    private String image;
    private String state;
    private Integer parentid;

    //子菜单集合
    private List<Menu> children = new ArrayList<Menu>();
    //子节点选中状态
    private boolean checked;

    public Menu() {
        super();
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Menu(Integer id, String text, String url, String image, String state, Integer parentid) {
        this.id = id;
        this.text = text;
        this.url = url;
        this.image = image;
        this.state = state;
        this.parentid = parentid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getParentid() {
        return parentid;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", state='" + state + '\'' +
                ", parentid=" + parentid +
                ", children=" + children +
                ", checked=" + checked +
                '}';
    }
}

