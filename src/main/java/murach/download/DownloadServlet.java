package murach.download;

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import murach.business.User;
import murach.data.UserIO;
import murach.util.CookieUtil;

public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String action = request.getParameter("action");
        String url = "/index.jsp";
        if (action.equals("viewAlbums")) {
            url = "/index.jsp";

        } else if (action.equals("checkUser")) {
            url = checkUser(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String action = request.getParameter("action");

        String url = "/index.jsp";

        if (action.equals("registerUser")) {

            url = registerUser(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String checkUser(
            HttpServletRequest request,
            HttpServletResponse response) {

        String productCode =
                request.getParameter("productCode");

        /*
         * ==========================
         * SESSION
         * ==========================
         */

        HttpSession session = request.getSession();

        session.setAttribute(
                "productCode",
                productCode);

        User user =
                (User) session.getAttribute("user");

        String url;

        /*
         * User chưa có trong Session
         */
        if (user == null) {

            /*
             * ==========================
             * COOKIE
             * ==========================
             */

            Cookie[] cookies =
                    request.getCookies();

            String emailAddress =
                    CookieUtil.getCookieValue(
                            cookies,
                            "emailCookie");

            /*
             * Không có Cookie
             */
            if (emailAddress == null
                    || emailAddress.isEmpty()) {

                url = "/register.jsp";

            }

            /*
             * Có Cookie
             */
            else {

                ServletContext sc =
                        getServletContext();

                String path =
                        sc.getRealPath(
                                "/WEB-INF/EmailList.txt");

                user =
                        UserIO.getUser(
                                emailAddress,
                                path);

                /*
                 * Nếu tìm thấy user
                 */
                if (user != null) {

                    session.setAttribute(
                            "user",
                            user);

                    url =
                        "/" + productCode
                        + "_download.jsp";

                }

                /*
                 * Cookie tồn tại nhưng
                 * user không tồn tại
                 */
                else {

                    url = "/register.jsp";
                }
            }
        }

        /*
         * User đã có trong Session
         */
        else {

            url =
                "/" + productCode
                + "_download.jsp";
        }

        return url;
    }

    private String registerUser(
            HttpServletRequest request,
            HttpServletResponse response) {

        /*
         * Lấy dữ liệu từ form
         */

        String email =
                request.getParameter("email");

        String firstName =
                request.getParameter("firstName");

        String lastName =
                request.getParameter("lastName");

        /*
         * Tạo User
         */

        User user = new User();

        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        /*
         * Lấy đường dẫn EmailList.txt
         */

        ServletContext sc =
                getServletContext();

        String path =
                sc.getRealPath(
                        "/WEB-INF/EmailList.txt");

        /*
         * Lưu User vào file
         */

        UserIO.add(user, path);

        /*
         * ==========================
         * SESSION
         * ==========================
         */

        HttpSession session =
                request.getSession();

        session.setAttribute(
                "user",
                user);

        /*
         * ==========================
         * COOKIE
         * ==========================
         */

        Cookie cookie =
                new Cookie(
                        "emailCookie",
                        email);

        /*
         * Cookie tồn tại 1 phút
         */

        cookie.setMaxAge(60);

        /*
         * Cookie áp dụng cho toàn application
         */

        cookie.setPath("/");

        response.addCookie(cookie);

        /*
         * Lấy productCode từ Session
         */

        String productCode =
                (String) session.getAttribute(
                        "productCode");

        /*
         * Chuyển tới trang download
         */

        return "/" + productCode+ "_download.jsp";
    }
}

