<%--
  Created by IntelliJ IDEA.
  User: adamhand
  Date: 2018/8/4
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<div>
    第${page.pageNum}页/共${page.totalPageSize}页&nbsp;&nbsp;
    <a href="${pageContext.request.contexPath}${page.url}&num=1">首页</a>&nbsp;&nbsp;
    <a href="${pageContext.request.contexPath}${page.url}&num=${page.prePageNum}">上一页</a>&nbsp;&nbsp;
    <!--   page.setUrl("/servlet/ManageServlet?op=listBooks"); -->
    <a href="${pageContext.request.contexPath}${page.url}&num=${page.nextPageNum}">下一页</a>&nbsp;&nbsp;
    <a href="${pageContext.request.contexPath}${page.url}&num=${page.totalPageNum}">尾页</a>&nbsp;&nbsp;

    <select id="jump" onchange="jump(this)">
        <c:forEach begin="1" end="${page.totalPageSize}" var="n">
            <option value="${n}" ${page.pageNum==n?'selected="selected"':''}>${n}</option>
        </c:forEach>
    </select>
</div>
