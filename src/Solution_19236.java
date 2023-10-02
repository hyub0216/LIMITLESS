import java.util.ArrayList;
import java.util.Scanner;

public class Solution_19236 {

    static class Filter{

        int row;
        int col;

        ArrayList<Dust> dusts;

        Filter(int pRow, int pCol){
            row = pRow;
            col = pCol;
            dusts = new ArrayList<>();
        }

        void setDust(){
            for(Dust dust : dusts){
                int r = dust.row;
                int c = dust.col;
                int v = dust.val;
                map[r][c] = v;
            }
        }
    }

    static class UpFilter extends Filter {

        UpFilter(int pRow, int pCol) {
            super(pRow, pCol);
        }

        void filter(){
            for(int r =1; r < row+1; r++){
                for(int c=1; c< C+1; c++){
                    int nRow;
                    int nCol;
                    int val = map[r][c];
                    // 시계반대방향
                    if(r == row && val != -1 && c != C){ //맨 아래
                        nRow = r+dr[3];//오른쪽으로
                        nCol = c+dc[3];
                        Dust dust = new Dust(nRow,nCol,val);
                        dusts.add(dust);
                    }else if(r ==1 && val != -1 && c != 1){// 맨 위
                        nRow = r+dr[2];//왼쪽으로
                        nCol = c+dc[2];
                        Dust dust = new Dust(nRow,nCol,val);
                        dusts.add(dust);
                    }else if(c == C && val != -1 && r != 1)//오른쪽
                    {
                        nRow = r + dr[0];//위로
                        nCol = c + dc[0];
                        Dust dust = new Dust(nRow,nCol,val);
                        dusts.add(dust);
                    }else if(c == col && val != -1 && r < row-1){//왼쪽
                        nRow = r + dr[1];//아래로
                        nCol = c + dc[1];
                        Dust dust = new Dust(nRow,nCol,val);
                        dusts.add(dust);
                    }else if(c == col && val != -1 && r == row -1){//필터통과
                        nRow = r + dr[4];
                        nCol = c + dc[4];
                        Dust dust = new Dust(nRow,nCol,0);
                        dusts.add(dust);
                    }
                }
            }
            setDust();
        }
    }
    static class DownFilter extends Filter{

        DownFilter(int pRow, int pCol) {
            super(pRow, pCol);
        }

        void filter(){
            for(int r =row; r < R+1; r++){
                for(int c=1; c< C+1; c++){
                    int nRow;
                    int nCol;
                    int val = map[r][c];
                    if(r == R && val != -1 && c != col){ //맨 아래
                        nRow = r+dr[2];//왼쪽
                        nCol = c+dc[2];
                        Dust dust = new Dust(nRow,nCol,val);
                        dusts.add(dust);
                    }else if(r ==row && val != -1 && c !=C){// 맨 위
                        nRow = r+dr[3];//오른쪽
                        nCol = c+dc[3];
                        Dust dust = new Dust(nRow,nCol,val);
                        dusts.add(dust);
                    }else if(c == C && val != -1 && r != R)//오른쪽
                    {
                        nRow = r + dr[1];//아래
                        nCol = c + dc[1];
                        Dust dust = new Dust(nRow,nCol,val);
                        dusts.add(dust);
                    }else if(c == col && val != -1 && r > row+1){//왼쪽
                        nRow = r + dr[0];//위
                        nCol = c + dc[0];
                        Dust dust = new Dust(nRow,nCol,val);
                        dusts.add(dust);
                    }else if(c == col && val != -1 && r == row +1){// 필터통과
                        nRow = r + dr[5];
                        nCol = c + dc[5];
                        Dust dust = new Dust(nRow,nCol,0);
                        dusts.add(dust);
                    }
                }
            }
            setDust();
        }
    }


    static class Dust{
        int row;
        int col;
        int val;
        ArrayList<Dust> nearDusts;

        Dust(int pRow, int pCol, int pVal){
            row = pRow;
            col = pCol;
            val = pVal;
            nearDusts = new ArrayList<>() ;
        }

        void spread(){
            for(int di = 0; di<4; di++){
                int nRow = row + dr[di];
                int nCol = col + dc[di];
                if(nRow > 0 && nRow < R+1 && nCol > 0 && nCol < C+1){
                    int nDustMount = map[nRow][nCol];
                    if(nDustMount != -1){
                        Dust nDust = new Dust(nRow, nCol,0);
                        nearDusts.add(nDust);
                    }
                }
            }
            int spreadSize = nearDusts.size();
            int nearDustVal = val/5;
            for(Dust dust : nearDusts){
                resultMap[dust.row][dust.col] += nearDustVal;
            }
            val = val -(nearDustVal)*spreadSize;
            resultMap[row][col] +=val;
        }
    }

    //상 하 좌 우 하우 상우
    static int[] dr = {-1,1,0,0,1,-1};
    static int[] dc = {0,0,-1,1,1,1};
    static int R,C,T;

    static UpFilter upFilter;
    static DownFilter downFilter;

    static int[][] resultMap = new int[51][51];
    static int[][] map = new int[51][51];
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        init();
        for(int i=0; i<T; i++){
            spreadDust();
            FilterSimulation();
        }
        System.out.println(calcDustTotal());
    }

    static int calcDustTotal(){
        int sum =2;
        for(int row=1; row<R+1; row++){
            for(int col=1; col<C+1; col++){
                sum += map[row][col];
            }
        }
        return sum;
    }
    static void FilterSimulation(){
        copyMap();
        upFilter.filter();
        downFilter.filter();
    }

    static void spreadDust(){
        initResultMap();
        for(int row=1; row<R+1; row++){
            for(int col=1; col<C+1; col++){
                int dustMount = map[row][col];
                if(dustMount > 0){
                    Dust dust = new Dust(row,col,dustMount);
                    dust.spread();
                }
            }
        }
    }

    static void printResultMap(){
        for(int row = 1; row <R+1; row++){
            for(int col =1; col< C+1; col++){
                System.out.print(resultMap[row][col] + " ");
            }
            System.out.println();
        }
    }

    static void initResultMap(){
        for(int row = 1; row <R+1; row++){
            for(int col =1; col< C+1; col++){
               resultMap[row][col] = 0;
               resultMap[upFilter.row][upFilter.col] =-1;
               resultMap[downFilter.row][downFilter.col] = -1;
            }
        }
    }

    static void copyMap(){
        for(int row = 1; row <R+1; row++){
            for(int col =1; col< C+1; col++){
                map[row][col] = resultMap[row][col];
            }
        }
    }

    static void init(){
        R = sc.nextInt();
        C = sc.nextInt();
        T = sc.nextInt();

        for(int row = 1; row <R+1; row++){
            for(int col =1; col< C+1; col++){
                int val = sc.nextInt();
                map[row][col] = val;
                if(val == -1 ){
                    if(upFilter == null)
                        upFilter = new UpFilter(row,col);
                    downFilter = new DownFilter(row,col);
                }
            }
        }
    }
    static void printMap(){
        for(int row = 1; row <R+1; row++){
            for(int col =1; col< C+1; col++){
                System.out.print(map[row][col]+" ");
            }
            System.out.println();
        }
    }
}
