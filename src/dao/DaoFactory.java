package dao;

/**
 *
 * @author Henri, Lucas, Louis
 */
public abstract class DaoFactory {
    public static DaoFactory getDaoFactory (PersistenceType type) {
        if(type.equals(PersistenceType.Jpa)) {
            return new DaoFactoryJpa();
        }
        return null;
    }
    
    public abstract DepotDao getDepotDao();

    public abstract CustomerDao getCustomerDao();

    public abstract RouteDao getRouteDao();

    public abstract VehicleDao getVehicleDao();

    public abstract PlanningDao getPlanningDao();

    public abstract InstanceDao getInstanceDao();
}
