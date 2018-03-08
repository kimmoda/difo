<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>
<jsp:useBean id="date" class="java.util.Date" />
<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
<div class="zion-pub-footer zion-desktop-visible">
    <div class="container">
        <div class="row">
            <div class="col-sm-5 social-container">
                <h3>CONNECT WITH US</h3>
                <div class="social-icons-links">
                </div>
                <div class="copyright-content">Copyright &copy; <c:out value="${currentYear}" /> <c:out value="${appBrandName}"/>. All rights reserved.</div>
            </div>
            <div id="footer-menu-container" class="col-sm-7 links-container">
            </div>
        </div>
    </div>
</div>

<div class="zion-tab-bar-container row zion-mobile-visible" id="zion-tab-bar-container"></div>