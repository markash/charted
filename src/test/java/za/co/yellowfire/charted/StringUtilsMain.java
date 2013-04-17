package za.co.yellowfire.charted;

import org.apache.commons.lang3.StringUtils;

/**
 */
public class StringUtilsMain {

    public static void main(String[] args) throws Exception {
        boolean matches = StringUtils.contains("ACB CREDIT STANSAL S607SALARY/WAGE", "Salary");
        System.out.println("matches = " + matches);
    }
}
