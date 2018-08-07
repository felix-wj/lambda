package cn.wangjie.lambda.example;

import cn.wangjie.lambda.util.ThreadPoolUtil;

import java.awt.event.ActionListener;
import java.util.function.BinaryOperator;

/**
 * @program: lambda
 * @description: lambda的几种形式
 * @author: WangJie
 * @create: 2018-08-07 17:51
 **/
public class LambdaFormat {
    public static void main(String[] args) {
        //无参数形式
        Runnable noArguments = () -> System.out.println("Hello Lambda");
        ThreadPoolUtil.getExecutorService().execute(noArguments);

        //一个参数，可以省略参数括号
        ActionListener oneArguments = event -> System.out.println("button clicked");

        //代码块形式的表达式主体
        Runnable multiStatement = () -> {
            System.out.println("hello");
            System.out.println("lambda");
        };
        ThreadPoolUtil.getExecutorService().execute(multiStatement);

        //多个参数
        BinaryOperator<Long> add = (x,y) -> x+y;
        System.out.println(add.apply(1L,2L));

        //显示声明参数类型
        BinaryOperator<Long> addExplicit = (Long x,Long y) -> x+y;



    }
}
