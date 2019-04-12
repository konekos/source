import com.jasu.ThreadDemo;

/**
 * @author @Jasu
 * @date 2018-07-11 15:45
 */
public class Test {

    public static void main(String[] args) {

        ThreadDemo threadDemo = new ThreadDemo();

        Thread t1 = new Thread(threadDemo, "t1");
        Thread t2 = new Thread(threadDemo, "t2");

        t1.start();
        t2.start();
    }
}
