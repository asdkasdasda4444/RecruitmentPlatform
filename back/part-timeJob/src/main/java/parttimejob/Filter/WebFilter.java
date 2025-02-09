package parttimejob.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;




@Slf4j
@Component
@javax.servlet.annotation.WebFilter(filterName = "loginFilter",urlPatterns = "/*")
public class WebFilter implements Filter {

    private static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        //定义不拦截的请求路径
        String[] breakUrl=new String[]{
                "/*/*",
                "/*/*/*",
                "/user/login",
                "/user/logout",
                "/user/statistics",
                "/job/jobPage",
                "/job/addJobInfo",
                "/user/pageInfo",
                "/messages",
                "/user/boosInfo/boos/*",
                "/user/boosInfo/candidate/*",
                "/report/addInfo",
                "/candidate/updateResume",
               "/report/reportPage",
                "/report/list/*"
        };
        boolean check = check(breakUrl, requestURI);
            if (check){

                //TODO:前端已经 localStorage.setItem("userId",res.data.id);后端拿不到数据
//                if (request.getSession().getAttribute("userId")!=null){
//                    Long userId = (Long) request.getSession().getAttribute("userId");
//                    System.out.println("拦截器获取userID:"+userId);
//                    BaseThread.setThreadLocal(userId);
//                }

            filterChain.doFilter(request,response);
            return;
        }
        log.info("请求被Filter拦截：{}",requestURI);
        response.getWriter().write("非法请求");
    }

    public boolean check(String[] breakUrl, String requestURI){
        for (String url:breakUrl) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                log.info("匹配到请求：{}",requestURI);
                return true;
            }
        }
        return false;
    }
}
