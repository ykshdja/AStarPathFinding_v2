import java.util.function.BiFunction;

public class EuclideanHeuristic implements BiFunction<Node, Node, Integer> {
    public Integer apply(Node start, Node goal) {
        return (int)Math.sqrt(Math.pow(start.x - goal.x, 2) + Math.pow(start.y - goal.y, 2));
    }
}
