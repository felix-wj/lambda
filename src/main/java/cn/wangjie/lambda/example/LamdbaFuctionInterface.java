package cn.wangjie.lambda.example;

import cn.wangjie.lambda.bean.Track;

import java.util.function.*;

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

        //输入(T,T) ，返回T
        BinaryOperator<Long> add = (x, y) -> x+y;
        System.out.println(add.apply(1L,2L));

        /**
         * Supplier<T> , 无入参，返回T类型，可用于工厂方法,用法参见CompleteableFutureExample
         * 使用方式：第一步，构造一个方法，入参为Supplier函数,方法体中调用get()执行这个函数。同时也可以做其他处理
         *         第二步，在其他地方使用第一步构造的方法，入参传入自己定义的一个Supplier函数
         */



        //UnaryOperator<T> 输入T类型，返回T类型





    }
}
