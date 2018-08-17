package cn.wangjie.lambda.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @program: lambda
 * @description: 商店
 * @author: WangJie
 * @create: 2018-08-13 11:36
 **/
@Data
public class Shop {

    /**
     * 商店名
     */
    private String name;

    public Shop(String name) {
        this.name = name;
    }

    /**
     * 模拟远程获取商品价格，延时一秒
     *
     * @return 商品价格
     */
    public String getPrice(String product) {
        delay();
        Random random = new Random();
        double price = random.nextDouble() * product.charAt(0) + product.charAt(1);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s",name,price,code);
    }



    /**
     * 模拟远程调用的延时
     */
    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    /**
     * 价格转换利率
     *
     * @return
     */
    public static double getpriceExchangeRate() {
        delay();
        return 0.8;
    }
    public static  List<String> getlist(){
        delay();
        List<String> list = new ArrayList(3);
        list.add("a");
        list.add("b");
        return list;
    }
}
