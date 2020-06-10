package dao;

/**
 *
 * @author Henri, Lucas, Louis
 */
public abstract class DaoFactory {

    public static DaoFactory getDaoFactory(PersistenceType type) {
        if (type.equals(PersistenceType.Jpa)) {
            return new DaoFactoryJpa();
        }
        return null;
    }

    public abstract CustomerDao getCustomerDao();

    public abstract DayHorizonDao getDayHorizonDao();

    public abstract DemandDao getDemandDao();

    public abstract DepotDao getDepotDao();

    public abstract InstanceDao getInstanceDao();

    public abstract ItineraryDao getItineraryDao();

    public abstract MachineTypeDao getMachineTypeDao();

    public abstract PlannedDemandDao getPlannedDemandDao();

    public abstract PlanningDao getPlanningDao();

    public abstract PointDao getPointDao();

    public abstract RouteDao getRouteDao();

    public abstract TechnicianDao getTechnicianDao();

    public abstract TechnicianItineraryDao getTechnicianItineraryDao();

    public abstract VehicleItineraryDao getVehicleItineraryDao();

    public abstract VehicleDao getVehicleDao();

}
