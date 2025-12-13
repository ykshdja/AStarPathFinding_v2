import java.util.function.BiFunction;

public class ManhattanHeuristic implements BiFunction<Node, Node, Integer> {
    public Integer apply(Node start, Node goal) {
        return Math.abs(start.x - goal.x) + Math.abs(start.y - goal.y);
    }
}
