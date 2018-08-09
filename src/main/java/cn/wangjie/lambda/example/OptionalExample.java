package cn.wangjie.lambda.example;

import cn.wangjie.lambda.bean.Track;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: lambda
 * @description:
 * @author: WangJie
 * @create: 2018-08-09 11:06
 **/
public class OptionalExample {
    public static void main(String[] args) {
        Optional<String> stringOptional = Optional.of("abc");
        System.out.println(stringOptional.get());
        Optional<String> t = Optional.empty();
        System.out.println(t.isPresent());
        //orElse()为空时返回一个自己填入的值
        //orElseGet()传入函数，为空时构造一个对象

        List<Track> trackList = Stream.of(new Track("十年",240) ,
                new Track("温柔",270),
                new Track("山丘",300)).collect(Collectors.toList());

        //min max 得到的是一个optional对象，调用他的get得到具体对象
        Track track = trackList.stream().min(Comparator.comparing(Track::getTime)).get();
        System.out.println(track);

        Optional<Track> trackOptional = trackList.stream().min(Comparator.comparing(Track::getTime));
        trackOptional.ifPresent(System.out::println);
        System.out.println(trackOptional.map(Track::getName).orElse("无"));




    }
}
