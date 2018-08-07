package cn.wangjie.lambda.example;

import cn.wangjie.lambda.bean.Track;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @program: lambda
 * @description: lambda的几个重要的函数接口
 * @author: WangJie
 * @create: 2018-08-07 19:34
 **/
public class LamdbaFuctionInterface {
    public static void main(String[] args) {
        Track track = new Track();
        track.setName("十年");

        //返回一个boolean值
        Predicate<Track> trackPredicate = (t) -> t.getName().equals("十年");
        System.out.println(trackPredicate.test(track));

        //返回void
        Consumer<Track> trackConsumer = t-> System.out.println(t.getName());
        trackConsumer.accept(track);

        //输入T类型，返回R类型
        Function<Track,String> trackStringFunction = t -> "这支曲目名叫："+t.getName();
        System.out.println(trackStringFunction.apply(track));





    }
}
