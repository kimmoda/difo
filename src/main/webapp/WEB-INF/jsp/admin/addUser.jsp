<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/admin/layout.jsp">
    <stripes:layout-component name="headMeta">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
        <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/zion.admin.adduser.css" />">
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div id="modal" class="modal fade" tabindex="-1" role="dialog">
		    <div class="modal-dialog" role="document">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
		                </button>
		                <h4 class="modal-title">Notification</h4>
		            </div>
		            <div class="modal-body">
		                <p id="message" class="text-center"></p>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		            </div>
		        </div>
		    </div>
		</div>
		<div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Add User</h1>
            </div>
        </div>
        <div id="role-info" class="row hidden">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Role
                </div>
                <div class="panel-body">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="checkbox-inline">
                                <input id="admin" name="roles" type="checkbox" value="ADMIN"/>Admin
                            </label>
                            <label class="checkbox-inline">
                                <input id="stylist" name="roles" type="checkbox" value="STYLIST"/>Stylist
                            </label>
                            <label class="checkbox-inline">
                                <input id="designer" name="roles" type="checkbox" value="DESIGNER"/>Designer
                            </label>
                            <label class="checkbox-inline">
                                <input id="influencer" name="roles" type="checkbox" value="INFLUENCER"/>Influencer
                            </label>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="radio-inline">
                                <input type="radio" name="status" value="enable">Enable
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="status" value="disable">Disable
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row" id="top-recommend">
             <div class="panel panel-default">
                <div class="panel-heading">
                    Top recommend
                </div>
                <div class="panel-body">
                     <div class="col-md-6">
                        <div class="form-group">
                            <label class="checkbox-inline">
                                <input id="top-recommend-selection" name="topRecommend" type="checkbox" value="TOP"/>TOP
                            </label>
                        </div>
                    </div>
                </div>
             </div>
        </div>
        <div class="row" id="account-info">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Account
                </div>
                <div class="panel-body">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Username</label>
                            <label class="required">*</label>
                            <input id="username" class="form-control" placeholder="Username">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Profile
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>User name:</label>
                                <input id="user-name-display" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Avatar</label>
                                <input id="avatar" class="form-control" placeholder="Avatar">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Title</label>
                                <input id="title" class="form-control" placeholder="Title">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>About me</label>
                                <textarea id="introduce" class="desc-area form-control" placeholder="About me"></textarea>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Prefer Name</label>
                                <input id="prefer-name" class="form-control" placeholder="Prefer Name">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>First name</label>
                                <input id="first-name" class="form-control" placeholder="First name">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Middle name</label>
                                <input id="middle-name" class="form-control" placeholder="Middle name">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Last name</label>
                                <input id="last-name" class="form-control" placeholder="Last name">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label>Gender</label>
                            <div class="form-group">
                                <label class="radio-inline">
                                    <input type="radio" name="gender" id="gender_female" value="Female"
                                           checked>Female
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="gender" id="gender_male"
                                           value="Male">Male
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <form class="experience"
                                  data-addel-animation-duration="500"
                                  data-addel-hide="true">
                                <label>Experience</label>
                                <div class="form-group addel-target">
                                    <div class="input-group">
                                        <input type="text" name="experience[]" class="form-control">
                                        <span class="input-group-btn">
                                        <button type="button" class="btn btn-danger addel-delete">
                                            <i class="fa fa-remove"></i>
                                        </button>
                                    </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button id="add-experience" type="button"
                                            class="btn btn-success btn-block addel-add">
                                        <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </form>
                        </div>

                        <div class="col-md-6">
                            <form class="certification"
                                  data-addel-animation-duration="500"
                                  data-addel-hide="true">
                                <label>Certification</label>
                                <div class="form-group addel-target">
                                    <div class="input-group">
                                        <input type="text" name="certification[]" class="form-control">
                                        <span class="input-group-btn">
                                        <button type="button" class="btn btn-danger addel-delete">
                                            <i class="fa fa-remove"></i>
                                        </button>
                                    </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button id="add-certification" type="button"
                                            class="btn btn-success btn-block addel-add">
                                        <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </form>
                        </div>

                        <div class="col-md-6">
                            <form class="award"
                                  data-addel-animation-duration="500"
                                  data-addel-hide="true">
                                <label>Award</label>
                                <div class="form-group addel-target">
                                    <div class="input-group">
                                        <input type="text" name="award[]" class="form-control">
                                        <span class="input-group-btn">
                                        <button type="button" class="btn btn-danger addel-delete">
                                            <i class="fa fa-remove"></i>
                                        </button>
                                    </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button id="add-award" type="button" class="btn btn-success btn-block addel-add">
                                        <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </form>
                        </div>

                        <div class="col-md-6">
                            <form class="partnership"
                                  data-addel-animation-duration="500"
                                  data-addel-hide="true">
                                <label>Partnership</label>
                                <div class="form-group addel-target">
                                    <div class="input-group">
                                        <input type="text" name="partnership[]" class="form-control">
                                        <span class="input-group-btn">
                                        <button type="button" class="btn btn-danger addel-delete">
                                            <i class="fa fa-remove"></i>
                                        </button>
                                    </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button id="add-partnership" type="button"
                                            class="btn btn-success btn-block addel-add">
                                        <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </form>
                        </div>

                        <div class="col-md-6">
                            <form class="client"
                                  data-addel-animation-duration="500"
                                  data-addel-hide="true">
                                <label>Client</label>
                                <div class="form-group addel-target">
                                    <div class="input-group">
                                        <input type="text" name="client[]" class="form-control">
                                        <span class="input-group-btn">
                                        <button type="button" class="btn btn-danger addel-delete">
                                            <i class="fa fa-remove"></i>
                                        </button>
                                    </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button id="add-client" type="button" class="btn btn-success btn-block addel-add">
                                        <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Contact
                </div>
                <div class="panel-body">
                    <div class="col-md-6">
                        <div id="email-container" class="form-group">
                            <label>Email
                                <b id="email-notice" class="has-error alert-danger hidden">(The email address is invalid.)</b>
                            </label>
                            <input id="email" class="form-control" placeholder="Email">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Address</label>
                            <input id="address" class="form-control zion-google-location-input" placeholder="Address">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Suburb</label>
                            <input id="suburb" class="form-control" placeholder="Suburb">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>City</label>
                            <input id="city" class="form-control" placeholder="City">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>State</label>
                            <input id="state" class="form-control" placeholder="State">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Country</label>
                            <input id="country" class="form-control" placeholder="Country" disabled/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Phone</label>
                            <input id="phone" class="form-control" placeholder="Phone">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Mobile</label>
                            <input id="mobile" class="form-control" placeholder="Mobile">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group" id="website-container">
                            <label>WebSite
                                <b id="website-notice" class="has-error alert-danger hidden">(The website url is invalid.)</b>
                            </label>
                            <input id="website" class="form-control" placeholder="Website">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group" id="facebook-container">
                            <label>Facebook</label>
                            <b id="facebook-notice" class="has-error alert-danger hidden">(The facebook url is invalid.)</b>
                            <input id="facebook" class="form-control" placeholder="Facebook">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group" id="youtube-container">
                            <label>Youtube</label>
                            <b id="youtube-notice" class="has-error alert-danger hidden">(The youtube url is invalid.)</b>
                            <input id="youtube" class="form-control" placeholder="Youtube">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group" id="instagram-container">
                            <label>Instagram</label>
                            <b id="instagram-notice" class="has-error alert-danger hidden">(The Instagram url is invalid.)</b>
                            <input id="instagram" class="form-control" placeholder="Instagram">
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="service-info" class="row">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Service & Price
                </div>
                <div class="panel-body">
                    <form class="service"
                          data-addel-animation-duration="500"
                          data-addel-hide="true">
                        <div class="form-group addel-target">
                            <div class="input-group service-block">
                                <div class="service-delete-container">
                                    <button type="button" class="btn btn-danger btn-xs addel-delete">
                                        <i class="fa fa-remove"></i>
                                    </button>
                                </div>
                                <table class=table>
                                    <tbody>
                                    <tr>
                                        <td><label>Service</label></td>
                                        <td><label>Price</label></td>
                                        <td><label>Duration</label></td>
                                    </tr>
                                    <tr>
                                        <td><input type="text" name="service[]" class="form-control"></td>
                                        <td><input type="text" name="price[]" class="form-control"></td>
                                        <td><input type="text" name="duration[]" class="form-control"></td>
                                    </tr>
                                    </tbody>
                                </table>
                                <label>Description</label>
                                <textarea name="desc[]" class="desc-area form-control" name="desc[]"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <button id="add-service" type="button"
                                    class="btn btn-success btn-block addel-add">
                                <i class="fa fa-plus"></i>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        
        <div class="row">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Social media information for applying to be a trendsetter.
                </div>
                <div class="panel-body">
                    <textarea id="registration-social-media-info" disabled="disabled" cols="200" rows="10">
                    </textarea>
                </div>
            </div>
        </div>
        
         <div class="row">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Reject reason and request information from this user
                </div>
                <div>Preview request info</div>
                <textarea id="preview-request-info" disabled="disabled" cols="200" rows="10"></textarea>
                <div class="panel-body">
                    <textarea id="requested-registration-info" cols="200" rows="10"></textarea>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <button id="add-user-button" type="submit" class="btn btn-default">Submit</button>
                <button id="apply-user-button" type="submit" class="btn btn-default hidden">Approve</button>
                <button id="reject-user-button" type="submit" class="btn btn-default hidden">Reject</button>
            </div>
        </div>
    </stripes:layout-component>
    
    <stripes:layout-component name="footer">
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.1/moment.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
        <script src="<c:url value="/static/js/service/googlelocationservice.js" />"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBxeKW0Bw2FccEE3wWJdbUvMWUIeKQ_PJw&libraries=places&callback=zionGoogleLocationService.init&language=en"></script>
		<script src="<c:url value="/static/js/lib/addel.jquery.js" />"></script>
		<script src="<c:url value="/static/js/lib/autosize.js" />"></script>
		<script src="<c:url value="/static/js/adduser.js" />"></script>
		<script type="text/javascript">
		    var adduserPath = "<c:url value="/restws/user/create"/>",
		    loadUserPath = "<c:url value="/restws/user/v1/view"/>",
		    loadCountryUrl = "<c:url value="/restws/country/v1/list"/>",
		    updatePath = "<c:url value="/restws/user/v1/update"/>",
		    updateStatusPath = "<c:url value="/restws/user/update/status"/>",
		    updateRolesPath = "<c:url value="/restws/user/update/role"/>",
		    addUserTagPath = "<c:url value="/restws/user/update/tags"/>",
		    homePagePath = "<c:url value="/action/superadmin/home" />",
		    stylistApplyUrl = "<c:url value="/restws/stylist/v1/approve" />",
		    stylistRejectUrl = "<c:url value="/restws/stylist/v1/reject" />";
		</script>
    </stripes:layout-component>
</stripes:layout-render>
