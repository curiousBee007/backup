
/*public class MultiAgentNaiveQLVer1 {

}*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import burlap.behavior.learningrate.ExponentialDecayLR;
import burlap.behavior.learningrate.LearningRate;
import burlap.behavior.policy.EpsilonGreedy;
import burlap.behavior.policy.Policy;
import burlap.behavior.stochasticgames.GameAnalysis;
import burlap.behavior.stochasticgames.agents.naiveq.SGNaiveQLAgent;
import burlap.behavior.stochasticgames.auxiliary.GameSequenceVisualizer;
import burlap.behavior.valuefunction.QValue;
import burlap.behavior.valuefunction.ValueFunctionInitialization;
import burlap.debugtools.DPrint;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.legacy.StateParser;
import burlap.oomdp.legacy.StateYAMLParser;
import burlap.oomdp.statehashing.DiscretizingHashableStateFactory;
import burlap.oomdp.stochasticgames.JointAction;
import burlap.oomdp.stochasticgames.JointActionModel;
import burlap.oomdp.stochasticgames.JointReward;
import burlap.oomdp.stochasticgames.SGDomain;
import burlap.oomdp.stochasticgames.World;
import burlap.oomdp.stochasticgames.WorldObserver;
import burlap.oomdp.stochasticgames.agentactions.GroundedSGAgentAction;
import burlap.oomdp.stochasticgames.agentactions.SimpleGroundedSGAgentAction;
import burlap.oomdp.stochasticgames.agentactions.SimpleSGAgentAction;
import burlap.oomdp.stochasticgames.common.ConstantSGStateGenerator;
import burlap.oomdp.visualizer.Visualizer;

public class MultiAgentNaiveQLVer1 {
	
	//grid game domain generator

	protected ExpGridGame2 domainGen;

   //stochastic games domain
   protected SGDomain domain;

    //joint action model we want to use for grid game
    protected JointActionModel jam;

    protected SGNaiveQLAgent at;
    
    protected SGNaiveQLAgent agent1;

	protected DiscretizingHashableStateFactory hashingFactory;

    protected JointReward rf;

   //a terminal function for defining end states of the gaableme
    protected TerminalFunction tf;

   //stochastic game discount factor
   protected double discount = 0.99;

   //initial state of the game
    protected State initialState;

    //a world of agents that will interact in a grid world
    protected World w;

  //Grid Game visualizer so we can watch examples of the policy
  Visualizer v;


  public static JointAction ja1;

  public static int count = 0;

  LearningRate lr;

  //public AgentQSourceMap.MAQLControlledQSourceMap qSourceMap;


    public MultiAgentNaiveQLVer1(){

    	 this.domainGen = new ExpGridGame2();
         this.domain = (SGDomain)domainGen.generateDomain(); 
         this.jam = new ExpGridMechanics(this.domain);
         this.rf = new ExpGridGame2.GGJointRewardFunction1(this.domain,0.0,100.0,-100.0);
         this.tf = new ExpGridGame2.GGTerminalFunction(this.domain);
        
         this.initialState = ExpGridGame2.getTurkeyInitialState(this.domain);
         this.hashingFactory = new DiscretizingHashableStateFactory(0);
         double learningRate = 0.1;
         double discount = 0.99;

         ValueFunctionInitialization qInitizalizer = new ValueFunctionInitialization.ConstantValueFunctionInitialization(0.0);
         
         this.at = new SGNaiveQLAgent(this.domain, discount,learningRate,qInitizalizer, hashingFactory);
         this.agent1 = new SGNaiveQLAgent(this.domain, discount,learningRate,qInitizalizer, hashingFactory);
         
         this.w = new World(domain, this.rf, new ExpGridGame2.GGTerminalFunction(this.domain),new ConstantSGStateGenerator(this.initialState));
        // w.
         
         this.v = GridGameVisualizer.getVisualizer(9, 9);
    
      }

    public void NaiveQLearning(){
     
    	//double learningRate = 0.01;
        double defaultQ = 0.0;
        
        double epsilon = 0.2;
        
        double initialLearningRate = 1.0;
        
        double decayRate = 0.0001;
        
        double minimumlearningrate = 0.001;
        
        this.lr = new ExponentialDecayLR(initialLearningRate,decayRate,minimumlearningrate);
        
        ValueFunctionInitialization valueFuninit = new ValueFunctionInitialization.ConstantValueFunctionInitialization(0.0);
        
        at.setLearningRate(lr);
        
        Policy learningPolicy;
        
        learningPolicy = new EpsilonGreedy(epsilon);

        at.setStrategy(learningPolicy);
       
        //we're going to run a lot of learning episodes, so lets disable the world debug console print outs
        DPrint.toggleCode(w.getDebugId(), false);

    	//run 2500 learning episodes. We'll let these run without using our visualizer for speed.

    	//first create a standard state parser for saving data to disk

    	StateParser sp = new StateYAMLParser(this.domain);
        //now run training; games will be saved in a directory

    	//that will automatically be created called "sgTests"

    	System.out.println("Starting learning");

    	int ngames = 1000;

      WorldObs worldObj = new WorldObs( this.domain, this.initialState,at);

       w.addWorldObserver(worldObj);
       
       for(int i = 0; i < ngames; i++){
    	   
      GameAnalysis ga = w.runGame();
      
       if(i % 10 == 0){
        System.out.println("Game: " + i + ": " + ga.numTimeSteps());
       }

        ga.writeToFile(String.format("sgQLearningTest/%4d", i));

    	}

      System.out.println("Finished learning");

      //now load the game sequence visualizer so we can review what happened during learning
      
      new GameSequenceVisualizer(v, domain, "sgQLearningTest/");

    	}

    
    public static class  WorldObs implements WorldObserver{

       SGDomain domain1;

      State initialState1;

      JointAction jaToBeQueried;
      
      SGNaiveQLAgent agent;
      
      public WorldObs(SGDomain domain,State initialState,SGNaiveQLAgent agent)

    	    {

    	    System.out.println("Inside world observer function");
            this.domain1 = domain;
            this.initialState1 = initialState;
    	    this.agent = agent;
           }
      
       
    	@Override

    	public void gameStarting(State s) {

    	// TODO Auto-generated method stub


    	}



    	@Override

    	public void observe(State s, JointAction ja, Map<String, Double> reward, State sp) {

    	// TODO Auto-generated method stub


    	}



    	@Override

    	public void gameEnding(State s) {


    	System.out.println("Inside game ending");
        
    	String sFileName ="MaxQResult";
        
    	SimpleSGAgentAction action0 = new SimpleSGAgentAction(this.domain1, "south");

        SimpleSGAgentAction action1 = new SimpleSGAgentAction(this.domain1, "noop");


        List<GroundedSGAgentAction> actions = new ArrayList<GroundedSGAgentAction>();
        actions.add(new SimpleGroundedSGAgentAction("agent0", action0));

        actions.add(new SimpleGroundedSGAgentAction("agent1", action1));

        JointAction ja = new JointAction(actions);
    	
    	
    	
    	//AbstractGroundedAction groundedAction1 = new GroundedSGAgentAction("agent0", action1);

        QValue qVal = this.agent.getQ(initialState1, ja);
        
        System.out.println("Q Value  " +qVal.q);

    	

        try {

    	  File file = new File("/Users/Anna/Documents/workspace/ReinforcementLearning/src/NaiveQ0Val");
         // File file1 = new File("/Users/Anna/Documents/workspace/ReinforcementLearning/src/NaiveQ11Val");

      if (!file.exists()) {

    	file.createNewFile();

    	}

    	


    	FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);


    	//FileWriter fw1 = new FileWriter(file1.getAbsoluteFile(),true);


    	BufferedWriter bw = new BufferedWriter(fw);


    	//BufferedWriter bw1 = new BufferedWriter(fw1);


    	count++;


    	if(count %20 ==0)


    	{

    	bw.newLine();

    	//bw1.newLine();


    	  }

    	bw.write(String.valueOf(qVal.q) +",");

    	//bw1.write(String.valueOf(qValAgent1) +",");


    	//bw1.flush();

    	bw.flush();

    	bw.close();

    //	bw1.close();



    	} catch (IOException e) {

    	// TODO Auto-generated catch block

    	e.printStackTrace();

    	}

    	 

    	}

    	}


    public static void main(String[] args) {

		MultiAgentNaiveQLVer1  esg = new MultiAgentNaiveQLVer1();
		
		esg.NaiveQLearning();

	}
}
    
