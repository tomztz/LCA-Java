import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DAG {

    private final int V;           // number of vertices in this digraph
    private int E;                 // number of edges in this digraph
    private ArrayList<Integer>[] adj;    // adj[v] = adjacency list for vertex v


    //Initializes an empty digraph with V vertices.

        DAG(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");
        this.V = V;
        this.E = 0;
        adj = (ArrayList<Integer>[]) new ArrayList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<Integer>();
        }
    }

    //Returns the number of vertices in this digraph.
    public int V() {
        return V;
    }
    //Returns the number of edges in this digraph.

    public int E() {
        return E;
    }

    // throw an IllegalArgumentException if contains cycle or invalid vertex
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    //Adds the directed edge v→w to this digraph.
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        E++;
        DirectedCycle checkCycle = new DirectedCycle(this);
        if(checkCycle.hasCycle()==true){
            adj[v].remove(w);
            E--;
            throw new IllegalArgumentException("edge from " + v + "to " + w +" not added because it froms a cycle");
        }

    }

    //Returns the vertices adjacent from vertex

    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    //Returns the reverse of the digraph.

    public DAG reverse() {
        DAG reverse = new DAG(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    public boolean hasPath(int x, int y)
    {
        DirectedDFS DAGDFS= new DirectedDFS(this, x);
        return DAGDFS.marked(y);
    }

    public ArrayList<Integer> lowestCommonAncestor(int p, int q)
    {
        ArrayList<Integer> lowestCommonAncestors = new ArrayList<>();
        if(p==q || p>=this.V || q>=this.V || p<0 || q<0) { throw new
                IllegalArgumentException("invalid vertices for LCA of " + p + " " + q); }
        for(int v:adj(p)){
            if(v==q){
                lowestCommonAncestors.add(p);
                return lowestCommonAncestors;
            }
        }
        for(int v:adj(q)){
            if(v==p){
                lowestCommonAncestors.add(q);
                return lowestCommonAncestors;
            }
        }

        int current= Integer.MAX_VALUE;


        DAG myDAGRev = this.reverse();
        DirectedDFS RevDFS = new DirectedDFS(myDAGRev,p);
        int Dist1, Dist2;

        for(int v = 0; v < this.V; v++)
        {


            if(RevDFS.marked(v) && hasPath(v, q))
            {
                Dist1 = bfs(v, p);
                Dist2 = bfs(v, q);

                if(Integer.max(Dist1, Dist2) < current)
                {
                    lowestCommonAncestors.clear();
                    lowestCommonAncestors.add(v);
                    current = Integer.max(Dist1, Dist2);
                }
                else if(Integer.max(Dist1, Dist2) == current)
                {
                    lowestCommonAncestors.add(v);
                    current = Integer.max(Dist1, Dist2);
                }
            }
        }
        return lowestCommonAncestors;
    }
    // breadth-first search from a single source x to s
    private int bfs(int x, int s)
    {

        if( x == s) { return 0; }
        else {
            Queue<Integer> q = new LinkedList<Integer>();
            int[] distTo = new int[this.V];
            boolean[] marked = new boolean[this.V];

            for (int v = 0; v < this.V(); v++)
            {   distTo[v] = Integer.MAX_VALUE;}

            distTo[x] = 0;
            marked[x] = true;
            q.add(x);

            while (!q.isEmpty()) {
                int v = q.remove();
                for (int w : this.adj(v)) {
                    if (!marked[w]) {

                        distTo[w] = distTo[v] + 1;
                        marked[w] = true;
                        q.add(w);
                    }
                }
            }
            return distTo[s];
        }
    }

    private class DirectedCycle {
        private boolean[] marked;        // marked[v] = has vertex v been marked?
        private int[] edgeTo;            // edgeTo[v] = previous vertex on path to v
        private boolean[] onStack;       // onStack[v] = is vertex on the stack?
        private Stack<Integer> cycle;    // directed cycle (or null if no such cycle)


        //Determines whether the digraph {@code G} has a directed cycle
            DirectedCycle(DAG G) {
            marked = new boolean[G.V()];
            onStack = new boolean[G.V()];
            edgeTo = new int[G.V()];
            for (int v = 0; v < G.V(); v++)
                if (!marked[v] && cycle == null) dfs(G, v);
        }

        // run DFS and find a directed cycle (if one exists)
        private void dfs(DAG G, int v) {
            onStack[v] = true;
            marked[v] = true;
            for (int w : G.adj(v)) {

                // short circuit if directed cycle found
                if (cycle != null) return;

                    // found new vertex, so recur
                else if (!marked[w]) {
                    edgeTo[w] = v;
                    dfs(G, w);
                }

                // trace back directed cycle
                else if (onStack[w]) {
                    cycle = new Stack<Integer>();
                    for (int x = v; x != w; x = edgeTo[x]) {
                        cycle.push(x);
                    }
                    cycle.push(w);
                    cycle.push(v);
                }
            }
            onStack[v] = false;
        }

        //Does the digraph have a directed cycle?

        private boolean hasCycle() {
            return cycle != null;
        }
    }
    private class DirectedDFS {
        private boolean[] marked;  // marked[v] = true iff v is reachable from source(s)


        //Computes the vertices in digraphthat are
        //reachable from the source vertex.

        DirectedDFS(DAG G, int s) {
            marked = new boolean[G.V()];
            validateVertex(s);
            dfs(G, s);
        }

        private void dfs(DAG G, int v) {

            marked[v] = true;
            for (int w : G.adj(v)) {
                if (!marked[w]) dfs(G, w);
            }
        }
        private boolean marked(int v) {
            validateVertex(v);
            return marked[v];
        }

    }
}

