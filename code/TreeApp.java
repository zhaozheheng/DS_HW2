package zheheng;

import java.util.ArrayList;
import java.util.Stack;

class Node{	//construct the node structure
	public int val;
	public Node lchild;
	public Node rchild;
	
	/*public void displayNode(){
		System.out.println(val);
	}*/
}

class Tree{	//construct the BST structure
	private Node root;
	
	public Tree(){
		root = null;
	}
	
	public void insert(int key){
		Node newNode = new Node();
		newNode.val = key;
		if (root == null) {
			root = newNode;
		}
		else{
			Node current = root;
			Node parent;
			while(true){
				parent = current;
				if (key < current.val) {
					current = current.lchild;
					if (current == null) {
						parent.lchild = newNode;
						return;
					}
				}
				else{
					current = current.rchild;
					if (current == null) {
						parent.rchild = newNode;
						return;
					}
				}
			}
		}
	}
	
	public boolean delete(int key){	//delete node from the tree
		Node current = root;
		Node parent = root;
		boolean isLeftChild = true;
		
		while (current.val != key) {
			parent = current;
			if (key < current.val) {
				isLeftChild = true;
				current = current.lchild;
			}
			else{
				isLeftChild = false;
				current = current.rchild;
			}
			if (current == null) {
				return false;
			}
		}
		
		if (current.lchild == null && current.rchild == null) {
			if (current == root) {
				root = null;
			}
			else if (isLeftChild) {
				parent.lchild = null;
			}
			else{
				parent.rchild = null;
			}
		}
		else if (current.rchild == null) {
			if (current == root) {
				root = current.lchild;
			}
			else if (isLeftChild) {
				parent.lchild = current.lchild;
			}
			else{
				parent.rchild = current.lchild;
			}
		}
		else if (current.lchild == null) {
			if (current == root) {
				root = current.rchild;
			}
			else if (isLeftChild) {
				parent.lchild = current.rchild;
			}
			else{
				parent.rchild = current.rchild;
			}
		}
		else{
			Node successor = getSuccessor(current);
			
			if (current == root) {
				root = successor;
			}
			else if (isLeftChild) {
				parent.lchild = successor;
			}
			else{
				parent.rchild = successor;
			}
			
			successor.lchild = current.lchild;
		}
		return true;
	}
	
	private Node getSuccessor(Node delNode){	//get successor of the chosen node
		Node successorParent = delNode;
		Node successor = delNode;
		Node current = delNode.rchild;
		while (current != null) {
			successorParent = successor;
			successor = current;
			current = current.lchild;
		}
		
		if (successor != delNode.rchild) {
			successorParent.lchild = successor.rchild;
			successor.rchild = delNode.rchild;
		}
		return successor;
	}
	
	public void traverse(int type){	//traverse the whole tree
		switch (type) {
		case 1:
			System.out.println("Preorder traversal: ");
			preOrder(root);
			break;
		case 2:
			System.out.println("Inorder traversal: ");
			inOrder(root);
			break;
		case 3:
			System.out.println("Postorder traversal: ");
			postOrder(root);
			break;
		default:
			break;
		}
	}
	
	private void preOrder(Node localNode){	//pre order traverse
		if (localNode != null) {
			System.out.print(localNode.val + " ");
			preOrder(localNode.lchild);
			preOrder(localNode.rchild);
		}
	}
	
	private void inOrder(Node localNode){	//in order traverse
		if (localNode != null) {
			inOrder(localNode.lchild);
			System.out.print(localNode.val + " ");
			inOrder(localNode.rchild);
		}
	}
	
	private void postOrder(Node localNode){	//post order traverse
		if (localNode != null) {
			postOrder(localNode.lchild);
			postOrder(localNode.rchild);
			System.out.print(localNode.val + " ");
		}
	}
	
	public void displayTree(int nBlanks){	//display the whole tree in a tree-like graph
		Stack globalstack = new Stack();
		globalstack.push(root);
		//int nBlanks = 64;
		boolean isRowEmpty = false;
		//System.out.println("...........................................................");
		while(isRowEmpty == false){
			Stack localstack = new Stack();
			isRowEmpty = true;
			
			for (int i = 0; i < nBlanks; i++) {
				System.out.print(' ');
			}
			
			while (globalstack.isEmpty() == false) {
				Node temp = (Node)globalstack.pop();
				if (temp != null) {
					System.out.print(temp.val);
					localstack.push(temp.lchild);
					localstack.push(temp.rchild);
					
					if (temp.lchild != null || temp.rchild != null) {
						isRowEmpty = false;
					}
				}
				else{
					System.out.print("--");
					localstack.push(null);
					localstack.push(null);
				}
				for (int i = 0; i < nBlanks*2-2; i++) {
					System.out.print(' ');
				}
			}
			System.out.println();
			nBlanks /= 2;
			while (localstack.isEmpty() == false) {
				globalstack.push(localstack.pop());
			}
		}
		//System.out.println("...........................................................");
	}
}

public class TreeApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int value;
		int[] numList = {50, 76, 21, 4, 32, 64, 15, 52, 14, 100, 83, 2, 3, 70, 87};
		ArrayList<Tree> treeList = new ArrayList<Tree>();
		for (int i = 0; i < 3; i++) {
			Tree theTree = new Tree();
			for (int j = 0; j < numList.length; j++) {
				theTree.insert(numList[j]);
			}
			treeList.add(theTree);
		}
		value = (int)(Math.pow(2 * 1.0,((int)(Math.log(numList.length)/Math.log(2)) + 2) * 1.0));
		//System.out.println((int)(Math.pow(2 * 1.0,((int)(Math.log(10)/Math.log(2)) + 1) * 1.0)));
		System.out.println("....................................................................");
		System.out.println("Initial Tree:");
		treeList.get(0).displayTree(value);
		treeList.get(0).traverse(2);
		treeList.get(0).delete(76);//successor is not a leaf
		treeList.get(1).delete(21);//successor is a leaf
		treeList.get(2).delete(87);//it is a leaf node
		System.out.println();
		System.out.println("....................................................................");
		System.out.println("Delete 76!");
		treeList.get(0).displayTree(value);
		treeList.get(0).traverse(2);
		System.out.println();
		System.out.println("....................................................................");
		System.out.println("Delete 21!");
		treeList.get(1).displayTree(value);
		treeList.get(1).traverse(2);
		System.out.println();
		System.out.println("....................................................................");
		System.out.println("Delete 87!");
		treeList.get(2).displayTree(value);
		treeList.get(2).traverse(2);
		}
		}
