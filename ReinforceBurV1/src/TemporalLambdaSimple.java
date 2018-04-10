

import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.ValueFunctionInitialization;
import burlap.behavior.singleagent.learning.actorcritic.ActorCritic;
import burlap.behavior.singleagent.learning.actorcritic.actor.BoltzmannActor;
import burlap.behavior.singleagent.learning.actorcritic.critics.TDLambda;
import burlap.behavior.statehashing.DiscreteStateHashFactory;

import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.AbstractGroundedAction;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import burlap.behavior.*;

public class TemporalLambdaSimple {
	
	DomainGenerator	dg;
	Domain domain;
	State initState;
	RewardFunction rf;
	TerminalFunction tf;
	//DiscretizingHashableStateFactory hashFactory;
	DiscreteStateHashFactory hashFactory;
	int numStates;
	double probability1;
	double probability2;
	ValueFunctionInitialization vinit;
	double gamma = 1.0;
	double lambda ;
	double learningRate = 1.0;
	TDLambda tdl;
	
	
	//Environment env;
	
	  public TemporalLambdaSimple(double probToState1, double[] valueEstimates, 
			  double[] rewards,double lambda) {
        // add your code here
     
	    this.probability1 = probToState1;
	    this.probability2 = (1 -probToState1);
	    this.numStates = 7;
	    this.lambda = lambda;
	    //Domain Generator is an interface implemented by different classes
	    
	    this.dg = new GraphDefinedDomain(numStates);
	  
	    
	    //transitions for node 0
	    ((GraphDefinedDomain) this.dg).setTransition(0, 0, 1,probability1);
	    ((GraphDefinedDomain) this.dg).setTransition(0, 1, 2,probability2);
	  
	    
	    //transitions for node 1
	    ((GraphDefinedDomain) this.dg).setTransition(1, 0, 3,1.);
	   
	    
	    //transitions for node 2
	    ((GraphDefinedDomain) this.dg).setTransition(2, 0, 3,1.);
	    
	    //transitions for node 3
	    ((GraphDefinedDomain) this.dg).setTransition(3, 0, 4,1.);
	    
	    //transitions for node 4
	    ((GraphDefinedDomain) this.dg).setTransition(4, 0, 5, 1.);
	    
	  //transitions for node 5
	    ((GraphDefinedDomain) this.dg).setTransition(5, 0, 6, 1.);
	    
		
	    this.domain = this.dg.generateDomain();
	    
	    
	    
	    
	    this.initState = GraphDefinedDomain.getState(this.domain,0);

		this.rf = new RF(rewards);
		
	    this.tf = new SingleStateTF(6);
	    
	    this.hashFactory = new DiscreteStateHashFactory();
	   
	    this.vinit = new ValueFunctionInit(valueEstimates);
	    }
	
	
	public static class SingleStateTF implements TerminalFunction{
		int terminalSid;
		
		public SingleStateTF(int sid){
			this.terminalSid = sid;
		}

		@Override
		public boolean isTerminal(State s) {
			int sid = GraphDefinedDomain.getNodeId(s);
			return sid == this.terminalSid;
		}
	}

	
	public double findBestLambda() {
		
		tdl = new TDLambda(rf,tf, gamma,hashFactory,learningRate,vinit, lambda);
        // add your code here
		
		double[] actionVals = {0.0,0.0};
		// A Boltzmann actor chooses actions at each state based on a particular probability
		// distribution. If there is just one possible action per state (as in this example), 
		// it just takes the only action available to it.
		BoltzmannActor actor = new BoltzmannActor(this.domain, this.hashFactory, learningRate);

		ActorCritic ac = new ActorCritic(this.domain, this.rf, this.tf, gamma, actor, tdl);
		
		EpisodeAnalysis ea = ac.runLearningEpisodeFrom(this.initState);
		List<GroundedAction> ls = ea.actionSequence;
	
	    String firstAct = ls.get(0).actionName();
	    String substr = firstAct.substring(6,7);
	    int arrayIndex = Integer.parseInt(substr);
	    
		String s = "";
		for(int j = 0; j < ls.size();j++){
			s = s + "  "+ls.get(j).actionName();
		}
		
		
		//System.out.println("Action sequence" + s);
		
		double x = tdl.value(initState);
		
		//System.out.println("Val returned" + x +" firstAction  "  +firstAct);
		//System.out.println();
		
		if(ls.size() == 5)
		actionVals[arrayIndex] =x;  // Returns the estimated value of the initial state based on
		                                // the learning episode. If there are stochastic actions,
		                                // only the outcomes actually observed will contribute to the
		                                // TD estimate.
		return x;
	}

	
	

//ValueFunction Initialization
	
	public static class ValueFunctionInit implements ValueFunctionInitialization{
		
	   double initValueArray[];
		
		public ValueFunctionInit(double[] valueEstimates){
			this.initValueArray = valueEstimates;
			
		}
		
		

		@Override
		public double value(State s) {
			// TODO Auto-generated method stub
			int sid = GraphDefinedDomain.getNodeId(s);
			return initValueArray[sid]	;
		}

		@Override
		public double qValue(State s, AbstractGroundedAction a) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		
 }
	
	
	
	
 public static class RF implements RewardFunction {
			
		 double[] reward1;
			
			public RF(double rewards[]) {
				this.reward1 = rewards;
				
			}
			
			@Override
			public double reward(State s, GroundedAction a, State sprime) { 
				
				int sid = GraphDefinedDomain.getNodeId(s);
				double r =0;
				int targetid = GraphDefinedDomain.getNodeId(sprime);
				if(sid == 0){
					if(targetid == 1) r = this.reward1[0];
					if(targetid == 2) r = this.reward1[1];
					
				}
			
				else if( sid == 1 /* && targetid == 3*/) { // b1
					r = this.reward1[2];
				}
				
				else if( sid == 2 /*&& targetid == 3*/) { // b2
					r = this.reward1[3];
				}
				else if( sid == 3 /*&& targetid == 4*/) { // b2
					r = this.reward1[4];
				}
				else if( sid == 4/* && targetid == 5*/) { // b2
				
					r = this.reward1[5];
		         }
				else if( sid == 5 /*&& targetid == 6*/) { // b2
					r = this.reward1[6];
				}
				else {
					throw new RuntimeException("Unknown state: " + sid);
				}
				
				return r;
			}
	    }
	    
	
 
  private static double avgArrayList(ArrayList<Double> arrList){
	  
	  double sum = 0.0;
	 
	  for(double k : arrList){
		  
		  sum = sum +k;
	  }
	  
	  double average = sum/arrList.size();
	  
	  return average;
	  
	  
	  
  }
  
  
 /* public double findingFinalVal(double[] rewards, double[] initVal, double prob, double lambda){
	  int noOfIterations = 7;
	  ArrayList<Double> arrValList0 = new ArrayList<Double>();
	  
	  ArrayList<Double> arrValList1 = new ArrayList<Double>();
	  double[] arr = {0.0,0.0};
	  
	  TemporalLambdaSimple obj ;
      obj = new TemporalLambdaSimple(prob,initVal,rewards,lambda);
	 
	  
	  for(int i = 0; i < noOfIterations;i++){
			 
			arr = obj.findBestLambda();
			 
		    if(arr[0] != 0.0)
				 arrValList0.add(arr[0]);
			 
			 if(arr[1] != 0.0)
				 arrValList1.add(arr[1]);
	  }
	  

	  double averageArrayList0 = avgArrayList(arrValList0);
	  
	  double averageArrayList1 = avgArrayList(arrValList1);
	 
	  System.out.println(prob);
	  
	  double finalVal = prob * averageArrayList0 + (1-prob) * averageArrayList1;
	  
	  return finalVal;
  }
  */
  
  
  public static double findingFinalVal(TemporalLambdaSimple obj){
	  
	 ArrayList<Double> arrList = new ArrayList<Double>();
     
	 Set<Double> valSet = new HashSet<Double>();
	 
	 double arr[] = {0.0,0.0};
	 int count = 0;
	  
	  for(int i = 0; i < 7;i++){
			 
			 double d = obj.findBestLambda();
			 arrList.add(d);
			 if (valSet.isEmpty() || (!valSet.contains(d))){
				 valSet.add(d);
			 }
			 
			  }
		 
	    for(double d : valSet){
			 if (count < 2){
				 arr[count] = d;
				 count ++;
			 }
			 }
		 
		// System.out.println("Arr Values " + arr[0]+ "    "+arr[1]);
		 
		 double finalVal = 0.5 * arr[0] + 0.5 *arr[1];
	  
	   return finalVal;
  }
  
 
  
  public static void callingFinalVal(double prob,double[] initValue,double[] rewards,double lambda1){
	  
	  
		 TemporalLambdaSimple obj ;
		
		 obj = new TemporalLambdaSimple(prob,initValue,rewards,lambda1);
		 
		 double finalVal = findingFinalVal(obj);
		 
		  System.out.println("Final val of state  " + finalVal +"when lambda is "+ lambda1);
		
       }
 
 
 
 
 
 
	 public static void main(String[] args){
		 
		 //First problem data
		 //double rewards[]= {7.9,-5.1,2.5,-7.2,9.0,0.0,1.6};
		 //double[] initValue = {0.0,4.0,25.7,0.0,20.1,12.2,0.0};
		 
		// Second problem data 
		 /*double rewards[]= {-2.4,0.8,4.0,2.5,8.6,-6.4,6.1} ;
		 double[] initValue = {0.0,-5.2,0.0,25.4,10.6,9.2,12.3};*/
		 
		 
		 //Third problem data 
		/* double rewards[]= {-2.4,9.6,-7.8,0.1,3.4,-2.1,7.9}  ;
		 double[] initValue = {0.0,4.9,7.8,-2.3,25.5,-10.2,-6.5};*/
		 
		 
		 /*First problem question 
		  * probToState=0.39, valueEstimates={0.0,19.5,13.6,-7.2,-9.5,0.0,0.0}, 
		  * rewards={-8.3,-7.7,1.2,1.1,1.6,3.4,-0.6}*/
		 
		 /* Second problem question 
		  * probToState=0.63, valueEstimates={0.0,2.7,3.9,-19.7,-2.9,0.0,0.0}, 
		  * rewards={-7.0,-1.2,4.5,0.9,-3.1,-4.9,3.0} */
		 
		 /*Third problem question
		  * probToState=0.5, valueEstimates={0.0,-4.8,-11.1,-4.2,25.1,26.7,-11.4}, 
		  * rewards={3.9,1.1,-4.5,0.0,2.4,3.3,-1.6} */
		 
		 
         double rewards[]= {3.9,1.1,-4.5,0.0,2.4,3.3,-1.6}  ;
		 double[] initValue = {0.0,-4.8,-11.1,-4.2,25.1,26.7,-11.4};
		 
		 
		double prob =  0.50;
		double lambda1 = 0.653;
		
		System.out.println("Lambda value is 1");
		
		callingFinalVal(prob,initValue,rewards,1);
		
		System.out.println("-------------------");
		
		
		
		
		 
	/*	for(int i = 0; i< 11; i++){
			
			double lamb = i * 0.1;
			
			System.out.println();
			
			callingFinalVal(prob,initValue,rewards,lamb);
			
			
		} */
		 
    for(int i = 500; i< 700; i++){
			
			double lamb = i * 0.001;
			
			System.out.println();
			
			callingFinalVal(prob,initValue,rewards,lamb);
			
			
		} 
		 
  }
}


