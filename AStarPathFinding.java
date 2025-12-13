/***
 * I Tested the map with 3 maps FileName - map_data1.txt, map_data2.txt, map_data3.txt
 * ------------------------------------------------------------------------------------
 * ---TESTING MAPS INCLUDED---
 * ---------------------------------------
 * >>> javac AStarPathFinding.java
 * >>> java AStarPathFinding map_data2.txt (Should Work)
 *-----------------------------------------
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.BiFunction;

public class AStarPathFinding {

    private List<List<Integer>> mPathMap;
    private Node mStart;
    private Node mGoal;

    public AStarPathFinding(){
        mPathMap = new ArrayList<>();
        mStart = null;
        mGoal = null;
    }

    public List<Node> reconstruct_path(Map<Node, Node> cameFrom, Node current){
        List<Node> total_path = new ArrayList<>();
        total_path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            total_path.add(current);
        }
        // reverse the path to make from the start to goal
        Collections.reverse(total_path);
        return total_path;
    }

    static final int[][] SIDE_DIRECTIONS = new int[][]{
            { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    public List<Node> get_neighbors(Node current) {
        List<Node> neighbors = new ArrayList<>();
        for (int i = 0; i < SIDE_DIRECTIONS.length; ++i) {
            Node node = new Node(current.x + SIDE_DIRECTIONS[i][0], current.y + SIDE_DIRECTIONS[i][1]);
            neighbors.add(node);
        }
        return neighbors;
    }

    public boolean canMoveTo(Node next) {
        // returns false when it hits the wall or can't move to
        if (next.x < 0 || next.y < 0) {
            return false;
        }
        else if(next.y >= mPathMap.size() || next.x >= mPathMap.get(next.y).size()) {
            return false;
        }
        // 0: wall, 1: road
        return mPathMap.get(next.y).get(next.x) == 1;
    }

    public List<Node> searchAStar(Node start, Node goal, BiFunction<Node, Node, Integer> heuristic){
        List<Node> openSet = new ArrayList<>();
        openSet.add(start);

        Map<Node, Node> cameFrom = new HashMap<>();

        Map<Node, Integer> gScores = new HashMap<>();
        gScores.put(start, 0);

        Map<Node, Integer> fScores = new HashMap<>();
        fScores.put(start, heuristic.apply(start, goal));
        while (!openSet.isEmpty()) {
            // The first index should be the lowest fScored node
            Node current = openSet.get(0);
            if (current.equals(goal)) {
                return reconstruct_path(cameFrom, current);
            }
            openSet.remove(0);

            List<Node> neighbors = get_neighbors(current);
            for (Node neighbor : neighbors) {
                if (canMoveTo(neighbor)) {
                    int tentative_gScore = gScores.getOrDefault(current, Integer.MAX_VALUE) + heuristic.apply(current, neighbor);
                    if (tentative_gScore < gScores.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                        // found a new way or better way than previous path
                        cameFrom.put(neighbor, current);
                        gScores.put(neighbor, tentative_gScore);
                        fScores.put(neighbor, tentative_gScore + heuristic.apply(neighbor, goal));
                        if (!openSet.contains(neighbor)) {
                            openSet.add(neighbor);
                        }
                    }
                }
            }
            // sort by fScore in ascending order
            openSet.sort(Comparator.comparingInt(n -> fScores.getOrDefault(n, Integer.MAX_VALUE)));
        }
        return null;
    }

    public void printMap() {
        for(List<Integer> line : mPathMap) {
            for(Integer val : line) {
                System.out.print(val == 0 ? 'O' : ' ');
            }
            System.out.println();
        }
    }

    public void printPath(List<Node> path) {
        List<String> pathRoute = new ArrayList<>();
        for(List<Integer> line : mPathMap) {
            StringBuffer sb = new StringBuffer();
            for(Integer val : line) {
                if( val == 0 ) {
                    sb.append("O");
                }
                else if(val == 1 ) {
                    sb.append(" ");
                }
            }
            pathRoute.add(sb.toString());
        }

        // print the shortest path as '+'
        for(Node node : path) {
            if( node.y < pathRoute.size() ){
                if(node.x < pathRoute.get(node.y).length() ) {
                    StringBuilder sb = new StringBuilder(pathRoute.get(node.y));
                    sb.setCharAt(node.x, '+');
                    pathRoute.set(node.y, sb.toString());
                }
            }
        }
        for(String line : pathRoute) {
            System.out.println(line);
        }
    }

    public static Node generateNode(String line){
        Node node = null;
        String[] nodeStr = line.split(" ");
        if( nodeStr.length == 2) {
            int y = Integer.parseInt(nodeStr[0]);
            int x = Integer.parseInt(nodeStr[1]);
            node  = new Node(x, y);
        }
        return node;
    }

    public static AStarPathFinding generatePathFinding(String fileName) {
        AStarPathFinding pathFinding = new AStarPathFinding();
        if( fileName.length() > 0 ) {
            try {
                File file = new File(fileName);
                Scanner scanner = new Scanner(file);
                while(scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    if (pathFinding.mStart == null) {
                        pathFinding.mStart = generateNode(data);
                        System.out.println(data);
                    }
                    else if (pathFinding.mGoal == null) {
                        pathFinding.mGoal = generateNode(data);
                        System.out.println(data);
                    }
                    else {
                        List<Integer> line = new ArrayList<>();
                        for(char ch : data.toCharArray()) {
                            line.add(Integer.parseInt(String.valueOf(ch)));
                        }
                        pathFinding.mPathMap.add(line);
                    }
                }
            }
            catch(FileNotFoundException e){
                System.out.println(e);
                e.printStackTrace();
            }
        }
        // set a default map if no file found or error happens
        if(pathFinding.mStart == null || pathFinding.mGoal == null || pathFinding.mPathMap.size() == 0) {
            pathFinding.mStart = new Node(3, 0);
            pathFinding.mGoal = new Node(5, 12);
            pathFinding.mPathMap.clear();
            // 0: wall, 1: road
            pathFinding.mPathMap.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0, 0)));
            pathFinding.mPathMap.add(new ArrayList<>(Arrays.asList(0, 1, 1, 1, 1, 1, 0, 1, 0)));
            pathFinding.mPathMap.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 1, 0, 1, 0)));
            pathFinding.mPathMap.add(new ArrayList<>(Arrays.asList(0, 1, 1, 1, 0, 1, 1, 1, 0)));
            pathFinding.mPathMap.add(new ArrayList<>(Arrays.asList(0, 1, 0, 0, 0, 1, 0, 0, 0)));
            pathFinding.mPathMap.add(new ArrayList<>(Arrays.asList(0, 1, 1, 1, 1, 1, 1, 1, 0)));
            pathFinding.mPathMap.add(new ArrayList<>(Arrays.asList(0, 1, 0, 0, 0, 0, 0, 1, 0)));
            pathFinding.mPathMap.add(new ArrayList<>(Arrays.asList(0, 1, 1, 1, 1, 1, 1, 1, 0)));
            pathFinding.mPathMap.add(new ArrayList<>(Arrays.asList(0, 1, 0, 1, 0, 1, 0, 0, 0)));
            pathFinding.mPathMap.add(new ArrayList<>(Arrays.asList(0, 1, 0, 1, 0, 1, 0, 1, 0)));
            pathFinding.mPathMap.add(new ArrayList<>(Arrays.asList(0, 1, 0, 1, 0, 0, 0, 1, 0)));
            pathFinding.mPathMap.add(new ArrayList<>(Arrays.asList(0, 1, 1, 1, 1, 1, 1, 1, 0)));
            pathFinding.mPathMap.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 1, 0, 0, 0)));
        }
        return pathFinding;
    }

    public static void main(String[] args) {
        AStarPathFinding pathFinding = generatePathFinding(args.length > 0 ? args[0] : "");
        System.out.println("Print a map");
        pathFinding.printMap();

        List<Node> manhattanWay = pathFinding.searchAStar(pathFinding.mStart, pathFinding.mGoal, new ManhattanHeuristic());
        System.out.println("Print path with Manhattan Heuristic");
        pathFinding.printPath(manhattanWay);

        List<Node> euclideanWay = pathFinding.searchAStar(pathFinding.mStart, pathFinding.mGoal, new EuclideanHeuristic());
        System.out.println("Print path with Euclidean Heuristic");
        pathFinding.printPath(euclideanWay);
    }
}