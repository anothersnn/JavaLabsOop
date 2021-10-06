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
            newStr += s.charAt(i); //Добавляет каждый символ с начальной строки в перевернутую строку
        }
        return newStr;
    }
    public static boolean isPalindrome(String s) {
        s = s.toLowerCase(); //Перевод строки в нижний регистр
        String reverses = reverseString(s); //Сохраняет перевернутую строку для дальнейшей проверки
        return s.equals(reverses); //Перевернутое слово равнивает с первоначальным словом
    }
}