/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edusys.dao;

import edusys.entity.NhanVien;
import edusys.utils.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;


public class NhanVienDAO extends EduSysDAO<NhanVien, String> {

    final String INSERT_SQL = """
                              INSERT INTO [dbo].[NhanVien]
                                         ([MANV]
                                         ,[MATKHAU]
                                         ,[HOTEN]
                                         ,[VAITRO])
                                   VALUES
                                         (?,?,?,?)
                              """;
    final String UPDATE_SQL = """
                             UPDATE [dbo].[NhanVien]
                                    SET [MATKHAU] = ?
                                       ,[HOTEN] = ?
                                       ,[VAITRO] = ?
                                  WHERE MANV = ?
                              """;
    final String DELETE_SQL = """
                              DELETE FROM [dbo].[NhanVien]
                                    WHERE MANV = ?
                              """;
    final String SELECT_ALL_SQL = """
                              SELECT [MANV]
                                    ,[MATKHAU]
                                    ,[HOTEN]
                                    ,[VAITRO]
                                FROM [dbo].[NhanVien]
                              """;
    final String SELECT_BY_ID_SQL = """
                              SELECT [MANV]
                                    ,[MATKHAU]
                                    ,[HOTEN]
                                    ,[VAITRO]
                                FROM [dbo].[NhanVien]
                                    WHERE MANV = ?
                              """;

    @Override
    public void insert(NhanVien entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaNV(), entity.getMatKhau(), entity.getHoTen(), entity.isVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL, entity.getMatKhau(), entity.getHoTen(), entity.isVaiTro(), entity.getMaNV());

    }

    @Override
    public void delete(String id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString(1));
                entity.setHoTen(rs.getString(3));
                entity.setMatKhau(rs.getString(2));
                entity.setVaiTro(rs.getBoolean(4));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
