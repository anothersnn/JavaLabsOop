public class Palindrome {
    public static void main(String[] args) {
        for (int i=0; i<args.length; i++) {
            String s = args[i];
            System.out.println(isPalindrome(s));
        }
    }
    public static String reverseString(String s) {
        String newStr = "";
        for (int i = s.length()-1; i>=0; i--) {
            char a = s.charAt(i);
            newStr+=a;
        }
        return newStr;
    }
    public static boolean isPalindrome(String s) {
        s = s.toLowerCase();
        String reverses = reverseString(s);
        return s.equals(reverses);
    }
}