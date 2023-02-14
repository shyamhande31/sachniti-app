package com.noticegenerator.dao;

import com.noticegenerator.entity.*;

import java.io.InputStream;
import java.util.List;

public interface NoticeGeneratorDAO {
    String JDBC_URL="jdbc:mysql://localhost:3306/";
    String DATABASE_USERNAME="root";
    String DATABASE_PASSWORD="root";
    String DRIVER_CLASS="com.mysql.cj.jdbc.Driver";

    String FIND_USER_SQL="SELECT * FROM noticegenerator.userdetails WHERE EMAIL=?";

    String FIND_USER_BY_USERNAME_SQL="SELECT EMAIL, PASSWORD FROM noticegenerator.userdetails WHERE EMAIL=?";

    String FIND_ROLE_BY_EMAIL_SQL="SELECT ROLE FROM noticegenerator.userdetails WHERE EMAIL=?";

    String CREATE_USER_SQL="INSERT into noticegenerator.userdetails VALUES(?,?,?,?,?,?)";

    String TEMPLATE_UPLOAD_SQL="INSERT into noticegenerator.templateupload VALUES(?,?,?)";

    String APPLICATION_UPLOAD_SQL="INSERT into noticegenerator.lots VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    String GET_LATEST_TEMPLATE_SQL="SELECT * FROM noticegenerator.templateupload WHERE TEMPLATE_NAME=?";

    String SELECT_DISTINCT_LOT_NUMBER_SQL="SELECT DISTINCT LOT_NUMBER FROM noticegenerator.lots";

    String SELECT_LOAN_NUMBER_SQL="SELECT LOAN_NO, REFERENCE_NUMBER, CUSTOMER_NAME FROM noticegenerator.lots WHERE LOT_NUMBER=?";

    String SELECT_LOT_DETAILS="SELECT * FROM noticegenerator.lots WHERE LOT_NUMBER=?";

    String SELECT_ALL_TEMPLATE_NAME_SQL="SELECT TEMPLATE_NAME FROM noticegenerator.templateupload";

    UserInfo login(String email);

    LogIn loadUserByUserName(String email);

    String getRoleByEmail(String email);

    Integer createUser(UserInfo info);

    TemplateUpload uploadTemplate(TemplateUpload template);

    String uploadApplication(List<ApplicationUpload> applications);

    InputStream getLatestTemplate(String templateName);

    List<String> getAllLotNumbers();

    List<String> getAllTemplateName();

    List<GenerateNoticesDetails> getAllDetailsByLotNumber(String lotNumber);

    List<ApplicationUpload> getLotDetails(String lotNumber);
}
