import java.util.Scanner;
public class Question_2502 {
    static Scanner sc = new Scanner(System.in);
    static int N,K;
    static int xCnt =0;
    static int yCnt =0;

    public static void main(String[] args){
        N = sc.nextInt();
        K = sc.nextInt();
        DFS(N);
        calc();
    }
    static void calc(){

        int x =0;
        int y =0;
        while(x*xCnt <= K){
            x++;
            int result = K;
            int xVal = x*xCnt;
            result = result - xVal;

            if(result%yCnt == 0){
                y = result / yCnt;
                break;
            }
        }
        System.out.println(x);
        System.out.println(y);
    }
    static void DFS(int num){

        if(num >2){
            DFS(num-2);
            DFS(num-1);
        }else if(num ==1){
            xCnt++;
        }else if(num ==2){
            yCnt++;
        }
    }
}
