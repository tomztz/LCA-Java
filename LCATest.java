import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(JUnit4.class)
public class LCATest {
    @Test
    public void testTreeEmpty(){
        assertEquals(null,LCA.lowestCommonAncestor(null,null,null),
                "checking LCA of empty tree expected null");

    }

    @Test
    public void testLCASingleNode(){
        LCA.TreeNode node = new LCA.TreeNode(3);
        node.left = null;
        node.right = null;

        assertEquals(3,LCA.lowestCommonAncestor(node,node,null).val,
                "checking LCA of a single node from a singe node tree expected the single node 3");

    }

    @Test
    public void testLCAOfLeftAndRightSubTree(){
        LCA.TreeNode node = new LCA.TreeNode(3);              //        _3_
        node.left = new LCA.TreeNode(5) ;                    //      /      \
        node.right = new LCA.TreeNode(1);                     //    _5_       1
        node.left.left = new LCA.TreeNode(6);                //  /     \    /  \
        node.left.right = new LCA.TreeNode(2);               // 6       2   0    8
        node.left.right.left= new LCA.TreeNode(7);          //        /  \
        node.left.right.right= new LCA.TreeNode(4);          //      7     4
        node.right.left=new LCA.TreeNode(0);
        node.right.right= new LCA.TreeNode(8);


        assertEquals(3,LCA.lowestCommonAncestor(node,node.left,node.right).val,
                "testing LCA of node 5 and 1 expected 3");
    }

    @Test
    public void testLCAOfLeftSubTreeOneNodeApart(){

        LCA.TreeNode node = new LCA.TreeNode(3);              //        _3_
        node.left = new LCA.TreeNode(5) ;                    //      /      \
        node.right = new LCA.TreeNode(1);                     //    _5_       1
        node.left.left = new LCA.TreeNode(6);                //  /     \    /  \
        node.left.right = new LCA.TreeNode(2);               // 6       2   0    8
        node.left.right.left= new LCA.TreeNode(7);          //        /  \
        node.left.right.right= new LCA.TreeNode(4);          //      7     4
        node.right.left=new LCA.TreeNode(0);
        node.right.right= new LCA.TreeNode(8);

        assertEquals(5,LCA.lowestCommonAncestor(node,node.left,node.left.right.right).val,
                "testing LCA of node 5 and 4 expected 5");

    }

    @Test
    public void testLCAOfTwoNodesBesideEachOther(){
        LCA.TreeNode node = new LCA.TreeNode(1);
        node.left = new LCA.TreeNode(2);

        assertEquals(1,LCA.lowestCommonAncestor(node,node,node.left).val,
                "testing LCA of node 1 and 2 expected 1");

    }

    @Test
    public void testLCAOFNodesInLeftAndRightSubTreeWithDiffLevel(){
        LCA.TreeNode node = new LCA.TreeNode(3);              //        _3_
        node.left = new LCA.TreeNode(5) ;                    //      /      \
        node.right = new LCA.TreeNode(1);                     //    _5_       1
        node.left.left = new LCA.TreeNode(6);                //  /     \    /  \
        node.left.right = new LCA.TreeNode(2);               // 6       2   0    8
        node.left.right.left= new LCA.TreeNode(7);          //        /  \
        node.left.right.right= new LCA.TreeNode(4);          //      7     4
        node.right.left=new LCA.TreeNode(0);
        node.right.right= new LCA.TreeNode(8);

        assertEquals(3,LCA.lowestCommonAncestor(node,node.left,node.right.right).val,
                "testing LCA of node 5 and 8 expected 3");
    }
}