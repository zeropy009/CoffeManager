﻿USE MASTER
GO

IF DB_ID('COFFEE_MANAGER') IS NOT NULL
BEGIN
    ALTER DATABASE COFFEE_MANAGER SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
END
GO

DROP DATABASE IF EXISTS COFFEE_MANAGER
GO

CREATE DATABASE COFFEE_MANAGER
GO

USE COFFEE_MANAGER
GO

CREATE TABLE [TABLE]
(
	[ID] INT PRIMARY KEY IDENTITY,
	[TABLE_NAME] NVARCHAR(30) NOT NULL,
	[STATUS] BIT DEFAULT 0
)
------------------------------------------
CREATE TABLE [ROLE]
(
	[ID] INT NOT NULL PRIMARY KEY IDENTITY,
	[ROLE_NAME] VARCHAR(20) not null,
);
GO

CREATE TABLE [USER]
(
	[USER_NAME] VARCHAR(30) NOT NULL PRIMARY KEY,
	[PASSWORD] NVARCHAR(50) NOT NULL,
	[ROLE_ID] INT DEFAULT 2 NOT NULL,
	[FULL_NAME] NVARCHAR(50),
	[SEX] BIT,
	[ADDRESS] NVARCHAR(100),
	[YEAR_OF_BIRTH] INT,
	[PHONE] VARCHAR(15),
	[EMAIL] VARCHAR(50),
	[SALARY] INT,
	[CREATED_BY] VARCHAR(30) DEFAULT 0,
	[CREATED_AT] DATE DEFAULT GETDATE(),
	[LAST_UPDATE_BY] VARCHAR(30) DEFAULT 0,
	[LAST_UPDATE_AT] DATETIME DEFAULT GETDATE(),
	[DELETED] BIT DEFAULT 0,
	FOREIGN KEY([ROLE_ID]) REFERENCES ROLE (ID)
);
GO

CREATE TRIGGER USER_UPDATED
ON [USER]
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE [USER]
    SET LAST_UPDATE_AT = GETDATE()
    FROM [USER] u
    INNER JOIN inserted i ON u.[USER_NAME] = i.[USER_NAME];
END;
GO

CREATE TABLE CUSTOMER_TIER (
    [ID] INT PRIMARY KEY IDENTITY,
    [NAME] NVARCHAR(50) NOT NULL, 
    [MIN_PURCHASE] DECIMAL(10,2) NOT NULL,
    [DISCOUNT_PERCENTAGE] DECIMAL(5,2) NOT NULL 
);
GO

CREATE TABLE CUSTOMER (
    [ID] INT PRIMARY KEY IDENTITY,
    [NAME] NVARCHAR(50) NOT NULL,
    [PHONE] VARCHAR(15) UNIQUE,
    [EMAIL] VARCHAR(100),
    [TIER_ID] INT DEFAULT 1 NOT NULL,
	[CREATED_BY] VARCHAR(30) DEFAULT 0,
	[CREATED_AT] DATETIME DEFAULT GETDATE(),
	[LAST_UPDATE_BY] VARCHAR(30) DEFAULT 0,
	[LAST_UPDATE_AT] DATETIME DEFAULT GETDATE(),
	[DELETED] BIT DEFAULT 0,
    FOREIGN KEY (TIER_ID) REFERENCES CUSTOMER_TIER(ID)
);
GO

CREATE TRIGGER CUSTOMER_LAST_UPDATED
ON CUSTOMER
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE CUSTOMER
    SET LAST_UPDATE_AT = GETDATE()
    FROM CUSTOMER u
    INNER JOIN inserted i ON u.ID = i.ID;
END;
GO

CREATE TABLE BEVERAGES_CATEGORY (
	[ID] INT PRIMARY KEY IDENTITY,
	[NAME] NVARCHAR(100) NOT NULL,
	[CREATED_BY] VARCHAR(30) DEFAULT 0,
	[CREATED_AT] DATETIME DEFAULT GETDATE(),
	[LAST_UPDATE_BY] VARCHAR(30) DEFAULT 0,
	[LAST_UPDATE_AT] DATETIME DEFAULT GETDATE(),
	[DELETED] BIT DEFAULT 0,
);
GO

CREATE TABLE BEVERAGES (
	[ID] INT PRIMARY KEY IDENTITY,
	[NAME] NVARCHAR(100) NOT NULL,
	[PRICE] INT,
	[BEVERAGES_CATEGORY_ID] INT,
	[CREATED_BY] VARCHAR(30) DEFAULT 0,
	[CREATED_AT] DATETIME DEFAULT GETDATE(),
	[LAST_UPDATE_BY] VARCHAR(30) DEFAULT 0,
	[LAST_UPDATE_AT] DATETIME DEFAULT GETDATE(),
	[DELETED] BIT DEFAULT 0,
	FOREIGN KEY(BEVERAGES_CATEGORY_ID) REFERENCES BEVERAGES_CATEGORY(ID)
);
GO


CREATE TRIGGER BEVERAGES_UPDATED
ON BEVERAGES
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE BEVERAGES
    SET LAST_UPDATE_AT = GETDATE()
    FROM BEVERAGES u
    INNER JOIN inserted i ON u.ID = i.ID;
END;
GO

CREATE TABLE WAREHOUSE(
	[ID] INT PRIMARY KEY IDENTITY,
	[INPUT_DATE] DATETIME,
	[USER_NAME] VARCHAR(30) NOT NULL,
	[TOTAL_AMOUNT] INT DEFAULT 0,
	[CREATED_BY] VARCHAR(30) DEFAULT 0,
	[CREATED_AT] DATETIME DEFAULT GETDATE(),
	[LAST_UPDATE_BY] VARCHAR(30) DEFAULT 0,
	[LAST_UPDATE_AT] DATETIME DEFAULT GETDATE(),
	[DELETED] BIT DEFAULT 0,
	FOREIGN KEY([USER_NAME]) REFERENCES [USER]([USER_NAME])
);
GO

CREATE TRIGGER WAREHOUSE_UPDATED
ON WAREHOUSE
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE WAREHOUSE
    SET LAST_UPDATE_AT = GETDATE()
    FROM WAREHOUSE u
    INNER JOIN inserted i ON u.ID = i.ID;
END;
GO

CREATE TABLE WAREHOUSE_DETAIL(
	[ID] INT PRIMARY KEY IDENTITY,
	[WAREHOUSE_ID] INT,
	[PRODUCT_NAME] NVARCHAR(200),
	[QUANTITY] INT DEFAULT 0,
	[PRICE] INT DEFAULT 0,
	[AMOUNT] INT DEFAULT 0,
	[CREATED_BY] VARCHAR(30) DEFAULT '0',
	[CREATED_AT] DATETIME DEFAULT GETDATE(),
	[LAST_UPDATE_BY] VARCHAR(30) DEFAULT '0',
	[LAST_UPDATE_AT] DATETIME DEFAULT GETDATE(),
	[DELETED] BIT DEFAULT 0,
	FOREIGN KEY(WAREHOUSE_ID) REFERENCES WAREHOUSE(ID)
);
GO

CREATE TRIGGER WAREHOUSE_DETAILL_UPDATED
ON WAREHOUSE_DETAIL
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE WAREHOUSE_DETAIL
    SET LAST_UPDATE_AT = GETDATE()
    FROM WAREHOUSE_DETAIL u
    INNER JOIN inserted i ON u.ID = i.ID;

	UPDATE WAREHOUSE
    SET TOTAL_AMOUNT = CEILING(
        (SELECT COALESCE(SUM(d.PRICE * d.QUANTITY), 0) 
         FROM WAREHOUSE_DETAIL d
         WHERE d.WAREHOUSE_ID = i.ID AND d.DELETED = 0)
    )
    FROM  WAREHOUSE i
    WHERE EXISTS (SELECT 1 FROM inserted WHERE inserted.WAREHOUSE_ID = i.ID)
       OR EXISTS (SELECT 1 FROM deleted WHERE deleted.WAREHOUSE_ID = i.ID);
END;
GO

CREATE TABLE INVOICE (
    [ID] INT PRIMARY KEY IDENTITY,
    [DATE] DATETIME,
    [TOTAL_AMOUNT] INT DEFAULT 0,
    [USER_NAME] VARCHAR(30) NOT NULL,
    [CUSTOMER_ID] INT,
    [DISCOUNT_PERCENTAGE] DECIMAL(5,2) DEFAULT 0,
    [TABLE_ID] INT NOT NULL,
	[PAYMENT_STATUS] BIT NOT NULL DEFAULT 0,
    [CREATED_BY] VARCHAR(30) DEFAULT '0',
    [CREATED_AT] DATETIME DEFAULT GETDATE(),
    [LAST_UPDATE_BY] VARCHAR(30) DEFAULT '0',
    [LAST_UPDATE_AT] DATETIME DEFAULT GETDATE(),
    [DELETED] BIT DEFAULT 0,
    FOREIGN KEY ([USER_NAME]) REFERENCES [USER]([USER_NAME]),
	FOREIGN KEY (TABLE_ID) REFERENCES [TABLE] (ID),
	FOREIGN KEY (CUSTOMER_ID) REFERENCES [CUSTOMER](ID)
);
GO

CREATE TRIGGER INVOICE_UPDATED
ON INVOICE
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE INVOICE
    SET LAST_UPDATE_AT = GETDATE()
    FROM INVOICE u
    INNER JOIN inserted i ON u.ID = i.ID;
END;
GO

CREATE TABLE INVOICE_DETAIL(
	[ID] INT PRIMARY KEY IDENTITY,
	[INVOICE_ID] INT NOT NULL,
	[BEVERAGES_ID] INT NOT NULL,
	[QUANTITY] INT DEFAULT 0,
	[PRICE] INT DEFAULT 0,
	[AMOUNT] INT DEFAULT 0,
	[CREATED_BY] VARCHAR(30) DEFAULT 0,
	[CREATED_AT] DATETIME DEFAULT GETDATE(),
	[LAST_UPDATE_BY] VARCHAR(30) DEFAULT 0,
	[LAST_UPDATE_AT] DATETIME DEFAULT GETDATE(),
	[DELETED] BIT DEFAULT 0,
	FOREIGN KEY(INVOICE_ID) REFERENCES INVOICE(ID),
	FOREIGN KEY(BEVERAGES_ID) REFERENCES BEVERAGES(ID)
);
GO

CREATE TRIGGER INVOICE_DETAIL_UPDATED
ON INVOICE_DETAIL
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;

    -- Cập nhật LAST_UPDATE_AT của INVOICE_DETAIL khi có thay đổi
    UPDATE INVOICE_DETAIL
    SET LAST_UPDATE_AT = GETDATE()
    FROM INVOICE_DETAIL d
    INNER JOIN inserted i ON d.ID = i.ID;

    -- Cập nhật TOTAL_AMOUNT của INVOICE (chỉ tính các INVOICE_DETAIL có DELETED = 0)
    UPDATE INVOICE
    SET TOTAL_AMOUNT = CEILING(
        (SELECT COALESCE(SUM(d.PRICE * d.QUANTITY), 0) 
         FROM INVOICE_DETAIL d
         WHERE d.INVOICE_ID = i.ID AND d.DELETED = 0) 
         * (100 - i.DISCOUNT_PERCENTAGE) / 100.0
    )
    FROM INVOICE i
    WHERE EXISTS (SELECT 1 FROM inserted WHERE inserted.INVOICE_ID = i.ID)
       OR EXISTS (SELECT 1 FROM deleted WHERE deleted.INVOICE_ID = i.ID);
END;
GO

INSERT INTO [TABLE] ([TABLE_NAME]) VALUES (N'Bàn 1'), (N'Bàn 2'), (N'Bàn 3'), (N'Bàn 4'), (N'Bàn 5'), (N'Bàn 6'), (N'Bàn 7'), (N'Bàn 8'), (N'Bàn 9'), (N'Bàn 10')

INSERT INTO [ROLE] VALUES ('MANAGER'), ('STAFF') 


INSERT INTO [USER] 
([USER_NAME], [PASSWORD], [ROLE_ID], [FULL_NAME], [SEX], [ADDRESS], [YEAR_OF_BIRTH], [PHONE], [EMAIL], [SALARY]) 
VALUES 
('TEST', N'192023a7bbd73250516f069df18b500', 2, N'TK test', 0, N'P.Tân Quy, Quận 7, Tp.HCM', 2000, '01234567897', '123456789@gmail.com', 999999999),
(N'PTHC', N'192023a7bbd73250516f069df18b500', 1, N'PHẠM TRƯƠNG HỮU CƯỜNG', 1, N'QUẬN THỦ ĐỨC, HCM', 1985, '0986853817', '24410010@ms.uit.edu.vn', 20000000),
(N'YNM', N'192023a7bbd73250516f069df18b500', 1, N'NGUYỄN MINH Ý', 1, N'QUẬN 8, HCM', 1996, '0987208677', '24410127@ms.uit.edu.vn', 50000000),
(N'DMP', N'192023a7bbd73250516f069df18b500', 1, N'ĐÀO MINH PHONG', 1, N'QUẬN 12, HCM', 1987, '0981233818', '24410081@ms.uit.edu.vn', 20000000),
(N'DVD', N'192023a7bbd73250516f069df18b500', 1, N'ĐÀO VĂN DŨNG', 1, N'QUẬN 3, HCM', 1988, '0986853888', '24410017@ms.uit.edu.vn', 20000000);


INSERT INTO [CUSTOMER_TIER] ([NAME], [MIN_PURCHASE], [DISCOUNT_PERCENTAGE]) VALUES
(N'Đồng', 0, 0), (N'Bạc', 2000000, 5), (N'Vàng', 10000000, 10), (N'Kim cương', 50000000, 20)

INSERT INTO CUSTOMER ([NAME], [PHONE], [EMAIL], [TIER_ID])
VALUES
(N'KHÁCH VÃN LAI', null, null, 1),
(N'PHẠM VĂN AN', '0364123456', 'an.pham@gmail.com', 1),
(N'LÊ MINH BẢO', '0463987654', 'bao.le@gmail.com', 2),
(N'ĐẶNG HOÀNG NAM', '0975123456', 'nam.dang@gmail.com', 3),
(N'VŨ THỊ THU', '0976987654', 'thu.vu@gmail.com', 2),
(N'TRẦN THANH HƯƠNG', '0945678912', 'huong.tran@gmail.com', 2),
(N'BÙI QUANG HUY', '0463123456', 'huy.bui@gmail.com', 3),
(N'NGUYỄN THỊ LAN', '0403654789', 'lan.nguyen@gmail.com', 2),
(N'LÝ THỊ NGỌC', '0402789654', 'ngoc.ly@gmail.com', 1),
(N'HOÀNG THỊ MỸ DUYÊN', '0286547893', 'duyen.hoang@gmail.com', 3),
(N'PHAN THỊ KIM ANH', '0483789654', 'kimanh.phan@gmail.com', 2),
(N'TỐNG THỊ HỒNG', '0354123789', 'hong.tong@gmail.com', 2),
(N'NGUYỄN THỊ NGỌC HÀ', '0835789654', 'hanguyen@gmail.com', 3),
(N'CHÂU MINH KHANG', '0987654789', 'khang.chau@gmail.com', 1),
(N'VÕ ĐỨC TRUNG', '0925987654', 'trung.vo@gmail.com', 2),
(N'HÀ TRỌNG TÍN', '0987678456', 'tin.ha@gmail.com', 2),
(N'ĐỖ HOÀNG PHÚC', '0876543212', 'phuc.do@gmail.com', 1),
(N'TRỊNH THANH TRÚC', '0376889123', 'truc.trinh@gmail.com', 3),
(N'NGUYỄN TRẦN MINH CHÂU', '0988325678', 'chau.nguyen@gmail.com', 2),
(N'PHẠM ANH QUÂN', '0878496543', 'quan.pham@gmail.com', 2),
(N'LÊ HOÀNG SƠN', '0987775432', 'son.le@gmail.com', 3);

INSERT INTO BEVERAGES_CATEGORY ([NAME])
VALUES
(N'CÀ PHÊ'),
(N'TRÀ SỮA'),
(N'SỮA CHUA'),
(N'SINH TỐ'),
(N'MATCHA'),
(N'THỨC UỐNG ĐÁ XAY'),
(N'SODA'),
(N'TRÀ ĐẶC BIỆT'),
(N'NƯỚC ÉP'),
(N'TRÀ TRÁI CÂY TƯƠI');

INSERT INTO BEVERAGES ([NAME], [PRICE], [BEVERAGES_CATEGORY_ID])
VALUES
(N'CÀ PHÊ ĐEN', 14000, 1),
(N'CÀ PHÊ SỮA TƯƠI', 18000, 1),
(N'TRÀ SỮA EARL GREY', 22000, 2),
(N'TRÀ SỮA SOCOLA', 20000, 2),
(N'SỮA CHUA ĐÁ', 25000, 3),
(N'SỮA CHUA DÂU', 25000, 3),
(N'SỮA CHUA TRÁI CÂY', 25000, 3),
(N'SINH TỐ DƯA HẤU', 28000, 4),
(N'SINH TỐ DỪA', 28000, 4),
(N'SINH TỐ CAM', 28000, 4),
(N'MATCHA MẬT ONG', 30000, 5),
(N'MATCHA XOÀI', 30000, 5),
(N'MATCHA OREO', 30000, 5),
(N'MATCHA ĐẬU ĐỎ', 30000, 5),
(N'ĐÁ XAY DÂU', 30000, 6),
(N'ĐÁ XAY MÂM XÔI', 30000, 6),
(N'SODA BẠC HÀ', 20000, 7),
(N'SODA CHANH', 20000, 7),
(N'TRÀ ĐEN', 15000, 8),
(N'TRÀ ĐẬU BIẾC', 22000, 8),
(N'TRÀ EARL GREY', 25000, 8),
(N'TRÀ Ô LONG', 25000, 8),
(N'NƯỚC CAM', 26000, 9),
(N'NƯỚC TÁO', 23000, 9),
(N'NƯỚC ÉP DƯA HẤU', 19000, 9),
(N'NƯỚC ÉP DÂU', 20000, 9),
(N'NƯỚC ÉP DỨA', 20000, 9),
(N'TRÀ HOA HỒNG DÂU', 30000, 10),
(N'TRÀ ĐÀO CAM SẢ', 30000, 10),
(N'TRÀ DÂU CAM', 30000, 10);

-- Insert dữ liệu bảng WAREHOUSE
INSERT INTO WAREHOUSE ([INPUT_DATE], [USER_NAME], [TOTAL_AMOUNT])
VALUES 
('2024-11-10', 'YNM', 295000),
('2024-12-03', 'YNM', 435000),
('2025-01-15', 'YNM', 500000);

-- Insert dữ liệu bảng WAREHOUSE_DETAIL (nguyên liệu pha chế + đơn vị)
INSERT INTO WAREHOUSE_DETAIL ([WAREHOUSE_ID], [PRODUCT_NAME], [QUANTITY], [PRICE], [AMOUNT])
VALUES 
-- Phiếu 1
(1, N'Bột cà phê (gói)', 2, 75000, 150000),
(1, N'Sữa đặc (hộp)', 3, 35000, 105000),
(1, N'Đường trắng (kg)', 2, 20000, 40000),

-- Phiếu 2
(2, N'Trà olong (gói)', 2, 50000, 100000),
(2, N'Siro dâu (chai)', 3, 65000, 195000),
(2, N'Sữa tươi không đường (hộp)', 4, 35000, 140000),

-- Phiếu 3
(3, N'Bột matcha (gói)', 2, 95000, 190000),
(3, N'Bột cacao (gói)', 2, 90000, 180000),
(3, N'Trân châu đen (kg)', 2, 65000, 130000);

INSERT INTO INVOICE ([DATE], USER_NAME, CUSTOMER_ID, DISCOUNT_PERCENTAGE, TABLE_ID, PAYMENT_STATUS)
VALUES 
    ('2024-03-01 10:00:00', 'YNM', 1, 10.00, 1, 1),
    ('2024-03-05 14:30:00', 'YNM', 2, 0.00, 2, 1),
    ('2024-03-10 18:45:00', 'YNM', NULL, 5.00, 3, 1);

INSERT INTO INVOICE_DETAIL (INVOICE_ID, BEVERAGES_ID, QUANTITY, PRICE, AMOUNT)
VALUES 
    (1, 1, 2, 20000, 40000),
    (1, 2, 1, 30000, 30000),
    (2, 1, 1, 20000, 20000), 
    (2, 2, 2, 30000, 60000), 
    (3, 1, 3, 15000, 45000);
GO

-- STORED PROCEDURE
-- Nhân viên bán được nhiều doanh thu nhất theo tháng và năm
CREATE PROCEDURE SP_BEST_EMPLOYEE_BY_REVENUE
    @Month INT,
    @Year INT
AS
BEGIN
    SELECT TOP 1
        u.FULL_NAME,
        i.USER_NAME,
        SUM(i.TOTAL_AMOUNT) AS TOTAL_REVENUE
    FROM INVOICE i
    JOIN [USER] u ON i.USER_NAME = u.USER_NAME
    WHERE i.DELETED = 0 
      AND i.PAYMENT_STATUS = 1
      AND MONTH(i.DATE) = @Month
      AND YEAR(i.DATE) = @Year
    GROUP BY i.USER_NAME, u.FULL_NAME
    ORDER BY TOTAL_REVENUE DESC;
END;
GO
-- Loại nước được mua nhiều nhất theo tháng và năm
CREATE PROCEDURE SP_TOP_SELLING_BEVERAGE
    @Month INT,
    @Year INT
AS
BEGIN
    SELECT TOP 1 
        b.NAME AS BEVERAGE_NAME,
        SUM(id.QUANTITY) AS TOTAL_QUANTITY
    FROM INVOICE_DETAIL id
    JOIN BEVERAGES b ON id.BEVERAGES_ID = b.ID
    JOIN INVOICE i ON id.INVOICE_ID = i.ID
    WHERE id.DELETED = 0 
      AND i.DELETED = 0 
      AND i.PAYMENT_STATUS = 1
      AND MONTH(i.DATE) = @Month
      AND YEAR(i.DATE) = @Year
    GROUP BY b.NAME
    ORDER BY TOTAL_QUANTITY DESC;
END;
GO
-- Khách hàng chi nhiều tiền nhất theo tháng và năm
CREATE PROCEDURE SP_TOP_SPENDING_CUSTOMER
    @Month INT,
    @Year INT
AS
BEGIN
    SELECT TOP 1 
        c.NAME AS CUSTOMER_NAME,
        SUM(i.TOTAL_AMOUNT) AS TOTAL_SPENT
    FROM INVOICE i
    JOIN CUSTOMER c ON i.CUSTOMER_ID = c.ID
    WHERE i.DELETED = 0 
      AND i.PAYMENT_STATUS = 1
      AND MONTH(i.DATE) = @Month
      AND YEAR(i.DATE) = @Year
    GROUP BY c.NAME
    ORDER BY TOTAL_SPENT DESC;
END;
GO
-- Tính lợi nhuận theo tháng và năm
CREATE PROCEDURE SP_PROFIT_BY_MONTH
    @Month INT,
    @Year INT
AS
BEGIN
    DECLARE @Revenue INT = (
        SELECT COALESCE(SUM(TOTAL_AMOUNT), 0)
        FROM INVOICE
        WHERE DELETED = 0 
          AND PAYMENT_STATUS = 1 
          AND MONTH(DATE) = @Month
          AND YEAR(DATE) = @Year
    );

    DECLARE @WarehouseCost INT = (
        SELECT COALESCE(SUM(TOTAL_AMOUNT), 0)
        FROM WAREHOUSE
        WHERE DELETED = 0 
          AND MONTH(INPUT_DATE) = @Month
          AND YEAR(INPUT_DATE) = @Year
    );

    DECLARE @SalaryCost INT = (
        SELECT COALESCE(SUM(SALARY), 0)
        FROM [USER]
        WHERE DELETED = 0
    );

    SELECT 
        @Revenue AS TOTAL_REVENUE,
        @WarehouseCost AS WAREHOUSE_COST,
        @SalaryCost AS SALARY_COST,
        (@Revenue - @WarehouseCost - @SalaryCost) AS PROFIT;
END;
GO