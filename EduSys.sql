CREATE DATABASE EduSys
GO

USE EduSys
GO

CREATE TABLE NhanVien
(
	MaNV nvarchar(20) NOT NULL,
	MatKhau nvarchar(50) NOT NULL,
	HoTen nvarchar(50) NOT NULL,
	VaiTro bit DEFAULT 0,
	CONSTRAINT PK_MaNV PRIMARY KEY (MaNV)
)
GO

CREATE TABLE NguoiHoc
(
	MaNH nchar(7) NOT NULL,
	HoTen nvarchar(50) NOT NULL,
	GioiTinh bit DEFAULT 1,
	NgaySinh date NOT NULL,
	DienThoai nvarchar(24) NOT NULL,
	Email nvarchar(50) NOT NULL,
	GhiChu nvarchar(255) NULL,
	MaNV nvarchar(20) NOT NULL,
	NgayDK date DEFAULT getdate(),
	CONSTRAINT PK_MaNH PRIMARY KEY (MaNH),
	CONSTRAINT FK_MaNV FOREIGN KEY (MaNV) REFERENCES NhanVien
)
GO

CREATE TABLE ChuyenDe
(
	MaCD nchar(5) NOT NULL,
	TenCD nvarchar(50) NOT NULL,
	HocPhi float NOT NULL,
	ThoiLuong int NOT NULL,
	Hinh nvarchar(50) NOT NULL,
	MoTa nvarchar(50) NOT NULL,
	CONSTRAINT PK_MaCD PRIMARY KEY(MaCD)
)
GO

CREATE TABLE KhoaHoc
(
	MaKH int IDENTITY(0, 1),
	MaCD nchar(5) NOT NULL,
	HocPhi float NOT NULL,
	ThoiLuong int NOT NULL,
	NgayKG date NOT NULL,
	GhiChu nvarchar(255) NULL,
	MaNV nvarchar(20) NOT NULL,
	NgayTao date DEFAULT getdate(),
	CONSTRAINT PK_MaKH PRIMARY KEY (MaKH),
	CONSTRAINT FK_MaCD FOREIGN KEY (MaCD) REFERENCES ChuyenDe ON DELETE NO ACTION ON UPDATE CASCADE,
	CONSTRAINT FK_MaNV_KH FOREIGN KEY (MaNV) REFERENCES NhanVien ON DELETE NO ACTION ON UPDATE CASCADE
)
GO

CREATE TABLE HocVien
(
	MaHV int IDENTITY(1, 1),
	MaKH int NOT NULL,
	MaNH nchar(7) NOT NULL,
	Diem float DEFAULT -1,
	CONSTRAINT PK_MaHV PRIMARY KEY (MaHV),
	CONSTRAINT FK_MaKH_HV FOREIGN KEY (MaKH) REFERENCES KhoaHoc ON DELETE NO ACTION ON UPDATE CASCADE,
	CONSTRAINT FK_MaNG_HV FOREIGN KEY (MaNH) REFERENCES NguoiHoc ON DELETE NO ACTION ON UPDATE CASCADE
)
GO


IF OBJECT_ID('spThemNhanVien') IS NOT NULL
	DROP PROCEDURE spThemNhanVien
GO
CREATE PROCEDURE spThemNhanVien
	@MaNV nvarchar(20),
	@MatKhau nvarchar(50),
	@HoTen nvarchar(50),
	@VaiTro bit
AS
BEGIN
	BEGIN TRY
		 IF (@MaNV in (SELECT MaNV FROM NhanVien))
			BEGIN
				PRINT N'Mã nhân viên trùng'
			END
		ELSE
		BEGIN
			INSERT INTO NhanVien(MaNV, MatKhau, HoTen, VaiTro)
			VALUES (@MaNV, @MatKhau, @HoTen, @VaiTro)
			PRINT N'Thêm thành công'
		END
	END TRY
	BEGIN CATCH
		PRINT N'Thêm thất bại'
		PRINT N'Lỗi ' + ERROR_MESSAGE()
	END CATCH
END

EXEC spThemNhanVien 'NV001', 'matkhau1', N'Nguyễn Văn A', 1
EXEC spThemNhanVien 'NV002', 'matkhau2', N'Phạm Thị B', 0
EXEC spThemNhanVien 'NV003', 'matkhau3', N'Trần Văn C', 0
EXEC spThemNhanVien 'NV004', 'matkhau4', N'Lê Thị D', 0
EXEC spThemNhanVien 'NV005', 'matkhau5', N'Hồ Văn E', 0


IF OBJECT_ID('spThemNguoiHoc') IS NOT NULL
	DROP PROCEDURE spThemNguoiHoc
GO
CREATE PROCEDURE spThemNguoiHoc
	@MaNH nchar(7),
	@HoTen nvarchar(50),
	@GioiTinh bit,
	@NgaySinh date,
	@DienThoai nvarchar(24),
	@Email nvarchar(50),
	@GhiChu nvarchar(255),
	@MaNV nvarchar(20)
AS
BEGIN
	BEGIN TRY
		IF (@MaNH in (SELECT MaNH FROM NguoiHoc))
			BEGIN
				PRINT N'Mã trùng không thêm được!'
			END
		ELSE
			BEGIN
				INSERT INTO NguoiHoc(MaNH, HoTen, GioiTinh, NgaySinh, DienThoai, Email, GhiChu, MaNV)
				VALUES (@MaNH, @HoTen, @GioiTinh, @NgaySinh, @DienThoai, @Email, @GhiChu, @MaNV)
				PRINT N'Thêm thành công'
			END
	END TRY
	BEGIN CATCH
		PRINT N'THêm thất bại'
		PRINT N'Lỗi ' + ERROR_MESSAGE()
	END CATCH
END

EXEC spThemNguoiHoc 'NH001', N'Trần Văn An', 0, '1990-01-01', '0987654321', 'tranvanan@example.com', NULL, 'NV001'
EXEC spThemNguoiHoc 'NH002', N'Nguyễn Thị Bình', 1, '1995-02-02', '0976543210', 'nguyenthibinh@example.com', NULL, 'NV002'
EXEC spThemNguoiHoc 'NH003', N'Lê Văn Cường', 0, '1992-03-03', '0965432109', 'levancuong@example.com', NULL, 'NV003'
EXEC spThemNguoiHoc 'NH004', N'Phạm Thị Dung', 1, '1998-04-04', '0954321098', 'phamthidung@example.com', NULL, 'NV004'
EXEC spThemNguoiHoc 'NH005', N'Hồ Văn Eo', 0, '1988-05-05', '0943210987', 'hovaneo@example.com', NULL, 'NV005'


IF OBJECT_ID('spThemChuyenDe') IS NOT NULL
	DROP PROCEDURE spThemChuyenDe
GO
CREATE PROCEDURE spThemChuyenDe
	@MaCD nchar(5),
	@TenCD nvarchar(50),
	@HocPhi float,
	@ThoiLuong int,
	@Hinh nvarchar(50),
	@MoTa nvarchar(50)
AS 
BEGIN
	BEGIN TRY
		IF(@MaCD in(SELECT MaCD FROM ChuyenDe))
			BEGIN
				PRINT N'Mã trùng không thêm được!'
			END
		ELSE
			BEGIN
				INSERT INTO ChuyenDe(MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa)
				VALUES (@MaCD, @TenCD, @HocPhi, @ThoiLuong, @Hinh, @MoTa)
				PRINT N'Thêm thành công'
			END
	END TRY
	BEGIN CATCH
		PRINT N'Thêm thất bại'
		PRINT N'Lỗi ' + ERROR_MESSAGE() 
	END CATCH
END

EXEC spThemChuyenDe 'CD001', N'Lập trình C# căn bản', 1500000, 24, N'C#.png', N'Khóa học dành cho người mới bắt đầu với lập trình C#'
EXEC spThemChuyenDe 'CD002', N'Lập trình Java nâng cao', 1800000, 30, N'java.png', N'Khóa học dành cho người đã có kiến thức căn bản về Java'
EXEC spThemChuyenDe 'CD003', N'Thiết kế website với HTML/CSS', 1200000, 20, N'WebDesign.jpg', N'Học cách thiết kế website đẹp và linh hoạt với HTML và CSS'
EXEC spThemChuyenDe 'CD004', N'Học Excel cơ bản', 800000, 16, N'Excel.jpg', N'Nắm vững các kỹ năng cơ bản và nâng cao trong việc sử dụng Excel'
EXEC spThemChuyenDe 'CD005', N'Lập trình Python căn bản', 1600000, 24, N'Python.jpg', N'Học cách lập trình bằng ngôn ngữ Python từ căn bản đến nâng cao'
EXEC spThemChuyenDe 'CD006', N'Lập trình JavaScipt căn bản', 1650000, 24, N'javascipt.png', N'Học cách lập trình bằng ngôn ngữ JavaScipt từ căn bản đến nâng cao'


IF OBJECT_ID('spThemKhoaHoc') IS NOT NULL
	DROP PROCEDURE spThemKhoaHoc
GO
CREATE PROCEDURE spThemKhoaHoc
	@MaCD nchar(5),
	@HocPhi float,
	@ThoiLuong int,
	@NgayKG date,
	@GhiChu nvarchar(255),
	@MaNV nvarchar(20)
AS 
BEGIN
	BEGIN TRY
		INSERT INTO KhoaHoc(MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV)
		VALUES (@MaCD, @HocPhi, @ThoiLuong, @NgayKG, @GhiChu, @MaNV)
		PRINT N'Thêm thành công!'
	END TRY
	BEGIN CATCH
		PRINT N'Thêm thất bại'
		PRINT N'Lỗi ' + ERROR_MESSAGE()
	END CATCH
END

EXEC spThemKhoaHoc 'CD001', 1500000, 24, '2024-05-13', NULL, 'NV001'
EXEC spThemKhoaHoc 'CD002', 1800000, 30, '2024-05-14', NULL, 'NV002'
EXEC spThemKhoaHoc 'CD003', 1200000, 20, '2024-05-15', NULL, 'NV003'
EXEC spThemKhoaHoc 'CD004', 800000, 16, '2024-05-16', NULL, 'NV004'
EXEC spThemKhoaHoc 'CD005', 1600000, 24, '2024-05-17', NULL, 'NV005'

IF OBJECT_ID('spThemHocVien') IS NOT NULL
	DROP PROCEDURE spThemHocVien
GO
CREATE PROCEDURE spThemHocVien
	@MaKH int,
	@MaNH nchar(7),
	@Diem float
AS
BEGIN
	BEGIN TRY
		INSERT INTO HocVien(MaKH, MaNH, Diem)
		VALUES (@MaKH, @MaNH, @Diem)
		PRINT N'Thêm thành công!'
	END TRY
	BEGIN CATCH
		PRINT N'Thêm thất bại!'
		PRINT N'Lỗi ' + ERROR_MESSAGE()
	END CATCH
END

EXEC spThemHocVien 0, 'NH001', 8.5
EXEC spThemHocVien 1, 'NH002', 7.8
EXEC spThemHocVien 2, 'NH003', 9.0
EXEC spThemHocVien 3, 'NH004', 6.5
EXEC spThemHocVien 4, 'NH005', 8.0


IF OBJECT_ID('sp_BangDiem') IS NOT NULL
	DROP PROCEDURE sp_BangDiem;
GO
CREATE PROCEDURE sp_BangDiem (@MaKH INT)
AS
BEGIN
	SELECT 
		nh.MaNH,
		nh.HoTen,
		hv.Diem
	FROM HocVien hv
		 JOIN NguoiHoc nh ON nh.MaNH=hv.MaNH
	WHERE hv.MaKH = @MaKH
	ORDER BY hv.Diem DESC
END
GO

EXEC sp_BangDiem 1

IF OBJECT_ID('sp_LuongNguoiHoc') IS NOT NULL
	DROP PROCEDURE sp_LuongNguoiHoc;
GO
CREATE PROCEDURE sp_LuongNguoiHoc
AS
BEGIN
	SELECT 
		YEAR(NgayDK) Nam,
		COUNT(*) SoLuong,
		MIN(NgayDK) DauTien,
		MAX(NgayDK) CuoiCung
	FROM NguoiHoc
	GROUP BY YEAR(NgayDK)
END
GO

IF OBJECT_ID('sp_DiemChuyenDe') IS NOT NULL
	DROP PROCEDURE sp_DiemChuyenDe;
GO
CREATE PROCEDURE sp_DiemChuyenDe
AS
BEGIN
	SELECT 
		TenCD ChuyenDe,
		COUNT(MaHV) SoHV,
		MIN(Diem) ThapNhat,
		MAX(Diem) CaoNhat,
		AVG(Diem) TrungBinh
	FROM KhoaHoc kh
		JOIN HocVien hv ON kh.MaKH=hv.MaKH
		JOIN ChuyenDe cd ON cd.MaCD=kh.MaCD
	GROUP BY TenCD
END
GO

IF OBJECT_ID('sp_DoanhThu') IS NOT NULL
	DROP PROCEDURE sp_DoanhThu;
GO
CREATE PROCEDURE sp_DoanhThu (@Year INT)
AS
BEGIN
	SELECT 
		TenCD ChuyenDe,
		COUNT(DISTINCT kh.MaKH) SoKH,
		COUNT(hv.MaHV) SoHV,
		SUM(kh.HocPhi) DoanhThu,
		MIN(kh.HocPhi) ThapNhat,
		MAX(kh.HocPhi) CaoNhat,
		AVG(kh.HocPhi) TrungBinh
	FROM KhoaHoc kh
		JOIN HocVien hv ON kh.MaKH=hv.MaKH
		JOIN ChuyenDe cd ON cd.MaCD=kh.MaCD
	WHERE YEAR(NgayKG) = @Year
	GROUP BY TenCD
END
GO

--INSERT INTO NhanVien (MaNV, MatKhau, HoTen, VaiTro) VALUES (?, ?, ?, ?)
--UPDATE NhanVien SET MatKhau = ?, HoTen = ?, VaiTro = ? WHERE MaNV = ?

--SELECT * FROM ChuyenDe
--SELECT MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa FROM ChuyenDe
--INSERT INTO ChuyenDe(MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa)
--VALUES(?, ?, ?, ?, ?, ?)

