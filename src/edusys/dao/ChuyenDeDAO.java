/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edusys.dao;

import edusys.entity.ChuyenDe;
import edusys.utils.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;


public class ChuyenDeDAO extends EduSysDAO<ChuyenDe, String> {

    final String INSERT_SQL = """
                             INSERT INTO [dbo].[ChuyenDe]
                                        ([MACD]
                                        ,[TENCD]
                                        ,[HOCPHI]
                                        ,[THOILUONG]
                                        ,[HINH]
                                        ,[MOTA])
                                  VALUES
                                        (?,?,?,?,?,?)
                             """;
    final String UPDATE_SQL = """
                             UPDATE [dbo].[ChuyenDe]
                                    SET [TENCD] = ?
                                       ,[HOCPHI] = ?
                                       ,[THOILUONG] = ?
                                       ,[HINH] = ?
                                       ,[MOTA] = ?
                                  WHERE MACD = ?
                             """;
    final String DELETE_SQL = """
                             DELETE FROM [dbo].[ChuyenDe]
                                WHERE MACD = ?
                             """;
    final String SELECT_ALL_SQL = """
                             SELECT [MACD]
                                    ,[TENCD]
                                    ,[HOCPHI]
                                    ,[THOILUONG]
                                    ,[HINH]
                                    ,[MOTA]
                                FROM [dbo].[ChuyenDe]
                             """;
    final String SELECT_BY_ID_SQL = """
                             SELECT [MACD]
                                    ,[TENCD]
                                    ,[HOCPHI]
                                    ,[THOILUONG]
                                    ,[HINH]
                                    ,[MOTA]
                                FROM [dbo].[ChuyenDe]
                                   WHERE MACD = ?
                             """;

    @Override
    public void insert(ChuyenDe entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaCD(), entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getHinh(), entity.getMoTa());
    }

    @Override
    public void update(ChuyenDe entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL, entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getHinh(), entity.getMoTa(), entity.getMaCD());

    }

    @Override
    public void delete(String id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<ChuyenDe> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public ChuyenDe selectById(String id) {
        List<ChuyenDe> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ChuyenDe> selectBySql(String sql, Object... args) {
        List<ChuyenDe> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                ChuyenDe entity = new ChuyenDe();
                entity.setMaCD(rs.getString(1));
                entity.setTenCD(rs.getString(2));
                entity.setHocPhi(rs.getDouble(3));
                entity.setThoiLuong(rs.getInt(4));
                entity.setHinh(rs.getString(5));
                entity.setMoTa(rs.getString(6));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
