import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnit4.class)
public class DAGTest {
    @Test
    public void testDAGEmpty(){
            try {
                new DAG(0).lowestCommonAncestor(0, 0);
                fail("invalid vertices for LCA of 0 0");
            }
            catch(IllegalArgumentException e){

            }

    }

    @Test
    public void TestDAGNegativeV(){
        try {
            new DAG(-1).lowestCommonAncestor(0, 0);
            fail("Number of vertices in a Digraph must be non-negative");
        } catch(IllegalArgumentException e){

        }
    }

    @Test
    public void TestDiGraphWithCycle(){
        try{
            DAG dag = new DAG(3);
            dag.addEdge(0,1);
            dag.addEdge(1,2);
            dag.addEdge(2,0);
            fail("edge from 2 to 0 not added because it froms a cycle");
        }catch (IllegalArgumentException e){

        }
    }



    @Test
    public void TestSingleV(){
        try{
            DAG dag = new DAG(1);
            dag.lowestCommonAncestor(0,0);
            fail("invalid vertices for LCA of 0 0");
        }catch(IllegalArgumentException e){

        }
    }
    @Test
    public void testSample1LCAAdj(){
        //test LCA of DAG from wikipedia(first image)
        DAG dag = new DAG(5);
        dag.addEdge(0,1);
        dag.addEdge(0,2);
        dag.addEdge(0,3);
        dag.addEdge(0,4);
        dag.addEdge(1,3);
        dag.addEdge(2,3);
        dag.addEdge(2,4);
        dag.addEdge(3,4);

        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(3);


            assertEquals(dag.lowestCommonAncestor(3,4).size(),expected.size());
            assertEquals(expected.get(0),dag.lowestCommonAncestor(3,4).get(0),
                    "LCA of DAG of two adjacent node expect the parent of the two nodes 3");


    }
    @Test
    public void testLCASample1(){
        //test LCA of DAG from wikipedia(first image)
        DAG dag = new DAG(5);
        dag.addEdge(0,1);
        dag.addEdge(0,2);
        dag.addEdge(0,3);
        dag.addEdge(0,4);
        dag.addEdge(1,3);
        dag.addEdge(2,3);
        dag.addEdge(2,4);
        dag.addEdge(3,4);

        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(0);
        assertEquals(dag.lowestCommonAncestor(1,4).size(),expected.size());
        assertEquals(expected.get(0),dag.lowestCommonAncestor(1,4).get(0),
                "LCA of DAG of two vertices not adjacent expect 0");
    }
    @Test
    public void testLCASample2Adj(){
        //test LCA of DAG from wikipedia(second image)
        DAG dag = new DAG(5);
        dag.addEdge(0,1);
        dag.addEdge(0,2);
        dag.addEdge(1,3);
        dag.addEdge(2,3);
        dag.addEdge(3,4);

        ArrayList<Integer>expected = new ArrayList<>();
        expected.add(1);
        assertEquals(expected.size(),dag.lowestCommonAncestor(1,3).size());
        assertEquals(expected.get(0),dag.lowestCommonAncestor(1,3).get(0),
                "LCA of DAG of two adjacent node expect the parent of the two nodes 1");

    }
    @Test
    public void testLCASample2(){
        //test LCA of DAG from wikipedia(second image)
        DAG dag = new DAG(5);
        dag.addEdge(0,1);
        dag.addEdge(0,2);
        dag.addEdge(1,3);
        dag.addEdge(2,3);
        dag.addEdge(3,4);

        ArrayList<Integer>expected = new ArrayList<>();
        expected.add(1);
        assertEquals(expected.size(),dag.lowestCommonAncestor(1,3).size());
        assertEquals(expected.get(0),dag.lowestCommonAncestor(1,3).get(0),"" +
                "LCA of DAG of two vertices not adjacent expect 1");

    }
    @Test
    public void testLCAMulParents(){
        ArrayList<Integer> expectedResult = new ArrayList<Integer>();
        DAG dag2 = new DAG(8);
        dag2.addEdge(0, 3);          //0 -> 3
        dag2.addEdge(1, 3);          // 1->3 1->4
        dag2.addEdge(1, 4);
        dag2.addEdge(2, 5);          //2->5 2->6
        dag2.addEdge(2, 6);
        dag2.addEdge(3, 5);         //3 ->5 3->6
        dag2.addEdge(3, 6);
        dag2.addEdge(4, 6);         // 4->6

        expectedResult.add(2);
        expectedResult.add(3);
        //lowest common ancestor of 5 and 6 is 2 and 3 because both have a dis of 1 which is shortest.
        // (0 dist 2:0->3->5,0->3->6)
        //(1 also has dist 2: 1->3->5 1->3->6 )
        //(4 is not a parent of 5)
        for(int i=0;i<expectedResult.size();i++)
        {
            assertEquals(expectedResult.get(i),dag2.lowestCommonAncestor(5,6).get(i),
                    "lowest common ancestor of 5 and 6 expected 3 and 4");
        }
    }



}