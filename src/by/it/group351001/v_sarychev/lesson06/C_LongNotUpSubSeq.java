package by.it.group351001.v_sarychev.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: наибольшая невозростающая подпоследовательность

Дано:
    целое число 1<=n<=1E5 ( ОБРАТИТЕ ВНИМАНИЕ НА РАЗМЕРНОСТЬ! )
    массив A[1…n] натуральных чисел, не превосходящих 2E9.

Необходимо:
    Выведите максимальное 1<=k<=n, для которого гарантированно найдётся
    подпоследовательность индексов i[1]<i[2]<…<i[k] <= длины k,
    для которой каждый элемент A[i[k]] не больше любого предыдущего
    т.е. для всех 1<=j<k, A[i[j]]>=A[i[j+1]].

    В первой строке выведите её длину k,
    во второй - её индексы i[1]<i[2]<…<i[k]
    соблюдая A[i[1]]>=A[i[2]]>= ... >=A[i[n]].

    (индекс начинается с 1)

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    5
    5 3 4 4 2

    Sample Output:
    4
    1 3 4 5
*/


public class C_LongNotUpSubSeq {

    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //общая длина последовательности
        int n = scanner.nextInt();
        int[] m = new int[n];
        //читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }
        //тут реализуйте логику задачи методами динамического программирования (!!!)
        int result = longestDecSubsequenceLength(m);


        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    int longestDecSubsequenceLength(int numbers[]) {
        int maxlength = 1;
        int[] d = new int[numbers.length];
        for (int i = 0; i <= numbers.length-1; i++)
        {
            d[i] = 1;
        }
        int[] prev = new int[numbers.length];
        for (int i = 0; i <= numbers.length-1; i++)
        {
            prev[i] = -1;
        }

        for (int i = 0; i <= numbers.length-1; i++)
        {
            for (int j = 0; j <= i - 1; j++){
                if (numbers[i] <= numbers[j] && d[j]+1 > d[i]){
                    d[i] = d[j] + 1;
                    prev[i] = j;
                    if (d[i]> maxlength) maxlength = i;
                }
            }
        }

        int[] resSeqind = new int[d[maxlength]];
        int k = maxlength;
        for (int i = resSeqind.length-1; i>=0; i--)
        {
            resSeqind[i] = k;
            k = prev[k];
        }

        for (int i = 0; i < resSeqind.length; i++) System.out.println(numbers[resSeqind[i]] + ' ');
        System.out.println();

        return d[maxlength];
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group351001/v_sarychev/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
        System.out.print(result);
    }

}