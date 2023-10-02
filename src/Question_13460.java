import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Question_13460 {

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

    static class Direction{
        int dirIdx;
        char[][] bfMap = new char[11][11];
        int moveCnt;

        Direction(int pDirIdx, char [][]pBfMap, int pMoveCnt){
            this.dirIdx = pDirIdx;
            this.moveCnt = pMoveCnt;
            for(int row =1; row<N+1; row++){
                for(int col=1; col<M+1; col++){
                    this.bfMap[row][col] = pBfMap[row][col];
                }
            }
        }
    }

    static Queue<Direction> queue = new LinkedList<>();
    static Scanner sc = new Scanner(System.in);
    static int N,M;
    static char[][] map = new char[11][11];
    static char[][] bkMap = new char[11][11];
    static int[] dr = {-1,1,0,0};
    static int[] dc = {0,0,-1,1};
    static Point red;
    static Point blue;
    static Point exit;
    static int moveCnt = -1;

    static int [][] validMap = {
            {0,0,1,1},
            {0,0,1,1},
            {1,1,0,0},
            {1,1,0,0}
    };

    //파란구슬 : B
    //빨간구슬 : R
    //구멍 : O
    //종료조건 : 빨간구슬이 구멍에 빠지면 성공
    //제한조건 : 빨간구슬과 파란구슬이 동시에 빠져도 실패
    public static void main(String[] args) {
        init();

        for(int i=0; i<4; i++){


            int nextRow = red.row + dr[i];
            int nextCol = red.col + dc[i];
            char nextVal = map[nextRow][nextCol];


            //빨간공의 다음이동위치가 벽이 아닌경우에만 이동
            if(nextVal != '#'){
                copyMap(bkMap);// 이동하기 전에 초기화
                simulation(i); //먼저 이동하고
                if(isSuccess()){// 성공했다면 다음 Depth를 탐색할 필요 없음
                    moveCnt =1;
                    break;
                }else if(!isGameOver()){// 게임종료가 아닌경우에만 다음 Depth 탐색을 위해 이동한 방향을 queue에 넣는다.
//                    System.out.println("### QUEUE ADD : "+i+ " MOVE CNT : 2" +" MOVE DIR : "+i);
//                    print();
                    Direction direction = new Direction(i,map,2);
                    queue.add(direction);
                }
            }
        }

        // 성공했다면 Queue에 들어간 값을 탐색할 필요 없음
        if(!isSuccess())
            BFS();

        System.out.println(moveCnt);
    }



    static void BFS(){

        //Queue에 데이터가 있을때까지 탐색
        while(queue.size() >0 && !isSuccess()){

            Direction dir = queue.poll();

            //queue에서 poll하여 이동했던 시점의 맵값을 가져와 세팅한다.
            if(dir.moveCnt < 11){
                for(int i=0; i<4; i++){
                    copyMap(dir.bfMap);
                    int nextRow = red.row + dr[i];
                    int nextCol = red.col + dc[i];
                    char nextVal = map[nextRow][nextCol];

//                    int validCheck = validMap[dir.dirIdx][i];
                    //전 이동이 위 였으면 다음이동은 좌또는 우

                    if(nextVal != '#'){// 이전에 이동한 방향과 다른 방향만 선택가능
//                        System.out.println("### QUEUE POLL : "+dir.dirIdx+ " MOVE CNT : "+dir.moveCnt +" MOVE DIR : "+i);
//                        System.out.println("### RED row : "+red.row + " col : "+red.col);
//                        System.out.println("### BLUE row : "+blue.row + " col : "+blue.col);
//                        print();
                        simulation(i);// 이동시켜준다.
//                        print();
                        int nextMoveCnt = dir.moveCnt+1;

                        if(isSuccess()){//성공이면 스탑
                            moveCnt = dir.moveCnt;
                            break;
                        }else if(!isGameOver()){//아니면 queue에 이동한 방향정보를 넣는다.
//                            System.out.println("### QUEUE ADD : "+i+ " MOVE CNT : "+nextMoveCnt);
//                            print();
                            Direction direction = new Direction(i,map,nextMoveCnt);
                            queue.add(direction);
                        }
                    }
                }
          }
        }
    }

    static void copyMap(char [][]bfMap){
        for(int row =1; row<N+1; row++){
            for(int col=1 ; col<M+1; col++){
                char color = bfMap[row][col];
                map[row][col] = color;

                if('R' == color)
                    red.setRowCol(row,col);
                else if('B' == color){
                    blue.setRowCol(row,col);
                }
            }
        }
    }

    static void initMap(){
        for(int row =1; row<N+1; row++){
            for(int col=1; col<M+1; col++){
                char color = bkMap[row][col];
                if('R' == color)
                    red.setRowCol(row,col);
                else if('B' == color){
                    blue.setRowCol(row,col);
                }
            }
        }
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
            return red.row < blue.row ? 'B' : 'R';
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

    static void init(){

        N = sc.nextInt();
        M = sc.nextInt();
        for(int row = 1; row<N+1; row++){

            String input = sc.next();
            for(int col =1; col<M+1; col++){
                char i = input.charAt(col-1);
                map[row][col] = i;
                bkMap[row][col] = i;
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

    static void bfMapPrint(char [][] map){
        for(int row =1 ; row<N+1; row++){
            for(int col=1; col<M+1; col++){
                System.out.print(map[row][col]+ " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
