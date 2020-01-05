package work;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AsList {
    public static void main(String[] args) {
        String[] s = {"110","120","130"};
        List<String> array = Arrays.asList(s);//底层还是数组，不能使用集合的方法
        List<String> list = new ArrayList<>(Arrays.asList(s));//这才真正转成了list集合
        System.out.println(list.size());
    }
}
