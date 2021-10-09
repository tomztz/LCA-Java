public class LCA {
    static class TreeNode {
        TreeNode(int x) {
            val = x;
        }

        int val;
        TreeNode left;
        TreeNode right;

    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root ==q){
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left,p,q);
        TreeNode right = lowestCommonAncestor(root.right,p,q);

        if(left!=null && right!=null){
            return root;
        }

        if(right == null)return left;
        else return right;
    }


}
