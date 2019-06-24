import java.io.IOException;
import java.io.InputStream;

public class ClazzLoader {

    public void test() throws Exception {
        ClassLoader loader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream in = getClass().getResourceAsStream("./com/test/"+fileName);
                    if (in == null) {
                        return super.loadClass(name);
                    }
                    byte[] buffer = new byte[in.available()];
                    in.read(buffer);
                    return defineClass(name, buffer, 0, buffer.length);
                }catch(IOException ioe) {
                    throw new ClassNotFoundException(name, ioe);
                }
            }
        };

        Object obj = loader.loadClass("com.test.Test").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof com.test.Test);
    }

    public static void main(String[] args) {
        ClazzLoader loader = new ClazzLoader();
        try {
            loader.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
