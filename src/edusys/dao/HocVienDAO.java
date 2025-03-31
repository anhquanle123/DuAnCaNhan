/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edusys.dao;

import edusys.entity.HocVien;
import edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class HocVienDAO extends EduSysDAO<HocVien, Integer> {

    final String INSERT_SQL = """
                        INSERT INTO [dbo].[HocVien]
                                   ([MAKH]
                                   ,[MANH]
                                   ,[DIEM])
                             VALUES
                                   (?,?,?)
                             """;
    final String UPDATE_SQL = """
                        UPDATE [dbo].[HocVien]
                           SET [MAKH] = ?
                              ,[MANH] = ?
                              ,[DIEM] = ?
                         WHERE MAHV = ?
                             """;
    final String DELETE_SQL = """
                             DELETE FROM [dbo].[HocVien]
                                   WHERE MAHV = ?
                             """;
    final String SELECT_ALL_SQL = """
                             SELECT [MAHV]
                                   ,[MAKH]
                                   ,[MANH]
                                   ,[DIEM]
                               FROM [dbo].[HocVien]
                             """;
    final String SELECT_BY_ID_SQL = """
                             SELECT [MAHV]
                                   ,[MAKH]
                                   ,[MANH]
                                   ,[DIEM]
                               FROM [dbo].[HocVien]
                                    WHERE MAHV = ?
                             """;
    final String SELECT_BY_KHOA_HOC_SQL = """
                             SELECT [MAHV]
                                   ,[MAKH]
                                   ,[MANH]
                                   ,[DIEM]
                               FROM [dbo].[HocVien]
                                    WHERE MAKH = ?
                             """;

    @Override
    public void insert(HocVien entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaKH(), entity.getMaNH(), entity.getDiem());

    }

    @Override
    public void update(HocVien entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL, entity.getMaKH(), entity.getMaNH(), entity.getDiem(), entity.getMaHV());

    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<HocVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HocVien selectById(Integer id) {
        List<HocVien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HocVien> selectBySql(String sql, Object... args) {
        List<HocVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                HocVien entity = new HocVien();
                entity.setMaHV(rs.getInt(1));
                entity.setMaKH(rs.getInt(2));
                entity.setMaNH(rs.getString(3));
                entity.setDiem(rs.getDouble(4));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HocVien> selectByKhoaHoc(int maKH) {
        return this.selectBySql(SELECT_BY_KHOA_HOC_SQL, maKH);
    }
}
