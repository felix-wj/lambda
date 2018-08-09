package cn.wangjie.lambda.example;

import cn.wangjie.lambda.bean.Track;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: lambda
 * @description: 收集器
 * @author: WangJie
 * @create: 2018-08-09 13:32
 **/
public class LambdaCollect {
    public static void main(String[] args) {
        List<Track> trackList = Stream.of(new Track("十年",240) ,
                new Track("温柔",270),
                new Track("山丘",300)).collect(Collectors.toList());

        //平均 averaging
        double avgTime = trackList.stream().collect(Collectors.averagingDouble(Track::getTime));

        //分块 partitioningBy
        Map<Boolean,List<Track>> map1 = trackList.stream().collect(Collectors.partitioningBy(track->track.getTime()>280));
        System.out.println(map1);


        //分组 groupingBy
        Map<String,List<Track>> map2 = trackList.stream().collect(Collectors.groupingBy(Track::getName));
        System.out.println(map2);

        //字符处理
        String songNames = trackList.stream().map(Track::getName).collect(Collectors.joining(",","[","]"));
        System.out.println(songNames);

    }
}
