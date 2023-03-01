package ca.cmpt354.easyNote.controllers;

import ca.cmpt354.easyNote.model.*;
import ca.cmpt354.easyNote.service.PostMessageService;
import ca.cmpt354.easyNote.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
Functionalities:

[1]. register: register account
[2]. sign_in: sign in
[3]. upload_file: upload file with a course name
[4]. files: exhibit all files
[5]. download: download the file and choose the path.
[6]. profilePage: show the details of the user, user can log out
[7]. library: show the fil;es the user saved
[8]. posts: show the discussion board, user is able to add/delete a post and reply.

*/


@Controller
public class EasyNotesController {
    public static User user;
    private Post thePost;

    //The gutFile function get a path to a file, return a  list contains all messages in the file.
    public static List<String> getFile(String path) throws Exception {
        FileReader fileReader =new FileReader(path);
        BufferedReader bufferedReader =new BufferedReader(fileReader);
        List<String> list =new ArrayList<String>();
        String str=null;
        while((str=bufferedReader.readLine())!=null) {
            if(str.trim().length()>2) {
                list.add(str);
            }
        }
        return list;
    }


    @GetMapping("/register")
    public String register(Model model,HttpSession httpSession) throws Exception {
        User newUser = new User();
        httpSession.setAttribute("newUsers", newUser);
        model.addAttribute("newUser", newUser);

        return "register";
    }

    @PostMapping ("/register")
    public String register_success(@ModelAttribute("newUser") User newUser, HttpSession httpSession, Model model) {

        //Get the User object sent from html page
        String name = newUser.username;
        String email = newUser.email;
        String password = newUser.password;
        int ID = 9527;
        int level = 0;

        //Refresh the User data

        boolean isExist = ConnectDB.ifExist(name, email);
        if(isExist){
            httpSession.setAttribute("status", isExist);
            model.addAttribute("status", isExist);
            return "register";
        }

        ConnectDB.createUser(name,email,password);

        user = ConnectDB.getLatestUser();

        ConnectDB.creatLibrary(user.ID,"new library");

        httpSession.setAttribute("status", isExist);
        model.addAttribute("status", isExist);
        httpSession.setAttribute("newUser", user);
        model.addAttribute("newUser", user);


        return "homePage";
    }

    @GetMapping("/sign_in")
    public String enroll (Model model,HttpSession httpSession){
        User theUser = new User();
        httpSession.setAttribute("theUser", theUser);
        model.addAttribute("theUser", theUser);
        httpSession.setAttribute("status", true);
        model.addAttribute("status", true);

        return "sign_in";
    }

    @PostMapping("/sign_in")
    public String check_sign_in(@ModelAttribute("theUser") User theUser, HttpSession httpSession, Model model) {

        String email = theUser.email;
        String password = theUser.password;

        boolean success = ConnectDB.isUser(email,password);

        if(success){
            user = ConnectDB.getSignInUser(email,password);
            return "homePage";
        }

        httpSession.setAttribute("status", success);
        model.addAttribute("status", success);
        return "sign_in";

    }

    @RequestMapping("/backToHomePage")
    public String backTiHomePage(){
        return "homePage";
    }

    @GetMapping("/upload_file")
    public String upload(HttpSession httpSession, Model model) {

        //String fileText = new String();
        FileOptionModel fileText = new FileOptionModel();

        List<String> optList = new ArrayList<>();
        optList.add("CMPT 213");
        optList.add("CMPT 225");
        optList.add("CMPT 276");
        optList.add("CMPT 295");
        optList.add("CMPT 300");
        optList.add("CMPT 307");
        optList.add("CMPT 354");
        optList.add("CMPT 363");
        optList.add("CMPT 365");
        optList.add("CMPT 376");


        model.addAttribute("optList", optList);
        httpSession.setAttribute("optList", optList);

        model.addAttribute("fileText", fileText);
        httpSession.setAttribute("fileText", fileText);

        httpSession.setAttribute("status", true);
        model.addAttribute("status", true);

        return "upload_file";
    }

    @GetMapping("/files")
    public String file(HttpSession httpSession, Model model) {

        String choice = "";
        FileOptionModel optionModel = new FileOptionModel();

        List<Document> fileList = ConnectDB.getDocumentInfo();

        List<String> optList = new ArrayList<>();
        optList.add("CMPT 213");
        optList.add("CMPT 225");
        optList.add("CMPT 276");
        optList.add("CMPT 295");
        optList.add("CMPT 300");
        optList.add("CMPT 307");
        optList.add("CMPT 354");
        optList.add("CMPT 363");
        optList.add("CMPT 365");
        optList.add("CMPT 376");

        ArrayList<CourseFileNum> courseNumList;
        courseNumList = ConnectDB.CourseFileNumber();

        List<Object[]> courseFileNums = ConnectDB.userDocCourse(user.ID);

        model.addAttribute("courseFileNums", courseFileNums);
        httpSession.setAttribute("courseFileNums", courseFileNums);

        model.addAttribute("courseNumList", courseNumList);
        httpSession.setAttribute("courseNumList", courseNumList);

        model.addAttribute("optList", optList);
        httpSession.setAttribute("optList", optList);

        model.addAttribute("choice", choice);
        httpSession.setAttribute("choice", choice);

        httpSession.setAttribute("fileList", fileList);
        model.addAttribute("fileList", fileList);

        httpSession.setAttribute("optionModel", optionModel);
        model.addAttribute("optionModel", optionModel);
        return "files";
    }

    @PostMapping("/files")
    public String getCourseName(@ModelAttribute("choice")String choice,@ModelAttribute("optionModel")FileOptionModel optionModel, HttpSession httpSession, Model model){

        List<Document> fileList = ConnectDB.getDocumentInfo(choice, optionModel.text);

        List<String> optList = new ArrayList<>();
        optList.add("CMPT 213");
        optList.add("CMPT 225");
        optList.add("CMPT 276");
        optList.add("CMPT 295");
        optList.add("CMPT 300");
        optList.add("CMPT 307");
        optList.add("CMPT 354");
        optList.add("CMPT 363");
        optList.add("CMPT 365");
        optList.add("CMPT 376");


        model.addAttribute("optList", optList);
        httpSession.setAttribute("optList", optList);

        model.addAttribute("choice", choice);
        httpSession.setAttribute("choice", choice);

        httpSession.setAttribute("fileList", fileList);
        model.addAttribute("fileList", fileList);

        httpSession.setAttribute("optionModel", optionModel);
        model.addAttribute("optionModel", optionModel);
        return "files";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file, @ModelAttribute("fileText") FileOptionModel fileText, HttpSession httpSession, Model model) {
        if (file.isEmpty()) {
            return "upload_file";
        }
        try {
            // Get the file
            byte[] bytes = file.getBytes();
            String name = file.getOriginalFilename();

            //inputdocument返回0如果课名为空
            boolean notEmpty = ConnectDB.inputDocument(user.ID, fileText.departmentCode,name,bytes);
            if(!notEmpty){
                fileText = new FileOptionModel();

                List<String> optList = new ArrayList<>();
                optList.add("CMPT 213");
                optList.add("CMPT 225");
                optList.add("CMPT 276");
                optList.add("CMPT 295");
                optList.add("CMPT 300");
                optList.add("CMPT 307");
                optList.add("CMPT 354");
                optList.add("CMPT 363");
                optList.add("CMPT 365");
                optList.add("CMPT 376");


                model.addAttribute("optList", optList);
                httpSession.setAttribute("optList", optList);

                model.addAttribute("fileText", fileText);
                httpSession.setAttribute("fileText", fileText);

                httpSession.setAttribute("status", notEmpty);
                model.addAttribute("status", notEmpty);
                return "upload_file";
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return "homePage";
    }


    @GetMapping("/download")
    public void download(@ModelAttribute("fileId") int fileId, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        ConnectDB.download(response, fileId);
    }

    @RequestMapping("previewTesting")
    public void preview(@ModelAttribute("fileId") int fileId,HttpServletResponse response) {
        ConnectDB.preview(response, fileId);
    }

    @RequestMapping("/profilePage")
    public String profile(HttpSession httpSession, Model model){

        httpSession.setAttribute("user", user);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/library")
    public String library(HttpSession httpSession, Model model){

        List<Document> fileList = ConnectDB.libraryDocument(user.ID);

        httpSession.setAttribute("fileList", fileList);
        model.addAttribute("fileList", fileList);
        httpSession.setAttribute("user", user);
        model.addAttribute("user", user);

        return "library";
    }

    @RequestMapping("/addToLibrary")
    public void addToLibrary(@ModelAttribute("fileId") int fileId, HttpServletResponse response, HttpServletRequest request, HttpSession httpSession, Model model){

        ConnectDB.addToLibrary(fileId, user.ID);

    }

    @GetMapping("/posts")
    public String post(HttpSession httpSession, Model model){

        PostOption postOption = new PostOption();
        PostService postService = new PostService();
        List<Post> postList= postService.searchPostByInfo("");



        httpSession.setAttribute("postOption", postOption);
        model.addAttribute("postOption", postOption);

        httpSession.setAttribute("postList", postList);
        model.addAttribute("postList", postList);

        return "post";

    }


    //    HttpServletResponse response, HttpServletRequest request is necessary!
    @GetMapping("/addPost")
    public void addPost( @ModelAttribute("title") String title,@ModelAttribute("content") String content, HttpServletResponse response, HttpServletRequest request, HttpSession httpSession, Model model){

        PostService postService = new PostService();
        postService.addPost(title, user, content);
    }


    @GetMapping("/showPost")
    public String showPost( @ModelAttribute("id") int id, @ModelAttribute("title") String title, HttpSession httpSession, Model model) throws IOException, ServletException {
        PostService postService = new PostService();
        thePost = postService.searchPostByID(id);


        PostMessageService postMessageService = new PostMessageService();
        List<PostMessage> messageList = postMessageService.findAllMessagesInOnePost(thePost);

        boolean isTheAuthor = false;
        if(thePost.userID == user.ID){
            isTheAuthor = true;
        }

        httpSession.setAttribute("thePost", thePost);
        model.addAttribute("thePost", thePost);

        httpSession.setAttribute("messageList", messageList);
        model.addAttribute("messageList", messageList);

        httpSession.setAttribute("isTheAuthor", isTheAuthor);
        model.addAttribute("isTheAuthor", isTheAuthor);
        return "viewPost";
    }


    @GetMapping("/addReply")
    public void addReply(@ModelAttribute("content") String content, HttpServletResponse response, HttpServletRequest request, HttpSession httpSession, Model model){
        if(content.length()>2){
            PostMessageService postMessageService = new PostMessageService();
            postMessageService.addPostMessage(thePost, content, user);
        }

    }


    @GetMapping("/deletePost")
    public String deletePost( HttpServletResponse response, HttpServletRequest request, HttpSession httpSession, Model model){
        PostService postService = new PostService();
        postService.deletePostByID(thePost.ID, user);

        return "homePage";
    }

    @GetMapping("/editPassword")
    public String editPassword ( @ModelAttribute("content") String content, HttpServletResponse response, HttpServletRequest request, HttpSession httpSession, Model model){
        //System.out.println(content);
        user.password = content;
        ConnectDB.changeUserInf(user.ID, user.password);

        return "homePage";
    }


}