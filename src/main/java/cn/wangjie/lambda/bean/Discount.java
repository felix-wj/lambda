package cn.wangjie.lambda.bean;

/**
 * @program: lambda
 * @description: 折扣
 * @author: WangJie
 * @create: 2018-08-13 13:15
 **/
public class Discount {
    public enum Code{
        NONE(0),SILVER(5),GOLD(10),PLATINUM(15),DTAMOND(20);
        private final int percentage;
        Code(int percentage){
            this.percentage = percentage;
        }
    }
    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " +
                Discount.apply(quote.getPrice(),
                        quote.getDiscountCode());
    }
    private static double apply(double price, Code code) {
        delay();
        return  (price * (100 - code.percentage) / 100);
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
}
