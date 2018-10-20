package telran.forum.service.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import telran.forum.api.Header;
import telran.forum.configuration.AccountConfiguration;
import telran.forum.dao.UserAccountRepository;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import telran.forum.api.Link;
import telran.forum.domain.UserAccount;

@Service
@Order(3)
public class AuthorizationFilter implements Filter {

    @Autowired
    AccountConfiguration userConfiguration;

    @Autowired
    UserAccountRepository accountRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getServletPath().startsWith(Link.ACCOUNT)
            && !request.getServletPath().contains(Link.REGISTER)){

            String id = request.getServletPath().substring(Link.ACCOUNT.length() + 1);
            UserAccount user = accountRepository.findById(id).orElse(null);

            if(user == null){
                response.sendError(401, "User ID: " + id + " is not found");
                return;
            }

            if(!userConfiguration.authorization(request.getHeader(Header.AUTHORIZATION), user)){
                response.sendError(403, "You does not have permission to change user " + id);
                return;
            }

        }

        filterChain.doFilter(request, response);

    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void destroy() {

    }
}
