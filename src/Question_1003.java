import java.util.ArrayList;
import java.util.Scanner;

public class Question_1003 {

    /*
    *  23.09.04일 
    *  시간 초과가 되는 문제를 MaxInput 값을 추출함으로써 해결
    * */
    public static void main(String[] args) {

        int t; //TEST CASE
        int maxInput =0;
        ArrayList<Integer> input = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        t = sc.nextInt();
        for(int i=0; i<t; i++)
        {
            int N = sc.nextInt();
            if(maxInput < N)
                maxInput =N;
            input.add(N);
        }

        //index 40까지 처리 가능
        int []zeroArray = new int[3];
        int []oneArray = new int[41];

        //초기값 생성
        zeroArray[0] = 1;
        zeroArray[1] = 0;
        zeroArray[2] = 1;
        oneArray[0] = 0;
        oneArray[1] = 1;
        oneArray[2] = 1;


        // 입력가능한 자연수가 40이지만 40까지 모든 0,1 의 조합을 계산해 놓을 필요는 없음
        // 입력받은 자연수중 가장 큰값까지만 0,1 조합계산 > 시간초과 해결
        for(int i=2; i<maxInput+1; i++)
        {
            oneArray[i] = oneArray[i-1]+oneArray[i-2];
        }

        for(int i=0; i<t; i++)
        {
            int testInput = input.get(i);
            if(testInput >2){
                System.out.println(oneArray[testInput-1]+ " "+oneArray[testInput]);
            }else{
                System.out.println(zeroArray[testInput]+ " "+oneArray[testInput]);
            }
        }

    }
}
