package com.yhj.chapter16.ParamArray;

import java.util.Arrays;

public class ParameterArrayMethod {
    public static void main(String[] args) {
        Integer[] doubles = {1,2,3,4,5,6,7,};
        Arrays.sort(doubles);
    //    Double[] doubles2 = MethodParameter.f(doubles); // illegal  不能使用参数化方法来转型数组
        System.out.println(doubles);
    }
}
