package Database.places.requests;

import Database.places.models.Place;
import Database.places.models.PlaceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PlaceRequests {
    Connection conn;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Integer addPlace(Place place) {
        int placeId = getNextPlaceId();
        place.setPlaceId(placeId);
        String sqlAddPlace = "INSERT INTO libraryAdmin.places_table VALUES (" +
                placeId + ", " +
                "'" + place.getPlaceName() + "' ," +
                "'" + place.getPlaceType() + "' ," +
                "'" + place.getPlaceAddress() + "')";

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlAddPlace);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't add new place");
            throwables.printStackTrace();
        }
        return placeId;
    }

    public void deletePlace(Integer placeId) {
        String sqlDeletePlace = "DELETE FROM libraryAdmin.places_table WHERE place_id = " + placeId;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlDeletePlace);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't delete place");
            throwables.printStackTrace();

        }
    }

    public void updatePlace(Place place) {
        String sqlUpdatePlace = "UPDATE libraryAdmin.places_table SET ";
        sqlUpdatePlace += "place_type = '" + place.getPlaceType() + "' ";
        sqlUpdatePlace += ",place_address =  '" + place.getPlaceAddress() + "' ";
        sqlUpdatePlace += "WHERE place_id = " + place.getPlaceId();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlUpdatePlace);
            preStatementReader.executeUpdate();
        } catch (SQLException throwables) {
            System.err.println("can't update place");
            throwables.printStackTrace();
        }
    }

    public Place getPlace(Integer placeId) {
        String sqlGetPlace = "SELECT * from libraryAdmin.places_table WHERE place_id = " + placeId;
        Place place = null;

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetPlace);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                place = new Place();
                place.setPlaceId(rs.getInt("place_id"));
                place.setPlaceName(rs.getString("PLACE_NAME"));
                place.setPlaceType(PlaceType.valueOf(rs.getString("place_type")));
                place.setPlaceAddress(rs.getString("place_address"));
            }
        } catch (SQLException throwables) {
            System.err.println("can't get place");
            throwables.printStackTrace();
        }
        return place;

    }

    public Place getPlaceByName(String name) {
        String sqlGetPlace = "SELECT * from libraryAdmin.places_table WHERE place_name = '" + name + "' ";
        Place place = null;

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetPlace);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                place = new Place();
                place.setPlaceId(rs.getInt("place_id"));
                place.setPlaceName(rs.getString("PLACE_NAME"));
                place.setPlaceType(PlaceType.valueOf(rs.getString("place_type")));
                place.setPlaceAddress(rs.getString("place_address"));
            }
        } catch (SQLException throwables) {
            System.err.println("can't get place");
            throwables.printStackTrace();
        }
        return place;

    }

    public List<Place> getPlaces(PlaceType type) {
        String sqlGetPlace = "SELECT * from libraryAdmin.places_table ";
        if (type != null) sqlGetPlace += "WHERE place_type = " + type;
        List<Place> places = new LinkedList<>();
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetPlace);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                Place place = new Place();
                place.setPlaceId(rs.getInt("place_id"));
                place.setPlaceName(rs.getString("PLACE_NAME"));
                place.setPlaceType(PlaceType.valueOf(rs.getString("place_type")));
                place.setPlaceAddress(rs.getString("place_address"));
                places.add(place);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get places");
            throwables.printStackTrace();
        }
        return places;

    }

    public List<Place> getPlaces() {
        String sqlGetPlace = "SELECT * from libraryAdmin.places_table ";
        List<Place> places = new LinkedList<>();
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetPlace);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                Place place = new Place();
                place.setPlaceId(rs.getInt("place_id"));
                place.setPlaceName(rs.getString("PLACE_NAME"));
                place.setPlaceType(PlaceType.valueOf(rs.getString("place_type")));
                place.setPlaceAddress(rs.getString("place_address"));
                places.add(place);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get places");
            throwables.printStackTrace();
        }
        return places;


    }

    public List<Place> getPlacesWithFilter(Place placeFIlter) {
        String sqlGetPlace = "SELECT * from libraryAdmin.places_table ";
        String sqlFilter = "";
        if (placeFIlter.getPlaceType() != null)
            sqlFilter += " AND place_type = '" + placeFIlter.getPlaceType() + "' ";
        if (placeFIlter.getPlaceName() != null)
            sqlFilter += " AND PLACE_NAME = '" + placeFIlter.getPlaceName() + "' ";

        if (placeFIlter.getPlaceAddress() != null)
            sqlFilter += " AND place_address = '" + placeFIlter.getPlaceAddress() + "' ";

        if (sqlFilter.contains("AND")) {
            sqlFilter = sqlFilter.replaceFirst("AND", "WHERE");
            sqlGetPlace += sqlFilter;

        }

        List<Place> places = new LinkedList<>();
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetPlace);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                Place place = new Place();
                place.setPlaceId(rs.getInt("place_id"));
                place.setPlaceName(rs.getString("PLACE_NAME"));
                place.setPlaceType(PlaceType.valueOf(rs.getString("place_type")));
                place.setPlaceAddress(rs.getString("place_address"));
                places.add(place);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get places");
            throwables.printStackTrace();
        }
        return places;


    }

    public Integer getNextPlaceId() {
        String sql = "select sq_place.nextval from DUAL";
        Integer nextID = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                nextID = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nextID;
    }
}
