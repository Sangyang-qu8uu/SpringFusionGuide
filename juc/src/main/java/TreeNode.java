import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

/**
 * @Description 递归调用节点对象
 * @Author SangY
 * @Date 2024/7/25 19:06
 **/
public class TreeNode {

    public String id;
    public String pid;
    public String name;
    public List<TreeNode> children;

    public TreeNode() {
    }

    public TreeNode(String id, String pid, String name, List<TreeNode> children) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }
}
