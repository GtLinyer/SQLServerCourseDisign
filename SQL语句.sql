/*
Navicat SQL Server Data Transfer

Source Server         : SQL Server
Source Server Version : 140000
Source Host           : localhost:1433
Source Database       : YYGLXT
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 140000
File Encoding         : 65001
*/


-- ----------------------------
-- Table structure for 仓库
-- ----------------------------
DROP TABLE [dbo].[仓库]
GO
CREATE TABLE [dbo].[仓库] (
[药品编号] varchar(8) NOT NULL ,
[药品数量] int NOT NULL DEFAULT ((0)) 
)


GO

-- ----------------------------
-- Table structure for 订单
-- ----------------------------
DROP TABLE [dbo].[订单]
GO
CREATE TABLE [dbo].[订单] (
[订单号] varchar(8) NOT NULL ,
[下单日期] date NOT NULL ,
[药品编号] varchar(8) NOT NULL ,
[数量] int NOT NULL ,
[总额] float(53) NOT NULL ,
[订单状态] varchar(1) NOT NULL DEFAULT ((0)) ,
[客户编号] varchar(8) NOT NULL 
)


GO

-- ----------------------------
-- Table structure for 供应商
-- ----------------------------
DROP TABLE [dbo].[供应商]
GO
CREATE TABLE [dbo].[供应商] (
[供应商编号] varchar(8) NOT NULL ,
[供应商名称] varchar(90) NOT NULL ,
[供应商联系方式] varchar(90) NOT NULL ,
[供应商地址] varchar(90) NOT NULL 
)


GO

-- ----------------------------
-- Table structure for 客户
-- ----------------------------
DROP TABLE [dbo].[客户]
GO
CREATE TABLE [dbo].[客户] (
[客户编号] varchar(8) NOT NULL ,
[客户姓名] varchar(30) NOT NULL ,
[客户联系方式] varchar(30) NOT NULL ,
[客户登录密码] varchar(30) NOT NULL ,
[客户地址] varchar(90) NOT NULL 
)


GO

-- ----------------------------
-- Table structure for 入库
-- ----------------------------
DROP TABLE [dbo].[入库]
GO
CREATE TABLE [dbo].[入库] (
[入库单号] varchar(8) NOT NULL ,
[药品编号] varchar(8) NOT NULL ,
[入库数量] int NOT NULL ,
[入库日期] date NOT NULL 
)


GO

-- ----------------------------
-- Table structure for 药品
-- ----------------------------
DROP TABLE [dbo].[药品]
GO
CREATE TABLE [dbo].[药品] (
[药品编号] varchar(8) NOT NULL ,
[药品名称] varchar(90) NOT NULL ,
[药品功效] varchar(90) NULL ,
[药品单价] float(53) NOT NULL ,
[有效期] varchar(30) NOT NULL ,
[药品类型] varchar(30) NOT NULL ,
[供应商编号] varchar(8) NOT NULL 
)


GO

-- ----------------------------
-- Table structure for 员工
-- ----------------------------
DROP TABLE [dbo].[员工]
GO
CREATE TABLE [dbo].[员工] (
[员工工号] varchar(8) NOT NULL ,
[员工姓名] varchar(30) NOT NULL ,
[员工联系方式] varchar(30) NOT NULL ,
[员工登录密码] varchar(30) NOT NULL ,
[员工职务] varchar(30) NOT NULL ,
[是否管理员] varchar(1) NOT NULL DEFAULT ((0)) 
)


GO

-- ----------------------------
-- View structure for 仓库查询
-- ----------------------------
DROP VIEW [dbo].[仓库查询]
GO
CREATE VIEW [dbo].[仓库查询] AS 
SELECT
dbo.[仓库].[药品编号],
dbo.[药品].[药品名称],
dbo.[仓库].[药品数量]

FROM
dbo.[仓库]
INNER JOIN dbo.[药品] ON dbo.[仓库].[药品编号] = dbo.[药品].[药品编号]
GO

-- ----------------------------
-- View structure for 当月统计
-- ----------------------------
DROP VIEW [dbo].[当月统计]
GO
CREATE VIEW [dbo].[当月统计] AS 
SELECT
dbo.[订单].[下单日期],
dbo.[药品].[药品名称],
dbo.[订单].[数量],
dbo.[订单].[总额]

FROM
dbo.[订单]
INNER JOIN dbo.[药品] ON dbo.[订单].[药品编号] = dbo.[药品].[药品编号]
WHERE
dbo.[订单].[订单状态] = 1
and MONTH(下单日期)=MONTH(GETDATE());
GO

-- ----------------------------
-- View structure for 管理员登录
-- ----------------------------
DROP VIEW [dbo].[管理员登录]
GO
CREATE VIEW [dbo].[管理员登录] AS 
SELECT
dbo.[员工].[员工工号] AS [管理员工号],
dbo.[员工].[员工姓名] AS [管理员姓名],
dbo.[员工].[员工登录密码] AS [管理员登录密码]

FROM
dbo.[员工]
WHERE
dbo.[员工].[是否管理员] = 1
GO

-- ----------------------------
-- View structure for 客户登录
-- ----------------------------
DROP VIEW [dbo].[客户登录]
GO
CREATE VIEW [dbo].[客户登录] AS 
SELECT
dbo.[客户].[客户编号],
dbo.[客户].[客户姓名],
dbo.[客户].[客户登录密码]

FROM
dbo.[客户]
GO

-- ----------------------------
-- View structure for 客户看订单
-- ----------------------------
DROP VIEW [dbo].[客户看订单]
GO
CREATE VIEW [dbo].[客户看订单] AS 
SELECT
dbo.[订单].[订单号],
dbo.[订单].[下单日期],
dbo.[订单].[药品编号],
dbo.[药品].[药品名称],
dbo.[客户].[客户姓名],
dbo.[客户].[客户地址],
dbo.[订单].[客户编号],
dbo.[订单].[数量],
dbo.[订单].[总额],
dbo.[订单].[订单状态]

FROM
dbo.[订单]
INNER JOIN dbo.[药品] ON dbo.[订单].[药品编号] = dbo.[药品].[药品编号]
INNER JOIN dbo.[客户] ON dbo.[订单].[客户编号] = dbo.[客户].[客户编号]
GO

-- ----------------------------
-- View structure for 员工登录
-- ----------------------------
DROP VIEW [dbo].[员工登录]
GO
CREATE VIEW [dbo].[员工登录] AS 
SELECT
dbo.[员工].[员工工号],
dbo.[员工].[员工姓名],
dbo.[员工].[员工登录密码]

FROM
dbo.[员工]
GO

-- ----------------------------
-- View structure for 在售药品
-- ----------------------------
DROP VIEW [dbo].[在售药品]
GO
CREATE VIEW [dbo].[在售药品] AS 
SELECT
dbo.[药品].[药品编号],
dbo.[药品].[药品名称],
dbo.[药品].[药品功效],
dbo.[药品].[药品单价],
dbo.[药品].[有效期],
dbo.[药品].[药品类型],
dbo.[仓库].[药品数量] AS [药品余量]

FROM
dbo.[药品]
INNER JOIN dbo.[仓库] ON dbo.[仓库].[药品编号] = dbo.[药品].[药品编号]
WHERE
dbo.[仓库].[药品数量] > 0
GO

-- ----------------------------
-- Primary Key structure for table 仓库
-- ----------------------------
ALTER TABLE [dbo].[仓库] ADD PRIMARY KEY ([药品编号])
GO

-- ----------------------------
-- Primary Key structure for table 订单
-- ----------------------------
ALTER TABLE [dbo].[订单] ADD PRIMARY KEY ([订单号])
GO

-- ----------------------------
-- Primary Key structure for table 供应商
-- ----------------------------
ALTER TABLE [dbo].[供应商] ADD PRIMARY KEY ([供应商编号])
GO

-- ----------------------------
-- Primary Key structure for table 客户
-- ----------------------------
ALTER TABLE [dbo].[客户] ADD PRIMARY KEY ([客户编号])
GO

-- ----------------------------
-- Indexes structure for table 入库
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table 入库
-- ----------------------------
ALTER TABLE [dbo].[入库] ADD PRIMARY KEY ([入库单号])
GO

-- ----------------------------
-- Triggers structure for table 入库
-- ----------------------------
DROP TRIGGER [dbo].[入库触发器]
GO
CREATE TRIGGER [dbo].[入库触发器]
ON [dbo].[入库]
AFTER INSERT
AS
    --定义变量
    declare @数量 int,@药品 varchar(8)
    --在inserted表中查询已经插入记录信息
    select @数量 = 入库数量,@药品 = 药品编号 from inserted;
    update 仓库 set 药品数量 = 药品数量 + @数量 where 药品编号=@药品;

GO
DROP TRIGGER [dbo].[退货触发器]
GO
CREATE TRIGGER [dbo].[退货触发器]
ON [dbo].[入库]
AFTER DELETE
AS
    --定义变量
    declare @数量 int,@药品 varchar(8)
    --在inserted表中查询已经插入记录信息
    select @数量 = 入库数量,@药品 = 药品编号 from deleted;
    update 仓库 set 药品数量 = 药品数量 - @数量 where 药品编号=@药品;

GO

-- ----------------------------
-- Primary Key structure for table 药品
-- ----------------------------
ALTER TABLE [dbo].[药品] ADD PRIMARY KEY ([药品编号])
GO

-- ----------------------------
-- Triggers structure for table 药品
-- ----------------------------
DROP TRIGGER [dbo].[仓库更新]
GO
CREATE TRIGGER [dbo].[仓库更新]
ON [dbo].[药品]
AFTER INSERT
AS
    --定义变量
    declare @药品 varchar(8)
    --在inserted表中查询已经插入记录信息
    select @药品 = 药品编号 from inserted;
    insert into 仓库(药品编号) values(@药品);

GO

-- ----------------------------
-- Primary Key structure for table 员工
-- ----------------------------
ALTER TABLE [dbo].[员工] ADD PRIMARY KEY ([员工工号])
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[仓库]
-- ----------------------------
ALTER TABLE [dbo].[仓库] ADD FOREIGN KEY ([药品编号]) REFERENCES [dbo].[药品] ([药品编号]) ON DELETE CASCADE ON UPDATE CASCADE
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[订单]
-- ----------------------------
ALTER TABLE [dbo].[订单] ADD FOREIGN KEY ([客户编号]) REFERENCES [dbo].[客户] ([客户编号]) ON DELETE CASCADE ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[订单] ADD FOREIGN KEY ([药品编号]) REFERENCES [dbo].[药品] ([药品编号]) ON DELETE CASCADE ON UPDATE CASCADE
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[入库]
-- ----------------------------
ALTER TABLE [dbo].[入库] ADD FOREIGN KEY ([药品编号]) REFERENCES [dbo].[药品] ([药品编号]) ON DELETE CASCADE ON UPDATE CASCADE
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[药品]
-- ----------------------------
ALTER TABLE [dbo].[药品] ADD FOREIGN KEY ([供应商编号]) REFERENCES [dbo].[供应商] ([供应商编号]) ON DELETE CASCADE ON UPDATE CASCADE
GO
