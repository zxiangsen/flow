
/**
 * Created by lb on 2017/12/5.
 */
public class Test {
    /** 自定义进制（选择你想要的进制数，不能重复且最好不要0、1这些容易混淆的字符） */
    private static final char[] r = new char[]{'2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v'
                                            ,'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V'
                                            ,'W', 'X', 'Y', 'Z'};

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(generateRandomStr(7));
        }
    }

    public static String generateRandomStr(int len) {
        //字符源，可以根据需要删减
        String generateSource = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ";//去掉1和i ，0和o
        String rtnStr = "";
        for (int i = 0; i < len; i++) {
            String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
            rtnStr += nowStr;
        }
        return rtnStr;
    }

}