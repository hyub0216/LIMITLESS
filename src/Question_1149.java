import java.util.Scanner;

public class Question_1149 {


    static Scanner sc = new Scanner(System.in);
    static int [][] map = new int[1001][4];
    static int N;
    static int minPrice = 1000000;

    static int []node1 = {0,2,3,1};
    static int []node2 = {0,3,1,2};

    public static void main(String[] args) {

        init();
        simulation();
        System.out.println(minPrice);
    }
    static void simulation(){

        for(int row =2; row<N+1; row++){
            for(int col=1; col<4; col++){
                int bfRow = row-1;
                int col1 = node1[col];
                int col2 = node2[col];

                int col1Val = map[bfRow][col1];
                int col2Val = map[bfRow][col2];

                if(col1Val > col2Val) {
                    map[row][col] += col2Val;
                }else
                    map[row][col] += col1Val;
            }
        }

        for(int col=1; col<4; col++){
            int value = map[N][col];
            if(value < minPrice)
                minPrice = value;
        }
    }


    static void init(){
        N = sc.nextInt();
        for(int row = 1; row< N+1; row++) {
            for(int col =1; col<4; col++){
                int input = sc.nextInt();
                map[row][col] = input;
            }
        }
    }
}
