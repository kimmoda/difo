(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['singleTrendsetterSummary'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                    "
    + ((stack1 = (helpers.listUserRoles || (depth0 && depth0.listUserRoles) || helpers.helperMissing).call(depth0 != null ? depth0 : (container.nullContext || {}),depth0,{"name":"listUserRoles","hash":{},"data":data})) != null ? stack1 : "")
    + "\n";
},"3":function(container,depth0,helpers,partials,data) {
    var helper;

  return "                        <i class=\"fa fa-lg fa-map-marker\" aria-hidden=\"true\"></i>\n                        <span>"
    + container.escapeExpression(((helper = (helper = helpers.location || (depth0 != null ? depth0.location : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"location","hash":{},"data":data}) : helper)))
    + "</span>\n";
},"5":function(container,depth0,helpers,partials,data) {
    var helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "                    <a href=\"mailto:"
    + alias4(((helper = (helper = helpers.email || (depth0 != null ? depth0.email : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"email","hash":{},"data":data}) : helper)))
    + "\">"
    + alias4(((helper = (helper = helpers.email || (depth0 != null ? depth0.email : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"email","hash":{},"data":data}) : helper)))
    + "</a>\n";
},"7":function(container,depth0,helpers,partials,data) {
    var helper;

  return "                    <a class=\"link\" href=\""
    + container.escapeExpression(((helper = (helper = helpers.facebook || (depth0 != null ? depth0.facebook : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"facebook","hash":{},"data":data}) : helper)))
    + "\" target=\"_blank\">\n                        <i class=\"fa fa-2x fa-facebook-square\" aria-hidden=\"true\"></i>\n                    </a>\n";
},"9":function(container,depth0,helpers,partials,data) {
    var helper;

  return "                    <a class=\"link\" href=\""
    + container.escapeExpression(((helper = (helper = helpers.youtube || (depth0 != null ? depth0.youtube : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"youtube","hash":{},"data":data}) : helper)))
    + "\" target=\"_blank\">\n                        <i class=\"fa fa-2x fa-youtube-square\" aria-hidden=\"true\"></i>\n                    </a>\n";
},"11":function(container,depth0,helpers,partials,data) {
    var helper;

  return "                    <a class=\"link\" href=\""
    + container.escapeExpression(((helper = (helper = helpers.instagram || (depth0 != null ? depth0.instagram : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"instagram","hash":{},"data":data}) : helper)))
    + "\" target=\"_blank\">\n                        <i class=\"fa fa-2x fa-instagram\" aria-hidden=\"true\"></i>\n                    </a>\n";
},"13":function(container,depth0,helpers,partials,data) {
    var helper;

  return "                    <a class=\"link\" href=\""
    + container.escapeExpression(((helper = (helper = helpers.website || (depth0 != null ? depth0.website : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"website","hash":{},"data":data}) : helper)))
    + "\" target=\"_blank\">\n                        <i class=\"fa fa-2x fa-globe\" aria-hidden=\"true\"></i>\n                    </a>\n";
},"15":function(container,depth0,helpers,partials,data) {
    var helper;

  return "                "
    + container.escapeExpression(((helper = (helper = helpers.introduce || (depth0 != null ? depth0.introduce : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"introduce","hash":{},"data":data}) : helper)))
    + "\n";
},"17":function(container,depth0,helpers,partials,data) {
    var helper;

  return "                    <i class=\"fa fa-map-marker\" aria-hidden=\"true\"></i>\n                    <span class=\"content\">"
    + container.escapeExpression(((helper = (helper = helpers.location || (depth0 != null ? depth0.location : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"location","hash":{},"data":data}) : helper)))
    + "</span>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<div class=\"zion-trendsetter-container row\">\n    <section class=\"col-xs-12 row\">\n        <section class=\"col-sm-3\">\n            <img class=\"img-circle image-avatar img-responsive\" src=\""
    + alias4(((helper = (helper = helpers.avatar || (depth0 != null ? depth0.avatar : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"avatar","hash":{},"data":data}) : helper)))
    + "\" alt=\"avatar\"/>\n        </section>\n        <section class=\"col-sm-9 user-summary-container\">\n            <h3 class=\"name\" title=\""
    + alias4(((helper = (helper = helpers.displayName || (depth0 != null ? depth0.displayName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"displayName","hash":{},"data":data}) : helper)))
    + "\">\n                "
    + alias4(((helper = (helper = helpers.displayName || (depth0 != null ? depth0.displayName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"displayName","hash":{},"data":data}) : helper)))
    + "\n            </h3>\n            <div class=\"role\">\n"
    + ((stack1 = helpers.each.call(alias1,(depth0 != null ? depth0.userRoles : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "            </div>\n            <div class=\"status hidden-xs\">\n                <div class=\"feed\">\n                    <span id=\"feed-count\" class=\"number\">"
    + alias4(((helper = (helper = helpers.feedCount || (depth0 != null ? depth0.feedCount : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"feedCount","hash":{},"data":data}) : helper)))
    + "</span>\n                    <span class=\"content\">Looks</span>\n                </div>\n                <div class=\"like\">\n                    <span id=\"fans-count\" class=\"number\">"
    + alias4(((helper = (helper = helpers.fansCount || (depth0 != null ? depth0.fansCount : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"fansCount","hash":{},"data":data}) : helper)))
    + "</span>\n                    <span class=\"content like-content\"></span>\n                </div>\n                <div class=\"location\">\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.location : depth0),{"name":"if","hash":{},"fn":container.program(3, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                </div>\n            </div>\n            <div class=\"email\">\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.email : depth0),{"name":"if","hash":{},"fn":container.program(5, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "            </div>\n            <div class=\"social-link\">\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.facebook : depth0),{"name":"if","hash":{},"fn":container.program(7, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.youtube : depth0),{"name":"if","hash":{},"fn":container.program(9, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.instagram : depth0),{"name":"if","hash":{},"fn":container.program(11, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.website : depth0),{"name":"if","hash":{},"fn":container.program(13, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "            </div>\n        </section>\n    </section>\n    <section class=\"col-xs-12 mobile-introduce-container\">\n        <div class=\"introduce\">\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.introduce : depth0),{"name":"if","hash":{},"fn":container.program(15, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "        </div>\n    </section>\n\n    <section class=\"col-xs-12 visible-xs mobile-status-container\">\n        <div class=\"mobile-status\">\n            <div class=\"feed\">\n                <span id=\"feed-count\" class=\"number\">"
    + alias4(((helper = (helper = helpers.feedCount || (depth0 != null ? depth0.feedCount : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"feedCount","hash":{},"data":data}) : helper)))
    + "</span>\n                <span class=\"content\">Looks</span>\n            </div>\n            <div class=\"like\">\n                <span id=\"fans-count\" class=\"number\">"
    + alias4(((helper = (helper = helpers.fansCount || (depth0 != null ? depth0.fansCount : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"fansCount","hash":{},"data":data}) : helper)))
    + "</span>\n                <span class=\"content like-content\"></span>\n            </div>\n            <div class=\"location\">\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.location : depth0),{"name":"if","hash":{},"fn":container.program(17, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "            </div>\n        </div>\n    </section>\n</div>\n";
},"useData":true});
})();