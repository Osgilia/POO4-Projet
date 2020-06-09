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
}
