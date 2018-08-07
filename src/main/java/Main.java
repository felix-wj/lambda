import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public final static ThreadLocal<DateFormatter> formatter = ThreadLocal.withInitial(() -> new DateFormatter(new SimpleDateFormat("dd-MMM-yyyy")));

    public static void main(String[] args) {


        List<Integer> list = new ArrayList<>(4);
        list.add(1);
        list.add(2);
        list.add(3);
        int count = list.stream().reduce(0,(a,b)->a+b);
        System.out.println(count);
    }
}
