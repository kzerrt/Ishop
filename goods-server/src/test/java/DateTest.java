import com.fc.ishop.utils.DateUtil;

import java.util.Date;

/**
 * @author florence
 * @date 2023/12/26
 */
public class DateTest {
    public static void main(String[] args) {
        String str = "2023 12 23 12:23:12";
        System.out.println(DateUtil.toDate(new Date(str), null));
    }
}
