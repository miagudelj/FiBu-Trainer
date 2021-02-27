package ch.fibuproject.fibu.controller;

import ch.fibuproject.fibu.database.*;
import ch.fibuproject.fibu.model.*;
import ch.fibuproject.fibu.model.Class;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Vector;

/**
 * Vinicius
 * 05.Feb 2021
 * Version: 1.0
 * Project description:
 * Controller for the website
 */

@RestController
public class Controller {

    /**
     * /user
     * is for responses for UserDAO
     *     /login - log into website
     *     /logout - logout of website
     *     /list - list user
     *     /delete - remove user form database
     *     /save - create new user and save into database
     *     /read - get a single user
     * @return boolean to check if successful
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public ResponseEntity login(@RequestBody User requestUser, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            User dbUser = new UserDAO().getUser(requestUser.getUsername());
            String pwdCheck = dbUser.getPasswordHash();
            if (pwdCheck.equals(requestUser.getPassword())) {
                session.setAttribute("username", dbUser.getUsername());
                if (request.getParameter("remember-me") != null) {
                    Cookie usrCookie = new Cookie("username", dbUser.getUsername());
                    Cookie pwdCookie = new Cookie("password", dbUser.getPassword());
                    usrCookie.setMaxAge(3600);
                    pwdCookie.setMaxAge(3600);
                    response.addCookie(usrCookie);
                    response.addCookie(pwdCookie);
                }
                return new ResponseEntity("login successful", new HttpHeaders(), HttpStatus.OK);
            } else {
                return new ResponseEntity("wrong login information", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            return new ResponseEntity("Error in ch.fibuproject.fibu.controller.Controller.login", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.DELETE)
    public HttpStatus logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        session.removeAttribute("username");
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equalsIgnoreCase("username")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            if (cookie.getName().equalsIgnoreCase("password")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public List<User> listUser() {
        return new UserDAO().getAllUsers();
    }

    @RequestMapping(value = "/user/delete?{userID}", method = RequestMethod.DELETE)
    public HttpStatus deleteUser(@PathVariable Integer userID) {
        switch (new UserDAO().deleteUser(userID)) {
            case SUCCESS:
                return HttpStatus.OK;
            case ERROR:
                return HttpStatus.BAD_REQUEST;
            case NOACTION:
                return HttpStatus.NOT_FOUND;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping(value = "/user/save", method = RequestMethod.POST, consumes = "application/json")
    public HttpStatus saveUser(@RequestBody User saveUser, @RequestParam Integer userClass) {
        switch (new UserDAO().saveUser(saveUser, userClass)){
            case SUCCESS:
                return HttpStatus.OK;
            case ERROR:
                return HttpStatus.BAD_REQUEST;
            case NOACTION:
                return HttpStatus.NOT_FOUND;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping(value = "/user/read", method = RequestMethod.GET)
    public User readUser(@RequestParam String username) {
        return new UserDAO().getUser(username);
    }

    /**
     * /class
     * is for responses for ClassDAO
     *     /list - list all classes
     *     /delete - remove class form database
     *     /save - create/update class and save into database
     *     /read - get a single class
     */
    @RequestMapping(value = "/class/list", method = RequestMethod.GET)
    public Vector<Class> listClasses() {
        return new ClassDAO().retrieveAllClasses();
    }

    @RequestMapping(value = "/class/delete?{classID}", method = RequestMethod.DELETE)
    public HttpStatus deleteClasses(@PathVariable Integer classID) {
        switch (new ClassDAO().deleteClass(classID)) {
            case SUCCESS:
                return HttpStatus.OK;
            case ERROR:
                return HttpStatus.BAD_REQUEST;
            case NOACTION:
                return HttpStatus.NOT_FOUND;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping(value = "/class/save", method = RequestMethod.POST)
    public HttpStatus saveClasses(@RequestBody Class saveClass) {
        switch (new ClassDAO().newClass(saveClass)){
            case SUCCESS:
                return HttpStatus.OK;
            case ERROR:
                return HttpStatus.BAD_REQUEST;
            case NOACTION:
                return HttpStatus.NOT_FOUND;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping(value = "/class/read", method = RequestMethod.GET)
    public Class readClasses(@RequestParam Integer classID) {
        return new ClassDAO().retrieveClass(classID);
    }

    /**
     * /blocks
     * is for responses for QuestionDAO and SubjectDAO
     *     /themes - responses for SubjectDAO
     *         /list - list all subjects
     *     /workID - response for questions inside of subjects
     *         /list - list all questions inside of a subject
     * @return
     */
    @RequestMapping(value = "/blocks/themes/list", method = RequestMethod.GET)
    public Vector<Subject> listThemes(){
        return new SubjectDAO().getAllSubjects();
    }

    @RequestMapping(value = "/blocks/{workID}/list", method = RequestMethod.GET)
    public Vector<ExerciseGroup> listThemes(@PathVariable Integer workID){
        return new ExerciseGroupDAO().getAllExerciseGroups(workID);
    }
}