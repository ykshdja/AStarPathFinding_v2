public class Node {
    public int x;
    public int y;

    public Node() {
        x = 0;
        y = 0;
    }

    public Node(int _x, int _y) {
        x = _x;
        y = _y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Node) {
            Node other = (Node) obj;
            return x == other.x && y == other.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return x << 16 + y;
    }
}
