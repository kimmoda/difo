<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>
<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" pageTitle="Contact Us">
    <stripes:layout-component name="contents">
        <div class="zion-general-page">
            <div class="page-header">
                <h1>Contact Us</h1>    
            </div>
            <div class="row">
                <div class="col-md-6 text-center">
                    <h3>Address</h3>
                    <address>
	                    Level 1 Lysaght Building<br /> 
	                    101 Pakenham Street West<br />
	                    Auckland 1010 New Zealand
                    </address>
                </div>
                <div class="col-md-6 text-center">
                    <h3>Contact</h3>
                    <b>Email</b>: <a href="mailto:<c:out value="${appSupportEmail}"/>"><c:out value="${appSupportEmail}"/></a><br />
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div id="googlemap" style="height: 300px;"></div>
                </div>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script>        
        function initMap() {
                var center = {lat: -36.843566, lng: 174.7550128};
                var map = new google.maps.Map(document.getElementById('googlemap'), {
                   zoom: 15,
                   center: center,
                   scrollwheel: false,
                   styles: [{"featureType":"water","elementType":"geometry","stylers":[{"color":"#e9e9e9"},{"lightness":17}]},{"featureType":"landscape","elementType":"geometry","stylers":[{"color":"#f5f5f5"},{"lightness":20}]},{"featureType":"road.highway","elementType":"geometry.fill","stylers":[{"color":"#ffffff"},{"lightness":17}]},{"featureType":"road.highway","elementType":"geometry.stroke","stylers":[{"color":"#ffffff"},{"lightness":29},{"weight":0.2}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"color":"#ffffff"},{"lightness":18}]},{"featureType":"road.local","elementType":"geometry","stylers":[{"color":"#ffffff"},{"lightness":16}]},{"featureType":"poi","elementType":"geometry","stylers":[{"color":"#f5f5f5"},{"lightness":21}]},{"featureType":"poi.park","elementType":"geometry","stylers":[{"color":"#dedede"},{"lightness":21}]},{"elementType":"labels.text.stroke","stylers":[{"visibility":"on"},{"color":"#ffffff"},{"lightness":16}]},{"elementType":"labels.text.fill","stylers":[{"saturation":36},{"color":"#333333"},{"lightness":40}]},{"elementType":"labels.icon","stylers":[{"visibility":"off"}]},{"featureType":"transit","elementType":"geometry","stylers":[{"color":"#f2f2f2"},{"lightness":19}]},{"featureType":"administrative","elementType":"geometry.fill","stylers":[{"color":"#fefefe"},{"lightness":20}]},{"featureType":"administrative","elementType":"geometry.stroke","stylers":[{"color":"#fefefe"},{"lightness":17},{"weight":1.2}]}]
                });
             var marker = new google.maps.Marker({
               position: center,
               map: map
             });
        }
     </script>
     <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBxeKW0Bw2FccEE3wWJdbUvMWUIeKQ_PJw&callback=initMap"></script>   
    </stripes:layout-component>
</stripes:layout-render>
