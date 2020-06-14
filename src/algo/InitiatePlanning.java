/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import dao.DemandDao;
import dao.InstanceDao;
import dao.PlannedDemandDao;
import dao.PlanningDao;
import java.io.IOException;
import java.util.Collection;
import modele.Demand;
import modele.Instance;
import modele.Planning;

/**
 *
 * @author Osgilia
 */
public class InitiatePlanning {

    public static Planning createPlanning(
            Instance instance,
            InstanceDao instanceManager,
            PlanningDao planningManager,
            DemandDao demandManager,
            PlannedDemandDao plannedDemandManager,
            String algoName
    ) throws IOException {

        Planning planning = new Planning(instance, instance.getNbDays(), algoName);
        instance.addPlanning(planning);
        planningManager.create(planning);
        //instanceManager.update(instance);

        //get customers by instance
        
        Collection<Demand> demands = demandManager.findByInstance(instance);
        System.out.println(demands);

        //set Planning Demands
        for (Demand d : demands) {
            planning.addDemand(d, plannedDemandManager, demandManager);
        }
        return planning;
    }
}
