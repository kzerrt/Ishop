import com.fc.ishop.utils.DateUtil;

import java.util.Date;

/**
 * @author florence
 * @date 2023/12/26
 */
public class DateTest {
    public static void main(String[] args) {
        String str = "Tue Dec 26 14:13:28 CST 2023 (香港时间)";
        System.out.println(DateUtil.toDate(new Date(str), null));
    }
}
