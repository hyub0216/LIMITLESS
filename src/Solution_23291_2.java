import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class Solution_23291_2 {

    static class Fish{
        int row;
        int col;
        int number;
        int dir;
        int [][]map;
        HashMap<Integer,Fish> fishBox;

        Shark shark;

        Fish(){
            map = new int[5][5];
            fishBox = new HashMap<>();
            shark = new Shark();
        }
        Fish(int pRow, int pCol, int pNum, int pDir){
            map = new int[5][5];
            fishBox = new HashMap<>();
            shark = new Shark();
            row = pRow;
            col = pCol;
            number = pNum;
            dir = pDir;
        }

        Fish(int pRow, int pCol, int pNum, int pDir, int[][] pMap, HashMap<Integer,Fish> pFishBox, Shark pShark){
            //상어 세팅
            shark = new Shark();
            setShark(pShark);

            // 맵 세팅
            map = new int[5][5];
            setMap(pMap);

            // fishbox 세팅
            fishBox = new HashMap<>();
            setFishBox(pFishBox);

            row = pRow;
            col = pCol;
            number = pNum;
            dir = pDir;
        }

        void setShark(Shark pShark){
            shark.copy(pShark);
        }

        void setFishBox(HashMap<Integer,Fish> pFishBox){
            for(int key : pFishBox.keySet()){
                Fish pFish = new Fish();
                pFish.copy(pFishBox.get(key));
                fishBox.put(key, pFish);
            }
        }

        void setMap(int[][] pMap){
            // 맵 세팅
            for(int row=1; row<5; row++){
                for(int col=1; col<5; col++){
                    map[row][col] = pMap[row][col];
                }
            }
        }

        void move(){
            if(canMove()){
//                System.out.println("### MOVE "+this);
                int nRow = row +dr[dir];
                int nCol = col +dc[dir];
                int nNumber = globalMap[nRow][nCol];

                if(nNumber != 0 ){// 이동할 곳이 비여있지 않다면.
                    Fish nFish = globalFishBox.get(nNumber);//바꿀 위치의 물고기를 가져와서 갱신해줌.
                    nFish.setData(row,col, nFish.dir);
                    globalFishBox.put(nNumber, nFish);
                    globalMap[row][col] = nNumber;
                }else{//이동할 곳이 비어있다면.
                    globalMap[row][col] = 0;
                }

                setData(nRow, nCol, dir);//현재 물고기의 정보를 갱신해줌.
                globalFishBox.put(number,this);
                globalMap[nRow][nCol] = number;

//                printMap();
            }
        }
        void setData(int pRow, int pCol, int pDir){
            row = pRow;
            col = pCol;
            dir = pDir;
        }

        boolean canMove(){
            for(int i = dir; i<17; i++){
                int idx = i > 8 ? i-8 : i;
                int nRow = row+dr[idx];
                int nCol = col+dc[idx];
                if(nRow > 0 && nRow < 5 && nCol >0 && nCol <5 ){
                    int nNumber = globalMap[nRow][nCol];
                    if(nNumber != -1){//상어가 있거나 경계를 벗어나는거라면 45도씩 이동방향을 바꿔줌
                        dir = idx;
                        return true;
                    }
                }
            }
            return false;
        }

        void copy(Fish pFish){
            row = pFish.row;
            col = pFish.col;
            dir = pFish.dir;
            number = pFish.number;
        }

        @Override
        public String toString() {
            return "Fish{" +
                    "row=" + row +
                    ", col=" + col +
                    ", number=" + number +
                    ", dir=" + dir +
                    '}';
        }
    }

    static class Shark{
        int row;
        int col;
        int total;
        int dir;

        Shark(){

        }
        Shark(int pRow, int pCol, int pTotal, int pDir){
            row = pRow;
            col = pCol;
            total = pTotal;
            dir = pDir;
        }

        void copy(Shark pShark){
            row = pShark.row;
            col = pShark.col;
            total = pShark.total;
            dir = pShark.dir;
        }

        void Eat(Fish fish){
            total += fish.number; //물고기 번호만큼 total증가.
            globalMap[row][col] = 0; //상어가 원래있던 위치는 빈칸으로 설정
            row = fish.row;
            col = fish.col; // 상어 위치 변경.
            globalMap[row][col] = -1;

            dir = fish.dir; //상어 방향 변경
            globalFishBox.remove(fish.number); //먹힌 물고기는 이동하는 물고기 목록에서 제외시킴
        }

        boolean canMove(){
            int nRow = row+dr[dir];
            int nCol = col+dc[dir];
            if(nRow > 0 && nRow < 5 && nCol >0 && nCol <5 ){
                int nNumber = globalMap[nRow][nCol];
                if(nNumber != 0){
                    return true;
                }
            }
            return false;
        }
    }

    // 1부터 순서대로 ↑, ↖, ←, ↙, ↓, ↘, →, ↗
    static int []dr ={0,-1,-1,0,1,1,1,0,-1};
    static int []dc ={0,0,-1,-1,-1,0,1,1,1};

    static String []dir ={"#","↑", "↖", "←", "↙", "↓", "↘", "→", "↗" };
    static Scanner sc = new Scanner(System.in);
    static HashMap<Integer, Fish> globalFishBox = new HashMap<>();
    static int[][] globalMap = new int[5][5];
    static Shark globalShark = new Shark();
    static Stack<Fish> fishStack = new Stack<>();
    static int max =0;

    public static void main(String[] args) {
        init();

        // 이동을 하기전에 맵과 피시박스를 해당시점으로 설정해준다.
        // 스택에서 가져온 Fish의 맵과 피시박스값으로
        // setDataByFish(Fish fish);

        //초기 값은 1,1 물고기를 넣는걸로 시작한다.
        int fishNumber = globalMap[1][1];
        Fish firstFish = globalFishBox.get(fishNumber);
        Shark shark = new Shark(1,1,0,0);
        firstFish.setShark(shark);
        firstFish.setFishBox(globalFishBox);
        firstFish.setMap(globalMap);
        fishStack.push(firstFish);

        Simulation();

        System.out.println(max);
    }
    static void moveFish(){
        for(int number =1; number< 17; number++){
            if(globalFishBox.get(number) != null){
                Fish fish = new Fish();
                fish.copy(globalFishBox.get(number));
                fish.move();
            }
        }

    }

    static void Simulation(){

        while(fishStack.size() > 0){
            Fish fish = fishStack.pop();// 물고기 한개를 꺼내서
            System.out.println("TARGET "+fish.toString());
            setDataByFish(fish);
            System.out.println("BEFORE EAT");
            printMap();

            globalShark.Eat(fish);// 물고기를 먹는다.
            System.out.println("AFTER EAT");
            printMap();

            moveFish();//물고기들이 이동한다.
            System.out.println("AFTER MOVE");
            printMap();
            System.out.println();
            DFS(fish);// depth First Search gogo
        }
    }

    static void DFS(Fish fish){
        if(globalShark.canMove()){
            for(int idx =1; idx<4; idx++){ // 같은 방향으로 한칸 이상 이동할수 있다.
                int nRow = globalShark.row + (dr[globalShark.dir]*idx);
                int nCol = globalShark.col + (dc[globalShark.dir]*idx);
                if(nRow > 0 && nRow <5 && nCol > 0 && nCol < 5){// 지도를 넘어가지 않는 범위내에서
                    int nNumber = globalMap[nRow][nCol];
                    if(nNumber == 0){// 만약 빈공간이면 상어는 이동할수 없다.
                        break;
                    }else{// 빈공간이 아니라면 숫자를 통해 물고기 정보를 가져온다.
                        Fish nFish = new Fish();
                        nFish.copy(globalFishBox.get(nNumber));
                        nFish.setShark(globalShark);
                        nFish.setFishBox(globalFishBox);
                        nFish.setMap(globalMap);
                        fishStack.push(nFish);
                        System.out.println("PUSH "+nFish.toString());
                    }
                }
            }
        }else{ // 더이상 이동할 공간이 없다면, 먹은 물고기의 합을 구한다.
            System.out.println("Total : "+globalShark.total);
            if(max < globalShark.total)
                max = globalShark.total;
        }
    }

    static void printMap(){
        for(int row =1; row< 5; row++){
            for(int col=1; col<5; col++){
                int number = globalMap[row][col];
                Fish fish = globalFishBox.get(number);
                String direction =" ";
                if(fish != null){
                    direction = dir[fish.dir];
                }else if(row == globalShark.row && col == globalShark.col){
                    direction = dir[globalShark.dir];
                }
                System.out.print(globalMap[row][col] + " "+ direction+" ");
            }
            System.out.println();
        }
    }

    static void setDataByFish(Fish fish){
        setFishBox(fish.fishBox);
        setMap(fish.map);
        globalShark.copy(fish.shark);
    }

    static void setFishBox(HashMap<Integer,Fish> pFishBox){
        for(int key : pFishBox.keySet()){
            Fish pFish = new Fish();
            pFish.copy(pFishBox.get(key));
            globalFishBox.put(key, pFish);
        }
    }

    static void setMap(int[][] pMap){
        // 맵 세팅
        for(int row=1; row<5; row++){
            for(int col=1; col<5; col++){
                globalMap[row][col] = pMap[row][col];
            }
        }
    }
    static void init(){
        for(int row=1; row<5; row++){
            for(int col=1; col<5; col++){
                int number = sc.nextInt();
                int dir = sc.nextInt();
                Fish fish = new Fish(row,col,number,dir);
                globalFishBox.put(number,fish);
                globalMap[row][col] = number;
            }
        }
    }

}
