import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static String[] texts = new String[100_000];
    //Заводим переменные
    private static AtomicInteger countLength3 = new AtomicInteger(0);
    private static AtomicInteger countLength4 = new AtomicInteger(0);
    private static AtomicInteger countLength5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3)); // длина от 3 до 5
        }

        //Создаём три потока на каждый из видов "красивого текста"
        Thread palindromeThread = new Thread(() -> countPalindromes());
        Thread sameLetterThread = new Thread(() -> countSameLetter());
        Thread increasingOrderThread = new Thread(() -> countIncreasingOrder());
        //Запускаем их
        palindromeThread.start();
        sameLetterThread.start();
        increasingOrderThread.start();
        //Ждём завершения всех потоков до того как вывести результат
        try {
            palindromeThread.join();
            sameLetterThread.join();
            increasingOrderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Выводим результат
        System.out.println("Красивых слов с длиной 3: " + countLength3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + countLength4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + countLength5 + " шт");
    }

    //Генератор текстов
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    //Проверщик на палиндром
    private static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    //Проверщик на повторяющиесе буквы
    private static boolean hasSameLetters(String s) {
        char firstChar = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    //Проверщик на последовательность символов
    private static boolean isIncreasingOrder(String s) {
        char[] arr = s.toCharArray();
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }

    //Счётчик последовательных символов
    private static void countIncreasingOrder() {
        for (String text : texts) {
            if (text.length() == 3 && isIncreasingOrder(text)) {
                countLength3.incrementAndGet();
            } else if (text.length() == 4 && isIncreasingOrder(text)) {
                countLength4.incrementAndGet();
            } else if (text.length() == 5 && isIncreasingOrder(text)) {
                countLength5.incrementAndGet();
            }
        }
    }

    //Счётик  палиндрома
    private static void countPalindromes() {
        for (String text : texts) {
            if (text.length() == 3 && isPalindrome(text)) {
                countLength3.incrementAndGet();
            } else if (text.length() == 4 && isPalindrome(text)) {
                countLength4.incrementAndGet();
            } else if (text.length() == 5 && isPalindrome(text)) {
                countLength5.incrementAndGet();
            }
        }
    }

    //Счётчик повторов
    private static void countSameLetter() {
        for (String text : texts) {
            if (text.length() == 3 && hasSameLetters(text)) {
                countLength3.incrementAndGet();
            } else if (text.length() == 4 && hasSameLetters(text)) {
                countLength4.incrementAndGet();
            } else if (text.length() == 5 && hasSameLetters(text)) {
                countLength5.incrementAndGet();
            }
        }
    }

}






