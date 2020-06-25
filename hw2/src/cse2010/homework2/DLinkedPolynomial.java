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
        /*
            Complete the code here
         */
       
    }

    private Term multTerms(Term x, Term y) {
        return new Term(x.coeff * y.coeff, x.expo + y.expo);
    }

    @Override
    public Polynomial mult(final Polynomial p) {
    	DLinkedPolynomial otherPoly = new DLinkedPolynomial();
    	DLinkedPolynomial resultPoly = new DLinkedPolynomial();
    	otherPoly = (DLinkedPolynomial) p;
    	Node<Term> n1 = this.list.getFirstNode();
    	Node<Term> n2 = otherPoly.list.getFirstNode();
    	Term zero = new Term(0.0, 0);
    	Node<Term> zero_n = new Node<Term>(zero);
    	if(p.getCoefficient(0) == 1 && p.termCount() == 1){
            return this;
         }
    	if(this.getCoefficient(0) == 1 && this.termCount() == 1){
            return p;
         }
    	if(this.termCount() == 0 && otherPoly.termCount() != 0) {
    		resultPoly.addTerm(0.0, 0);
    		return resultPoly;
    	}
    	if(otherPoly.termCount() == 0 && this.termCount() != 0) {
    		resultPoly.addTerm(0.0, 0);
    		return resultPoly;
    	}
    	if(this.termCount() != 0 && otherPoly.termCount() != 0) {
    		for(int i =0; i < this.termCount(); i++) {
        		for(int j = 0; j < otherPoly.termCount(); j++) {
        			Term newTerm = new Term(0.0,0);
        			newTerm = new Term(n1.getInfo().coeff * n2.getInfo().coeff, n1.getInfo().expo + n2.getInfo().expo);
        			resultPoly.addTerm(newTerm.coeff, newTerm.expo);
        			n2 = n2.getNext();       			
        		}
        		n2 = otherPoly.list.getFirstNode();
        		n1.getNext();
        	}
    	}
    	
    	return resultPoly;

    
    	
    }

    @Override
    public void addTerm(final double coeff, final int expo) {
    	Term newTerm = new Term(coeff, expo);
    	Node<Term> newNode = new Node<Term>(newTerm);
    	Node<Term> flag = list.getFirstNode();
    	if(termCount() == 0) {
    		list.addFirst(newNode);
    	}
    	else if(termCount() == 1) {
    		if(list.getFirstNode().getInfo().expo > newNode.getInfo().expo) {
    			list.addLast(newNode);
    		}
    		else if(list.getFirstNode().getInfo().expo == newNode.getInfo().expo) {
    			Term temp = new Term(list.getFirstNode().getInfo().coeff + coeff, expo);
    			list.getFirstNode().setInfo(temp);
    		}
    		else {
    			Node<Term> temp = list.getFirstNode();
    			list.remove(temp);
    			list.addFirst(newNode);
    			list.addLast(temp);
    		}
    	}
    	
    	else {
    		for(int i = 0; i < termCount(); i++) {
    			if(flag!=list.getFirstNode()) {
    				if((flag.getInfo().expo > newNode.getInfo().expo) && (newNode.getInfo().expo > flag.getNext().getInfo().expo)) {
        				list.addAfter(flag, newNode);
        				break;
        			}
    			}
    			if(newNode.getInfo().expo > flag.getInfo().expo && flag == list.getFirstNode()) {
    				list.addFirst(newNode);
    				break;
    			}
    			if(flag.getInfo().expo > newNode.getInfo().expo && flag == list.getLastNode()) {
    				list.addLast(newNode);
    				break;
    			}
    			if(flag.getInfo().expo == newNode.getInfo().expo) {
    				Term temp = new Term(coeff +flag.getInfo().coeff, expo);
    				flag.setInfo(temp);
    				break;
    			}
    			else {
    				flag = flag.getNext();
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

