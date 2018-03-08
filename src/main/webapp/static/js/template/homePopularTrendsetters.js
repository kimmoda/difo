(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['homePopularTrendsetters'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "    <div class=\"item\">\n        <div class=\"row\">\n"
    + ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),depth0,{"name":"each","hash":{},"fn":container.program(2, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "        </div>\n    </div>\n";
},"2":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "                <section class=\"col-sm-4 col-md-3 col-lg-3 trendsetter-container\">\n                    <div class=\"col-md-12 image\">\n                        <a href=\""
    + alias4(((helper = (helper = helpers.stylistUrl || (depth0 != null ? depth0.stylistUrl : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"stylistUrl","hash":{},"data":data}) : helper)))
    + "\">\n                            <img class=\"zion-circle-avatar-lg\" src=\""
    + alias4(container.lambda(((stack1 = (depth0 != null ? depth0.person : depth0)) != null ? stack1.avatar : stack1), depth0))
    + "\"\n                                 alt=\"trendsetter avatar\">\n                            <!--<img class=\"style-pick\"-->\n                                 <!--src=\"https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/stylePick_icon.png\"-->\n                                 <!--alt=\"style-pick\">-->\n                        </a>\n                    </div>\n                    <div class=\"col-md-12 content\">\n                        <div class=\"info\">\n                            <a href=\""
    + alias4(((helper = (helper = helpers.stylistUrl || (depth0 != null ? depth0.stylistUrl : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"stylistUrl","hash":{},"data":data}) : helper)))
    + "\">\n                                <h3 class=\"title\">\n                                    "
    + alias4(((helper = (helper = helpers.displayName || (depth0 != null ? depth0.displayName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"displayName","hash":{},"data":data}) : helper)))
    + "\n                                </h3>\n                            </a>\n                            <div class=\"role\">\n"
    + ((stack1 = helpers.each.call(alias1,(depth0 != null ? depth0.userRoles : depth0),{"name":"each","hash":{},"fn":container.program(3, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                            </div>\n                        </div>\n                        <!--<div class=\"follow\">-->\n                        <!--<button class=\"btn zion-btn-white\">FOLLOW</button>-->\n                        <!--</div>-->\n                    </div>\n                    <hr class=\"trendsetter-bottom\">\n                </section>\n";
},"3":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                                    "
    + ((stack1 = (helpers.listUserRoles || (depth0 && depth0.listUserRoles) || helpers.helperMissing).call(depth0 != null ? depth0 : (container.nullContext || {}),depth0,{"name":"listUserRoles","hash":{},"data":data})) != null ? stack1 : "")
    + "\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.users : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
})();