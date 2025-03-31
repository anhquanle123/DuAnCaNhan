/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edusys.ui;

import edusys.dao.NhanVienDAO;
import edusys.entity.NhanVien;
import java.util.List;


public class TestDemo {
    public static void main(String[] args) {
        NhanVienDAO dao = new NhanVienDAO();
        dao.insert(new NhanVien("NV07", "123456", "Quan Le", true));
//        List<NhanVien> ls = dao.selectAll();
//        for (NhanVien nv : ls) {
//            System.out.println("->"+nv.toString());
//        }
    }
}
