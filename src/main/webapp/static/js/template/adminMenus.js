(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['adminMenus'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "\n                <li>\n                    "
    + ((stack1 = (helpers.listUserRoles || (depth0 && depth0.listUserRoles) || helpers.helperMissing).call(depth0 != null ? depth0 : (container.nullContext || {}),depth0,{"name":"listUserRoles","hash":{},"data":data})) != null ? stack1 : "")
    + "\n                </li>\n";
},"3":function(container,depth0,helpers,partials,data) {
    var alias1=container.lambda, alias2=container.escapeExpression;

  return "                <li>\n                    <a class=\"btn btn-link\" href=\""
    + alias2(alias1((depth0 != null ? depth0.url : depth0), depth0))
    + "\">\n                        <div>"
    + alias2(alias1((depth0 != null ? depth0.label : depth0), depth0))
    + "</div>\n                    </a>\n                </li>\n";
},"5":function(container,depth0,helpers,partials,data) {
    return "            <a class=\"btn btn-default apply-button\">\n                Become an Influencer\n            </a>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=container.lambda, alias2=container.escapeExpression, alias3=depth0 != null ? depth0 : (container.nullContext || {});

  return "<div class=\"admin-side-menu\">\n    <div class=\"user-information-container\">\n        <div class=\"side-avatar-container\">\n            <img class=\"zion-circle-avatar-lg\" src=\""
    + alias2(alias1(((stack1 = ((stack1 = (depth0 != null ? depth0.userInfo : depth0)) != null ? stack1.person : stack1)) != null ? stack1.avatar : stack1), depth0))
    + "\" alt=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.userInfo : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\">\n        </div>\n\n        <div class=\"side-name\">\n        <span>\n            "
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.userInfo : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\n        </span>\n        </div>\n        <div class=\"user-roles-container\">\n            <ul>"
    + ((stack1 = helpers.each.call(alias3,((stack1 = (depth0 != null ? depth0.userInfo : depth0)) != null ? stack1.userRoles : stack1),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "            </ul>\n        </div>\n\n    </div>\n    <div class=\"favourite-container\">\n        <ul>\n"
    + ((stack1 = helpers.each.call(alias3,(depth0 != null ? depth0.authSideMenus : depth0),{"name":"each","hash":{},"fn":container.program(3, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "        </ul>\n    </div>\n    <div class=\"side-footer-container\">\n"
    + ((stack1 = helpers.unless.call(alias3,((stack1 = (depth0 != null ? depth0.userInfo : depth0)) != null ? stack1.approvedTrendsetter : stack1),{"name":"unless","hash":{},"fn":container.program(5, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "    </div>\n    <div class=\"logout-button-container\">\n        <a class=\"btn btn-default logout-button\">\n            Log out\n        </a>\n    </div>\n</div>";
},"useData":true});
})();