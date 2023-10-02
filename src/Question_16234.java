import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Question_16234 {

    /*
    * 드래곤커브
    * */

    static class Point{
        int row;
        int col;
        int val;

        Point(int pRow, int pCol, int pVal){
            this.row = pRow;
            this.col = pCol;
            this.val = pVal;
        }
    }

    static Scanner sc = new Scanner(System.in);
    static int[][] map = new int[51][101];
    static boolean[][] visit = new boolean[51][101];
    static int N,L,R;
    static int []dr ={0,0,-1,1};
    static int []dc ={-1,1,0,0};
    static Stack<Point> stack = new Stack<>();

    static List<Point> openCitys = new ArrayList<>();

    public static void main(String[] args) {

        init();
//        print();
        int callCnt =0;
        while(goNextRound()){
            simulation();
            callCnt++;
//            print();
        }
        System.out.println(callCnt);
    }

    static void initVisit(){
        for(int row =1; row<N+1; row++){
            for(int col=1; col<N+1; col++)
                visit[row][col] =false;
        }
    }

    static void simulation(){
        for(int row=1; row<N+1; row++){
            for(int col=1; col<N+1; col++){
                if(!visit[row][col]){
                    int val = map[row][col];
                    openCitys.clear();
                    visit[row][col] =true;
                    Point point = new Point(row,col,val);
                    stack.add(point);
                    openCitys.add(point);
                    DFS();
                }
            }
        }
    }

    static void DFS(){
        while(stack.size() > 0){
            Point point = stack.pop();
            for(int dirIdx =0; dirIdx<4; dirIdx++){
                if(validCheck(point,dirIdx)){
                    int nextRow = point.row+dr[dirIdx];
                    int nextCol = point.col+dc[dirIdx];
                    int nextVal = map[nextRow][nextCol];
                    Point nPoint = new Point(nextRow,nextCol,nextVal);
                    visit[nextRow][nextCol] = true;
                    stack.add(nPoint);
                    openCitys.add(nPoint);
                }
            }
        }
        closeCityDoor();
    }

    static void closeCityDoor(){
        int peopleCnt =0;
        for(Point point : openCitys){
            peopleCnt += point.val;
        }

        int averPeopleCnt = peopleCnt / openCitys.size();
        for(Point point : openCitys){
            int row = point.row;
            int col = point.col;

            map[row][col] = averPeopleCnt;
        }
    }

    static boolean goNextRound(){
        initVisit();
        for(int row=1; row<N+1; row++){
            for(int col=1; col<N+1; col++){
                Point point = new Point(row,col,map[row][col]);
                for(int dirIdx =0; dirIdx <4; dirIdx++){
                        if(validCheck(point,dirIdx))
                            return true;
                }
            }
        }
        return false;
    }

    static boolean validCheck(Point point, int dirIdx){
        int nextRow = point.row+dr[dirIdx];
        int nextCol = point.col+dc[dirIdx];
        if(nextCol> 0 && nextCol <N+1 && nextRow >0 && nextRow < N+1 && !visit[nextRow][nextCol]){
            int nextVal = map[nextRow][nextCol];
            int diff = point.val - nextVal;
            if(diff < 0)
                diff = diff*-1;

            if(L<=diff && diff<=R)
                return true;
        }

        return false;
    }

    static void print(){
        for(int row =1; row<N+1; row++){
            for(int col=1; col<N+1; col++){
                System.out.print(map[row][col]+ " ");
            }
            System.out.println();
        }
    }

    static void init(){
        N = sc.nextInt();
        L = sc.nextInt();
        R = sc.nextInt();
        for(int row =1; row<N+1; row++){
            for(int col=1; col<N+1; col++){
                map[row][col] = sc.nextInt();
            }
        }
    }

}
