package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AvailableRides;


/**
 *
 * @author Othman Kurdi
 * 
 */
public class AvailableRidesDao extends ConnectionDao {    
    
    //it will be used by the AvailableRidesBean to flush the data into the tabel;(working)
    public ArrayList<AvailableRides> getRides() throws Exception {
                
        ArrayList<AvailableRides> list = new ArrayList<>();
        try {   
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM AVAILABLE_RIDES";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateRide(rs));
            }
            
            rs.close();
            ps.close();
            
            return list;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

   
    //it will cooperate with the getRide() methode so that it will seperate the 
    //returned data before return it again.
    private AvailableRides populateRide(ResultSet rs) throws SQLException {
        AvailableRides ride = new AvailableRides();
        
         ride.setRideID(rs.getInt("RIDE_ID"));
        ride.setRideFrom(rs.getString("RIDE_FROM"));
        ride.setRideTo(rs.getString("RIDE_TO"));
        ride.setName(rs.getString("DRIVER_NAME"));
        ride.setPhone(rs.getString("DRIVER_PHONE"));
        ride.setDepartureTime(rs.getString("DEPARTURE_TIME"));
        return ride;
    }
    //not working
    public void insertRide(AvailableRides ride) throws Exception {                
        System.out.println("reached dao...");
        try {
            Connection conn = getConnection();
            
            String sql = "INSERT INTO AVAILABLE_RIDES "
                    + "( RIDE_ID,"
                    + " RIDE_FROM,"
                    + " RIDE_TO,"
                    + " DRIVER_NAME,"
                    + " DRIVER_PHONE,"
                    + " DEPARTURE_TIME"
                    + " VALUES ((select max(RIDE_ID) from AVAILABLE_RIDES)+1,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, ride.getRideFrom());
                ps.setString(2, ride.getRideTo());
                ps.setString(3, ride.getName());
                ps.setString(4, ride.getPhone());
                ps.setString(5, ride.getDepartureTime());
                
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    //updating(not working)
    public void updateRide(AvailableRides ride) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE AVAILABLE_RIDES SET "
                    + "(RIDE_FROM=?,"
                    + " RIDE_TO=?,"
                    + " DRIVER_NAME=?,"
                    + " DRIVER_PHONE=?,"
                    + " DEPARTURE_TIME=?"
                    + " WHERE RIDE_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
           ps.setString(1, ride.getRideFrom());
            ps.setString(2, ride.getRideTo());
            ps.setString(3, ride.getName());
            ps.setString(4, ride.getPhone());
            ps.setString(5, ride.getDepartureTime());            
            ps.setInt(6, ride.getRideID());

            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    //to delete the selected ride.(working)
    public void deleteRide(int RideID) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM AVAILABLE_RIDES WHERE RIDE_ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, RideID);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    //this methode is to get only one ride by id;
         public AvailableRides getRide(int ride_id) throws Exception {
        try {   
            AvailableRides ride=null;
            Connection conn = getConnection();
            
            String sql = "SELECT AVAILABLE_RIDES.* WHERE RIDE_ID=?";                        
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, ride_id);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                ride = populateRide(rs);
            }

            rs.close();
            ps.close();
            
            return ride;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public static void main(String [] args){        
        try {
            AvailableRidesDao dao = new AvailableRidesDao();                
            
        } catch (Exception ex) {
            Logger.getLogger(AvailableRidesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
