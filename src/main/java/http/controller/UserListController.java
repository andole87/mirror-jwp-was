package http.controller;

import db.DataBase;
import http.model.request.ServletRequest;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;

import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {
    public UserListController(RequestMapping mapping) {
        super(mapping);
    }

    public UserListController(RequestMapping... mappings) {
        super(mappings);
    }

    @Override
    public void handle(ServletRequest servletRequest, ServletResponse servletResponse) {
        if (hasAuth(servletRequest)) {
            Map<String, Object> model = new HashMap<String, Object>() {{
                put("users", DataBase.findAll());
            }};
            servletResponse.ok("/user/list", model);
            return;
        }

        servletResponse.redirect("/index.html");
    }

    private boolean hasAuth(ServletRequest servletRequest) {
        return servletRequest.hasCookie() && "true".equals(servletRequest.getCookie("logined"));
    }
}
