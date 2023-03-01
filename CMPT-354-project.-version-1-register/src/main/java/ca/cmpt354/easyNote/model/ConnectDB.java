package ca.cmpt354.easyNote.model;

import ca.cmpt354.easyNote.dao.BasicDao;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConnectDB {

    //New user register a new account
    public static void createUser(String name, String email, String password){
        Connection conn = BasicDao.getConnection();

        try{
            String sql;
            sql = "INSERT INTO user (username, email, `password`) VALUES (?,?,?)";
            Object[] args = {name,email,password};
            BasicDao.update(conn,sql,args);

            BasicDao.closeResources(conn,null,null);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    //Judge if the name and email are same with the one in sql
    public static boolean ifExist(String name, String email){
        if(name.length()<2 || email.length()<4){
            return true;
        }
        Connection conn = BasicDao.getConnection();
        PreparedStatement stmt1;
        int emailState = 0;
        int nameState = 0;
        try{
            String sql;
            sql = "SELECT `username` FROM `user` WHERE `username` IN (?);";
            stmt1 = conn.prepareStatement(sql);
            ResultSet rs = BasicDao.search(conn,sql,new String[]{name});
            while (rs.next()) {
                if (rs.getString("username").equals(name)) {
                    nameState = 1;
                }
            }
            sql = "SELECT email FROM `user` WHERE email IN (?);";
            stmt1 = conn.prepareStatement(sql);
            rs = BasicDao.search(conn,sql,new String[]{email});
            while (rs.next()) {
                if (rs.getString("email").equals(email)) {
                    emailState = 1;
                }
            }


            BasicDao.closeResources(conn,stmt1,rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

        return emailState > 0 || nameState > 0;
    }

    //Use to get a individual id
    public static User getLatestUser(){
        Connection conn = BasicDao.getConnection();
        User user = new User();

        try{
            String sql;
            sql = "SELECT * FROM `user` WHERE ID IN (SELECT MAX(ID) FROM `user`);";
            Object[] args = {};
            ResultSet rs = BasicDao.search(conn,sql,args);
            while (rs.next()){
                user.ID = rs.getInt("ID");
                user.username = rs.getString("username");
                user.email = rs.getString("email");
                user.password = rs.getString("password");
                user.level = rs.getInt("level");
            }

            BasicDao.closeResources(conn,null,null);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

        return user;
    }

    //return the sign_in user information
    public static User getSignInUser(String email, String pass) {
        Connection conn = BasicDao.getConnection();
        User user = new User();

        try{
            String sql;

            sql = "SELECT * FROM `user` WHERE email IN (?) AND `password` IN (?);";
            Object[] args = {email, pass};
            ResultSet rs = BasicDao.search(conn,sql,args);
            while (rs.next()){
                user.ID = rs.getInt("ID");
                user.username = rs.getString("username");
                user.email = rs.getString("email");
                user.password = rs.getString("password");
                user.level = rs.getInt("level");
            }

            BasicDao.closeResources(conn,null,null);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

        return user;
    }

    //Judge if the email and password in the sql(if exist user)
    public static boolean isUser(String email, String password){
        Connection conn = BasicDao.getConnection();
        PreparedStatement stmt1;
        int emailState = 0;
        int passState = 0;
        try{
            String sql;
            sql = "SELECT email FROM `user` WHERE email IN (?);";
            stmt1 = conn.prepareStatement(sql);
            ResultSet rs = BasicDao.search(conn,sql,new String[]{email});
            while (rs.next()) {
                if (rs.getString("email").equals(email)) {
                    emailState = 1;
                }
            }
            sql = "SELECT `password` FROM `user` WHERE `password` IN (?);";
            stmt1 = conn.prepareStatement(sql);
            rs = BasicDao.search(conn,sql,new String[]{password});
            while (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    passState = 1;
                }
            }

            BasicDao.closeResources(conn,stmt1,rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

        return emailState > 0 && passState > 0;
    }

    //Which course the user has upload the files and how many
    public static List<Object[]> userDocCourse(int userID){
        Connection conn = BasicDao.getConnection();
        PreparedStatement stmt1;
        List<Object[]> courseList = new ArrayList<>();
        try{
            String sql;
            sql = "SELECT course, COUNT(ID) FROM document WHERE userID = ? GROUP BY course;";
            stmt1 = conn.prepareStatement(sql);
            ResultSet rs = BasicDao.search(conn, stmt1, sql, new Integer[]{userID});
            while(rs.next()){
                Object[] ob = {rs.getString("course"), rs.getInt("COUNT(ID)")};
                courseList.add(ob);
            }
            BasicDao.closeResources(conn,stmt1,rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

        return courseList;
    }

    //Upload document
    public static boolean inputDocument(int userID, String course, String fileName, byte[] file){
        if(course.equals("") || course == null)
            return false;
        if(file == null)
            return false;
        Connection conn = BasicDao.getConnection();
        try{
            String sql;

            sql = "SELECT MAX(ID) FROM document";
            PreparedStatement stmt;
            Object[] args = {};
            ResultSet rs = BasicDao.search(conn, sql, args);
            int id = 0;
            while(rs.next()){
                id = rs.getInt(1) + 1;
            }
            FileType filetype = new FileType(file);
            String name = fileName.substring(0,fileName.lastIndexOf('.'));
            name = name + Integer.toString(id);
            String path = "src\\main\\resources\\file\\" + name + "." + filetype.getFileType();
            File newFile = new File(path);
            BufferedOutputStream bos = null;
            FileOutputStream fos = null;
            fos = new FileOutputStream(newFile);
            bos = new BufferedOutputStream(fos);
            bos.write(file);

            sql = "INSERT INTO document (userID, course, fileName, type, URL) VALUES (?,?,?,?,?)";
            args = new Object[]{userID, course, fileName, filetype.getFileType(), path};
            BasicDao.update(conn,sql,args);

            BasicDao.closeResources(conn,null,null);

        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return true;
    }

    public static void preview(HttpServletResponse response, int fileID) {
        Document document = findDocument(fileID);
        previewFile(response, document);
    }

    //View files' content in web page
    private static void previewFile(HttpServletResponse response, Document document) {
        if(response == null || document == null) return ;

        String className = "ConnectDB";
        String functionName = "previewFile";

        response.setCharacterEncoding("utf-8");

        String contentType = "text/html";
        switch (document.getType().toLowerCase()) {
            case "png": contentType = "image/png"; break;
            case "gif": contentType = "image/gif"; break;
            case "jpeg": contentType = "image/jpeg"; break;
            case "jpg": contentType = "image/jpeg"; break;
            case "xml": contentType = "text/xml"; break;
            case "html": contentType = "text/html"; break;
            case "txt": contentType = "text/plain"; break;
            case "pdf": contentType = "application/pdf"; break;
            case "dox": contentType = "application/msword"; break; // will go to download page
            case "docx": contentType = "application/msword"; break; // will go to download page
        }
        response.setContentType(contentType);

        int len = 0;
        FileInputStream in = null;
        byte[] buffer = new byte[1024];
        ServletOutputStream out = null;
        try {
            in = new FileInputStream(document.getURL());
            out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            BasicDao.printErrorMessage(className, functionName);
//            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                BasicDao.printErrorMessage(className, functionName);
//                e.printStackTrace();
            }
            try {
                in.close();
            } catch (Exception e) {
                BasicDao.printErrorMessage(className, functionName);
//                e.printStackTrace();
            }
        }
    }

    public static void download(HttpServletResponse response, int fileID) {
        Document document = findDocument(fileID);
        downloadFile(response, document);
    }

    /**
     * 這裡需要處理異常
     * @param fileID
     * @return
     */

    //Return the document information
    private static Document findDocument(int fileID){
        String className = "ConnectDB";
        String functionName = "findFileNameAndFileURL";

        Connection connection = BasicDao.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "select * from document where ID = ?;";
        Object[] args = {fileID};

        Document document = null;
        ResultSet resultSet = null;
        try {
            resultSet = BasicDao.search(connection, preparedStatement, sql, args);
            if(resultSet.next()) {
                document = new Document(resultSet.getInt("ID"), resultSet.getInt("userID"), resultSet.getString("course"),
                        resultSet.getString("fileName"), resultSet.getString("type"), resultSet.getString("URL"));
            }
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage(className, functionName);
            throwables.printStackTrace();
        }

        return document;
    }

    private static void downloadFile(HttpServletResponse response, Document document) {
        if(response == null || document == null) return ;

        String className = "ConnectDB";
        String functionName = "downloadFile";

        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+document.getFileName());

        int len = 0;
        FileInputStream in = null;
        byte[] buffer = new byte[1024];
        ServletOutputStream out = null;
        try {
            in = new FileInputStream(document.getURL());
            out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            BasicDao.printErrorMessage(className, functionName);
//            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                BasicDao.printErrorMessage(className, functionName);
//                e.printStackTrace();
            }
            try {
                in.close();
            } catch (Exception e) {
                BasicDao.printErrorMessage(className, functionName);
//                e.printStackTrace();
            }
        }
    }

    //Add the document to library
    public static void addToLibrary(int docID, int userID){
        Connection conn = BasicDao.getConnection();
        PreparedStatement stmt1;

        try{
            String sql;
            sql = "SELECT ID FROM library WHERE userID =" + userID;
            stmt1 = conn.prepareStatement(sql);
            Object[] args = {};
            ResultSet rs = BasicDao.search(conn,stmt1,sql,args);
            int libID = 0;
            while (rs.next()){
                libID = rs.getInt("ID");
            }
            sql = "INSERT INTO CollectDoc VALUES (?,?,?);";
            args = new Object[]{docID, userID, libID};
            BasicDao.update(conn,sql,args);

            BasicDao.closeResources(conn,stmt1,rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    //List the all the files in the sql
    public static List<Document> getDocumentInfo(){
        Connection conn = BasicDao.getConnection();
        PreparedStatement stmt1;
        List<Document> list = new ArrayList();

        try{
            String sql;
            sql = "SELECT * FROM document";
            stmt1 = conn.prepareStatement(sql);
            Object[] args = {};
            ResultSet rs = BasicDao.search(conn,sql,args);
            while (rs.next()){
                int id = rs.getInt("ID");
                int userID = rs.getInt("userID");
                String course = rs.getString("course");
                String fileName = rs.getString("fileName");
                String type = rs.getString("type");
                String URL = rs.getString("URL");
                list.add(new Document(id,userID,course,fileName,type,URL));
            }

            BasicDao.closeResources(conn,stmt1,rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return list;
    }

    //choose the file by the information given by user
    public static List<Document> getDocumentInfo(String courseName, String key){
        Connection conn = BasicDao.getConnection();
        PreparedStatement stmt1;
        List<Document> list = new ArrayList();

        try{
            String sql;
            sql = "SELECT * FROM document WHERE course LIKE '%"+courseName+"%' AND filename LIKE '%"+key+"%'";
            stmt1 = conn.prepareStatement(sql);
            Object[] args = {};
            ResultSet rs = BasicDao.search(conn,stmt1,sql,args);
            while (rs.next()){
                int id = rs.getInt("ID");
                int userID = rs.getInt("userID");
                String course = rs.getString("course");
                String fileName = rs.getString("fileName");
                String type = rs.getString("type");
                String URL = rs.getString("URL");
                list.add(new Document(id,userID,course,fileName,type,URL));
            }

            BasicDao.closeResources(conn,stmt1,rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return list;
    }

    //List the all the document in the library
    public static List<Document> libraryDocument(int ID){
        Connection conn = BasicDao.getConnection();
        PreparedStatement stmt1;
        List<Document> list = new ArrayList();

        try{
            String sql;
            sql = "SELECT * FROM document INNER JOIN collectdoc " +
                    "ON document.ID = collectdoc.docID AND collectdoc.userID =" + ID;
            stmt1 = conn.prepareStatement(sql);
            Object[] args = {};
            ResultSet rs = BasicDao.search(conn,stmt1,sql,args);
            while (rs.next()){
                int id = rs.getInt("ID");
                int userID = rs.getInt("userID");
                String course = rs.getString("course");
                String fileName = rs.getString("fileName");
                String type = rs.getString("type");
                String URL = rs.getString("URL");
                list.add(new Document(id,userID,course,fileName,type,URL));
            }

            BasicDao.closeResources(conn,stmt1,rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return list;
    }

    //When add a new user create a new library
    public static void creatLibrary(int userID, String name){
        Connection conn = BasicDao.getConnection();

        try{
            String sql;

            sql = "INSERT INTO library (userID, `name`) VALUES (?,?)";
            Object[] args = {userID,name};
            BasicDao.update(conn,sql,args);

            BasicDao.closeResources(conn,null,null);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    public static void changeUserInf(int userID, String password){
        Connection conn = BasicDao.getConnection();
        try{
            String sql;

            sql = "UPDATE `user` SET `password` = (?) WHERE ID = (?)";
            Object[] args3 = {password, userID};
            BasicDao.update(conn,sql,args3);

            BasicDao.closeResources(conn,null,null);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //List the number of files in the course
    public static ArrayList<CourseFileNum> CourseFileNumber(){
        Connection conn = BasicDao.getConnection();
        PreparedStatement stmt1;
        ArrayList<CourseFileNum> list = new ArrayList<>();

        try{
            String sql;
            sql = "SELECT course, COUNT(ID) FROM document GROUP BY course ORDER BY COUNT(ID) DESC";
            stmt1 = conn.prepareStatement(sql);
            Object[] args = {};
            ResultSet rs = BasicDao.search(conn,stmt1,sql,args);
            while (rs.next()){
                String name = rs.getString("course");
                int num = rs.getInt("COUNT(ID)");
                list.add(new CourseFileNum(name, num));
            }

            BasicDao.closeResources(conn,stmt1,rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return list;
    }
}
