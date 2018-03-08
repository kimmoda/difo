<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>
<%--
    includeFile is optional;
    when includeFile is empty string output default setting;
    when includeFile doesn't exist output default setting;
    when includeFile has value output response setting;
--%>
<%@ attribute name="includeFile" type="java.lang.String" %>
<%@ attribute name="name" required="true" type="java.lang.String" %>

<pack:script enabled="${actionBean.webConfig.packTagEnabled}">
    <c:choose>
        <c:when test="${empty includeFile}">
            <src>/static/js/components/pub/${name}/business.js</src>
            <src>/static/js/components/pub/${name}/ui.js</src>
        </c:when>
        <c:otherwise>
            <c:forTokens items="${includeFile}" delims="," var="file">
                <src>/static/js/components/pub/${name}/${file}.js</src>
            </c:forTokens>
        </c:otherwise>
    </c:choose>
</pack:script>