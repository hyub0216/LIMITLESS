import java.util.*;

public class Solution_23291 {

    static class Fish implements Comparable<Fish>{
        int row;
        int col;
        int dir;
        int number;

        Shark bfShark;
        int [][] bfMap = new int[5][5];
        HashMap<Integer, Fish> bfFishBox;

        Fish(){
            bfShark = new Shark();
            bfFishBox = new HashMap<>();
        }
        Fish(int pRow, int pCol, int pDir, int pNumber){
            row = pRow;
            col = pCol;
            dir = pDir;
            number = pNumber;
            bfShark = new Shark();
            bfFishBox = new HashMap<>();
        }

        void copyFish(Fish pFish){
            row = pFish.row;
            col = pFish.col;
            dir = pFish.dir;
            number = pFish.number;
        }

        void setBfNode(Shark pBfShark){
            bfShark.copy(pBfShark);
            bfFishBox.clear();
            for(int i=1; i<17; i++){
                Fish fish = fishBox.get(i);
                if(fish != null){
                    bfFishBox.put(i,fish);
                }
            }
            for(int row=1; row<5; row++){
                for(int col=1; col<5; col++){
                    bfMap[row][col] = map[row][col];
                }
            }
        }

        void move(Fish fish){
            if(canMove()){
                int nRow = row +dr[dir];
                int nCol = col +dc[dir];
                int nNumber = fish.bfMap[nRow][nCol];

                if(nNumber != 0 ){// 이동할 곳이 비여있지 않다면.
                    Fish nFish = fish.bfFishBox.get(nNumber);//바꿀 위치의 물고기를 가져와서 갱신해줌.
                    nFish.setData(row,col, nFish.dir);
                    fish.bfFishBox.put(nNumber, nFish);
                }else
                    bfMap[row][col] = 0;

                this.setData(nRow, nCol, dir);//현재 물고기의 정보를 갱신해줌.
                fish.bfFishBox.put(number,this);
//                System.out.println("#### NUM : "+number );
                setMap();
            }
        }
        boolean canMove(){
            for(int i = dir; i<17; i++){
                int idx = i > 8 ? i-8 : i;
                int nRow = row+dr[idx];
                int nCol = col+dc[idx];
                if(nRow > 0 && nRow < 5 && nCol >0 && nCol <5 ){
                    int nNumber = map[nRow][nCol];
                    if(nNumber != -1){//상어가 있거나 경계를 벗어나는거라면 45도씩 이동방향을 바꿔줌
                        dir = idx;
                        return true;
                    }
                }
            }
            return false;
        }

        void setData(int pRow, int pCol, int pDir){
            row = pRow;
            col = pCol;
            dir = pDir;
        }

        @Override
        public int compareTo(Fish o) {
            if(number > o.number){
                return 1;
            }else if(number < o.number)
                return -1;
            else
                return 0;
        }

        @Override
        public String toString() {
            return "Fish{" +
                    "row=" + row +
                    ", col=" + col +
                    ", dir=" + dir +
                    ", number=" + number +
                    '}';
        }
    }

    static class Shark{

        int totalEatNumber;
        int row;
        int col;
        int dir;

        Shark() {
            totalEatNumber = 0;
        }

        public void copy(Shark pShark){
            row = pShark.row;
            col = pShark.col;
            dir = pShark.dir;
            totalEatNumber = pShark.totalEatNumber;
        }

        @Override
        public String toString() {
            return "Shark{" +
                    "totalEatNumber=" + totalEatNumber +
                    ", row=" + row +
                    ", col=" + col +
                    ", dir=" + dir +
                    '}';
        }

        void eatFish(Fish fish){
            System.out.println("### EAT FISH : "+fish.toString());
            int fRow = fish.row;
            int fCol = fish.col;
            fish.bfMap[row][col] =0;// 원래 상어자리는 0으로 표기
            row = fRow;
            col = fCol;
            fish.bfMap[row][col] = -1;//해당 물고기 자리로 이동 (상어는 -1로 표기.)
            totalEatNumber += fish.number;//먹은 물고기 총수
            dir = fish.dir;
            System.out.println(this);
            fish.bfFishBox.remove(fish.number);//물고기 상자에서 제거
        }

        boolean canMove(int [][]pMap){
            int nRow = row+dr[dir];
            int nCol = col+dc[dir];
            if(nRow > 0 && nRow < 5 && nCol >0 && nCol <5 ){
                int nNumber = pMap[nRow][nCol];
                if(nNumber != 0){
                    return true;
                }
            }
            return false;
        }
    }

    static HashMap<Integer, Fish> fishBox = new HashMap<>();

    // 1부터 순서대로 ↑, ↖, ←, ↙, ↓, ↘, →, ↗
    static int []dr ={0,-1,-1,0,1,1,1,0,-1};
    static int []dc ={0,0,-1,-1,-1,0,1,1,1};

    static int [][]map = new int [5][5];

    static Scanner sc = new Scanner(System.in);
    static Stack<Fish> fishStack = new Stack<>();

    static int max = 0;

    public static void main(String[] args) {
        init();
        Shark shark = new Shark();
        int fishNumber = map[1][1];
        Fish fish = fishBox.get(fishNumber);
        fish.setBfNode(shark);
        fishStack.push(fish);
        Simulation();
        System.out.println(max);
    }

    static void initData(int [][]pMap, HashMap<Integer, Fish> bfFishBox){
        fishBox.clear();
        for(int i=1; i<17; i++){
            Fish fish = bfFishBox.get(i);
            if(fish != null){
                fishBox.put(i,fish);
            }
        }
        for(int row=1; row<5; row++){
            for(int col=1; col<5; col++){
                map[row][col] = pMap[row][col];
            }
        }
    }

    static void Simulation(){

        while(fishStack.size() > 0){
            Fish fish = fishStack.pop();// 물고기 한개를 꺼내서

            System.out.println();
            System.out.println("### BF EAT");
            printMap(fish.bfMap);
            fish.bfShark.eatFish(fish);// 물고기를 먹는다.

            printMap(fish.bfMap);
            moveFish(fish);//물고기들이 이동한다.
            System.out.println("### AFTER MOVE");
            printMap(fish.bfMap);
            DFS(fish);// depth First Search gogo
        }
    }

    static void DFS(Fish fish){
        if(fish.bfShark.canMove(fish.bfMap)){
            for(int idx =1; idx<4; idx++){ // 같은 방향으로 한칸 이상 이동할수 있다.
                int nRow = fish.bfShark.row + (dr[fish.bfShark.dir]*idx);
                int nCol = fish.bfShark.col + (dc[fish.bfShark.dir]*idx);
                if(nRow > 0 && nRow <5 && nCol > 0 && nCol < 5){// 지도를 넘어가지 않는 범위내에서
                    int nNumber = map[nRow][nCol];
                    if(nNumber == 0){// 만약 빈공간이면 상어는 이동할수 없다.
                        break;
                    }else{// 빈공간이 아니라면 숫자를 통해 물고기 정보를 가져온다.
                        Fish nFish = new Fish();
                        nFish.copyFish(fishBox.get(nNumber));
                        nFish.setBfNode(fish.bfShark);// 해당 물고기를 먹기 직전의 상어정보를 세팅하고 stack에 넣는다.
                        fishStack.push(nFish);
                    }
                }
            }
        }else{ // 더이상 이동할 공간이 없다면, 먹은 물고기의 합을 구한다.
            System.out.println("TOTAL EAT : "+fish.bfShark.totalEatNumber);
            if(max < fish.bfShark.totalEatNumber)
                max = fish.bfShark.totalEatNumber;
        }
    }

    static void setMap(){
        for(int idx =1; idx < 17; idx ++){
            Fish fish = fishBox.get(idx);
            if(fish != null){
                map[fish.row][fish.col] = fish.number;
            }
        }
    }

    static void moveFish(Fish pFish){
        for(int idx =1; idx < 17; idx ++){
            Fish fish = pFish.bfFishBox.get(idx);
            if(fish != null){
                fish.move(fish);
            }
        }
    }
    static void init(){
        for(int row =1; row<5; row++){
            for(int col=1; col<5; col++){
                int number = sc.nextInt();
                int dir = sc.nextInt();
                map[row][col] = number;
                Fish fish = new Fish(row, col, dir, number);
                fishBox.put(number,fish);
            }
        }
    }

    static void printMap(int [][]pMap){
        for(int row=1; row<5; row++){
            for(int col=1; col<5; col++){
                System.out.print(pMap[row][col] + " ");
            }
            System.out.println();
        }
    }

    static void printFishBox(){
        System.out.println("#### FISH BOX ####");

        for(int idx = 1 ; idx< 17; idx++){
            Fish fish = fishBox.get(idx);
            if(fish != null){
                System.out.println(fish.toString());
            }
        }
    }
}
