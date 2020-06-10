package dao;
/**
 *
 * @author Henri, Lucas, Louis
 */
public class DaoFactoryJpa extends DaoFactory {
    @Override
    public DepotDao getDepotDao() {
        return JpaDepotDao.getInstance();
    }

    @Override
    public CustomerDao getCustomerDao() {
        return JpaCustomerDao.getInstance();
    }

    @Override
    public RouteDao getRouteDao() {
        return JpaRouteDao.getInstance();
    }

    @Override
    public VehicleDao getVehicleDao() {
        return JpaVehicleDao.getInstance();
    }

    @Override
    public PlanningDao getPlanningDao() {
        return JpaPlanningDao.getInstance();
    }

    @Override
    public InstanceDao getInstanceDao() {
        return JpaInstanceDao.getInstance();
    }

    @Override
    public DayHorizonDao getDayHorizonDao() {
        return JpaDayHorizonDao.getInstance();
    }

    @Override
    public DemandDao getDemandDao() {
        return JpaDemandDao.getInstance();
    }

    @Override
    public ItineraryDao getItineraryDao() {
        return JpaItineraryDao.getInstance();
    }

    @Override
    public MachineTypeDao getMachineTypeDao() {
        return JpaMachineTypeDao.getInstance();
    }

    @Override
    public PlannedDemandDao getPlannedDemandDao() {
        return JpaPlannedDemandDao.getInstance();
    }

    @Override
    public PointDao getPointDao() {
        return JpaPointDao.getInstance();
    }

    @Override
    public TechnicianDao getTechnicianDao() {
        return JpaTechnicianDao.getInstance();
    }

    @Override
    public TechnicianItineraryDao getTechnicianItineraryDao() {
        return JpaTechnicianItineraryDao.getInstance();
    }

    @Override
    public VehicleItineraryDao getVehicleItineraryDao() {
        return JpaVehicleItineraryDao.getInstance();
    }
    
    
}
