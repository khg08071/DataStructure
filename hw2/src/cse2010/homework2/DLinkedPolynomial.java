package cse2010.homework2;

import java.util.Arrays;
import java.util.stream.Collectors;

/*
 * © 2020 CSE2010 HW #2
 *
 * You can freely modify this class except the signatures of the public methods!!
 */
public class DLinkedPolynomial implements Polynomial {

    private DLinkedList<Term> list;

    public DLinkedPolynomial() {
        list = new DLinkedList<>();
    }

    public int getDegree() {
    	if (termCount() == 0){
            return 0;
        }
        else{
            return list.getFirstNode().getInfo().expo;
        }
    }

    @Override
    public double getCoefficient(final int expo) {
        //Node<Term> term = list.find(new Term(0.0, expo), Term::compareExponents);
    	if (list.size() == 0){
            return 0;
        }
        else {
            Node<Term> current = list.getFirstNode();
            Node<Term> trailer = list.getLastNode();

            while(true){ // list 끝까지 탐색

                if(current.getInfo().expo == expo){

                    return current.getInfo().coeff;
                } 

                else {
                    if (current == trailer){
                        break;
                    }
                    current = current.getNext();

                }

            }
            return 0;
        }
            
        
    }

    private Term addTerms(Term x, Term y) {
        return new Term(x.coeff + y.coeff, x.expo);
    }

    @Override
    public Polynomial add(final Polynomial p) {
        DLinkedPolynomial otherPoly = new DLinkedPolynomial();
        int length;

        if(p.termCount() == 0){
           return this;
       }

        if(this.getDegree() > p.getDegree()){

            length = this.getDegree();
        } 
        else {

            length = p.getDegree();
        }

        for(int i = 0; i <= length+1; i++){

            if(this.getCoefficient(i) != 0.0 || p.getCoefficient(i) != 0.0){

                Term newTerm = new Term(0.0,0);

                if(this.getCoefficient(i) == 0.0 && p.getCoefficient(i) != 0.0){

                    newTerm = new Term(p.getCoefficient(i), i);
                }

                if(this.getCoefficient(i) != 0.0 && p.getCoefficient(i) == 0.0){
                  newTerm = new Term(this.getCoefficient(i), i);
                }

                if(this.getCoefficient(i) != 0.0 && p.getCoefficient(i) != 0.0){

                  newTerm = new Term(this.getCoefficient(i) + p.getCoefficient(i), i);
                }
                if(this.getCoefficient(i) == 0.0 && p.getCoefficient(i) == 0.0){
                  newTerm = new Term(this.getCoefficient(i) + p.getCoefficient(i), i);
                }

                otherPoly.addTerm(newTerm.coeff, newTerm.expo);
            }
        }

      return otherPoly;
       
       
    }

    private Term multTerms(Term x, Term y) {
        return new Term(x.coeff * y.coeff, x.expo + y.expo);
    }

    @Override
    public Polynomial mult(final Polynomial p) {
    DLinkedPolynomial resultpoly = new DLinkedPolynomial();
    DLinkedPolynomial other = new DLinkedPolynomial();
    other = (DLinkedPolynomial) p;
    Node<Term> flag1 = this.list.getFirstNode();
    Node<Term> flag2 = other.list.getFirstNode();
   
    while(true) {
    	if(flag1 == this.list.getLastNode() && flag2 == other.list.getLastNode()) {
    		int ex = flag1.getInfo().expo + flag2.getInfo().expo;
			double cof = flag1.getInfo().coeff +flag2.getInfo().coeff;
			resultpoly.addTerm(cof, ex);
			break;
    	}
    	else {
    		if(flag2 == other.list.getLastNode()) {
    			int ex = flag1.getInfo().expo + flag2.getInfo().expo;
    			double cof = flag1.getInfo().coeff +flag2.getInfo().coeff;
    			resultpoly.addTerm(cof, ex);
    			flag2 = other.list.getFirstNode();
    			flag1 = flag1.getNext();
    		}
    		else {
    			int ex = flag1.getInfo().expo + flag2.getInfo().expo;
    			double cof = flag1.getInfo().coeff +flag2.getInfo().coeff;
    			resultpoly.addTerm(cof, ex);
    			flag2 = flag2.getNext();
    		}
    	}
    }
    return other;
    }

    @Override
    public void addTerm(final double coeff, final int expo) {
    	Term newTerm = new Term(coeff, expo);
    	Node<Term> newNode = new Node<Term>(newTerm);
    	Node<Term> flag = list.getFirstNode();
    	if(this.termCount() == 0) {
    		list.addFirst(newNode);
    	}
    	else if(this.termCount() == 1) {
    		if(flag.getInfo().expo > newNode.getInfo().expo) {
    			list.addLast(newNode);
    		}
    		else if(flag.getInfo().expo == newNode.getInfo().expo) {
    			Node<Term> temp = list.getFirstNode();
    			double cof = temp.getInfo().coeff;
    			Term tmp = new Term(coeff + cof , expo);
    			temp.setInfo(tmp);
    		}
    		else {
    			Node<Term> temp = list.getFirstNode();
    			list.removeFirst();
    			list.addFirst(newNode);
    			list.addLast(temp);
    		}
    	}
    	else {
    		while(true) {
    			if(flag.getInfo().expo > newNode.getInfo().expo) {
    				if(flag == list.getLastNode()) {
    					list.addLast(newNode);
    					break;
    				}
    				else {
    					flag = flag.getNext();
    				}
    				
    			}
    			else if(flag.getInfo().expo == newNode.getInfo().expo) {
    				double cof = flag.getInfo().coeff;
        			Term tmp = new Term(coeff + cof , expo);
        			flag.setInfo(tmp);
        			break;
    			}
    			else {
    				if(flag == list.getFirstNode()) {
    					list.addFirst(newNode);
    					break;
    				}
    				else {
    					list.addBefore(flag, newNode);
    					break;
    				}
    			}
    		}
    	}
    }
    

    @Override
    public void removeTerm(final int expo) {
        //Node<Term> node = list.find(new Term(0, expo), Term::compareExponents);
    	Node<Term> current = list.getFirstNode();
        Node<Term> trailer = list.getLastNode();
        while(true){ // list 끝까지 탐색

            if(current.getInfo().expo == expo){

                list.remove(current);
                break;
            }

             else {
                if (current == trailer){

                    throw new NoSuchTermExistsException();
                }

            }
             current = current.getNext();
         }
        /*
            Complete the code here
         */
    }

    @Override
    public int termCount() {
        return list.size();
    }

    @Override
    public double evaluate(final double val) {
    	double result = 0.0;
        int max = this.getDegree();

        for (int i = 0; i <= max; i++){
            result = result + this.getCoefficient(i) * Math.pow(val, i);
        }

        return result;
        /*
            Complete the code here
         */
         // you may delete this line
    }

    @Override
    public String toString() {
        if (list.isEmpty())
            return "Empty Polynomial";
        else {
            String[] terms = new String[termCount()];
            int i = 0;
            Node<Term> current = list.getFirstNode();
            do {
                if (current.getInfo().expo == 0) {
                    terms[i++] = String.valueOf(current.getInfo().coeff);
                } else if (current.getInfo().expo == 1) {
                    terms[i++] = current.getInfo().coeff + "x";
                } else {
                    terms[i++] = current.getInfo().coeff + "x^" + current.getInfo().expo;
                }
                current = list.getNextNode(current);
            } while (current != null);
            return String.join("+", terms);
        }
    }
  

}

