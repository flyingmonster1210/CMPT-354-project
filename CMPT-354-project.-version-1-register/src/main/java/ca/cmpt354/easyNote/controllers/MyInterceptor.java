package ca.cmpt354.easyNote.controllers;

import ca.cmpt354.easyNote.controllers.EasyNotesController;
import ca.cmpt354.easyNote.model.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // compost url
        String servletPath = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");

        if(servletPath.contains("/error")){
            return true;
        }
        if(servletPath.contains("/styles/")||servletPath.contains("/music/")||servletPath.contains("/images/")){
            return true;
        }

        User userObject = EasyNotesController.user;

        if(userObject == null || userObject.getID() == 0 || userObject.getUsername().equals("null")) {
            response.sendRedirect("/sign_in");
        } else {
            return true;
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
