package com.hariko.alg.greedy;

import java.util.*;

public class BroadDistribution {

    public static void main(String[] args) {
        //创建广播电台
        Map<String, HashSet<String>> broadMap = new HashMap<>();
        //将各个电台放入
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");
        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");
        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");
        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");
        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");

        broadMap.put("K1", hashSet1);
        broadMap.put("K2", hashSet2);
        broadMap.put("K3", hashSet3);
        broadMap.put("K4", hashSet4);
        broadMap.put("K5", hashSet5);

        HashSet<String> allAreaSet = new HashSet<>();
        allAreaSet.addAll(hashSet1);
        allAreaSet.addAll(hashSet2);
        allAreaSet.addAll(hashSet3);
        allAreaSet.addAll(hashSet4);
        allAreaSet.addAll(hashSet5);

        System.out.println("所有地区是: " + allAreaSet);


        //创建一个存放的电台集合
        List<String> selectBroadList = new ArrayList<>();

        //定义一个临时集合,保存电台覆盖地区和当前还澧覆盖地区的交集
        Set<String> mixedAreaSet = new HashSet<>();

        //能够覆盖最大地区时对应的电台,放入到selectBroadList
        String maxBroad = null;
        while (allAreaSet.size() != 0){
            maxBroad = null;

            //取出电台
            for(String broad : broadMap.keySet()){
                Set<String> areaSet = broadMap.get(broad);

                mixedAreaSet.clear();
                mixedAreaSet.addAll(areaSet);
                //求出交集,并把交集赋值给mixedAreaSet
                mixedAreaSet.retainAll(allAreaSet);

                //mixedAreaSet.size() > broadMap.get(maxBroad).size() 体现贪心算法,每次选择最大的
                if(mixedAreaSet.size() > 0 && (null == maxBroad || mixedAreaSet.size() > broadMap.get(maxBroad).size())){
                    maxBroad = broad;

                    broadMap.get(maxBroad).clear();
                    broadMap.get(maxBroad).addAll(mixedAreaSet);
                }
            }

            if(maxBroad != null){
                selectBroadList.add(maxBroad);
                //把已选择的电台的覆盖地区从所有地区集合中清楚
                allAreaSet.removeAll(broadMap.get(maxBroad));
            }
        }


        Set<String> broadList = broadMap.keySet();
        List<String> removeBroadList = new ArrayList<>();
        for(String broad : broadList){
            if(!selectBroadList.contains(broad)){
                removeBroadList.add(broad);
            }
        }

        for(String broad : removeBroadList){
            broadMap.remove(broad);
        }
        System.out.println("得到的选择电台结果是: " + selectBroadList);
        System.out.println("得到的电台及对应地区是: " + broadMap);
    }


}
