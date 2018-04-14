package com.yhj.object2map.com.yhj.launch;

import com.yhj.object2map.com.yhj.bean.User;
import com.yhj.object2map.com.yhj.util.Utils;

import java.util.Map;

public class Test {

    public static void main(String[] args) {
        User user = new User("YHJ",21);
        Map<String, Object> map = Utils.ObjectToMap(user);
        System.out.println(map);
    }
}
