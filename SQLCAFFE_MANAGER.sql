﻿use master
go

DROP database IF EXISTS COFFEE_MANAGER
GO

CREATE DATABASE COFFEE_MANAGER
GO

USE COFFEE_MANAGER
GO

CREATE TABLE [ROLE]
(
	[ID] INT NOT NULL PRIMARY KEY IDENTITY,
	[ROLE_NAME] VARCHAR(20) not null,
);
GO

CREATE TABLE [USER]
(
	[USER_NAME] VARCHAR(30) NOT NULL PRIMARY KEY,
	[PASS_WORD] NVARCHAR(50) NOT NULL,
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
	[LAST_UPDATE_AT] DATE DEFAULT GETDATE(),
	[DELETED] BIT DEFAULT 0,
	FOREIGN KEY([ROLE_ID]) REFERENCES [ROLE](ID)
);
GO

CREATE TABLE CUSTOMER_TIER (
    [ID] INT PRIMARY KEY IDENTITY,
    [TIER_NAME] NVARCHAR(50) NOT NULL, -- (VD: Bronze, Silver, Gold)
    [MIN_PURCHASE] DECIMAL(10,2) NOT NULL, -- Số tiền tối thiểu để đạt cấp độ này
    [DISCOUNT_PERCENTAGE] DECIMAL(5,2) NOT NULL -- Giảm giá được hưởng
);
GO

CREATE TABLE CUSTOMER (
    [ID] INT PRIMARY KEY IDENTITY,
    [NAME] NVARCHAR(50) NOT NULL,
    [PHONE] VARCHAR(15) NOT NULL UNIQUE,
    [EMAIL] VARCHAR(100),
    [TIER_ID] INT,
	[CREATED_BY] VARCHAR(30) DEFAULT 0,
	[CREATED_AT] DATE DEFAULT GETDATE(),
	[LAST_UPDATE_BY] VARCHAR(30) DEFAULT 0,
	[LAST_UPDATE_AT] DATE DEFAULT GETDATE(),
	[DELETED] BIT DEFAULT 0,
    FOREIGN KEY (TIER_ID) REFERENCES CUSTOMER_TIER(ID)
);
GO
CREATE TABLE BEVERAGES_CATEGORY (
	[ID] INT PRIMARY KEY IDENTITY,
	[NAME] NVARCHAR(100) NOT NULL,
);
GO
CREATE TABLE BEVERAGES (
	[ID] INT PRIMARY KEY IDENTITY,
	[NAME] NVARCHAR(100) NOT NULL,
	[PRICE] INT,
	[BEVERAGES_CATEGORY_ID] INT,
	[CREATED_BY] VARCHAR(30) DEFAULT 0,
	[CREATED_AT] DATE DEFAULT GETDATE(),
	[LAST_UPDATE_BY] VARCHAR(30) DEFAULT 0,
	[LAST_UPDATE_AT] DATE DEFAULT GETDATE(),
	[DELETED] BIT DEFAULT 0,
	FOREIGN KEY(BEVERAGES_CATEGORY_ID) REFERENCES BEVERAGES_CATEGORY(ID)
);
GO

INSERT INTO [ROLE] VALUES ('Manager'), ('Staff') 

INSERT INTO [USER] ([USER_NAME], [PASS_WORD], [ROLE_ID], [FULL_NAME], [SEX], [ADDRESS], [YEAR_OF_BIRTH], [PHONE], [EMAIL], [SALARY]) VALUES
('YNM', '192023a7bbd73250516f069df18b500', 1, N'Nguyễn Minh Ý', 1, N'P.Tân Quy, Quận 7, Tp.HCM', 1996, '0987208677', '24410127@ms.uit.edu.vn', 20000000)
GO