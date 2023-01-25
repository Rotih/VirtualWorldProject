import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.*;
import java.util.stream.*;

public class AStarPathingStrategy
        implements PathingStrategy
{
    private class Node {
        private Point position;
        private Node prevNode;
        private int g;
        private int h;
        private int f;


        public Node(Point position, Node prevNode, int g, int h, int f) {
            this.position = position;
            this.prevNode = prevNode;
            this.g = g;
            this.h = h;
            this.f = f;

        }

        public int getF() { return f; }
        public int getG() { return g; }
        public void setF(int F){ this.f = F; }
        public void setH(int H){ this.f = H; }
        public void setG(int G){ this.f = G; }
        public void setPrev(Node prev){ this.prevNode = prev; }
        public Point getPosition() { return position; }
        public Node getPrevNode() { return prevNode; }

        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (other.getClass() != getClass()) {
                return false;
            }
            Node a = ((Node)other);
            return a.getPosition().equals(this.position);
        }
    }

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        //NOTE: I changed pathingstrategy so that the first neighbors it checks are the X direction neighbors.
        /*
        System.out.println(end.getX());
        System.out.println(end.getY());
        */
        List<Point> path = new LinkedList<>();
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparing(Node::getF));
        Map<Point, Node> closed = new HashMap<>();

        int g = 0;
        int h = start.heuristic(end);
        int f = start.heuristic(end);

        //Step 2 of A* Algorithm
        Node current = new Node(start, null, g, h, f);
        open.add(current);

        while (!open.isEmpty()) {
            /*
            System.out.println(open.peek().getPosition().getX()+ " "+ open.peek().getPosition().getY());
            System.out.println();
             */
            // Step 2 / Step 5
            current = open.poll();
            if (withinReach.test(current.getPosition(), end))
            {
                construct(path, current);
                break;
            }


            List<Point> neighbors = potentialNeighbors
                    .apply(current.getPosition())
                    .filter(canPassThrough)
                    .collect(Collectors.toList());
            //Step 3 of A* Algorithm
            if (!neighbors.isEmpty()){
                for (Point neighbor : neighbors) {
                    if(!closed.containsKey(neighbor)){
                        //a & b
                        int newG = g + 1;
                        int newH = neighbor.heuristic(end);
                        int newF = newH + newG;

                        Node next = new Node(neighbor, current, newG, newH, newF);

                        if (!open.contains(next)) {
                            open.add(next);
                        }
                        if (open.contains(next)){
                            for (Node node : open){
                                if(node.equals(next)){
                                    //c
                                    if(next.getG() < node.getG()){
                                        node.setPrev(current);
                                        node.setF(newF);
                                        node.setG(newG);
                                        node.setH(newH);
                                    }
                                }
                            }
                        }
                    }
                }
                //Step 4
                closed.put(current.getPosition(), current);
            }
            // Was using ArrayLists before refactoring to Priority Queue + Hashmap
            //Collections.sort(open, Comparator.comparing(Node::getF));
        }
        return path;
    }

    private void construct(List<Point> path, Node node) {
        Node previous = node.getPrevNode();
        Node current = node;
        while (previous != null) {
            path.add(current.getPosition());
            current = previous;
            previous = previous.getPrevNode();
        }
    }



}