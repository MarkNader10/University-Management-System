Create database UniversityDB
use UniversityDB ;


Create table Department(
D_ID int primary key not null ,
D_Name nvarchar(200) not null ,
D_Location nvarchar(300)not null,
unique (D_Name, D_Location),
HEAT_ID int ,
foreign key(HEAT_ID) references Instructor(Instructor_ID)
);

Create table Instructor(
 Instructor_ID int primary key not null ,
 FName nvarchar(255)not null,
 LName nvarchar(255)not null,
Department_ID int foreign key references Department(D_ID)
);

Create table Course (
C_ID int primary key not null ,
C_Name nvarchar(255)not null,
Duration int,
INST_ID int foreign key references Instructor(Instructor_ID),
Department_ID int foreign key references Department(D_ID)
);

Create table Student(
Student_ID int primary key not null ,
 FName nvarchar(255)not null,
 LName nvarchar(255)not null
);

Create table Student_Phone(
 ST_ID int,
 Phone nvarchar(20),
 primary key (ST_ID, Phone),
 foreign key (ST_ID) references Student(Student_ID)
);

Create table Instructor_Phone(
 INST_ID int,
 Phone nvarchar(20),
 primary key (INST_ID, Phone),
 foreign key (INST_ID) references Instructor(Instructor_ID)
);

Create table Course_Student(
  Course_ID int,
  ST_ID int,
  primary key (Course_ID, ST_ID),
  foreign key (Course_ID) references Course(C_ID),
  foreign key (ST_ID) references Student(Student_ID)
);

Create table Course_Instructor (
    Course_ID int not null,
    INST_ID int not null,
    primary key (Course_ID, INST_ID),
    foreign key (Course_ID) references Course(C_ID),
    foreign key (INST_ID) references Instructor(Instructor_ID)
);
