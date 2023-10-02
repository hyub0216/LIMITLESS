import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    static class Point{
        int row;
        int col;

        Point(int pRow, int pCol){
            row = pRow;
            col = pCol;
        }
    }
    static Queue<Point> queue = new LinkedList<>();
    static int[][] map = new int[4][4];
    static int []dc1 ={0,2,1,1};
    static int []dc2 ={0,3,3,2};
    public static void main(String[] args) {
        init();
        print();
        for(int i=0; i<4; i++)
        {

        }
        BFS();
    }

    static void BFS(){

        while(queue.size() > 0){
            Point point = queue.poll();
            int row = point.row;
            int col = point.col;

            int nRow = row+1;
            if(nRow < 4){
                int nCol1 = dc1[col];
                int nCol2 = dc2[col];
            }
        }

    }

    static void init(){
        int input =1;
        for(int row = 1; row<4; row++){
            for(int col =1; col<4; col++){
                map[row][col] = input;
                input ++;
            }
        }
    }
    static void print(){
        System.out.println();
        for(int row = 1; row<4; row++){
            for(int col =1; col<4; col++){
                System.out.print(map[row][col]+ " ");
            }
            System.out.println();
        }
    }
}
