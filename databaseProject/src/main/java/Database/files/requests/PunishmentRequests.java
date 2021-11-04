package Database.files.requests;

import Database.files.models.Punishment;
import Database.files.models.PunishmentStatus;
import Database.files.models.PunishmentType;
import Database.search.DateSearch;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class PunishmentRequests {

    Connection conn;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Integer addPunishment(Punishment punishment) {
        int punishmentId = getNextPunishmentId();
        punishment.setPunishmentId(punishmentId);
        String sqlAddPunishment = "INSERT INTO libraryAdmin.punishments_table VALUES (" +
                punishmentId + ", " +
                punishment.getReaderId() + " ," +
                "'" + punishment.getPunishmentType() + "' ," +
                toDate(punishment.getStartDate()) + " ," +
                toDate(punishment.getEndDate()) + " ," +
                punishment.getPayment() + " ," +
                "'" + punishment.getStatus() + "' ," +
                "'" + punishment.getReason() + "'" +
                ")";

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlAddPunishment);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't add new punishment");
            throwables.printStackTrace();
        }
        return punishmentId;

    }

    public void deletePunishment(Integer punishmentId) {
        String sqlDeletePunishment = "DELETE FROM libraryAdmin.punishments_table WHERE punishment_id=" + punishmentId;
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlDeletePunishment);
            preStatementReader.executeUpdate();

        } catch (SQLException throwables) {
            System.err.println("can't delete punishment");
            throwables.printStackTrace();

        }
    }

    public void updatePunishment(Punishment punishment) {
        String sqlUpdatePunishment = "UPDATE libraryAdmin.punishments_table SET ";
        sqlUpdatePunishment += "punishment_id = " + punishment.getPunishmentId() + ", ";
        sqlUpdatePunishment += "reader_id = " + punishment.getReaderId() + ", ";
        sqlUpdatePunishment += "punishment_type = '" + punishment.getPunishmentType() + "', ";
        sqlUpdatePunishment += "punishment_start_date = " + toDate(punishment.getStartDate()) + ", ";
        sqlUpdatePunishment += "punishment_end_date = " + toDate(punishment.getEndDate()) + ", ";
        sqlUpdatePunishment += "punishment_payment = " + punishment.getPayment()+ ", ";
        sqlUpdatePunishment += "punishment_status = '" + punishment.getStatus() + "', ";
        sqlUpdatePunishment += "punishment_comment = '"+ punishment.getReason() + "' ";
        sqlUpdatePunishment += "WHERE punishment_id = " + punishment.getPunishmentId();

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlUpdatePunishment);
            preStatementReader.executeUpdate();
        } catch (SQLException throwables) {
            System.err.println("can't update punishment");
            throwables.printStackTrace();
        }
    }

    public Punishment getPunishment(Integer punishmentId) {
        String sqlGetPunishment = "SELECT * from libraryAdmin.punishments_table WHERE punishment_id=" + punishmentId;
        Punishment punishment = null;

        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetPunishment);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                punishment = new Punishment();
                punishment.setPunishmentId(rs.getInt("punishment_id"));
                punishment.setReaderId(rs.getInt("reader_id"));
                punishment.setPunishmentType(PunishmentType.valueOf(rs.getString("punishment_type")));
                punishment.setStartDate(rs.getDate("punishment_start_date"));
                punishment.setEndDate(rs.getDate("punishment_end_date"));
                punishment.setPayment(rs.getInt("punishment_payment"));
                punishment.setStatus(PunishmentStatus.valueOf(rs.getString("punishment_status")));
                punishment.setReason(rs.getString("punishment_comment"));

            }
        } catch (SQLException throwables) {
            System.err.println("can't get punishment");
            throwables.printStackTrace();
        }
        return punishment;
    }

    public List<Punishment> getPunishments() {
        String sqlGetPunishments = "SELECT * from libraryAdmin.punishments_table ";
        List<Punishment> punishments = new LinkedList<>();
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetPunishments);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                Punishment punishment = new Punishment();
                punishment.setPunishmentId(rs.getInt("punishment_id"));
                punishment.setReaderId(rs.getInt("reader_id"));
                punishment.setPunishmentType(PunishmentType.valueOf(rs.getString("punishment_type")));
                punishment.setStartDate(rs.getDate("punishment_start_date"));
                punishment.setEndDate(rs.getDate("punishment_end_date"));
                punishment.setPayment(rs.getInt("punishment_payment"));
                String status = rs.getString("punishment_status");
                if (status != null)
                    punishment.setStatus(PunishmentStatus.valueOf(rs.getString("punishment_status")));
                punishment.setReason(rs.getString("punishment_comment"));
                punishments.add(punishment);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get punishments");
            throwables.printStackTrace();
        }
        return punishments;
    }

    public List<Punishment> getPunishments(Integer readerId) {
        String sqlGetPunishments = "SELECT * from libraryAdmin.punishments_table WHERE reader_id=" + readerId;
        List<Punishment> punishments = new LinkedList<>();
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetPunishments);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                Punishment punishment = new Punishment();
                punishment.setPunishmentId(rs.getInt("punishment_id"));
                punishment.setReaderId(rs.getInt("reader_id"));
                punishment.setPunishmentType(PunishmentType.valueOf(rs.getString("punishment_type")));
                punishment.setStartDate(rs.getDate("punishment_start_date"));
                punishment.setEndDate(rs.getDate("punishment_end_date"));
                punishment.setPayment(rs.getInt("punishment_payment"));
                punishment.setStatus(PunishmentStatus.valueOf(rs.getString("punishment_status")));
                punishment.setReason(rs.getString("punishment_comment"));
                punishments.add(punishment);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get punishments");
            throwables.printStackTrace();
        }
        return punishments;
    }

    public List<Punishment> getPunishmentsWithFilter(Punishment filter, DateSearch dateTake, DateSearch dateRet){
        String sqlGetPunishments = "SELECT * from libraryAdmin.punishments_table ";
        String sqlFilter = "";


        if(filter.getStartDate()!=null){
            switch (dateTake){
                case РАВНО:
                    sqlFilter += " AND taking_date = " + toDate(filter.getStartDate());
                    break;

                case БОЛЬШЕ:
                    sqlFilter += " AND taking_date > " + toDate(filter.getStartDate());
                    break;

                case МЕНЬШЕ:
                    sqlFilter += " AND taking_date < " + toDate(filter.getStartDate());
                    break;

            }
        }

        if(filter.getEndDate()!=null){
            switch (dateRet){
                case РАВНО:
                    sqlFilter += " AND returning_date = " + toDate(filter.getStartDate());
                    break;

                case БОЛЬШЕ:
                    sqlFilter += " AND returning_date > " + toDate(filter.getStartDate());
                    break;

                case МЕНЬШЕ:
                    sqlFilter += " AND returning_date < " + toDate(filter.getStartDate());
                    break;

            }
        }

        if(filter.getPunishmentType()!=null)
            sqlFilter += " AND punishment_type = '" + filter.getPunishmentType()+"'";
        if(filter.getPayment()!=null)
            sqlFilter += " AND punishment_payment = " + filter.getPayment();

        if(filter.getStatus()!=null)
            sqlFilter += " AND punishment_status = '" + filter.getStatus()+"'";
        if(filter.getReaderId()!=null)
            sqlFilter += " AND reader_id = '" + filter.getReaderId()+"'";


        if(sqlFilter.contains("AND")) {
            sqlFilter = sqlFilter.replaceFirst("AND", "WHERE");
            sqlGetPunishments+=sqlFilter;

        }

        List<Punishment> punishments = new LinkedList<>();
        try {
            PreparedStatement preStatementReader = conn.prepareStatement(sqlGetPunishments);
            ResultSet rs = preStatementReader.executeQuery();
            while (rs.next()) {
                Punishment punishment = new Punishment();
                punishment.setPunishmentId(rs.getInt("punishment_id"));
                punishment.setReaderId(rs.getInt("reader_id"));
                punishment.setPunishmentType(PunishmentType.valueOf(rs.getString("punishment_type")));
                punishment.setStartDate(rs.getDate("punishment_start_date"));
                punishment.setEndDate(rs.getDate("punishment_end_date"));
                punishment.setPayment(rs.getInt("punishment_payment"));
                punishment.setStatus(PunishmentStatus.valueOf(rs.getString("punishment_status")));
                punishment.setReason(rs.getString("punishment_comment"));
                punishments.add(punishment);
            }
        } catch (SQLException throwables) {
            System.err.println("can't get punishments");
            throwables.printStackTrace();
        }
        return punishments;

    }

    public Integer getNextPunishmentId() {
        String sql = "select sq_punishment.nextval from DUAL";
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

    public String toDate(Date date) {
        if (date == null)
            return "TO_DATE (null, 'dd.mm.yyyy')";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedString = date.toLocalDate().format(formatter);

        return "TO_DATE ( '" + formattedString + "', 'dd.mm.yyyy')";
    }
}
