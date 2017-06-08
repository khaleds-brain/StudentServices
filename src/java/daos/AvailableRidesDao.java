package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AvailableRide;


/**
 *
 * @author Othman Kurdi
 * 
 */
public class AvailableRidesDao extends ConnectionDao {    
    
    //it will be used by the AvailableRidesBean to flush the data into the tabel;(working)
    public ArrayList<AvailableRide> getRides() throws Exception {
                
        ArrayList<AvailableRide> list = new ArrayList<>();
        try {   
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM STUDENTS_CARPOOLING JOIN DESTINATIONS ON STUDENTS_CARPOOLING.RIDE_ID=DESTINATIONS.RIDE_ID JOIN CARS ON STUDENTS_CARPOOLING.RIDE_ID=CARS.RIDE_ID;";                     
            try (PreparedStatement ps = conn.prepareStatement(sql);) {
                 ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(populateRide(rs));
                }
                
            }
            
            return list;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private AvailableRide populateRide(ResultSet rs) throws SQLException {
        AvailableRide ride = new AvailableRide();
        
        ride.setRideID(rs.getInt("RIDE_ID"));
        ride.setStudentId(rs.getInt("STUDENT_ID"));
        ride.setName(rs.getString("STUDENT_NAME"));
        ride.setPhone(rs.getString("PHONE"));
        ride.setGender(rs.getString("GENDER"));
        
        ride.setRideFrom(rs.getString("RIDE_FROM"));
        ride.setRideTo(rs.getString("RIDE_TO"));
        ride.setDepartureTime(rs.getString("DEPARTURE_TIME"));
        
        ride.setCarPlateNumber(rs.getInt("CAR_PLATE_NUMBER"));
        ride.setCarMake(rs.getString("MAKE"));
        ride.setCarModel(rs.getString("MODEL"));
        ride.setYearOfMake(rs.getString("YEAR_OF_MAKE"));
        ride.setCarColor(rs.getString("COLOR"));
        
        return ride;
    }
    //it will cooperate with the getRide() methode so that it will seperate the 
    //returned data before return it again.
   private AvailableRide populateRide2(ResultSet rs) throws SQLException {
        AvailableRide ride = new AvailableRide();
        
        
        
        ride.setName(rs.getString("STUDENT_NAME"));
        ride.setPhone(rs.getString("PHONE"));
        //ride.setGender(rs.getString("GENDER"));
        
        ride.setRideFrom(rs.getString("RIDE_FROM"));
        ride.setRideTo(rs.getString("RIDE_TO"));
        ride.setDepartureTime(rs.getString("DEPARTURE_TIME"));
        
        
        
        return ride;
    }
    
    //to delete the selected ride.(working)
    public void deleteRide(int RideID) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM STUDENTS_CARPOOLING, CARS, DESTINATION WHERE RIDE_ID=?";                               
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, RideID);
                
                ps.executeUpdate();
            }
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
