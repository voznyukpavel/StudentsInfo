package studentsinfo3.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldsChecker {

    private FieldsChecker() {

    }

    private static final String LATERS_ONLY_REGULAR_EXPRESSION = "[A-Za-z ]+";
    private static final String LATERS_NUMBERS_SIGNS_REGULAR_EXPRESSION = "[A-Za-z0-9_,. ]+";
    private static final String INTEGER_0_5_ONLY_REGULAR_EXPRESSION = "^[0-5]?$";

    public static boolean latersCheck(String name) {
        if (checkTextFild(name, LATERS_ONLY_REGULAR_EXPRESSION)) {
            return true;
        }
        return false;
    }

    public static boolean numbersSignsAndLatersCheck(String group) {
        if (checkTextFild(group, LATERS_NUMBERS_SIGNS_REGULAR_EXPRESSION)) {
            return true;
        }
        return false;
    }

    public static boolean numbersCheck(String result) {
        if (checkTextFild(result, INTEGER_0_5_ONLY_REGULAR_EXPRESSION)) {
            return true;
        }
        return false;
    }

    private static boolean checkTextFild(String value, String expresion) {
        Pattern p = Pattern.compile(expresion);
        Matcher m = p.matcher(value);
        if (m.find() && m.group().equals(value))
            return true;
        else
            return false;
    }

}
