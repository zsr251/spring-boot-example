package com.jc.util;

import java.util.Random;

/**
 * 密码强度校验
 *
 * @author jasonzhu
 * @date 2017/11/1
 */
public class CheckStrength {
    private final static int[] SIZE_TABLE = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999,
            Integer.MAX_VALUE};

    public enum LEVEL {
        EASY, MIDIUM, STRONG, VERY_STRONG, EXTREMELY_STRONG
    }
    private final static char[] otherChar = {'!', '?', '@', '#', '$', '%', '^', '&', '*', '+', '-'};
    private static final int NUM = 1;
    private static final int SMALL_LETTER = 2;
    private static final int CAPITAL_LETTER = 3;
    private static final int OTHER_CHAR = 4;

    /**
     * Simple password dictionary
     */
    private final static String[] DICTIONARY = {"password", "abc123", "iloveyou", "adobe123", "123123", "sunshine",
            "1314520", "a1b2c3", "123qwe", "aaa111", "qweasd", "admin", "passwd"};

    /**
     * Check character's type, includes num, capital letter, small letter and other character.
     *
     * @param c
     * @return
     */
    private static int checkCharacterType(char c) {
        if (c >= 48 && c <= 57) {
            return NUM;
        }
        if (c >= 65 && c <= 90) {
            return CAPITAL_LETTER;
        }
        if (c >= 97 && c <= 122) {
            return SMALL_LETTER;
        }
        return OTHER_CHAR;
    }

    /**
     * Count password's number by different type
     *
     * @param passwd
     * @param type
     * @return
     */
    private static int countLetter(String passwd, int type) {
        int count = 0;
        if (null != passwd && passwd.length() > 0) {
            for (char c : passwd.toCharArray()) {
                if (checkCharacterType(c) == type) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Check password's strength
     *
     * @param passwd
     * @return strength level
     */
    public static int checkPasswordStrength(String passwd) {
        if (equalsNull(passwd)) {
            throw new IllegalArgumentException("password is empty");
        }
        int len = passwd.length();
        int level = 0;

        // increase points
        if (countLetter(passwd, NUM) > 0) {
            level++;
        }
        if (countLetter(passwd, SMALL_LETTER) > 0) {
            level++;
        }
        if (len > 4 && countLetter(passwd, CAPITAL_LETTER) > 0) {
            level++;
        }
        if (len > 6 && countLetter(passwd, OTHER_CHAR) > 0) {
            level++;
        }

        if (len > 4 && ((countLetter(passwd, NUM) > 0 && countLetter(passwd, SMALL_LETTER) > 0)
                || (countLetter(passwd, NUM) > 0 && countLetter(passwd, CAPITAL_LETTER) > 0)
                || (countLetter(passwd, NUM) > 0 && countLetter(passwd, OTHER_CHAR) > 0)
                || (countLetter(passwd, SMALL_LETTER) > 0 && countLetter(passwd, CAPITAL_LETTER) > 0)
                || (countLetter(passwd, SMALL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0)
                || (countLetter(passwd, CAPITAL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0))) {
            level++;
        }

        if (len > 6 && ((countLetter(passwd, NUM) > 0 && countLetter(passwd, SMALL_LETTER) > 0
                && countLetter(passwd, CAPITAL_LETTER) > 0 || countLetter(passwd, NUM) > 0
                && countLetter(passwd, SMALL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0)
                || (countLetter(passwd, NUM) > 0 && countLetter(passwd, CAPITAL_LETTER) > 0
                && countLetter(passwd, OTHER_CHAR) > 0 || countLetter(passwd, SMALL_LETTER) > 0
                && countLetter(passwd, CAPITAL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0))) {
            level++;
        }

        if (len > 8 && countLetter(passwd, NUM) > 0 && countLetter(passwd, SMALL_LETTER) > 0
                && countLetter(passwd, CAPITAL_LETTER) > 0 && countLetter(passwd, OTHER_CHAR) > 0) {
            level++;
        }

        if (len > 6 && ((countLetter(passwd, NUM) >= 3 && countLetter(passwd, SMALL_LETTER) >= 3)
                || (countLetter(passwd, NUM) >= 3 && countLetter(passwd, CAPITAL_LETTER) >= 3)
                || (countLetter(passwd, NUM) >= 3 && countLetter(passwd, OTHER_CHAR) >= 2)
                || (countLetter(passwd, SMALL_LETTER) >= 3 && countLetter(passwd, CAPITAL_LETTER) >= 3)
                || (countLetter(passwd, SMALL_LETTER) >= 3 && countLetter(passwd, OTHER_CHAR) >= 2)
                || (countLetter(passwd, CAPITAL_LETTER) >= 3 && countLetter(passwd, OTHER_CHAR) >= 2))) {
            level++;
        }

        if (len > 8 && countLetter(passwd, NUM) >= 2 && countLetter(passwd, SMALL_LETTER) >= 2
                && countLetter(passwd, CAPITAL_LETTER) >= 2 || countLetter(passwd, NUM) >= 2
                && countLetter(passwd, SMALL_LETTER) >= 2 && countLetter(passwd, OTHER_CHAR) >= 2
                || countLetter(passwd, NUM) >= 2 && countLetter(passwd, CAPITAL_LETTER) >= 2
                && countLetter(passwd, OTHER_CHAR) >= 2 || countLetter(passwd, SMALL_LETTER) >= 2
                && countLetter(passwd, CAPITAL_LETTER) >= 2 && countLetter(passwd, OTHER_CHAR) >= 2) {
            level++;
        }

        if (len > 10 && countLetter(passwd, NUM) >= 2 && countLetter(passwd, SMALL_LETTER) >= 2
                && countLetter(passwd, CAPITAL_LETTER) >= 2 && countLetter(passwd, OTHER_CHAR) >= 2) {
            level++;
        }

        if (countLetter(passwd, OTHER_CHAR) >= 3) {
            level++;
        }
        if (countLetter(passwd, OTHER_CHAR) >= 6) {
            level++;
        }

        if (len > 12) {
            level++;
            if (len >= 16) {
                level++;
            }
        }

        // decrease points
        if ("abcdefghijklmnopqrstuvwxyz".indexOf(passwd) > 0 || "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(passwd) > 0) {
            level--;
        }
        if ("qwertyuiop".indexOf(passwd) > 0 || "asdfghjkl".indexOf(passwd) > 0 || "zxcvbnm".indexOf(passwd) > 0) {
            level--;
        }
        if (isNumeric(passwd) && ("01234567890".indexOf(passwd) > 0 || "09876543210".indexOf(passwd) > 0)) {
            level--;
        }

        if (countLetter(passwd, NUM) == len || countLetter(passwd, SMALL_LETTER) == len
                || countLetter(passwd, CAPITAL_LETTER) == len) {
            level--;
        }

        if (len % 2 == 0) { // aaabbb
            String part1 = passwd.substring(0, len / 2);
            String part2 = passwd.substring(len / 2);
            if (part1.equals(part2)) {
                level--;
            }
            if (isCharEqual(part1) && isCharEqual(part2)) {
                level--;
            }
        }
        if (len % 3 == 0) {
            // ababab
            String part1 = passwd.substring(0, len / 3);
            String part2 = passwd.substring(len / 3, len / 3 * 2);
            String part3 = passwd.substring(len / 3 * 2);
            if (part1.equals(part2) && part2.equals(part3)) {
                level--;
            }
        }
        if (isNumeric(passwd) && len >= 6 && len < 10) {
            // 19881010 or 881010
            int year = 0;
            if (len == 8 || len == 6) {
                year = Integer.parseInt(passwd.substring(0, len - 4));
            }
            int size = sizeOfInt(year);
            int month = Integer.parseInt(passwd.substring(size, size + 2));
            int day = Integer.parseInt(passwd.substring(size + 2, len));
            if (year >= 1950 && year < 2050 && month >= 1 && month <= 12 && day >= 1 && day <= 31) {
                level--;
            }
        }
        if (null != DICTIONARY && DICTIONARY.length > 0) {
            // dictionary
            for (int i = 0; i < DICTIONARY.length; i++) {
                if (passwd.equals(DICTIONARY[i]) || DICTIONARY[i].indexOf(passwd) >= 0) {
                    level--;
                    break;
                }
            }
        }

        if (len <= 6) {
            level--;
            if (len <= 4) {
                level--;
                if (len <= 3) {
                    level = 0;
                }
            }
        }

        if (isCharEqual(passwd)) {
            level = 0;
        }

        if (level < 0) {
            level = 0;
        }

        return level;
    }

    /**
     * Get password strength level, includes easy, midium, strong, very strong, extremely strong
     *
     * @param passwd
     * @return
     */
    public static LEVEL getPasswordLevel(String passwd) {
        int level = checkPasswordStrength(passwd);
        switch (level) {
            case 0:
            case 1:
            case 2:
            case 3:
                return LEVEL.EASY;
            case 4:
            case 5:
            case 6:
                return LEVEL.MIDIUM;
            case 7:
            case 8:
            case 9:
                return LEVEL.STRONG;
            case 10:
            case 11:
            case 12:
                return LEVEL.VERY_STRONG;
            default:
                return LEVEL.EXTREMELY_STRONG;
        }
    }

    /**
     * calculate the size of an integer number
     *
     * @param x
     * @return
     */
    private static int sizeOfInt(int x) {
        for (int i = 0; ; i++) {
            if (x <= SIZE_TABLE[i]) {
                return i + 1;
            }
        }
    }

    /**
     * Judge whether each character of the string equals
     *
     * @param str
     * @return
     */
    private static boolean isCharEqual(String str) {
        return str.replace(str.charAt(0), ' ').trim().length() == 0;
    }

    /**
     * Determines if the string is a digit
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Judge whether the string is whitespace, empty ("") or null.
     *
     * @param str
     * @return
     */
    private static boolean equalsNull(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 || str.equalsIgnoreCase("null")) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
    /**
     * 生成密码
     *
     * @param length      密码长度
     * @param haveNum     是否包含数字
     * @param haveCapital 是否包含大写字母
     * @param haveSmall   是否包含小写字母
     * @param haveOther   是否包含特殊字母
     * @return
     */
    public static String generatePassword(int length, boolean haveNum, boolean haveCapital, boolean haveSmall, boolean haveOther) {
        //如果长度小于4 或者 没有选择类型
        if (length < 4 || (haveNum || haveCapital || haveSmall || haveOther) == false) {
            return "null";
        }
        //结果
        char[] result = new char[length];
        int num = 0;
        int capital = 0;
        int small = 0;
        int other = 0;

        Random random = new Random();
        while (length-- > 0) {
            if (random.nextInt(4) == 0 && haveNum) {
                result[length] = (char) (random.nextInt(10) % 10 + 48);
                num++;
            } else if (random.nextInt(4) == 1 && haveCapital) {
                result[length] = (char) (random.nextInt(91) % 26 + 65);
                capital++;
            } else if (random.nextInt(4) == 2 && haveSmall) {
                result[length] = (char) (random.nextInt(123) % 26 + 97);
                small++;
            } else if (random.nextInt(4) == 3 && haveOther) {
                result[length] = otherChar[random.nextInt(otherChar.length)];
                other++;
            } else {
                length++;
            }
        }
        // 如果需要含数字 但是没有
        if (num == 0 && haveNum) {
            int type = 0;
            if (capital >= small && capital >= other) {
                type = 1;
            } else if (small >= capital && small >= other) {
                type = 2;
            } else if (other >= small && other >= capital) {
                type = 3;
            }
            length = result.length;
            while (length-- > 0) {
                if (type == 1 && checkCharacterType(result[length]) == CAPITAL_LETTER) {
                    result[length] = (char) (random.nextInt(10) % 10 + 48);
                    num++;
                    capital--;
                    break;
                } else if (type == 2 && checkCharacterType(result[length]) == SMALL_LETTER) {
                    result[length] = (char) (random.nextInt(10) % 10 + 48);
                    num++;
                    small--;
                    break;
                } else if (type == 3 && checkCharacterType(result[length]) == OTHER_CHAR) {
                    result[length] = (char) (random.nextInt(10) % 10 + 48);
                    num++;
                    other--;
                    break;
                }
            }

        }
        // 如果需要含大写字母 但是没有
        if (capital == 0 && haveCapital) {
            int type = 0;
            if (num >= small && num >= other) {
                type = 0;
            } else if (small >= num && small >= other) {
                type = 2;
            } else if (other >= num && other >= small) {
                type = 3;
            }
            length = result.length;
            while (length-- > 0) {
                if (type == 0 && checkCharacterType(result[length]) == NUM) {
                    result[length] = (char) (random.nextInt(91) % 26 + 65);
                    capital++;
                    num--;
                    break;
                } else if (type == 2 && checkCharacterType(result[length]) == SMALL_LETTER) {
                    result[length] = (char) (random.nextInt(91) % 26 + 65);
                    capital++;
                    small--;
                    break;
                } else if (type == 3 && checkCharacterType(result[length]) == OTHER_CHAR) {
                    result[length] = (char) (random.nextInt(91) % 26 + 65);
                    capital++;
                    other--;
                    break;
                }
            }
        }
        // 如果需要含小写字母 但是没有
        if (small == 0 && haveSmall) {
            int type = 0;
            if (num >= capital && num >= other) {
                type = 0;
            } else if (capital >= num && capital >= other) {
                type = 1;
            } else if (other >= num && other >= capital) {
                type = 3;
            }
            length = result.length;
            while (length-- > 0) {
                if (type == 0 && checkCharacterType(result[length]) == NUM) {
                    result[length] = (char) (random.nextInt(123) % 26 + 97);
                    small++;
                    num--;
                    break;
                } else if (type == 1 && checkCharacterType(result[length]) == CAPITAL_LETTER) {
                    result[length] = (char) (random.nextInt(123) % 26 + 97);
                    small++;
                    capital--;
                    break;
                } else if (type == 3 && checkCharacterType(result[length]) == OTHER_CHAR) {
                    result[length] = (char) (random.nextInt(123) % 26 + 97);
                    small++;
                    other--;
                    break;
                }
            }
        }
        // 如果需要特殊符号 但是没有
        if (other == 0 && haveOther) {
            int type = 0;
            if (num >= capital && num >= small) {
                type = 0;
            } else if (capital >= num && capital >= small) {
                type = 1;
            } else if (small >= num && small >= capital) {
                type = 2;
            }
            length = result.length;
            while (length-- > 0) {
                if (type == 0 && checkCharacterType(result[length]) == NUM) {
                    result[length] = otherChar[random.nextInt(otherChar.length)];
                    other++;
                    num--;
                    break;
                } else if (type == 1 && checkCharacterType(result[length]) == CAPITAL_LETTER) {
                    result[length] = otherChar[random.nextInt(otherChar.length)];
                    other++;
                    capital--;
                    break;
                } else if (type == 2 && checkCharacterType(result[length]) == SMALL_LETTER) {
                    result[length] = otherChar[random.nextInt(otherChar.length)];
                    other++;
                    small--;
                    break;
                }
            }
        }
        return new String(result);
    }
}
