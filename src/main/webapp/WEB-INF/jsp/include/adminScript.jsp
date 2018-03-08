<!-- MetisMenu CSS -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/metisMenu/2.7.0/metisMenu.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/startbootstrap-sb-admin-2/3.3.7+1/css/sb-admin-2.min.css" rel="stylesheet" type="text/css">

<!-- Morris Charts CSS -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css" rel="stylesheet">

<!-- Metis Menu Plugin JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/metisMenu/2.7.0/metisMenu.min.js"></script>

<!-- Morris Charts JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.2.7/raphael.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="<c:url value="/static/js/lib/sb-admin-2.js"/>"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

<script>
    var redirectToAdmin = function(redirectUrl) {
        var jwtToken = localStorage.getItem('zion-Jwt-Token');
        window.location.replace(redirectUrl + '?token=' + jwtToken);
    };
    jQuery('body').on('click', '.zion-admin-action', function(e) {
        var redirectUrl = jQuery(this).data('action-bean-url');
        redirectToAdmin(redirectUrl);
    });
</script>