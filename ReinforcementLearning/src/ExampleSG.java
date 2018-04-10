import burlap.behavior.stochasticgames.GameAnalysis;
import burlap.behavior.stochasticgames.PolicyFromJointPolicy;
import burlap.behavior.stochasticgames.agents.maql.MultiAgentQLearning;
import burlap.behavior.stochasticgames.auxiliary.GameSequenceVisualizer;
import burlap.behavior.stochasticgames.madynamicprogramming.backupOperators.CoCoQ;
import burlap.behavior.stochasticgames.madynamicprogramming.backupOperators.CorrelatedQ;
import burlap.behavior.stochasticgames.madynamicprogramming.policies.ECorrelatedQJointPolicy;
import burlap.behavior.stochasticgames.madynamicprogramming.policies.EGreedyMaxWellfare;
import burlap.behavior.stochasticgames.solvers.CorrelatedEquilibriumSolver.CorrelatedEquilibriumObjective;
import burlap.debugtools.DPrint;
import burlap.domain.stochasticgames.gridgame.GGVisualizer;
import burlap.domain.stochasticgames.gridgame.GridGame;
import burlap.domain.stochasticgames.gridgame.GridGameStandardMechanics;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.legacy.StateParser;
import burlap.oomdp.legacy.StateYAMLParser;
import burlap.oomdp.statehashing.DiscretizingHashableStateFactory;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;
import burlap.oomdp.stochasticgames.JointActionModel;
import burlap.oomdp.stochasticgames.JointReward;
import burlap.oomdp.stochasticgames.SGAgentType;
import burlap.oomdp.stochasticgames.SGDomain;
import burlap.oomdp.stochasticgames.World;
import burlap.oomdp.stochasticgames.common.ConstantSGStateGenerator;
import burlap.oomdp.visualizer.Visualizer;

public class ExampleSG {
	
	//grid game domain generator
		protected ExpGridGame2 domainGen;

		//stochastic games domain
		protected SGDomain domain;

		//joint action model we want to use for grid game
		protected JointActionModel jam;

		//the type of agent for both agents (specifies their object class defining their personal state and their action set)
		protected  SGAgentType at;

		//discrete state hashing factory for state look up and equality checks
		protected SimpleHashableStateFactory hashingFactory;

		//the joint reward function defining the objective
		protected JointReward rf;

		//a terminal function for defining end states of the game
		protected TerminalFunction tf;

		//stochastic game discount factor
		protected double discount = 0.99;

		//initial state of the game
		protected State initialStste;

		//a world of agents that will interact in a grid world
		protected World w;

		//Grid Game visualizer so we can watch examples of the policy
		Visualizer v;


		public static void main(String[] args) {

			ExampleSG esg = new ExampleSG();

			//esg.MAValueIteration();
			esg.MAQLearning();

		}



		public ExampleSG(){

			this.domainGen = new ExpGridGame2();
			this.domain = (SGDomain)domainGen.generateDomain(); //type cast output to stochastic games domain
			this.jam = new ExpGridMechanics(this.domain);
			this.rf = new ExpGridGame2.GGJointRewardFunction1(this.domain,0.0,100.0,-100.0);
			this.tf = new ExpGridGame2.GGTerminalFunction(this.domain);

			//set the grid game agents will play to turkey
			//this.initialStste = GridGame.getTurkeyInitialState(this.domain);
			this.initialStste = ExpGridGame2.getTurkeyInitialState(this.domain);

			this.hashingFactory = new SimpleHashableStateFactory();

			//both agents are the same type in grid games with the same action set available
			this.at = new SGAgentType(ExpGridGame2.CLASSAGENT, domain.getObjectClass(ExpGridGame2.CLASSAGENT), domain.getAgentActions());
            //this.w = new World(domain, new GridGameStandardMechanics(domain), this.rf, new GridGame.GGTerminalFunction(this.domain),
			//new ConstantSGStateGenerator(this.initialStste));
			
			this.w = new World(domain, this.rf, new ExpGridGame2.GGTerminalFunction(this.domain),
					new ConstantSGStateGenerator(this.initialStste));


			this.v = GGVisualizer.getVisualizer(9, 9);

		}
		
		
		public void MAQLearning(){

			//set parameters for our learning agents
			//we will use a semi-optimistic q-value initialization which we will then couple with an epsilon-greedy
			//policy to promote exploration, but not be overly conservative which takes a long time to learn
			double learningRate = 0.001;
			double defaultQ = 0.0;
			double epsilon = 0.1;
			//CorrelatedEquilibriumSolver.CorrelatedEquilibriumObjective objectiveType = UTILITARIAN ;

			//create CoCo-Q (promotes cooperation) learning agents
			MultiAgentQLearning a0 = new MultiAgentQLearning(this.domain, this.discount, learningRate,
					this.hashingFactory, defaultQ, new CorrelatedQ(
							CorrelatedEquilibriumObjective.EGALITARIAN), true);
			MultiAgentQLearning a1 = new MultiAgentQLearning(this.domain, this.discount, learningRate,
					this.hashingFactory, defaultQ, new CorrelatedQ(
							CorrelatedEquilibriumObjective.EGALITARIAN), true);

			//have them joint the world
			a0.joinWorld(this.w, this.at);
			a1.joinWorld(this.w, this.at);


			//set their policies to be a epsilon greedy maxwelfare (which CoCo-Q uses) policy over the joint actions
			//with ties broken randomly
			ECorrelatedQJointPolicy ja0 = new ECorrelatedQJointPolicy(CorrelatedEquilibriumObjective.UTILITARIAN,epsilon);
			ECorrelatedQJointPolicy ja1 = new ECorrelatedQJointPolicy(CorrelatedEquilibriumObjective.UTILITARIAN,epsilon);
			//ja0.setBreakTiesRandomly(true);
			//ja1.setBreakTiesRandomly(true);

			a0.setLearningPolicy(new PolicyFromJointPolicy(a0.getAgentName(), ja0));
			a1.setLearningPolicy(new PolicyFromJointPolicy(a1.getAgentName(), ja1));


			//we're going to run a lot of learning episodes, so lets disable the world debug console print outs
			DPrint.toggleCode(w.getDebugId(), false);


			//run 2500 learning episodes. We'll let these run without using our visualizer for speed.
			//However, we'll save each learning episode to disk so we can review them afterwards.

			//first create a standard state parser for saving data to disk
			StateParser sp = new StateYAMLParser(this.domain);

			//now run training; games will be saved in a directory
			//that will automatically be created called "sgTests"
			System.out.println("Starting learning");
			int ngames = 2500;
			for(int i = 0; i < ngames; i++){

				GameAnalysis ga = w.runGame();
				if(i % 10 == 0){
					System.out.println("Game: " + i + ": " + ga.numTimeSteps());
					System.out.println(a1.getQSources());
				}
				//ga.writeToFile(String.format("sgTests/%4d", i));
				ga.writeToFile(String.format("sgTests/%4d", i));
			}

			System.out.println("Finished learning");

			//now load the game sequence visualizer so we can review what happened during learning
			new GameSequenceVisualizer(v, domain, "sgTests/");
			//new GameSequenceVisualizer(v, domain, sp, "sgTests/");



		}


	}

		
		
		
		
		
		
		
		


