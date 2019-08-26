package com.yhj.chapter7;

import com.yhj.chapter7.entity.Chapter7;

/**
 * @author : yhj
 * @version :  $
 * @date : Created in 2019/8/26 11:13
 * @description : main 方法 final 参数测试
 * @modified By :
 */
public class Launch {

    public static void main(String[] args) {
        Chapter7 chapter7 = new Chapter7("第七章", "1");
        System.out.println(chapter7);

        chapter7.finalMethodTest(chapter7);


        System.out.println(chapter7);




    }




}
