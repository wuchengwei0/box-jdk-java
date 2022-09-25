package com.my;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: 吴成伟
 * @date: 2022/9/18 15:15
 * @Description: TODO
 */
public class Test {
    public static <T> List<Map<String,T>> cut(List<Map<String,T>> list,List<String> cols,boolean remove){
        System.out.println("1");
        String format = remove? "删除":"保留";
        return list.stream().map(map->{
            return map.entrySet().stream()
                    .filter(entry->{
                        String key = entry.getKey();
                        if( (remove^cols.contains(key))^false ){
                            System.out.println(format+",key="+key);
                            return true;
                        }else{
                            return false;
                        }
                    })
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue, (k1,k2)->k1,LinkedHashMap::new ));
        }).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Map<String,Object>> list = new ArrayList<>();
        HashMap<String, Object> map1 = new LinkedHashMap<String, Object>() {{
            put("1", new Object[3]);
            put("2", new Object[2]);
            put("3", new Object[1]);
        }};
        HashMap<String, Object> map2 = new HashMap<String, Object>() {{
            put("1", new byte[3]);
            put("2", new byte[2]);
            put("3", new byte[3]);
        }};
        HashMap<String, Object> map3 = new LinkedHashMap<String, Object>() {{
            put("1", new Double[3]);
            put("2", new Double[2]);
            put("3", new Double[3]);
        }};
        list.add(map1);
        list.add(map2);
        list.add(map3);
        List<String> cols = Arrays.asList("1", "3");
        List<Map<String, Object>> cut = cut(list, cols,false);
        System.out.println(cut);
    }
}
