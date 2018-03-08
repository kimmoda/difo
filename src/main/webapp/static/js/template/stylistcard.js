(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['stylistcard'] = template({"1":function(container,depth0,helpers,partials,data,blockParams,depths) {
    var stack1, helper, alias1=container.lambda, alias2=container.escapeExpression, alias3=depth0 != null ? depth0 : (container.nullContext || {}), alias4=helpers.helperMissing, alias5="function";

  return "    <section class=\"zion-stylist-item-container "
    + alias2(alias1(((stack1 = (depths[1] != null ? depths[1].config : depths[1])) != null ? stack1.customCss : stack1), depth0))
    + " "
    + alias2(alias1(((stack1 = (depths[1] != null ? depths[1].config : depths[1])) != null ? stack1.grid : stack1), depth0))
    + " col-sm-6 col-xs-12\">\n        <a class=\"content\" href=\""
    + alias2(((helper = (helper = helpers.stylistUrl || (depth0 != null ? depth0.stylistUrl : depth0)) != null ? helper : alias4),(typeof helper === alias5 ? helper.call(alias3,{"name":"stylistUrl","hash":{},"data":data}) : helper)))
    + "\">\n            <div class=\"avatar col-sm-12\">\n"
    + ((stack1 = helpers["if"].call(alias3,(depth0 != null ? depth0.avatar : depth0),{"name":"if","hash":{},"fn":container.program(2, data, 0, blockParams, depths),"inverse":container.program(4, data, 0, blockParams, depths),"data":data})) != null ? stack1 : "")
    + "            </div>\n            <div class=\"title col-sm-12\">\n                <h3 class=\"username\" title=\""
    + alias2(((helper = (helper = helpers.displayName || (depth0 != null ? depth0.displayName : depth0)) != null ? helper : alias4),(typeof helper === alias5 ? helper.call(alias3,{"name":"displayName","hash":{},"data":data}) : helper)))
    + "\">"
    + alias2(((helper = (helper = helpers.displayName || (depth0 != null ? depth0.displayName : depth0)) != null ? helper : alias4),(typeof helper === alias5 ? helper.call(alias3,{"name":"displayName","hash":{},"data":data}) : helper)))
    + "</h3>\n            </div>\n"
    + ((stack1 = helpers["if"].call(alias3,((stack1 = (depths[1] != null ? depths[1].config : depths[1])) != null ? stack1.showRating : stack1),{"name":"if","hash":{},"fn":container.program(6, data, 0, blockParams, depths),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "            <div class=\"role col-sm-12 no-padding\">\n"
    + ((stack1 = helpers["if"].call(alias3,(depth0 != null ? depth0.roles : depth0),{"name":"if","hash":{},"fn":container.program(8, data, 0, blockParams, depths),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "            </div>\n            <div class=\"location col-sm-12\">\n"
    + ((stack1 = helpers["if"].call(alias3,(depth0 != null ? depth0.location : depth0),{"name":"if","hash":{},"fn":container.program(14, data, 0, blockParams, depths),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "            </div>\n"
    + ((stack1 = helpers["if"].call(alias3,((stack1 = (depths[1] != null ? depths[1].config : depths[1])) != null ? stack1.showSummary : stack1),{"name":"if","hash":{},"fn":container.program(16, data, 0, blockParams, depths),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "            <div class=\"look col-sm-12\">\n                    <span>\n                        LOOKS ("
    + alias2(((helper = (helper = helpers.feedCount || (depth0 != null ? depth0.feedCount : depth0)) != null ? helper : alias4),(typeof helper === alias5 ? helper.call(alias3,{"name":"feedCount","hash":{},"data":data}) : helper)))
    + ")\n                    </span>\n            </div>\n        </a>\n    </section>\n";
},"2":function(container,depth0,helpers,partials,data) {
    var helper;

  return "                    <img class=\"zion-circle-avatar-lg\" src=\""
    + container.escapeExpression(((helper = (helper = helpers.avatar || (depth0 != null ? depth0.avatar : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"avatar","hash":{},"data":data}) : helper)))
    + "\" alt=\"avatar\" />\n";
},"4":function(container,depth0,helpers,partials,data) {
    return "                    <img class=\"zion-circle-avatar-lg\"  src=\"https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/avatar.png\" alt=\"avatar\" />\n";
},"6":function(container,depth0,helpers,partials,data) {
    return "                <div class=\"rate col-sm-12\">\n                    <span class=\"fa fa-lg fa-star active\" data-rating=\"1\"></span>\n                    <span class=\"fa fa-lg fa-star active\" data-rating=\"2\"></span>\n                    <span class=\"fa fa-lg fa-star active\" data-rating=\"3\"></span>\n                    <span class=\"fa fa-lg fa-star active\" data-rating=\"4\"></span>\n                    <span class=\"fa fa-lg fa-star\" data-rating=\"5\"></span>\n                </div>\n";
},"8":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers["if"].call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.approvedTrendsetter : depth0),{"name":"if","hash":{},"fn":container.program(9, data, 0),"inverse":container.program(12, data, 0),"data":data})) != null ? stack1 : "");
},"9":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.roles : depth0),{"name":"each","hash":{},"fn":container.program(10, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"10":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                            "
    + ((stack1 = (helpers.listUserRoles || (depth0 && depth0.listUserRoles) || helpers.helperMissing).call(depth0 != null ? depth0 : (container.nullContext || {}),depth0,{"name":"listUserRoles","hash":{},"data":data})) != null ? stack1 : "")
    + "\n";
},"12":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                        "
    + ((stack1 = (helpers.listUserRoles || (depth0 && depth0.listUserRoles) || helpers.helperMissing).call(depth0 != null ? depth0 : (container.nullContext || {}),"DEFAULT",{"name":"listUserRoles","hash":{},"data":data})) != null ? stack1 : "")
    + "\n";
},"14":function(container,depth0,helpers,partials,data) {
    var helper;

  return "                    <i class=\"fa fa-lg fa-map-marker\" aria-hidden=\"true\"></i>\n                    <span>"
    + container.escapeExpression(((helper = (helper = helpers.location || (depth0 != null ? depth0.location : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"location","hash":{},"data":data}) : helper)))
    + "</span>\n";
},"16":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                <p class=\"summary col-sm-12\">\n"
    + ((stack1 = helpers["if"].call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.introSummary : depth0),{"name":"if","hash":{},"fn":container.program(17, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                </p>\n";
},"17":function(container,depth0,helpers,partials,data) {
    var helper;

  return "                        "
    + container.escapeExpression(((helper = (helper = helpers.introSummary || (depth0 != null ? depth0.introSummary : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"introSummary","hash":{},"data":data}) : helper)))
    + "\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data,blockParams,depths) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.users : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0, blockParams, depths),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true,"useDepths":true});
})();