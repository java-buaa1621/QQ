package qq.ui.friend;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

import qq.db.info.UserInfo;
import qq.util.ResourceManagement;

public class FriTreeNode implements TreeNode{

	protected UserInfo info;
	protected String nodeName;
	protected TreeNode parent;
	protected ArrayList<TreeNode> children;
	
	/**
	 * 用于创建根节点
	 */
	public FriTreeNode() {
		
	}
	
	public FriTreeNode(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public FriTreeNode(UserInfo info){
		this.info = info;
	}
	
	public void addChild(FriTreeNode child) {
		if(child == null)
			throw new IllegalArgumentException("argument child is not exist");
		
		if(this.children == null){
			children = new ArrayList<TreeNode>();
		}
		children.add(child);
		child.setParent(this);
	}
	
	public void addChild(ArrayList<FriTreeNode> childs) {
		for(FriTreeNode child : childs)
			addChild(child);
	}
	
	protected boolean haveChild(TreeNode child) {
		if(child == null)
			throw new IllegalArgumentException("argument child is not exist");
		else if (child.getParent() != this)
            return false;
		else 
			return true;
	}
	
	@Override
	public TreeNode getChildAt(int childIndex) {
		if (children == null) 
            throw new ArrayIndexOutOfBoundsException("node has no children");
		return children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		if(children == null)
			return 0;
		return children.size();
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	
	@Override
	public TreeNode getParent() {
		return this.parent;
	}

	@Override
	public int getIndex(TreeNode child) {
		if(this.children == null)
			return -1;
		else
			return children.indexOf(child); 
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		boolean isRoot = (this.getParent() == null);
		boolean noChild = (this.children == null);
		return (!isRoot && noChild);
	}

	public boolean isThirdLayer() {
		int layer = getLayer();
		if(layer == 3) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getLayer() {
		FriTreeNode node = this;
		int layer = 1;
		while (node.getParent() != null) {
			node = (FriTreeNode) node.getParent();
			layer++;
		}
		return layer;
	}
	
	@Override
	public Enumeration children() {
		return null;
	}

	public String getNodeName() {
		return this.nodeName;
	}
	
	public UserInfo getUserInfo() {
		return this.info;
	}
	
}