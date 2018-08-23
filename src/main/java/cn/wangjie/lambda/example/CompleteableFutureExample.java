package cn.wangjie.lambda.example;


import cn.wangjie.lambda.bean.Discount;
import cn.wangjie.lambda.bean.Quote;
import cn.wangjie.lambda.bean.Shop;
import cn.wangjie.lambda.util.ThreadPoolUtil;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: lambda
 * @description: 异步操作
 * @author: WangJie
 * @create: 2018-08-13 11:35
 **/
public class CompleteableFutureExample {
    static List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("大润发"),
            new Shop("联华"),
            new Shop("华联"),
            new Shop("沃尔玛"),
            new Shop("物美")

    );

    /**
     * 串行
     */
    @Test
    public void test1() {
        Long start = System.currentTimeMillis();
        List<String> prices = shops.stream().map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice("peach"))).collect(Collectors.toList());
        Long end = System.currentTimeMillis();
        System.out.println(prices);
        System.out.println("用时：" + (end - start));
    }

    /**
     * 并行
     */
    @Test
    public void test2() {
        Long start = System.currentTimeMillis();
        List<String> prices = shops.stream().parallel().map(shop -> shop.getPrice("peach")).collect(Collectors.toList());
        Long end = System.currentTimeMillis();
        System.out.println(prices);
        System.out.println("用时：" + (end - start));
    }

    /**
     * 使用CompleteableFuture
     */
    @Test
    public void test3() {
        long start = System.currentTimeMillis();
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(
                        shop -> CompletableFuture.supplyAsync(
                                () -> shop.getPrice("peach")
                        )
                )
                .collect(Collectors.toList());
        List<String> prices = priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        System.out.println(prices);
        System.out.println("用时：" + (end - start));
    }

    /**
     * 并行加并发
     * 本电脑为八核,并行处理数量超过8时，其他任务会排队等待CPU将之前的任务处理完成
     * 此时需要创建线程，并发执行
     */
    @Test
    public void test4() {
        long start = System.currentTimeMillis();
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(
                        shop -> CompletableFuture.supplyAsync(
                                () -> shop.getPrice("peach"), ThreadPoolUtil.getExecutorService()
                        )
                )
                .collect(Collectors.toList());
        List<String> prices = priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        System.out.println(prices);
        System.out.println("用时：" + (end - start));
    }

    /**
     * 多个任串行处理
     */
    @Test
    public void test5() {
        excute(() -> shops.stream()
                .map(shop -> shop.getPrice("peach"))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList()));
    }

    public <T> void excute(Supplier<List<T>> supplier) {
        long start = System.currentTimeMillis();
        List<T> prices = supplier.get();
        long end = System.currentTimeMillis();
        System.out.println(prices);
        System.out.println("用时：" + (end - start));
    }

    /**
     * thenApply与thenCompose
     * 多任务的同步和异步操作
     * 此处进行另一个异步任务时使用thenCompose而不是thenCompose是因为第一个任务在获取价格，
     * 即构造完future后没有再做其他操作，可以由这个线程继续处理折扣计算。
     * 这与另起一个线程进行价格计算是一样的，甚至另起一个会增加线程转换的开销
     */
    @Test
    public void test6() {
        Supplier<List<String>> method = () -> {
            List<CompletableFuture<String>> priceFutures = shops.stream()
                    .map(
                            shop -> CompletableFuture.supplyAsync(
                                    () -> shop.getPrice("peach"), ThreadPoolUtil.getExecutorService())
                    )
                    .map(future -> future.thenApply(Quote::parse))
                    .map(
                            future -> future.thenCompose(
                                    quote -> CompletableFuture.supplyAsync(
                                            () -> Discount.applyDiscount(quote), ThreadPoolUtil.getExecutorService()
                                    )
                            )
                    ).collect(Collectors.toList());
            return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        };
        excute(method);
    }

    /**
     * test6的另一种写法
     */
    @Test
    public void test7() {
        Supplier<List<String>> method = () -> {
            List<CompletableFuture<String>> priceFutures = shops.stream()
                    .map(
                            shop -> CompletableFuture.supplyAsync(
                                    () -> shop.getPrice("peach"), ThreadPoolUtil.getExecutorService())
                                    .thenApply(Quote::parse)
                                    .thenCompose(
                                            quote -> CompletableFuture.supplyAsync(
                                                    () -> Discount.applyDiscount(quote), ThreadPoolUtil.getExecutorService()
                                            )
                                    )
                    )
                    .collect(Collectors.toList());
            return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        };
        excute(method);
    }

    /**
     * thenCombine
     * 合并两个独立的CompleteableFuture
     * thenCombine的第一个参数为另一个CompleteableFut
     * 第二个参数为BiFunction，定义合并逻辑的Function接口函数
     */
    @Test
    public void test8() {
        Supplier<List<Double>> method = () -> {
            List<CompletableFuture<Double>> priceFutures = shops.stream()
                    .map(
                            shop -> CompletableFuture.supplyAsync(
                                    () -> shop.getPrice("peach"), ThreadPoolUtil.getExecutorService()
                            )
                                    .thenApply(Quote::parse)
                                    .thenCombine(
                                            CompletableFuture.supplyAsync(Shop::getpriceExchangeRate),
                                            (quote, rate) -> quote.getPrice() * rate

                                    )
                    )
                    .collect(Collectors.toList());
            return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());


        };
        excute(method);

    }

    /**
     * 将CompleteableFuture<List<Object>对象中的list转成流，操作每一个元素
     */
    @Test
    public void test9() {
        CompletableFuture<List<List<String>>> future = CompletableFuture.supplyAsync(() -> Arrays.asList(Arrays.asList("a", "b"), Arrays.asList("c", "d")));
        System.out.println(future.thenApply(t -> Stream.of(t).flatMap(Collection::stream)).thenApply(stream -> stream.flatMap(Collection::stream)).join().collect(Collectors.toList()));
        System.out.println(future.thenApply(t -> t.stream().flatMap(Collection::stream)).join().collect(Collectors.toList()));
        future.thenApply(Collection::stream).thenApply(listStream -> listStream.flatMap(Collection::stream)).thenAccept(stringStream -> stringStream.forEach(System.out::println));
        //List<String> strings =future.join().stream().flatMap(t->t.stream()).collect(Collectors.toList());
        // System.out.println(strings);
    }

    @Test
    public void test10() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<Void> f = future.thenAcceptBoth(CompletableFuture.completedFuture(10), (x, y) -> System.out.println(x * y));
        System.out.println(f.getNow(null));
        CompletableFuture.supplyAsync(() -> Shop.getlist()).thenApply(list -> {
            list.add("f");
            return list;
        }).thenAccept(System.out::println).join();


    }

    /**
     * 异常处理
     */
    @Test
    public void test11 (){
        CompletableFuture.supplyAsync(()->{throw new RuntimeException("test");}).handle((result,ex)->{
            if (ex!=null){
                return null;
            }
            return result;
        }).join();
        CompletableFuture.supplyAsync(()->{throw new RuntimeException("test");}).exceptionally((ex)->{
            System.out.println("出错");
            return new RuntimeException("出错");
        }).join();
    }


}


