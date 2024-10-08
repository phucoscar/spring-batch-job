CREATE TABLE `USER` (
                                        `ID` INT NOT NULL AUTO_INCREMENT,
                                        `USERNAME` VARCHAR(45) NULL,
                                        `NAME` VARCHAR(45) NULL,
                                        `PASSWORD` VARCHAR(45) NOT NULL,
                                        `EMAIL` VARCHAR(45) NOT NULL,
                                        `ADDRESS` VARCHAR(45) NULL,
                                        `CREATED_AT` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
                                        `CREATED_BY` VARCHAR(45) NOT NULL,
                                        `UPDATED_AT` DATETIME NULL,
                                        `UPDATED_BY` VARCHAR(45) NULL,
                                        PRIMARY KEY (`ID`),
                                        UNIQUE INDEX `USERNAME_UNIQUE` (`USERNAME` ASC) VISIBLE)
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
