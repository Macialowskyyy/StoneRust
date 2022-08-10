package me.macialowskyyy.stonerust.sql;

import me.macialowskyyy.stonerust.main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SQLGetter {


    public void createTable() {
        PreparedStatement ps;
        try {
            ps = Main.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS chunks " + "(id CHAR(255))");
            ps.executeUpdate();
            ps = Main.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS stones " + "(center CHAR(255), others VARCHAR(2000), hp INT(255))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createChunk(String id) {
        try {
            if (!existsChunk(id)) {
                PreparedStatement ps2 = Main.SQL.getConnection().prepareStatement("INSERT IGNORE INTO chunks (id) VALUES (?)");
                ps2.setString(1, id);
                ps2.executeUpdate();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void createCenter(String id, List<String> other, Integer hp) {
        try {
            if (!existsCenter(id)) {
                PreparedStatement ps2 = Main.SQL.getConnection().prepareStatement("INSERT IGNORE INTO stones (center,others,hp) VALUES (?,?,?)");
                ps2.setString(1, id);
                ps2.setString(2, String.valueOf(other).replace("[", " ").replace("]",""));
                ps2.setInt(3,hp);
                ps2.executeUpdate();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteCenter(String id) {
        try {
            if (!existsCenter(id)) {
                PreparedStatement ps2 = Main.SQL.getConnection().prepareStatement("DELETE FROM stones WHERE center=?");
                ps2.setString(1, id);
                ps2.executeUpdate();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public void deleteChunk(String id) {
        try {
            if (!existsCenter(id)) {
                PreparedStatement ps2 = Main.SQL.getConnection().prepareStatement("DELETE FROM chunks WHERE id=?");
                ps2.setString(1, id);
                ps2.executeUpdate();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public List<String> getBlocks(String id) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT others FROM stones WHERE center=?");
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                List<String> topa = new ArrayList<>();
                for (String str : rs.getString("others").split(", ")) {
                    topa.add(str);
                }
                return topa;
            }
            return null;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //find_in_set

    public String getCenter(String id) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT center FROM stones WHERE FIND_IN_SET(?, others)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            String players;
            if (rs.next()) {
                players = rs.getString("center");
                return players;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getHp(String id) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT hp FROM stones WHERE FIND_IN_SET(?, others)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            int players = 0;
            if (rs.next()) {
                players = rs.getInt("hp");
                return players;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getHpCenter(String id) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT hp FROM stones WHERE center=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            int players = 0;
            if (rs.next()) {
                players = rs.getInt("hp");
                return players;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void removeHp(String id, Integer pl) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("UPDATE stones SET hp=? WHERE center=?");
            ps.setInt(1, (getHpCenter(id) - pl));
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public boolean existsChunk(String id) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT * FROM chunks WHERE id=?");
            ps.setString(1, id);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsCenter(String id) {
        try {
            PreparedStatement ps = Main.SQL.getConnection().prepareStatement("SELECT * FROM stones WHERE FIND_IN_SET(?, others)");
            ps.setString(1, id);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




}
