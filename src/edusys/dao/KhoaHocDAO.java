/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edusys.dao;

import edusys.entity.KhoaHoc;
import edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class KhoaHocDAO extends EduSysDAO<KhoaHoc, Integer> {

    final String INSERT_SQL = """
                        INSERT INTO [dbo].[KhoaHoc]
                                ([MaCD]
                                ,[HocPhi]
                                ,[ThoiLuong]
                                ,[NgayKG]
                                ,[GhiChu]
                                ,[MaNV]
                                ,[NgayTao])
                          VALUES
                                (?,?,?,?,?,?,?)
                             """;
    final String UPDATE_SQL = """
        UPDATE [dbo].[KhoaHoc]
                     SET [MaCD] = ?
                        ,[HocPhi] = ?
                        ,[ThoiLuong] = ?
                        ,[NgayKG] = ?
                        ,[GhiChu] = ?
                        ,[MaNV] = ?
                        ,[NgayTao] = ?
                   WHERE MaKH = ?
                             """;
    final String DELETE_SQL = """
                             DELETE FROM [dbo].[KhoaHoc]
                                   WHERE MaKH = ?
                             """;
    final String SELECT_ALL_SQL = """
                             SELECT [MaKH]
                                   ,[MaCD]
                                   ,[HOCPHI]
                                   ,[THOILUONG]
                                   ,[NGAYKG]
                                   ,[GHICHU]
                                   ,[MANV]
                                   ,[NGAYTAO]
                               FROM [dbo].[KhoaHoc]
                             """;
    final String SELECT_BY_ID_SQL = """
                             SELECT [MAKH]
                                   ,[MACD]
                                   ,[HOCPHI]
                                   ,[THOILUONG]
                                   ,[NGAYKG]
                                   ,[GHICHU]
                                   ,[MANV]
                                   ,[NGAYTAO]
                               FROM [dbo].[KhoaHoc]
                                    WHERE MAKH = ?
                             """;
    final String SELECT_BY_MA_CD_SQL = """
                             SELECT [MAKH]
                                   ,[MACD]
                                   ,[HOCPHI]
                                   ,[THOILUONG]
                                   ,[NGAYKG]
                                   ,[GHICHU]
                                   ,[MANV]
                                   ,[NGAYTAO]
                               FROM [dbo].[KhoaHoc]
                                    WHERE MACD = ?
                             """;

    @Override
    public void insert(KhoaHoc entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getNgayKG(), entity.getGhiChu(),entity.getMaNV(), entity.getNgayTao());

    }

    @Override
    public void update(KhoaHoc entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL, entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getNgayKG(), entity.getGhiChu(), entity.getMaNV(), entity.getNgayTao(), entity.getMaKH());

    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);

    }

    @Override
    public List<KhoaHoc> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);

    }

    @Override
    public KhoaHoc selectById(Integer id) {
        List<KhoaHoc> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                KhoaHoc entity = new KhoaHoc();
                entity.setMaKH(rs.getInt(1));
                entity.setMaCD(rs.getString(2));
                entity.setHocPhi(rs.getDouble(3));
                entity.setThoiLuong(rs.getInt(4));
                entity.setNgayKG(rs.getDate(5));
                entity.setGhiChu(rs.getString(6));
                entity.setMaNV(rs.getString(7));
                entity.setNgayTao(rs.getDate(8));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<KhoaHoc> selectByChuyenDe(String maCD) {
        String sql = """
                    SELECT [MAKH]
                            ,[MACD]
                            ,[HOCPHI]
                            ,[THOILUONG]
                            ,[NGAYKG]
                            ,[GHICHU]
                            ,[MANV]
                            ,[NGAYTAO]
                        FROM [dbo].[KhoaHoc]
                             WHERE MACD = ?
                    """;
        return this.selectBySql(sql, maCD);
    }

    public List<KhoaHoc> selectKhoaHocByChuyenDe(String maCD) {
        return this.selectBySql(SELECT_BY_MA_CD_SQL, maCD);
    }
    public List<Integer> selectYears() {
        String sql = "SELECT DISTINCT year(NGAYKG) FROM KhoaHoc ORDER BY year(NGAYKG) DESC";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
