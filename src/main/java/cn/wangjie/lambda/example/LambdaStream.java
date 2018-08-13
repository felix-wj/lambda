package cn.wangjie.lambda.example;

import cn.wangjie.lambda.bean.Artist;
import cn.wangjie.lambda.bean.Track;
import cn.wangjie.lambda.util.StringCombiner;

import java.util.*;

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


        List<Track> trackList = Stream.of(new Track("十年",240) ,
                new Track("温柔",270),
                new Track("山丘",300)).collect(Collectors.toList());

        //min max 得到的是一个optional对象，调用他的get得到具体对象
        Track track = trackList.stream().min(Comparator.comparing(Track::getTime)).get();
        System.out.println(track);
        Optional<Track> trackOptional = trackList.stream().min(Comparator.comparing(Track::getTime));
        trackOptional.ifPresent(System.out::println);
        System.out.println(trackOptional.map(Track::getName).orElse("无"));


        IntSummaryStatistics trackTimeStats = trackList.stream().mapToInt(Track::getTime).summaryStatistics();
        System.out.printf("Max:%d ,Min:%d,Ave:%f,Sum:%d ,Total:%d \n",
                trackTimeStats.getMax(),
                trackTimeStats.getMin(),
                trackTimeStats.getAverage(),
                trackTimeStats.getSum(),
                trackTimeStats.getCount());

        /** reduce有三种
         *  1.无初始值，起始时，第一个参数为stream第一个值，第二个参数为stream第二个值，
         *    之后第一个参数为中间值，第二个参数为stream下一个值。
         *    返回数据为optional类型，因为stream可能为空。
         *  2.指定初始值，不会返回optional，因为已经指定了初始值
         *  3.自定义返回类型数据，
         *    第一个参数为要返回的数据类型的实例化参数，
         *    第二个参数是接口函数，什么累加逻辑(u,t)->u.apply(t)
         *    第三个参数是接口函数，组合器,如果进行并行操作，期间会得到多个中间值，需要指定合并逻辑
         *
         */
        Optional<Integer> optionalInteger = Stream.of(1,2,3,4,5,6).reduce((a,b)->a+b);
        System.out.println(optionalInteger.get());

        int sum = Stream.of(1,2,3,4,5,6).reduce(0,(a,b)->a+b);
        System.out.println(sum);

        /**
         *  这里如果使用parallel指定流为并行流，并行之后的数据出乎意料
         *  如果不指定并行，第三个函数几口将不会发挥作用
         */
        StringBuilder str = Stream.of("a","b","c","d").parallel().reduce(new StringBuilder(),StringBuilder::append,StringBuilder::append);
        System.out.println(str);
        str = Stream.of("a","b","c","d").reduce(new StringBuilder(),(builder,s)->{builder.append(s);return builder;},(left,right)->left.append(right));
        System.out.println(str);
        StringCombiner str1 = Stream.of("a","b","c","d").reduce(new StringCombiner(",","[","]"),StringCombiner::add,StringCombiner::merge);
        System.out.println(str1);
        int num = Stream.of(1,2,3,4,5,6).parallel().reduce(0,(x,y)->x+y,(x,y)->x+y);
        System.out.println(num);


    }


}
