import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {

    public Main() {
       print("default construction");
    }

    public Main(String s) {
        print(s);
    }

    public void print(String s) {
        System.out.println("this is from print : " + s);
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Main.class);
        enhancer.setUseCache(false);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                if("print".equals(method.getName())) {
                    System.out.println("interrupt the method of print...");
                }
                return methodProxy.invokeSuper(o, objects);
            }
        });
        enhancer.create();
        System.out.println("end");
    }
}
