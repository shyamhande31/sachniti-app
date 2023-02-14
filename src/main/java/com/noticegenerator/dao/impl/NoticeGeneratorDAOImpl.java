package com.noticegenerator.dao.impl;

import com.noticegenerator.dao.NoticeGeneratorDAO;
import com.noticegenerator.entity.ApplicationUpload;
import com.noticegenerator.entity.GenerateNoticesDetails;
import com.noticegenerator.entity.LogIn;
import com.noticegenerator.entity.TemplateUpload;
import com.noticegenerator.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoticeGeneratorDAOImpl implements NoticeGeneratorDAO {

    private Connection connection;

    public NoticeGeneratorDAOImpl() {
        try {
            Class.forName(DRIVER_CLASS);
            connection = DriverManager.getConnection(JDBC_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserInfo login(String email) {
        UserInfo userInfo=new UserInfo();
        PreparedStatement st=null;
        ResultSet rs=null;
        try {
            st=connection.prepareStatement(FIND_USER_SQL);
            st.setString(1,email);
            rs=st.executeQuery();
            while(rs.next()) {
                userInfo.setUserId(rs.getInt(1));
                userInfo.setUserName(rs.getString(2));
                userInfo.setEmail(rs.getString(3));
                userInfo.setContactNumber(rs.getInt(4));
                userInfo.setRole(rs.getString(5));
            }
            return userInfo;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                rs.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public LogIn loadUserByUserName(String email) {
        LogIn user=new LogIn();
        PreparedStatement st=null;
        ResultSet rs=null;
        try {
            st=connection.prepareStatement(FIND_USER_BY_USERNAME_SQL);
            st.setString(1,email);
            rs=st.executeQuery();
            while(rs.next()) {
                user.setEmail(rs.getString(1));
                user.setPassword(rs.getString(2));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                rs.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String getRoleByEmail(String email) {
        String role="";
        PreparedStatement st=null;
        ResultSet rs=null;
        try {
            st=connection.prepareStatement(FIND_ROLE_BY_EMAIL_SQL);
            st.setString(1,email);
            rs=st.executeQuery();
            while(rs.next()) {
                role= rs.getString(1);
            }
            return role;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                rs.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Integer createUser(UserInfo info) {
        int noOfRowsUpdated=0;
        PreparedStatement st=null;
        try {
            st=connection.prepareStatement(CREATE_USER_SQL);
            st.setInt(1,info.getUserId());
            st.setString(2, info.getUserName());
            st.setString(3, info.getEmail());
            st.setInt(4, info.getContactNumber());
            st.setString(5, info.getRole());
            st.setString(6, info.getPassword());
            noOfRowsUpdated=st.executeUpdate();
            return noOfRowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    @Transactional
    public TemplateUpload uploadTemplate(TemplateUpload template) {
        int noOfRowsUpdated=0;
        PreparedStatement st=null;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(getByteArrayFromFile(template.getSelectedTemplateLocation()));
            st=connection.prepareStatement(TEMPLATE_UPLOAD_SQL);
            st.setInt(1,template.getTemplateId());
            st.setString(2, template.getTemplateName());
            st.setBlob(3,inputStream);
            st.executeUpdate();
            return new TemplateUpload();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    @Transactional
    public String uploadApplication(List<ApplicationUpload> applications) {
        int noOfRowsUpdated=1;
        PreparedStatement st=null;
        try {
            int i=0;
            st=connection.prepareStatement(APPLICATION_UPLOAD_SQL);
            for(ApplicationUpload applicationUpload:applications){
                st.setInt(1,applicationUpload.getLotId());
                st.setString(2,applicationUpload.getLotNumber() );
                st.setDate(3, applicationUpload.getUploadTime());
                st.setString(4,applicationUpload.getReferenceNumber());
                st.setString(5,applicationUpload.getLoanNo());
                st.setString(6,applicationUpload.getCustomerName());
                st.setString(7,applicationUpload.getProduct());
                st.setString(8,applicationUpload.getProdGroup());
                st.setString(9,applicationUpload.getEmiAmt());
                st.setString(10,applicationUpload.getNoticeDate());
                st.setString(11,applicationUpload.getNoticeSendDate());
                st.setString(12,applicationUpload.getLanguage());
                st.setString(13,applicationUpload.getNoticeNo());
                st.setString(14,applicationUpload.getAddress1());
                st.setString(15,applicationUpload.getAddress2());
                st.setString(16,applicationUpload.getAddress3());
                st.setString(17,applicationUpload.getAddress4());
                st.setString(18,applicationUpload.getCity());
                st.setString(19,applicationUpload.getZipCode());
                st.setString(20,applicationUpload.getState());
                st.setString(21,applicationUpload.getEmailId());
                st.setString(22,applicationUpload.getToBeIssuedVia());
                st.setString(23,applicationUpload.getAddType());
                st.setString(24,applicationUpload.getVendor());
                st.setString(25,applicationUpload.getBranch());
                st.setString(26,applicationUpload.getRegion());
                st.addBatch();
                i++;
                if (i % 1000 == 0 || i == applications.size()) {
                    st.executeBatch();
                }
            }
            return applications.get(0).getLotNumber();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public InputStream getLatestTemplate(String templateName) {
        PreparedStatement st=null;
        ResultSet rs=null;
        try {
            st=connection.prepareStatement(GET_LATEST_TEMPLATE_SQL);
            st.setString(1,templateName);
            rs=st.executeQuery();
            while(rs.next()) {
                InputStream stream=rs.getBlob(3).getBinaryStream();
                return stream;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<String> getAllLotNumbers() {
        List<String> allLotNumbers=new ArrayList<>();
        PreparedStatement st=null;
        ResultSet rs=null;
        try {
            st=connection.prepareStatement(SELECT_DISTINCT_LOT_NUMBER_SQL);
            rs=st.executeQuery();
            while(rs.next()) {
                allLotNumbers.add(rs.getString(1));
            }
            return allLotNumbers;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<String> getAllTemplateName() {
        List<String> allTemplateName=new ArrayList<>();
        PreparedStatement st=null;
        ResultSet rs=null;
        try {
            st=connection.prepareStatement(SELECT_ALL_TEMPLATE_NAME_SQL);
            rs=st.executeQuery();
            while(rs.next()) {
                allTemplateName.add(rs.getString(1));
            }
            return allTemplateName;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<GenerateNoticesDetails> getAllDetailsByLotNumber(String lotNumber) {
        List<GenerateNoticesDetails> details=new ArrayList<>();
        PreparedStatement st=null;
        ResultSet rs=null;
        try {
            st=connection.prepareStatement(SELECT_LOAN_NUMBER_SQL);
            st.setString(1,lotNumber);
            rs=st.executeQuery();
            while(rs.next()) {
                GenerateNoticesDetails generateNoticesDetails=new GenerateNoticesDetails();
                generateNoticesDetails.setLoanNumber(rs.getString(1));
                generateNoticesDetails.setReferenceNumber(rs.getString(2));
                generateNoticesDetails.setCustomerName(rs.getString(3));
                details.add(generateNoticesDetails);
            }
            return details;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<ApplicationUpload> getLotDetails(String lotNumber) {
        List<ApplicationUpload> details=new ArrayList<>();
        PreparedStatement st=null;
        ResultSet rs=null;
        try {
            st=connection.prepareStatement(SELECT_LOT_DETAILS);
            st.setString(1,lotNumber);
            rs=st.executeQuery();
            while (rs.next()){
                ApplicationUpload applicationUpload=new ApplicationUpload();
                applicationUpload.setLotId((rs.getInt(1)));
                applicationUpload.setLotNumber((rs.getString(2)));
                applicationUpload.setUploadTime((rs.getDate(3)));
                applicationUpload.setReferenceNumber((rs.getString(4)));
                applicationUpload.setLoanNo((rs.getString(5)));
                applicationUpload.setCustomerName((rs.getString(6)));
                applicationUpload.setProduct((rs.getString(7)));
                applicationUpload.setProdGroup((rs.getString(8)));
                applicationUpload.setEmiAmt((rs.getString(9)));
                applicationUpload.setNoticeDate((rs.getString(10)));
                applicationUpload.setNoticeSendDate((rs.getString(11)));
                applicationUpload.setLanguage((rs.getString(12)));
                applicationUpload.setNoticeNo((rs.getString(13)));
                applicationUpload.setAddress1((rs.getString(14)));
                applicationUpload.setAddress2((rs.getString(15)));
                applicationUpload.setAddress3((rs.getString(16)));
                applicationUpload.setAddress4((rs.getString(17)));
                applicationUpload.setCity((rs.getString(18)));
                applicationUpload.setZipCode((rs.getString(19)));
                applicationUpload.setState((rs.getString(20)));
                applicationUpload.setEmailId((rs.getString(21)));
                applicationUpload.setToBeIssuedVia((rs.getString(22)));
                applicationUpload.setAddType((rs.getString(23)));
                applicationUpload.setVendor((rs.getString(24)));
                applicationUpload.setBranch((rs.getString(25)));
                applicationUpload.setRegion((rs.getString(26)));
                details.add(applicationUpload);
            }
            return details;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private byte[] getByteArrayFromFile(MultipartFile file) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final InputStream in = file.getInputStream();
        final byte[] buffer = new byte[500];

        int read = -1;
        while ((read = in.read(buffer)) > 0) {
            baos.write(buffer, 0, read);
        }
        in.close();

        return baos.toByteArray();
    }
}
