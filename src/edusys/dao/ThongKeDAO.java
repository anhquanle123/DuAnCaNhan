/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edusys.dao;

import edusys.utils.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ThongKeDAO {

    public List<Object[]> getNguoiHoc() {
    List<Object[]> list = new ArrayList<>();
    ResultSet rs = null;
    try {
        String sql = "{call sp_LuongNguoiHoc}";
        rs = JdbcHelper.executeQuery(sql);
        if (rs == null) {
            System.out.println("Lỗi: Không có dữ liệu từ stored procedure!");
            return list;
        }
        while (rs.next()) {
            Object[] model = {
                rs.getInt("Nam"),
                rs.getInt("SoLuong"),
                rs.getDate("DauTien"),
                rs.getDate("CuoiCung")
            };
            list.add(model);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) {
                rs.getStatement().getConnection().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return list;
}


    public List<Object[]> getBangDiem(int maKH) {
    List<Object[]> list = new ArrayList<>();
    ResultSet rs = null;
    try {
        String sql = "{call sp_BangDiem(?)}";
        rs = JdbcHelper.executeQuery(sql, maKH);
        if (rs == null) {
            System.out.println("Lỗi: Không có dữ liệu từ stored procedure!");
            return list;
        }
        while (rs.next()) {
            Double diem = rs.getDouble("Diem");
            if (rs.wasNull()) {
                diem = null; // Nếu điểm bị NULL, đặt là null để tránh lỗi
            }

            String xepLoai;
            if (diem == null) {
                xepLoai = "Chưa nhập";
            } else if (diem < 3) {
                xepLoai = "Kém";
            } else if (diem < 5) {
                xepLoai = "Yếu";
            } else if (diem < 6.5) {
                xepLoai = "Trung bình";
            } else if (diem < 7.5) {
                xepLoai = "Khá";
            } else if (diem < 9) {
                xepLoai = "Giỏi";
            } else {
                xepLoai = "Xuất sắc";
            }

            Object[] model = {
                rs.getString("MaNH"),
                rs.getString("HoTen"),
                diem, // Có thể là null
                xepLoai
            };
            list.add(model);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) {
                rs.getStatement().getConnection().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return list;
}

    public List<Object[]> getDiemTheoChuyenDe() {
    List<Object[]> list = new ArrayList<>();
    ResultSet rs = null;
    try {
        String sql = "{call sp_DiemChuyenDe}";
        rs = JdbcHelper.executeQuery(sql);
        if (rs == null) {
            System.out.println("Lỗi: Không có dữ liệu từ stored procedure!");
            return list;
        }
        while (rs.next()) {
            String tenChuyenDe = rs.getString("ChuyenDe");
            int soHV = rs.getInt("SoHV");

            Double thapNhat = rs.getDouble("ThapNhat");
            if (rs.wasNull()) thapNhat = null; // Nếu NULL thì đặt thành null

            Double caoNhat = rs.getDouble("CaoNhat");
            if (rs.wasNull()) caoNhat = null;

            Double trungBinh = rs.getDouble("TrungBinh");
            if (rs.wasNull()) trungBinh = null;

            Object[] model = {tenChuyenDe, soHV, thapNhat, caoNhat, trungBinh};
            list.add(model);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) {
                rs.getStatement().getConnection().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return list;
}


   public List<Object[]> getDoanhThu(int nam) {
    List<Object[]> list = new ArrayList<>();
    ResultSet rs = null;
    try {
        String sql = "{call sp_DoanhThu(?)}";
        rs = JdbcHelper.executeQuery(sql, nam);
        if (rs == null) {
            System.out.println("Lỗi: Không có dữ liệu từ stored procedure!");
            return list;
        }
        while (rs.next()) {
            String tenChuyenDe = rs.getString("ChuyenDe");
            int soKH = rs.getInt("SoKH");
            int soHV = rs.getInt("SoHV");

            Double doanhThu = rs.getDouble("DoanhThu");
            if (rs.wasNull()) doanhThu = null;

            Double thapNhat = rs.getDouble("ThapNhat");
            if (rs.wasNull()) thapNhat = null;

            Double caoNhat = rs.getDouble("CaoNhat");
            if (rs.wasNull()) caoNhat = null;

            Double trungBinh = rs.getDouble("TrungBinh");
            if (rs.wasNull()) trungBinh = null;

            Object[] model = {tenChuyenDe, soKH, soHV, doanhThu, thapNhat, caoNhat, trungBinh};
            list.add(model);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) {
                rs.getStatement().getConnection().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return list;
}

}
