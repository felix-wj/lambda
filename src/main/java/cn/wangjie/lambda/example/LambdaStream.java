package cn.wangjie.lambda.example;

import cn.wangjie.lambda.bean.Artist;
import cn.wangjie.lambda.bean.Track;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: lambda
 * @description: lambda表达式的流操作
 * @author: WangJie
 * @create: 2018-08-08 10:39
 **/
public class LambdaStream {

    public static void main(String[] args) {
        List<Artist> artists = new ArrayList<>(3);
        artists.add(new Artist("陈奕迅","China"));
        artists.add(new Artist("林宥嘉","China"));
        artists.add(new Artist("五月天","China"));

        //使用stream替代for循环，filter过滤，count统计,filter的形参是Predicate接口函数
        long count = artists.stream().filter(artist -> artist.getOrigin().equals("China")).count();
        System.out.println(count);

        //使用collect(toList())将流中的数据生成一个列表
        List<Integer> collected = Stream.of(1,2,3,4,5,6).filter(n -> n>3).collect(Collectors.toList());
        System.out.println(collected);

        //map的形参是Function接口函数
        List<String> strings1 = Stream.of("a","b","c","d").map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(strings1);

        //flatMap将多个流拼接成一个流
        List<String> strings2 = Stream.of("f","g").collect(Collectors.toList());
        List<String> strings3 = Stream.of(strings1,strings2).flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(strings3);

        //min max
        Track track = Stream.of(new Track("十年",240) ,
                new Track("温柔",270),
                new Track("山丘",300)).min(Comparator.comparing(Track::getTime)).get();
        System.out.println(track);
    }


}
