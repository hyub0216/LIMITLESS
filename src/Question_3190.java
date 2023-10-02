import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Question_3190 {

    static public class Point{
        int row;
        int col;
        Point(int pRow, int pCol){
            row = pRow;
            col = pCol;
        };
    }

    static public class Order{
        String dir;
        int cnt;

        Order(String pDir, int pCnt) {
            dir = pDir;
            cnt = pCnt;
        }
    }

    static Scanner sc = new Scanner(System.in);
    static int N, K, L;
    static int [][] map = new int[101][101];

    static int dirIdx = 1;
    static ArrayList<Order> orders = new ArrayList<>();

    static HashMap<Integer, String> orderList = new HashMap<>();

    static int totalMoveCnt =0;
    static Stack<Point> sneak = new Stack<>();
    public static void main(String[] args) {
        N = sc.nextInt();
        initMap();
        K = sc.nextInt();
        initApple();
        L = sc.nextInt();
        saveOrder();

        Point point = new Point(1,1);
        sneak.add(point);

        simulation2();
        System.out.println(totalMoveCnt);
    }

    static void setDirIdx(String dir){

        /*
        * 1 : 오른쪽 col++
        * 2 : 아래 row ++
        * 3 : 왼쪽 col --
        * 4 : 위로 row --
        *  */
        if ("L".equals(dir)){
            dirIdx --;
        }else if("D".equals(dir)){
            dirIdx ++;
        }

        dirIdx = dirIdx % 4;

        if(dirIdx == 0)
            dirIdx = 4;
    }

    static void simulation2(){

        while(true){
            totalMoveCnt ++;
            //printMap();
            Point sneakHead = sneak.peek();
            int nHeadRow, nHeadCol;
            nHeadRow = sneakHead.row;
            nHeadCol = sneakHead.col;
            if(dirIdx == 1){
                nHeadCol ++; //오른쪽
            }else if(dirIdx == 2){
                nHeadRow ++; //아래
            }else if(dirIdx == 3){
                nHeadCol --; //왼쪽
            }else if(dirIdx == 4){
                nHeadRow --; //위
            }
            int nextVal = map[nHeadRow][nHeadCol];
            if(nextVal == 0){// 빈공간인 경우 그자리로 머리가 옮겨가고 꼬리도 한칸 당겨진다.

                // 뱀 머리넣기
                map[nHeadRow][nHeadCol] = 7;
                sneak.push(new Point(nHeadRow,nHeadCol));

                // 뱀꼬리 한칸 당기기
                map[sneak.get(0).row][sneak.get(0).col] =0;
                sneak.remove(0);

            }else if(nextVal == 1){ // 사과를 먹은경우 몸이 길어짐
                // 뱀 머리넣기
                map[nHeadRow][nHeadCol] = 7;
                sneak.push(new Point(nHeadRow,nHeadCol));

            }else{
                //gameover;
                break;
            }

            String dir = orderList.get(totalMoveCnt);
            if (!"".equals(dir)){
                setDirIdx(dir);
            }
        }
    }
    static void simulation(){

/*        boolean isStop = false;
        for(int i=0; i<L; i++){
            if(isStop){
               break;
            }
            Order order = orders.get(i);
            for(int j=0; j<order.cnt; j++)
            {
                printMap();
                totalMoveCnt ++;
                Point sneakHead = sneak.peek();
                int nHeadRow, nHeadCol;
                nHeadRow = sneakHead.row;
                nHeadCol = sneakHead.col;
                if(dirIdx == 1){
                    nHeadCol ++; //오른쪽
                }else if(dirIdx == 2){
                    nHeadRow ++; //아래
                }else if(dirIdx == 3){
                    nHeadCol --; //왼쪽
                }else if(dirIdx == 4){
                    nHeadRow --; //위
                }
                int nextVal = map[nHeadRow][nHeadCol];
                if(nextVal == 0){// 빈공간인 경우 그자리로 머리가 옮겨가고 꼬리도 한칸 당겨진다.

                    // 뱀 머리넣기
                    map[nHeadRow][nHeadCol] = 7;
                    sneak.push(new Point(nHeadRow,nHeadCol));

                    // 뱀꼬리 한칸 당기기
                    map[sneak.get(0).row][sneak.get(0).col] =0;
                    sneak.remove(0);

                }else if(nextVal == 1){ // 사과를 먹은경우 몸이 길어짐
                    // 뱀 머리넣기
                    map[nHeadRow][nHeadCol] = 7;
                    sneak.push(new Point(nHeadRow,nHeadCol));

                }else{
                    //gameover;
                    isStop = true;
                    break;
                }
            }
            setDirIdx(order.dir);
        }*/
    }

    static void saveOrder(){
        for(int i=0; i<L; i++){
            int cnt = sc.nextInt();
            String dir = sc.next();
            Order order = new Order(dir, cnt);
            orders.add(order);
            orderList.put(cnt,dir);
        }
    }

    static void initApple(){
        for(int i=0; i<K; i++)
        {
            int row,col;
            row = sc.nextInt();
            col = sc.nextInt();
            map[row][col] = 1;
        }
    }

    static void initMap(){
        for(int i =0; i<N+2; i++)
        {
            map[1][1] = 7;
            map[i][0] = 2;
            map[i][N+1] = 2;
            map[0][i] = 2;
            map[N+1][i] = 2;
        }
    }

    static void printMap(){
        System.out.println();
        System.out.println("### TOTAL CNT : "+totalMoveCnt +" DIR IDX : "+dirIdx);
        for(int row =0; row<N+2; row++){
            for(int col=0; col<N+2; col++){
                System.out.print(map[row][col] + " ");
            }
            System.out.println();
        }
    }
}
