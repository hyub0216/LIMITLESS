import java.util.Scanner;

public class Question_10026 {

    static Scanner sc = new Scanner(System.in);
    static int N;
    static char[][] normal = new char[101][101];
    static char[][] uNormal = new char[101][101];

    static int [] nRow ={0,0,-1,1};
    static int [] nCol ={1,-1,0,0};

    static int normalCnt =0;
    static int uNormalCnt =0;

    public static void main(String[] args) {
        init();
//        printNormal();
//        printUnormal();
        normalSimulation();
        unormalSimulation();
        System.out.println(normalCnt +" "+uNormalCnt);
    }

    static void normalSimulation(){
        for(int row=1; row<N+1; row++){
            for(int col=1; col<N+1; col++){
                char rgb = normal[row][col];
                if('X' != rgb){
                    normalCnt++;
                    normal[row][col] ='X';
                    normalDFS(row,col,rgb);
                }
            }
        }
    }
    static void normalDFS(int row, int col, char rgb){

        for(int i=0; i<4; i++){
            int nextRow = row+nRow[i];
            int nextCol = col+nCol[i];
            if(nextCol<N+1 && nextRow<N+1){
                char nextRgb = normal[nextRow][nextCol];
                if(nextRgb == rgb){
                    normal[nextRow][nextCol] ='X';
                    normalDFS(nextRow,nextCol,nextRgb);
                }
            }
        }
    }

    static void unormalSimulation(){
        for(int row=1; row<N+1; row++){
            for(int col=1; col<N+1; col++){
                char rgb = uNormal[row][col];
                if('X' != rgb){
                    uNormalCnt++;
                    uNormal[row][col] ='X';
                    unormalDFS(row,col,rgb);
                }
            }
        }
    }
    static void unormalDFS(int row, int col, char rgb){

        for(int i=0; i<4; i++){
            int nextRow = row+nRow[i];
            int nextCol = col+nCol[i];
            if(nextCol<N+1 && nextRow<N+1){
                char nextRgb = uNormal[nextRow][nextCol];
                if(nextRgb == rgb){
                    uNormal[nextRow][nextCol] ='X';
                    unormalDFS(nextRow,nextCol,nextRgb);
                }
            }
        }
    }

    static void init(){

        N = sc.nextInt();
        for(int row =1; row<N+1; row++){
            String input = sc.next();
            for(int col =1; col<N+1; col++){
                char rgb = input.charAt(col-1);
                normal[row][col] =rgb;
                if('R' == rgb)
                    rgb = 'G';
                uNormal[row][col] = rgb;
            }
        }
    }

    static void printNormal(){

        for(int row=1; row<N+1; row++){
            for(int col=1; col<N+1; col++){
                System.out.print(normal[row][col] + " ");
            }
            System.out.println();
        }
    }

    static void printUnormal(){

        for(int row=1; row<N+1; row++){
            for(int col=1; col<N+1; col++){
                System.out.print(uNormal[row][col] + " ");
            }
            System.out.println();
        }
    }
}
