package cse2010.homework5;

import java.util.Stack;

public class SyntaxTree extends LinkedBinaryTree<String> {
	String output = "";
    public static SyntaxTree buildSyntaxTree(String[] expr) {

    	Stack<SyntaxTree> stack = new Stack<SyntaxTree>();
    	SyntaxTree tree1 = null;
    	for(String str : expr) {
    		if(Character.isDigit(str.charAt(0))) {
    			SyntaxTree tree = new SyntaxTree();
    			tree.addRoot(str);
    			stack.push(tree);
    		}else {
    			tree1 = new SyntaxTree();
    			Node<String> root = tree1.addRoot(str);
    			SyntaxTree right= stack.pop();
    			SyntaxTree left = stack.pop();
    			tree1.attach(root,left ,right);
    			stack.push(tree1);	    			
    		}
    	}
		return tree1;
    }
        // construct an expression syntax tree ...
    
    private String inOrder(Node trees) {
		if(trees.getLeft() != null) {
			output += "(";
			inOrder(trees.getLeft());
		}
		if(trees.getElement().equals("+") || trees.getElement().equals("-") || trees.getElement().equals("/") || trees.getElement().equals("*")) {
			output += " "+trees.getElement()+" ";
		} else {
			output+=trees.getElement();
		}
		if(trees.getRight() != null) {
			inOrder(trees.getRight());
			output += ")";	
		}
	return output;
}
    public String parenthesize() {
    	return inOrder(this.root);
        // you may define helper recursive method and use it here...
    }
    
    private double postOrderC(Node trees) {
    	if(trees.getLeft()==null && trees.getRight() == null) {
    		return new Double(trees.getElement().toString());
    	}
    	else {
    		if(trees.getElement().equals("+")) {
    			double x = postOrderC(trees.getLeft());
        		double y = postOrderC(trees.getRight());
        		return x+y;}
    		else if(trees.getElement().equals("-")) {
    			double x = postOrderC(trees.getLeft());
        		double y = postOrderC(trees.getRight());
        		return x-y;}
    		else if(trees.getElement().equals("/")) {
    			double x = postOrderC(trees.getLeft());
        		double y = postOrderC(trees.getRight());
        		return x/y;}
    		else if(trees.getElement().equals("*")) {
    			double x = postOrderC(trees.getLeft());
        		double y = postOrderC(trees.getRight());
        		return x*y;}
    	}
		return 0;		
    	}

    public double evaluate() {
    	return postOrderC(this.root);
        // you may define helper recursive method and use it here...
    }
    
    private void Preorder(Node trees) {
    	output += trees.getElement() + " ";
    	if(trees.getLeft() != null)Preorder(trees.getLeft());
    	if(trees.getRight() != null)Preorder(trees.getRight());	
    }

    public String toPrefix() {
    	
    	output = "";
    	Preorder(this.root);
    	output = output.substring(0, output.length()-1);
    	return output;
        // you may define helper recursive method and use it here... 
    }
    
    private void Indents(Node tree, int d ) {
    	String space = "";
    	for(int i = 0; i<d;i++) {
    		space = space + "  ";
    	}
    	output += space + tree.getElement() +"\n";
    	if(tree.getLeft()!=null)Indents(tree.getLeft(),d+1);
    	if(tree.getRight()!=null)Indents(tree.getRight(),d+1);
    }
	

    public String indentSyntaxTree() {
    	
    	output = "";
    	Indents(this.root,this.depth(root));
    	return output;
        // you may define helper recursive method and use it here...
    }

    public static void main(String... args) {

        System.out.println("Homework 5");
    }
}


