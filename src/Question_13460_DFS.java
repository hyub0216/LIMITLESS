import java.util.Scanner;
import java.util.Stack;

public class Question_13460_DFS {

    static class Point{
        int row;
        int col;

        Point(int pRow, int pCol){
            this.row = pRow;
            this.col = pCol;
        }
        void setRowCol(int pRow, int pCol){
            this.row = pRow;
            this.col = pCol;
        }

        boolean equalPoint(Point point){
            if(this.col == point.col && this.row == point.row)
                return true;
            return false;
        }
    }
    static Scanner sc = new Scanner(System.in);
    static int N,M;
    static char[][] map = new char[11][11];
    static char[][] bfMap = new char[11][11];
    static int[] dr = {-1,1,0,0};
    static int[] dc = {0,0,-1,1};
    static Point red;
    static Point blue;
    static Point exit;

    static int [][]validMap ={
            {0,0,1,1},
            {0,0,1,1},
            {1,1,0,0},
            {1,1,0,0}
    };

    static int callCnt = 0;

    static int minCallCnt = 10;

    static int moveCnt = 0;

    static boolean isPossible = false;

    static Stack<Integer> stack = new Stack<>();

    //파란구슬 : B
    //빨간구슬 : R
    //구멍 : O
    //종료조건 : 빨간구슬이 구멍에 빠지면 성공
    //제한조건 : 빨간구슬과 파란구슬이 동시에 빠져도 실패
    public static void main(String[] args) {
        init();
        for(int i=0; i<4; i++){
            callCnt++;
            stack.push(i);
            DFS(i);
            stack.pop();
            callCnt--;
        }
        if(!isPossible)
            minCallCnt = -1;
        System.out.println(minCallCnt);

    }

    static boolean validCheck(int dirIdx, int nDirIdx){
        int nextRow = red.row+dr[nDirIdx];
        int nextCol = red.col+dc[nDirIdx];
        char nextVal = map[nextRow][nextCol];

        if('#' != nextVal && validMap[dirIdx][nDirIdx] > 0)
            return true;
        return false;
    }

    static boolean isStop(){

        boolean redP = true;
        boolean blueP = true;
        for(int i=0; i<4; i++)
        {
            int redNextRow = red.row+dr[i];
            int redNextCol = red.col+dc[i];
            char redNextVal = map[redNextRow][redNextCol];

            if('.' == redNextVal || 'O' == redNextVal)
                redP = false;

            int blueNextRow = blue.row+dr[i];
            int blueNextCol = blue.col+dc[i];
            char blueNextVal = map[blueNextRow][blueNextCol];

            if('.' == blueNextVal || 'O' == blueNextVal)
                blueP = false;
        }

        return redP && blueP;
    }

    static void printStack(){

        for(int i=0; i<stack.size(); i++){
            System.out.print(stack.get(i)+ " - ");
        }
        System.out.println();
    }

    static void calc(){

        copyMap();
        for(int i=0; i<minCallCnt; i++){
            int dirIdx = stack.get(i);
            simulation(dirIdx);
            moveCnt++;
            if(isGameOver()){
                break;
            }else if(isSuccess()){
                isPossible =true;
                if(minCallCnt > moveCnt){
                    minCallCnt = moveCnt;
                }
                break;
            }
        }
        initMap();
    }


    static void DFS(int dirIdx){

        if(callCnt > 9)
        {
            calc();
        }else if(!isStop()){
            for(int i=0; i<4; i++)
            {
                if(validCheck(dirIdx,i)){
                    callCnt++;
                    stack.push(i);
                    DFS(i);
                    stack.pop();
                    callCnt--;
                }
            }
        }
    }

    static void copyMap(){
        for(int row =1; row<N+1; row++){
            for(int col=1 ; col<M+1; col++){
                bfMap[row][col] = map[row][col];
            }
        }
    }

    static void initMap(){
        moveCnt =0;
        for(int row =1; row<N+1; row++){
            for(int col=1 ; col<M+1; col++){
                char input = bfMap[row][col];
                map[row][col] = input;
                if('R' == input){
                    red.setRowCol(row,col);
                }else if('B' == input){
                    blue.setRowCol(row,col);
                }
            }
        }
    }

    static void test(){

        /*
        *
        * 7 7
        #######
        #...RB#
        #.#####
        #.....#
        #####.#
        #O....#
        #######
        * */
        //좌 하 우 하 좌
        simulation(2);
        print();
        simulation(1);
        print();
        simulation(3);
        print();
        simulation(1);
        print();
        simulation(2);
        print();

        System.out.println("RESULT : "+isSuccess());
    }

    static boolean isSuccess(){

        if(red.equalPoint(exit) && !blue.equalPoint(exit))
            return true;

        return false;
    }

    static boolean isGameOver(){
        if(blue.equalPoint(exit))
            return true;
        return false;
    }


    static void simulation(int dirIdx){
        char color = firstBallColor(dirIdx);
        if('R' == color){
            move('R',dirIdx);
            move('B',dirIdx);
        }else{
            move('B',dirIdx);
            move('R',dirIdx);
        }
    }

    static char firstBallColor(int dirIdx){
        if(0 == dirIdx){//상
            return red.row < blue.row ? 'R' : 'B';
        }else if(1 == dirIdx){//하
            return red.row > blue.row ? 'R' : 'B';
        }else if(2 == dirIdx){//좌
            return red.col < blue.col ? 'R' : 'B';
        }else{//우
            return red.col > blue.col ? 'R' : 'B';
        }
    }

    static void move(char color, int dirIdx){
        int nRow, nCol;
        int row, col;

        if('R' == color){
            row = red.row;
            col = red.col;
        }else{
            row = blue.row;
            col = blue.col;
        }

        nRow = row + dr[dirIdx];
        nCol = col + dc[dirIdx];

        char nVal = map[nRow][nCol];
        if('O' == nVal){
            map[row][col] ='.';
            if('R' == color){
                red.setRowCol(nRow,nCol);
            }else{
                blue.setRowCol(nRow,nCol);
            }
        }else if(nVal == '.'){
            map[nRow][nCol] =color;
            map[row][col] ='.';
            if('R' == color){
                red.setRowCol(nRow,nCol);
            }else{
                blue.setRowCol(nRow,nCol);
            }
            move(color, dirIdx);
        }
    }

    static void printBall(){
        System.out.println("RED row : "+ red.row+" col : "+red.col);
        System.out.println("BLUE row : "+ blue.row+" col : "+blue.col);
        System.out.println("EXIT row : "+ exit.row+" col : "+exit.col);
    }

    static void init(){

        N = sc.nextInt();
        M = sc.nextInt();
        for(int row = 1; row<N+1; row++){

            String input = sc.next();
            for(int col =1; col<M+1; col++){
                char i = input.charAt(col-1);
                map[row][col] = i;
                if('R' == i)
                    red = new Point(row,col);
                else if('B' == i){
                    blue = new Point(row,col);
                }else if('O' == i){
                    exit = new Point(row,col);
                }
            }
        }
    }
    static void print(){
        for(int row =1 ; row<N+1; row++){
            for(int col=1; col<M+1; col++){
                System.out.print(map[row][col]+ " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
