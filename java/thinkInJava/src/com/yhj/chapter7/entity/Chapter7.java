package com.yhj.chapter7.entity;

import java.util.Objects;

/**
 * @author : yhj
 * @version :  $
 * @date : Created in 2019/8/26 11:15
 * @description : 第七章 测试类
 * @modified By :
 */
public class Chapter7 {
    private String title;
    private String type;

    public Chapter7(String title, String type) {
        this.title = title;
        this.type = type;
    }


    public void finalMethodTest(final Chapter7 chapter7) {
        chapter7.setTitle("这是一个 final 的 title");
    }





    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }




}
