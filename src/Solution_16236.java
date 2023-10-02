import java.util.*;

public class Solution_16236 {

    static class Shark{
        int row;
        int col;
        int size;
        int eatCnt;

        Shark(int pRow, int pCol, int pSize)
        {
            this.row = pRow;
            this.col = pCol;
            this.size = pSize;
            this.eatCnt = 0;
        }

        void eatFish(Fish fish){
            map[row][col] = 0; // 상어의 원래 위치는 0으로
//            print();
            row = fish.row;
            col = fish.col;
            map[row][col] = 9; // 상어를 이동시킨다.
//            System.out.println("### SHARK EAT FISH row : "+fish.row + " col : "+fish.col +" FISH SIZE : "+fish.size +" FISH DIST : " +fish.dist+" SHARK SIZE : "+size + " EAT CNT : "+eatCnt);

            eatCnt ++;
            if(eatCnt == size){
                size ++;
                eatCnt =0;
            }

            moveSec += fish.dist;// 이동 시간은 물고기와의 거리만큼 증가.
        }

        void print(){
            System.out.println("#### SHARK");
            System.out.println("ROW : "+row + " COL : "+col+ " SIZE : "+size +" EAT CNT : "+eatCnt);
        }

        // 다음 인덱스로 이동가능할지.
        boolean canMove(Fish fish){
            return size >= fish.size ? true : false;
        }

        boolean canEat(Fish fish){
            return size > fish.size && fish.size >0 ? true : false;
        }
    }

    static class Fish implements Comparable<Fish>{
        int row;
        int col;
        int dist;

        int size;

        Fish(int pRow, int pCol, int pDist, int pSize){
            this.row = pRow;
            this.col = pCol;
            this.dist = pDist;
            this.size = pSize;
        }

        void print(){
            System.out.println("ROW : "+row + " COL : "+col + " dist "+dist+ " size : "+size);
        }

        @Override
        public int compareTo(Fish o) {
            if(this.dist > o.dist)
                return 1;
            else if(this.dist < o.dist)
                return -1;
            else {
                if(this.row > o.row)
                    return 1;
                else if(this.row < o.row)
                    return -1;
                else{
                    if(this.col > o.col)
                        return 1;
                    else if(this.col < o.col)
                        return -1;
                }
            }
            return 0;
        }
    }

    static ArrayList<Fish> fishBox = new ArrayList<>();

    static Shark shark;
    static int N;
    static Scanner sc = new Scanner(System.in);
    static int[][] map = new int[21][21];
    static boolean[][] visit = new boolean[21][21];

    //상 좌 우 하
    static int []dr ={-1,0,0,1};
    static int []dc ={0,-1,1,0};

    static Queue<Fish> queue = new LinkedList<>();

    static int moveSec = 0;

    public static void main(String[] args) {
        init();
        simulation();
        System.out.println(moveSec);

    }

    static void printFishBox(){

        System.out.println();
        print();
        System.out.println("### FISH BOX LIST START ###");
        for(Fish fish : fishBox){
            fish.print();
        }
        System.out.println("### FISH BOX LIST END ###");
        System.out.println();
    }
    static boolean isSomethingToEat(){
        initVisit();// 방문체크를 초기화
        queue.clear();//queue도 비운다.
        fishBox.clear();// 물고기 상자도 비운다.
        visit[shark.row][shark.col] = true; // 상어최초 위치는 방문했다고 체크

        // 4방향의 인접한 노드를 체크 상좌우하 순으로 탐색
        for(int dirIdx =0; dirIdx < 4; dirIdx++){
            int nRow = shark.row + dr[dirIdx];
            int nCol = shark.col + dc[dirIdx];
            move(nRow,nCol,1);
            if(fishBox.size() > 0) //같은 depth 상좌우하 순으로 탐색시 먹을수 있는 물고기가 있다면 더이상의 탐색X
                break;
        }

        //가장 인접한 노드에 먹을수 있는 물고기가 없다면 BFS로 탐색시작
        if(fishBox.size() == 0){
            BFS();
        }

        return fishBox.size() > 0 ? true : false;
    }

    static void simulation(){

        while(isSomethingToEat()){//탐색하여 먹을수 있는 물고기가 있다면.
            Collections.sort(fishBox);
//            printFishBox();
            Fish fish = fishBox.get(0);// 가장 첫번째 물고기를 가져와서 먹는다.
            shark.eatFish(fish);
        }
    }

    static void initVisit(){
        for(int row =1; row<N+1; row++){
            for(int col =1; col<N+1; col++){
                visit[row][col] = false;
            }
        }
    }

    static void BFS(){
        while(queue.size() > 0){ // queue에 있는걸 하나씩 빼서 탐색
            Fish fish = queue.poll();

            for(int di =0; di<4; di++){// poll한 물고기의 인접 노드를 탐색한다.
                int nDist = fish.dist +1;
                int nRow = fish.row + dr[di];
                int nCol = fish.col + dc[di];
                move(nRow, nCol,nDist);
            }
        }
    }

    static void move(int nRow, int nCol, int nDist){

        if(validCheck(nRow,nCol)){
            // 다음 칸의 물고기 크기
            int nSize = map[nRow][nCol];

            // 물고기 객체 생성
            Fish nFish = new Fish(nRow,nCol,nDist,nSize);
            if(shark.canMove(nFish)){// 물고기 자리로 이동가능한지 비교( 상어 사이즈보다 작거나 같은 경우 만)
                queue.add(nFish);// 탐색을 위해 queue에 넣는다.
                visit[nRow][nCol] = true; // 방문한 노드는 체크
                if(shark.canEat(nFish)){//물고기 먹을수 있는지( 상어 사이즈보다 작은 경우 만)
                    int minDist;
                    if(fishBox.size() ==0 ){
                        minDist = nFish.dist;
                    }else{
                        minDist = fishBox.get(0).dist;
                    }

                    if(minDist == nFish.dist){
                        fishBox.add(nFish);
                    }
                }
            }
        }
    }
    static boolean validCheck(int nRow, int nCol)
    {
        return nRow > 0 && nRow < N+1 && nCol > 0 && nCol < N+1 && !visit[nRow][nCol] ? true : false;
    }

    static void collectionSort(){
        Fish fish = new Fish(1,1,4,9);
        fishBox.add(fish);
        fish = new Fish(1,2,3,9);
        fishBox.add(fish);
        fish = new Fish(1,3,2,9);
        fishBox.add(fish);
        fish = new Fish(1,4,3,9);
        fishBox.add(fish);
        fish = new Fish(4,1,3,9);
        fishBox.add(fish);
        fish = new Fish(4,2,2,9);
        fishBox.add(fish);
        fish = new Fish(4,3,1,9);
        fishBox.add(fish);
        fish = new Fish(4,4,2,9);
        fishBox.add(fish);
        System.out.println("### BEFORE SORT");
        for(Fish fish1 : fishBox){
            fish1.print();
        }
        System.out.println();
        System.out.println("### AFTER SORT");
        Collections.sort(fishBox);
        for(Fish fish1 : fishBox){
            fish1.print();
        }
    }

    static void print(){
        System.out.println("#### MOVE SEC : "+moveSec);
        for(int row =1; row<N+1; row++){
            for(int col=1; col<N+1; col++){
                System.out.print(map[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
        printVisit();
    }

    static void printVisit(){
        for(int row =1; row<N+1; row++){
            for(int col=1; col<N+1; col++){
                System.out.print(visit[row][col] == false ? 0 + " " : 1 + " ");
            }
            System.out.println();
        }
    }

    static void init(){
        N = sc.nextInt();
        for(int row =1; row<N+1; row++){
            for(int col=1; col<N+1; col++){
                int input = sc.nextInt();
                if(input == 9){
                    shark = new Shark(row,col,2);
                }
                map[row][col] =input;
            }
        }
    }
}
