/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edusys.dao;

import edusys.entity.NguoiHoc;
import edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class NguoiHocDAO extends EduSysDAO<NguoiHoc, String> {

    final String INSERT_SQL = """
                        INSERT INTO [dbo].[NguoiHoc]
                                    ([MANH]
                                    ,[HOTEN]
                                    ,[GIOITINH]
                                    ,[NGAYSINH]
                                    ,[DIENTHOAI]
                                    ,[EMAIL]
                                    ,[GHICHU]
                                    ,[MANV]
                                    ,[NGAYDK])
                              VALUES
                                    (?,?,?,?,?,?,?,?,?)
                             """;
    final String UPDATE_SQL = """
        UPDATE [dbo].[NguoiHoc]
                    SET [HOTEN] = ?
                       ,[GIOITINH] = ?
                       ,[NGAYSINH] = ?
                       ,[DIENTHOAI] = ?
                       ,[EMAIL] = ?
                       ,[GHICHU] = ?
                       ,[MANV] = ?
                       ,[NGAYDK] = ?
                  WHERE MANH = ?
                             """;
    final String DELETE_SQL = """
                             DELETE FROM [dbo].[NguoiHoc]
                                      WHERE MANH = ?
                             """;
    final String SELECT_ALL_SQL = """
                             SELECT [MANH]
                                      ,[HOTEN]
                                      ,[GIOITINH]
                                      ,[NGAYSINH]
                                      ,[DIENTHOAI]
                                      ,[EMAIL]
                                      ,[GHICHU]
                                      ,[MANV]
                                      ,[NGAYDK]
                                  FROM [dbo].[NguoiHoc]
                             """;
    final String SELECT_BY_ID_SQL = """
                             SELECT [MANH]
                                    ,[HOTEN]
                                    ,[GIOITINH]
                                    ,[NGAYSINH]
                                    ,[DIENTHOAI]
                                    ,[EMAIL]
                                    ,[GHICHU]
                                    ,[MANV]
                                    ,[NGAYDK]
                                FROM [dbo].[NguoiHoc]
                                    WHERE MANH = ?
                             """;
    final String SELECT_BY_KEYWORD_SQL = """
                             SELECT [MANH]
                                    ,[HOTEN]
                                    ,[GIOITINH]
                                    ,[NGAYSINH]
                                    ,[DIENTHOAI]
                                    ,[EMAIL]
                                    ,[GHICHU]
                                    ,[MANV]
                                    ,[NGAYDK]
                                FROM [dbo].[NguoiHoc]
                                    WHERE HOTEN LIKE ?
                             """;
    final String SELECT_BY_COURSE_SQL = """
                                       SELECT [MANH]
                                            ,[HOTEN]
                                            ,[GIOITINH]
                                            ,[NGAYSINH]
                                            ,[DIENTHOAI]
                                            ,[EMAIL]
                                            ,[GHICHU]
                                            ,[MANV]
                                            ,[NGAYDK]
                                        FROM [dbo].[NguoiHoc]
                                            WHERE HOTEN LIKE ? AND MANH NOT IN (SELECT MANH FROM HocVien WHERE MAKH = ?)
                                       """;

    @Override
    public void insert(NguoiHoc entity) {
        JdbcHelper.executeUpdate(INSERT_SQL, entity.getMaNH(), entity.getHoTen(), entity.isGioiTinh(), entity.getNgaySinh(), entity.getDienThoai(), entity.getEmail(), entity.getGhiChu(), entity.getMaNV(), entity.getNgayDK());
    }

    @Override
    public void update(NguoiHoc entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL, entity.getHoTen(), entity.isGioiTinh(), entity.getNgaySinh(), entity.getDienThoai(), entity.getEmail(), entity.getGhiChu(), entity.getMaNV(), entity.getNgayDK(), entity.getMaNH());

    }

    @Override
    public void delete(String id) {
        JdbcHelper.executeUpdate(DELETE_SQL, id);

    }

    @Override
    public List<NguoiHoc> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NguoiHoc selectById(String id) {
        List<NguoiHoc> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NguoiHoc> selectBySql(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                NguoiHoc entity = new NguoiHoc();
                entity.setMaNH(rs.getString(1));
                entity.setHoTen(rs.getString(2));
                entity.setNgaySinh(rs.getDate(4));
                entity.setGioiTinh(rs.getBoolean(3));
                entity.setDienThoai(rs.getString(5));
                entity.setEmail(rs.getString(6));
                entity.setGhiChu(rs.getString(7));
                entity.setMaNV(rs.getString(8));
                entity.setNgayDK(rs.getDate(9));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<NguoiHoc> selectByKeyword(String keyword) {
        return selectBySql(SELECT_BY_KEYWORD_SQL, "%" + keyword + "%");
    }

    public List<NguoiHoc> selectByCourse(Integer maKH, String keyword) {
        return selectBySql(SELECT_BY_COURSE_SQL, "%" + keyword + "%", maKH);
    }
}
