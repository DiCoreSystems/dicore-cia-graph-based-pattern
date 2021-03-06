package de.uniks.vs.evolution.tests.dicore;

import de.uniks.vs.evolution.dicore.cia.ChangeImpactPattern;
import de.uniks.vs.evolution.dicore.cia.MatchingAlgorithms;
import de.uniks.vs.evolution.graph.analysis.graphs.DirectedGraph;
import de.uniks.vs.evolution.graphmodels.*;
import de.uniks.vs.evolution.tests.dicore.pattern.PatternCreator;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by alex on 16/13/10.
 */


public class SimpleCIATest2 {

	public static final String PATH = "./src/de/uniks/vs/evolution/tests/graph/analysis/";
    public static final String CREATOR_PATH = "./src/de/uniks/vs/evolution/tests/dicore/creators/";

    String oldBPMN      = "Simple1.bpmn.20.xml";
    String newBPMN      = "Simple2.bpmn.20.xml";

    CodeGeneratorTools generatorTools = new CodeGeneratorTools();
    GraphTools graphTools = new GraphTools();
    
    @Test
    public void WorkflowTest() throws InterruptedException  {
    	PatternCreator patternCreator = new PatternCreator();
        ChangeImpactPattern insertPattern = patternCreator.createInsertPattern();
        
        // STEP1
        ModelManager modelManager =  graphTools.getPGModelManager(PATH + oldBPMN);
        GraphModel oldPGModel = modelManager.getLastModel();
				        ModelGraphManager.getInstance().add(modelManager);

        ModelManager modelManager1 =  graphTools.getPGModelManager(PATH + oldBPMN);		
        DirectedGraph dg = graphTools.getDependencyGraph(modelManager1);
        System.out.println("DG (Dependency Graph): " + dg);
        graphTools.convertDPtoGraphModel(dg, modelManager1, "DG1");
				        ModelGraphManager.getInstance().add(modelManager1);

        
        //STEP2
        ModelManager modelManager2 =  graphTools.getPGModelManager(PATH + newBPMN);
        GraphModel newPGModel = modelManager2.getLastModel();
				        ModelGraphManager.getInstance().add(modelManager2);


	    // STEP3
        MatchingAlgorithms matchingAlgorithms = new MatchingAlgorithms();
        HashMap<GraphItem, String> results = matchingAlgorithms.compareProcessGraphs(newPGModel, oldPGModel);
				        graphTools.printResults(results);
				        ModelGraphManager.getInstance().add(modelManager2);

        
        // STEP4
        ModelManager cloneModelManager = new ModelManager();
        GraphModel clone = newPGModel.clone();
        cloneModelManager.addGraphModel(clone, "Clone");
				        ModelGraphManager.getInstance().add(cloneModelManager);
//				        guiTest.startGUI2(1000, 250);
				        
				        
		ModelManager cloneModelManager2 = new ModelManager();
		GraphModel clone2 = newPGModel.clone();
		graphTools.reduceGraph(oldPGModel, clone2, results);
        
        HashMap<String, String> patternMatchingResult = matchingAlgorithms.matchPattern(insertPattern, clone2);
        matchingAlgorithms.printPatternMatchingResult(patternMatchingResult);
        cloneModelManager2.addGraphModel(clone2, "CloneReduced");
				        ModelGraphManager.getInstance().add(cloneModelManager2);

        // STEP5
//		ModelManager cloneModelManager3 = new ModelManager();
//		GraphModel clone3 = clone2.clone();
//		cloneModelManager3.addGraphModel(clone3, "Clone2");
//        DirectedGraph dg2 = graphTools.getDependencyGraph(cloneModelManager3);
//        System.out.println("DG (Dependency Graph): " + dg2);
//        graphTools.convertDPtoGraphModel(dg2, cloneModelManager3, "DG2");
//
//				        ModelGraphManager.getInstance().add(cloneModelManager3);
    }
}
