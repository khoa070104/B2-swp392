<%-- 
    Document   : admin
    Created on : Mar 1, 2022, 8:29:12 PM
    Author     : hd
--%>


<%@page import="java.util.List"%>
<%@page import="pe.models.ProductDTO"%>
<%@page import="pe.models.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>product Page</title>
    </head>
    <body>
        <%
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
            if (user == null || !user.getRoleID().equals("AD")) {
                response.sendRedirect("login.jsp");
                return;
            }
            List<ProductDTO> list = (List<ProductDTO>) request.getAttribute("LIST_PRODUCT");
            String search = request.getParameter("search");
            if (search == null) {
                search = "";
            }
        %>
        <h1>Welcome <%=user.getFullName()%></h1>
        <form action="MainController">
            <input type="text" name="search"/>
            <input type="submit" name="action" value="Search"/>
            <input type="submit" name="action" value="Logout"/>            
        </form>

        <table border="1">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Size</th>
                    <th>Description</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                <%if (list != null) {
                        int count = 0;
                        for (ProductDTO p : list) {

                            count++;
                %>

                <tr>
                <td><%=count%></td>
                <td>
                    <%=p.getId()%>
                </td>
                <td>
                    <%=p.getName()%>
                </td>
                <td>
                    <%=p.getPrice()%>
                </td>
                <td>
                    <%=p.getSize()%>
                </td>
                <td>
                    <%=p.getDescription()%>
                </td>
                <td>
                    <a href="MainController?action=Delete&search=<%=search%>&id=<%=p.getId()%>">Delete</a>
                </td>
        </tr>
        <% }
            }%>
    </tbody>
</table>        

</body>
</html>
