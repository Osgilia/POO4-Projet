package dao;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class DaoFactoryJpa extends DaoFactory {

    /**
     * get an instance of Depot's JPADAO
     *
     * @return Depot JPADAO
     */
    @Override
    public DepotDao getDepotDao() {
        return JpaDepotDao.getInstance();
    }

    /**
     * get an instance of Customer's JPADAO
     *
     * @return Customer JPADAO
     */
    @Override
    public CustomerDao getCustomerDao() {
        return JpaCustomerDao.getInstance();
    }

    /**
     * get an instance of Route's JPADAO
     *
     * @return Route JPADAO
     */
    @Override
    public RouteDao getRouteDao() {
        return JpaRouteDao.getInstance();
    }

    /**
     * get an instance of Vehicle's JPADAO
     *
     * @return Vehicle JPADAO
     */
    @Override
    public VehicleDao getVehicleDao() {
        return JpaVehicleDao.getInstance();
    }

    /**
     * get an instance of Planning's JPADAO
     *
     * @return Planning JPADAO
     */
    @Override
    public PlanningDao getPlanningDao() {
        return JpaPlanningDao.getInstance();
    }

    /**
     * get an instance of Instance's JPADAO
     *
     * @return Instance JPADAO
     */
    @Override
    public InstanceDao getInstanceDao() {
        return JpaInstanceDao.getInstance();
    }

    /**
     * get an instance of DayHorizon's JPADAO
     *
     * @return DayHorizon JPADAO
     */
    @Override
    public DayHorizonDao getDayHorizonDao() {
        return JpaDayHorizonDao.getInstance();
    }

    /**
     * get an instance of Demand's JPADAO
     *
     * @return Demand JPADAO
     */
    @Override
    public DemandDao getDemandDao() {
        return JpaDemandDao.getInstance();
    }

    /**
     * get an instance of Itinerary's JPADAO
     *
     * @return Itinerary JPADAO
     */
    @Override
    public ItineraryDao getItineraryDao() {
        return JpaItineraryDao.getInstance();
    }

    /**
     * get an instance of MachineType's JPADAO
     *
     * @return MachineType JPADAO
     */
    @Override
    public MachineTypeDao getMachineTypeDao() {
        return JpaMachineTypeDao.getInstance();
    }

    /**
     * get an instance of PlannedDemand's JPADAO
     *
     * @return PlannedDemand JPADAO
     */
    @Override
    public PlannedDemandDao getPlannedDemandDao() {
        return JpaPlannedDemandDao.getInstance();
    }

    /**
     * get an instance of Point's JPADAO
     *
     * @return Point JPADAO
     */
    @Override
    public PointDao getPointDao() {
        return JpaPointDao.getInstance();
    }

    /**
     * get an instance of Technician's JPADAO
     *
     * @return Technician JPADAO
     */
    @Override
    public TechnicianDao getTechnicianDao() {
        return JpaTechnicianDao.getInstance();
    }

    /**
     * get an instance of TechnicianItinerary's JPADAO
     *
     * @return TechnicianItinerary JPADAO
     */
    @Override
    public TechnicianItineraryDao getTechnicianItineraryDao() {
        return JpaTechnicianItineraryDao.getInstance();
    }

    /**
     * get an instance of VehicleItinerary's JPADAO
     *
     * @return VehicleItinerary JPADAO
     */
    @Override
    public VehicleItineraryDao getVehicleItineraryDao() {
        return JpaVehicleItineraryDao.getInstance();
    }

}
