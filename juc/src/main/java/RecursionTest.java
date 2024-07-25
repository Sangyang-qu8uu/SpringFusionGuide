import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author SangY
 * @Date 2024/7/25 19:08
 **/
public class RecursionTest {
    public static void main(String[] args) throws JsonProcessingException {

        TreeNode root = new TreeNode();
        root.setId("1");
        root.setPid("0");
        root.setName("根节点");
        TreeNode node1 = new TreeNode();
        node1.setId("2");
        node1.setPid("1");
        node1.setName("子节点1");
        TreeNode node2 = new TreeNode();
        node2.setId("3");
        node2.setPid("1");
        node2.setName("子节点2");
        TreeNode node3 = new TreeNode();
        node3.setId("4");
        node3.setPid("2");
        node3.setName("子节点3");
        TreeNode node4= new TreeNode();
        node4.setId("5");
        node4.setPid("2");
        node4.setName("子节点4");

        List<TreeNode>dbSourceList=new ArrayList<>();
        dbSourceList.add(root);
        dbSourceList.add(node1);
        dbSourceList.add(node2);
        dbSourceList.add(node3);
        dbSourceList.add(node4);

        System.out.println("原始数据："+dbSourceList);

        //组装树
        List<TreeNode> parentList = dbSourceList.stream()
                .filter(a -> a.getPid().equals("0"))
                .peek(s-> s.setChildren(recursion(s,dbSourceList))).toList();

        System.out.println("组装树数据"+parentList);
    }

    public static List<TreeNode> recursion(TreeNode node,List<TreeNode> parentList){

        return parentList.stream()
                .filter(a -> a.getPid().equals(node.getId()))
                .peek(m -> m.setChildren(recursion(m, parentList))).toList();
    }

}
